package view;

import controller.LevelController;//>??

//Scene and layout
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;

//Components
import javafx.scene.text.*;
import javafx.scene.control.Button;

//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;


//import javafx.scene.shape.*;


public class LevelVisuals {
	
	//private InterfaceController controller;
	
	private double sceneWidth;
	private double sceneHeight;
	private Scene scene;
	
	public Scene testScene;
	
	//private Button button1;
	//private Button button2;
	//public int buttonPressed = -1;
	
	
	public void createLevel() {
		sceneWidth = 1440.0;
		sceneHeight = 990.0;//??
		//this.controller = controller;
		AnchorPane root = new AnchorPane(); //Layout based on coordinate from edge
		root.setStyle("-fx-background-color: null;"); //controls add default css so need to remove
		addComponents(root); //add nodes to the layout (buttons, text etc)
		
		Scene level = new Scene(root,sceneWidth,sceneHeight);
		level.setFill(Color.LIGHTBLUE);
		
		
		scene = level;
		
		//test
		AnchorPane testPane = new AnchorPane();
		testPane.setStyle("-fx-background-color: null;");
		Scene test1 = new Scene(testPane, 1440, 980);
		test1.setFill(Color.LIGHTGREEN);
		testScene = test1;
		
	}
	public Scene returnScene() {
		return scene;
	}
	
	private void addComponents(AnchorPane pane) {
		
		//Setup home screen nodes
		double mapStartY = 100;
		double mapStartX = 100;
		

				
				
				
				//Add the nodes to the scene
				//pane.getChildren().add(title);
	}

}
