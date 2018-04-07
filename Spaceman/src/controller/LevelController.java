package controller;

import model.Level;
import model.Map;

import view.LevelVisuals;
import view.Spaceman;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class LevelController {
	public InterfaceController interfaceCtrl;
	public LevelVisuals currentView;
	public Scene levelScene;
	public Level currentLevel;
	public Spaceman spaceman;
	
	public LevelController(InterfaceController controller) {
		interfaceCtrl = controller;
	}
	
	
	public void init() {
		LevelVisuals visual = new LevelVisuals();
		visual.createLevel();
		levelScene = visual.returnScene();
		currentView = visual;
		Level lvl = new Level();
		lvl.makeMaps();
		currentLevel = lvl;
		spaceman = new Spaceman(this, 10, 15);
		levelScene.setOnKeyPressed(new EventHandler <KeyEvent> () {
			public void handle(KeyEvent input) {
				if (input.getCode() == KeyCode.LEFT) {
					currentLevel.spaceman.setKeyInput(0);
				} else if(input.getCode() == KeyCode.RIGHT) {
					currentLevel.spaceman.setKeyInput(2);
				} else if(input.getCode() == KeyCode.UP) {
					currentLevel.spaceman.setKeyInput(1);
				} else if(input.getCode() == KeyCode.DOWN) {
					currentLevel.spaceman.setKeyInput(3);
				}
			}
		});
		//Map currentMap = new Map(1);
	}
	
	
	
	public void setLevel(int type){
		//.. set model char etc
		System.out.println(type);
		currentLevel.setMap(type, spaceman);
		//currentView.pane.getChildren().clear();
		currentView.updateMap(currentLevel); //changed to pass in level instead
		//..update visuals
		
		//interfaceCtrl.game.changeScene(levelScene);
		interfaceCtrl.game.changeScene(currentView.returnScene());
	}
	
	public int checkMap(int x, int y) {
//		currentLevel.currentMap
		return currentLevel.currentMap.getData(y, x);
	}
}
