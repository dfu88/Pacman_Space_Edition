package view;

import controller.LevelController;//>??

import model.Map;
import model.Level;

//Scene and layout
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;

import javafx.scene.shape.*;

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
	private AnchorPane pane;
	
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
		pane = root;
		
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
		
		

				
				
				
				//Add the nodes to the scene
				//pane.getChildren().add(title);
	}
	public void updateMap(Level currentLevel) {
		
		pane.getChildren().clear();//not sure if needed
		
		double tileWidth = 45;
		double tileHeight = 45;
		double mapStartY = (980-tileHeight*21)*0.5; //(WindowH - MapH)/2 (centers it)
		double mapStartX = (1440 - tileWidth*21)*0.5; //WIndowW - MapW)/2
		
		for (int row = 0; row < 21; row++) {
			for (int col = 0; col < 21; col++) {
				int currentElement = currentLevel.currentMap.getData(row, col);
				if (currentElement == 1) {
					Rectangle wall = new Rectangle(mapStartX+tileWidth*col, mapStartY+tileHeight*row, tileWidth, tileHeight);
					wall.setFill(Color.INDIANRED); //fill
					wall.setStroke(Color.INDIANRED);//storke
					pane.getChildren().add(wall);
				} else if (currentElement == 2) {
					Circle pellet = new Circle(mapStartX+tileWidth*col+tileWidth*0.5, mapStartY+tileHeight*row+tileHeight*0.5, tileWidth*0.125);
					pellet.setFill(Color.LEMONCHIFFON); //we can have a class theme to have a combination of colours to use
					pane.getChildren().add(pellet);
				} else if (currentElement == 3) {
					Circle powerup = new Circle(mapStartX+tileWidth*col+tileWidth*0.5, mapStartY+tileHeight*row+tileHeight*0.5, tileWidth*0.35);
					powerup.setFill(Color.CRIMSON);
					pane.getChildren().add(powerup);
				}
			}
		}
		
		Text lives = new Text();
		lives.setText("Lives");
		lives.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,50));
		lives.setX((mapStartX-lives.getLayoutBounds().getWidth())*0.5);
		lives.setY(100.0);
		pane.getChildren().add(lives);
		
		
		Text timeLabel = new Text();
		timeLabel.setText("Time:");
		timeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,50));
		timeLabel.setX(1440-mapStartX + ((mapStartX-timeLabel.getLayoutBounds().getWidth())*0.5));
		timeLabel.setY(100.0);
		pane.getChildren().add(timeLabel);
		
		Text time = new Text();
		time.setText(Integer.toString(currentLevel.timeRemaining));
		time.setFont(Font.font("Comic Sans MS",50));
		time.setX(1440-mapStartX + ((mapStartX-time.getLayoutBounds().getWidth())*0.5));
		time.setY(100+timeLabel.getLayoutBounds().getHeight()+10);
		pane.getChildren().add(time);
		
	}
	

}
