package pe.edu.upeu.syscine.components;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TableViewHelper<T> {

    public void addColumnsInOrderWithSize(TableView<T> tableView, LinkedHashMap<String, ColumnInfo> columns, Consumer<T> updateAction, Consumer<T> deleteAction) {
        addColumnsInOrderWithSizeExtra(tableView, columns, updateAction, deleteAction, null);
    }

    public void addColumnsInOrderWithSizeExtra(TableView<T> tableView, LinkedHashMap<String, ColumnInfo> columns,
                                                Consumer<T> updateAction, Consumer<T> deleteAction,
                                                LinkedHashMap<String, Consumer<T>> extraActions) {
        for (Map.Entry<String, ColumnInfo> entry : columns.entrySet()) {
            TableColumn<T, Object> column = new TableColumn<>(entry.getKey());
            String field = entry.getValue().getField();

            if (field.contains(".")) {

                column.setCellValueFactory(cellData -> {

                    T item = cellData.getValue();
                    String[] fieldPath = field.split("\\.");

                    try {
                        Object value = item.getClass().getMethod("get" + capitalize(fieldPath[0])).invoke(item);
                        if (value != null) {
                            Object nestedValue = value.getClass().getMethod("get" + capitalize(fieldPath[1])).invoke(value);
                            return new SimpleObjectProperty<>(nestedValue != null ? nestedValue : "N/A");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return new SimpleObjectProperty("N/A");
                });
            } else {
                column.setCellValueFactory(new PropertyValueFactory<>(field));
            }

            if (entry.getValue().getWidth() != null) {
                column.setPrefWidth(entry.getValue().getWidth());
            }

            tableView.getColumns().add(column);
        }

        addActionColumn(tableView, updateAction, deleteAction, extraActions);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    private void addActionColumn(TableView<T> tableView, Consumer<T> updateAction, Consumer<T> deleteAction,
                                  LinkedHashMap<String, Consumer<T>> extraActions) {
        TableColumn<T, Void> actionColumn = new TableColumn<>("Acciones");

        Callback<TableColumn<T, Void>, TableCell<T, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<T, Void> call(final TableColumn<T, Void> param) {
                final TableCell<T, Void> cell = new TableCell<>() {

                    Image updateImage = new Image(getClass().getResource("/img/document-edit-icon.png").toExternalForm());
                    Image deleteImage = new Image(getClass().getResource("/img/del-icon.png").toExternalForm());

                    ImageView updateImageView = new ImageView(updateImage);
                    ImageView deleteImageView = new ImageView(deleteImage);

                    private final Button btnUpdate = new Button("", updateImageView);
                    private final Button btnDelete = new Button("", deleteImageView);

                    {
                        updateImageView.setFitWidth(18);
                        updateImageView.setFitHeight(18);
                        deleteImageView.setFitWidth(18);
                        deleteImageView.setFitHeight(18);

                        btnUpdate.setOnAction(event -> {
                            T data = getTableView().getItems().get(getIndex());
                            updateAction.accept(data);
                        });

                        btnDelete.setOnAction(event -> {
                            T data = getTableView().getItems().get(getIndex());
                            deleteAction.accept(data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox buttons = new HBox(btnUpdate, btnDelete);
                            buttons.setSpacing(8);
                            if (extraActions != null) {
                                for (Map.Entry<String, Consumer<T>> extra : extraActions.entrySet()) {
                                    Button btnExtra = new Button(extra.getKey());
                                    btnExtra.getStyleClass().add("btn-extra-accion");
                                    btnExtra.setOnAction(event -> {
                                        T data = getTableView().getItems().get(getIndex());
                                        extra.getValue().accept(data);
                                    });
                                    buttons.getChildren().add(btnExtra);
                                }
                            }
                            setGraphic(buttons);
                        }
                    }
                };
                return cell;
            }
        };

        actionColumn.setCellFactory(cellFactory);
        actionColumn.setPrefWidth(extraActions == null ? 100 : 260);
        tableView.getColumns().add(actionColumn);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
