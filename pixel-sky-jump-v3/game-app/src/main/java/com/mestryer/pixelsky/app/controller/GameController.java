package com.mestryer.pixelsky.app.controller;
import com.mestryer.pixelsky.app.engine.GameEngine;
import com.mestryer.pixelsky.core.model.Level;
import com.mestryer.pixelsky.core.service.LevelCrudService;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
public class GameController {
 private final GameEngine engine;
 private final LevelCrudService levels;
 private final Stage stage;
 private int currentLevel=1;
 public GameController(GameEngine engine,LevelCrudService levels,Stage stage){
  this.engine=engine;
  this.levels=levels;
  this.stage=stage;levels.loadDefaults();loadLevel(1);
 }
 public void install(Scene scene){scene.setOnKeyPressed(e->{scene.getProperties().put("key:"+e.getCode(),true);
  switch(e.getCode()){
   case ENTER->{
    if(!engine.isStarted())engine.start();
   }
   case R->{
    if(engine.isGameOver())loadLevel(currentLevel);
   }case N->{
    if(engine.isComplete())nextLevel();
   }case F9->compactWindow();
   case F10->toggleMaximized();
   case F11->stage.setFullScreen(!stage.isFullScreen());
   default->{}}});scene.setOnKeyReleased(e->scene.getProperties().put("key:"+e.getCode(),false));
 }
 public void update(double dt,Scene s){
  engine.update(dt,down(s,KeyCode.LEFT)||down(s,KeyCode.A),down(s,KeyCode.RIGHT)||down(s,KeyCode.D),down(s,KeyCode.SPACE)||down(s,KeyCode.W)||down(s,KeyCode.UP));}
 private boolean down(Scene s,KeyCode k){
  return Boolean.TRUE.equals(s.getProperties().get("key:"+k));
 }
 public void loadLevel(int n){
  Level l=levels.readAll().stream().filter(x->x.getNumber()==n).findFirst().orElseThrow();currentLevel=n;engine.load(l,n);
 }
 private void nextLevel(){
  loadLevel(currentLevel>=levels.readAll().size()?1:currentLevel+1);
 }
 public void compactWindow(){
  stage.setFullScreen(false);stage.setMaximized(false);stage.setResizable(false);
  stage.setWidth(1280);stage.setHeight(720);stage.centerOnScreen();
 }
 public void toggleMaximized(){
  if(!stage.isFullScreen())stage.setMaximized(!stage.isMaximized());
 }
}
