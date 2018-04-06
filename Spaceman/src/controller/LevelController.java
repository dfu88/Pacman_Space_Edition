package controller;

import model.Level;

import view.LevelVisuals;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class LevelController {

	private InterfaceController interfaceCtrl;
	private LevelVisuals currentView;
	private Level levelModel;

	public LevelController(InterfaceController controller) {
		interfaceCtrl = controller;
		currentView = new LevelVisuals(this);
		levelModel = new Level();

		levelModel.makeMaps();

		currentView.returnScene().setOnKeyPressed(new EventHandler <KeyEvent> () {
			public void handle(KeyEvent input) {
				if (input.getCode() == KeyCode.LEFT) {
					levelModel.spaceman.setKeyInput(0);
				} else if(input.getCode() == KeyCode.RIGHT) {
					levelModel.spaceman.setKeyInput(2);
				} else if(input.getCode() == KeyCode.UP) {
					levelModel.spaceman.setKeyInput(1);
				} else if(input.getCode() == KeyCode.DOWN) {
					levelModel.spaceman.setKeyInput(3);
				}
			}
		});
	}
	
	public Level getLevel() {
		return levelModel;
	}
	public void setLevel(int type){
		levelModel.setMap(type);
		currentView.generateMap();
		interfaceCtrl.getMainApp().changeScene(currentView.returnScene());
	}
}
