package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;

import controller.InterfaceController;

public class MainApp extends Application {
	
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		
		stage = primaryStage;
		
		InterfaceController view = new InterfaceController(this);
		view.init();
		//view.run(); //maybe?? refering to ass3 from last year
		
		//GUI startMenu = new GUI();
		//startMenu.createGUI(view);
		
		primaryStage.setTitle("Pac-Man: Space Edition");
		primaryStage.setResizable(false);
		
		
		//primaryStage.setScene(startMenu.returnScene());
		//primaryStage.setScene(view.currentScene);
		changeScene(view.currentScene);
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void changeScene(Scene scene) {
		stage.setScene(scene);
	}
}
