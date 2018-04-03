package controller;

import view.GUI;
import javafx.scene.*;

public class InterfaceController {
	
	public MainApp game;
	
	public LevelController lvlCtrl;
	
	public Scene currentScene; //should not be in controller imo
	public Scene test;			// same
	
	public InterfaceController(MainApp instance) {
		game = instance;
	}
	
	public void init() {
		//Initialise instances of classes
		GUI startMenu = new GUI();
		startMenu.createGUI(this);
		currentScene = startMenu.returnScene();
		test = startMenu.testScene;
		//...
		LevelController lvlControl = new LevelController(this);
		lvlControl.init();
		lvlCtrl = lvlControl;
		
		
	}
	
	public void process(int actionNumber) {
		if (actionNumber == 1) {
				//do something
			System.out.println("420");
			//currentScene = test;
			//game.changeScene(currentScene);
			lvlCtrl.setLevel(1);
			
			
		}
	}
}
