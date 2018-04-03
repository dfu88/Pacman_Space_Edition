package controller;

import view.GUI;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;

public class GameController extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Pac-Man:Space Edition");
		GUI menu = new GUI();
		menu.createGUI();
		changeScene(primaryStage, menu.returnScene());
		
	}
	//not sure if this method is high coupling
	private void changeScene(Stage primaryStage, Scene scene) {
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
