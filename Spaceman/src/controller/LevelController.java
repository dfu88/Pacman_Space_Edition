package controller;

import model.Level;
import model.Map;

import view.LevelVisuals;

import javafx.scene.*;

public class LevelController {
	public InterfaceController interfaceCtrl;
	public Scene levelScene;
	public Level currentLevel;
	
	public LevelController(InterfaceController controller) {
		interfaceCtrl = controller;
	}
	
	
	public void init() {
		LevelVisuals visual = new LevelVisuals();
		visual.createLevel();
		levelScene = visual.returnScene();
		Level lvl = new Level();
		lvl.makeMaps();
		currentLevel = lvl;
		//Map currentMap = new Map(1);
	}
	
	public void setLevel(int type){
		//.. set model char etc
		currentLevel.setMap(1);
		
		//..update visuals
		interfaceCtrl.game.changeScene(levelScene);
	}
}
