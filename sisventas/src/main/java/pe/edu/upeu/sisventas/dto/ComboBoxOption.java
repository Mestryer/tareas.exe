package pe.edu.upeu.sisventas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComboBoxOption {
    String key;
    String valvue;

    @Override
    public String toString() {
        return "ComboBoxOption{" +
                "valvue='" + valvue + '\'' +
                '}';
    }
}
