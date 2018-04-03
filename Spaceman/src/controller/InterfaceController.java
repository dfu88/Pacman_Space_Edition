package controller;

import view.GUI;
import javafx.scene.*;

public class InterfaceController {
	
	public MainApp game;
	
	public Scene currentScene;
	public Scene test;
	
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
		
		
		
	}
	
	public void process(int actionNumber) {
		if (actionNumber == 1) {
				//do something
			System.out.println("420");
			currentScene = test;
			game.changeScene(currentScene);
			
			
		}
	}
}
