package view;

import java.util.ArrayList;

import controller.LevelController;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;

//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;


public class LevelVisuals {
	
	private double SCENE_WIDTH = 1440;
	private double SCENE_HEIGHT = 900;
	
	//NOTE MAKE CONST FOR NOW UNLESS TILE SIZE CHANGES BASED ON MAPARRAY SIZE
	double tileWidth = 40;
	double tileHeight = 40;

	//NOTE: CHANGE MAGIC NUMBER (21) TO var or constant
	double mapOffsetY = (SCENE_HEIGHT-tileHeight*21)*0.5; //(WindowH - MapH)/2 (centers it) = 30
	double mapOffsetX = (SCENE_WIDTH - tileWidth*21)*0.5; //WIndowW - MapW)/2 = 300
	
	private LevelController controller;
	private Scene scene;
	private Group root;
	private ArrayList<Pellet> pelletsRendered;
	private ArrayList<PowerUp> powerUpsRendered;
	private Text score;
	public Spaceman spaceman;
	
	public LevelVisuals (LevelController controller) {
		this.controller = controller;
		pelletsRendered = new ArrayList<Pellet>();
		powerUpsRendered = new ArrayList<PowerUp>();
		
		//Setup Scene for game visuals
		root = new Group(); 
		scene = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT);
		scene.setFill(Color.LIGHTBLUE);
	}

	public Scene returnScene() {
		return scene;
	}
	
	public void generateMap() {
		
//		//NOTE MAKE CONST FOR NOW UNLESS TILE SIZE CHANGES BASED ON MAPARRAY SIZE
//		double tileWidth = 40;
//		double tileHeight = 40;
//		
//		//NOTE: CHANGE MAGIC NUMBER (21) TO var or constant
//		double mapOffsetY = (SCENE_HEIGHT-tileHeight*21)*0.5; //(WindowH - MapH)/2 (centers it) = 30
//		double mapOffsetX = (SCENE_WIDTH - tileWidth*21)*0.5; //WIndowW - MapW)/2 = 300
		
		pelletsRendered.clear();
		powerUpsRendered.clear();
		root.getChildren().clear();
		
		
		int startX = 0, startY = 0, tunnelXLeft = 0, tunnelXRight = 0;
		
		for (int row = 0; row < 21; row++) {
			for (int col = 0; col < 21; col++) {
				System.out.print(controller.getLevel().getCurrentMap().getData(row, col));
				int currentElement = controller.getLevel().getCurrentMap().getData(row, col);
				//Walls
				if (currentElement == 1) {
					Rectangle wall = new Rectangle(mapOffsetX+tileWidth*col, mapOffsetY+tileHeight*row, tileWidth, tileHeight);
					wall.setFill(Color.INDIANRED); //fill
					wall.setStroke(Color.INDIANRED);//outline
					root.getChildren().add(wall);	
				} 
				//Pellets
				else if (currentElement == 2) {
					Pellet pellet = new Pellet(mapOffsetX+tileWidth*(col+0.5), mapOffsetY+tileHeight*(0.5+row), tileWidth*0.125);
					//we can have a class 'Theme' to have a combination of preset colours to use
					pellet.returnPellet().setFill(Color.BLUEVIOLET); 
					root.getChildren().add(pellet.returnPellet());
					pelletsRendered.add(pellet);
				} 
				//Magic Pellet	
				else if (currentElement == 3) {
//					Circle powerup = new Circle(mapOffsetX+tileWidth*col+tileWidth*0.5, mapOffsetY+tileHeight*row+tileHeight*0.5, tileWidth*0.35);
//					powerup.setFill(Color.CRIMSON);
//					root.getChildren().add(powerup);
					PowerUp powerUp = new PowerUp(mapOffsetX+tileWidth*(col+0.5), mapOffsetY+tileHeight*(0.5+row), tileWidth*0.325);
					//we can have a class 'Theme' to have a combination of preset colours to use
					powerUp.returnPowerUp().setFill(Color.CRIMSON); 
					root.getChildren().add(powerUp.returnPowerUp());
					powerUpsRendered.add(powerUp);
				}
				//Tunnel Wall x position
				else if (currentElement == 5) {
					if (col < 2) {
						tunnelXLeft = col;	
					} else if (col > 18) {
						tunnelXRight = col;
					}
				} 
				//Set Spaceman Start x and y position
				else if (currentElement == 7) {
					startX = col;
					startY = row;
				}
			}
			System.out.println("");
		}
		
		//Add Spaceman after map added to scene
		spaceman = new Spaceman(controller, startX, startY);
		root.getChildren().add(spaceman);
		spaceman.start(); // NOTE: Change start spaceman animation after countdown
		
		//Add tunnel wall cover after Spaceman added to scene - CHANGE MAGIC NUMBERS
		Rectangle tunnelWallLeft = new Rectangle(mapOffsetX+tileWidth*tunnelXLeft, mapOffsetY+tileHeight*0, tileWidth, tileHeight*20);
		tunnelWallLeft.setFill(Color.LIGHTBLUE); //fill
		tunnelWallLeft.setStroke(Color.LIGHTBLUE);//outline
		root.getChildren().add(tunnelWallLeft);
		Rectangle tunnelWallRight = new Rectangle(mapOffsetX+tileWidth*tunnelXRight, mapOffsetY+tileHeight*0, tileWidth, tileHeight*20);
		tunnelWallRight.setFill(Color.LIGHTBLUE); //fill
		tunnelWallRight.setStroke(Color.LIGHTBLUE);//outline
		root.getChildren().add(tunnelWallRight);
		
		
		
		//add other level objects
		Text lives = new Text();
		lives.setText("Lives");
		lives.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,50));
		lives.setX((mapOffsetX-lives.getLayoutBounds().getWidth())*0.5);
		lives.setY(100.0);
		root.getChildren().add(lives);
		
		
		Text timeLabel = new Text();
		timeLabel.setText("Time:");
		timeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,50));
		timeLabel.setX(SCENE_WIDTH-mapOffsetX + ((mapOffsetX-timeLabel.getLayoutBounds().getWidth())*0.5));
		timeLabel.setY(100.0);
		root.getChildren().add(timeLabel);
		
		Text time = new Text();
		time.setText(Integer.toString(controller.getLevel().timeRemaining));
		time.setFont(Font.font("Comic Sans MS",50));
		time.setX(SCENE_WIDTH-mapOffsetX + ((mapOffsetX-time.getLayoutBounds().getWidth())*0.5));
		time.setY(100+timeLabel.getLayoutBounds().getHeight()+10);
		root.getChildren().add(time);
		
		Text score = new Text();
		//score.setText(Integer.toString(controller.getLevel().getScore()));
		score.setText("0");
		score.setFont(Font.font("Comic Sans MS",50));
		//score.setX(SCENE_WIDTH-mapOffsetX + ((mapOffsetX-score.getLayoutBounds().getWidth())*0.5));
		//score.setY(100+scoreLabel.getLayoutBounds().getHeight()+10);
		score.setX((mapOffsetX-lives.getLayoutBounds().getWidth())*0.5);
		score.setY(500);
		root.getChildren().add(score);
		this.score = score;
	}
	
	public void hideCorrespondingPellet(int charX, int charY) {
		for (int index = 0; index < pelletsRendered.size(); index++) {
			if ((pelletsRendered.get(index).getGraphicalX() - mapOffsetX)/tileWidth -0.5 == charX) {
				if ((pelletsRendered.get(index).getGraphicalY() - mapOffsetY)/tileHeight -0.5 == charY) {
					pelletsRendered.get(index).returnPellet().setVisible(false);
				}
			}
		}
	}
	//change for powerup after making powerup class
	public void hideCorrespondingPowerUp(int charX, int charY) {
		for (int index = 0; index < powerUpsRendered.size(); index++) {
			if ((powerUpsRendered.get(index).getGraphicalX() - mapOffsetX)/tileWidth -0.5 == charX) {
				if ((powerUpsRendered.get(index).getGraphicalY() - mapOffsetY)/tileHeight -0.5 == charY) {
					powerUpsRendered.get(index).returnPowerUp().setVisible(false);
				}
			}
		}
	}
	
	public void updateScore(int score) {
		this.score.setText(Integer.toString(controller.getLevel().getScore()));
	}

}
