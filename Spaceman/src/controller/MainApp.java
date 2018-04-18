package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;

import controller.InterfaceController;

public class MainApp extends Application {
	
	private Stage stage;
	
	//Initialises the starting objects for the game
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		
		InterfaceController controller = new InterfaceController(this);
		
		changeScene(controller.getGUI().returnScene());
		primaryStage.setTitle("Pac-Man: Space Edition");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/*	This function will change the scene of the stage to the scene passed in
	Inputs:	Scene scene: the scene you want the stage to display	*/
	public void changeScene(Scene scene) {
		stage.setScene(scene);
	}
}
