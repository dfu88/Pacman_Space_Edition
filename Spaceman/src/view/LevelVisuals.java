package view;

import java.util.ArrayList;

import controller.LevelController;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
	private Text time;
	private Text message;
	public Spaceman spaceman;
	private Group setting;
	private Group gameView;
	private GaussianBlur blur;
	private DropShadow shadow;
	private ArrayList<ImageView> pauseOptions;
	
	public LevelVisuals (LevelController controller) {
		this.controller = controller;
		pelletsRendered = new ArrayList<Pellet>();
		powerUpsRendered = new ArrayList<PowerUp>();
		pauseOptions = new ArrayList<ImageView>();
		
		blur = new GaussianBlur();
		shadow = new DropShadow(500, Color.YELLOW);
		
		//Setup Scene for game visuals
		root = new Group(); 
		scene = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT);
		scene.setFill(Color.LIGHTBLUE);
		
//		BackgroundImage bg = new BackgroundImage(new Image(getClass().getResourceAsStream("bg/test.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
//		root.setBackground(new Background(bg));
	}

	public Scene returnScene() {
		return scene;
	}
	
	public void generateMap() {
		pelletsRendered.clear();
		powerUpsRendered.clear();
		pauseOptions.clear();
		root.getChildren().clear();
		
		gameView = addGameComponents();
		root.getChildren().add(gameView);
		
		
		
		setting = addPauseMenu();
		setting.setVisible(false);
		root.getChildren().add(setting);
		
				
		//setting.setVisible(false);
	}
	
/*	public void generateMap() {
		
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
		//spaceman.start(); // NOTE: Change start spaceman animation after countdown
		
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
		this.time = time;
		
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
		
		Text message = new Text();
		message.setText("Press Enter to start");
		message.setX((SCENE_WIDTH-message.getLayoutBounds().getWidth())*0.5);
		message.setY((SCENE_HEIGHT-message.getLayoutBounds().getHeight())*0.5);
		root.getChildren().add(message);
		this.message = message;
		
		setting = addPauseMenu();
		root.getChildren().add(setting);
		
		//GaussianBlur blur = new GaussianBlur();
		//root.setEffect(blur);
		
		//setting.setVisible(false);
	}
	
	*/
	
	
	private Group addGameComponents() {
		Group group = new Group();
		
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
					group.getChildren().add(wall);	
				} 
				//Pellets
				else if (currentElement == 2) {
					Pellet pellet = new Pellet(mapOffsetX+tileWidth*(col+0.5), mapOffsetY+tileHeight*(0.5+row), tileWidth*0.125);
					//we can have a class 'Theme' to have a combination of preset colours to use
					pellet.returnPellet().setFill(Color.BLUEVIOLET); 
					group.getChildren().add(pellet.returnPellet());
					pelletsRendered.add(pellet);
				} 
				//Magic Pellet	
				else if (currentElement == 3) {
//					Circle powerup = new Circle(mapOffsetX+tileWidth*col+tileWidth*0.5, mapOffsetY+tileHeight*row+tileHeight*0.5, tileWidth*0.35);
//					powerup.setFill(Color.CRIMSON);
//					group.getChildren().add(powerup);
					PowerUp powerUp = new PowerUp(mapOffsetX+tileWidth*(col+0.5), mapOffsetY+tileHeight*(0.5+row), tileWidth*0.325);
					//we can have a class 'Theme' to have a combination of preset colours to use
					powerUp.returnPowerUp().setFill(Color.CRIMSON); 
					group.getChildren().add(powerUp.returnPowerUp());
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
		group.getChildren().add(spaceman);
		//spaceman.start(); // NOTE: Change start spaceman animation after countdown
		
		//Add tunnel wall cover after Spaceman added to scene - CHANGE MAGIC NUMBERS
		Rectangle tunnelWallLeft = new Rectangle(mapOffsetX+tileWidth*tunnelXLeft, mapOffsetY+tileHeight*0, tileWidth, tileHeight*20);
		tunnelWallLeft.setFill(Color.LIGHTBLUE); //fill
		tunnelWallLeft.setStroke(Color.LIGHTBLUE);//outline
		group.getChildren().add(tunnelWallLeft);
		Rectangle tunnelWallRight = new Rectangle(mapOffsetX+tileWidth*tunnelXRight, mapOffsetY+tileHeight*0, tileWidth, tileHeight*20);
		tunnelWallRight.setFill(Color.LIGHTBLUE); //fill
		tunnelWallRight.setStroke(Color.LIGHTBLUE);//outline
		group.getChildren().add(tunnelWallRight);
		
		
		
		//add other level objects
		Text lives = new Text();
		lives.setText("Lives");
		lives.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,50));
		lives.setX((mapOffsetX-lives.getLayoutBounds().getWidth())*0.5);
		lives.setY(100.0);
		group.getChildren().add(lives);
		
		
		Text timeLabel = new Text();
		timeLabel.setText("Time:");
		timeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,50));
		timeLabel.setX(SCENE_WIDTH-mapOffsetX + ((mapOffsetX-timeLabel.getLayoutBounds().getWidth())*0.5));
		timeLabel.setY(100.0);
		group.getChildren().add(timeLabel);
		
		Text time = new Text();
		time.setText(Integer.toString(controller.getLevel().timeRemaining));
		time.setFont(Font.font("Comic Sans MS",50));
		time.setX(SCENE_WIDTH-mapOffsetX + ((mapOffsetX-time.getLayoutBounds().getWidth())*0.5));
		time.setY(100+timeLabel.getLayoutBounds().getHeight()+10);
		group.getChildren().add(time);
		this.time = time;
		
		Text score = new Text();
		//score.setText(Integer.toString(controller.getLevel().getScore()));
		score.setText("0");
		score.setFont(Font.font("Comic Sans MS",50));
		//score.setX(SCENE_WIDTH-mapOffsetX + ((mapOffsetX-score.getLayoutBounds().getWidth())*0.5));
		//score.setY(100+scoreLabel.getLayoutBounds().getHeight()+10);
		score.setX((mapOffsetX-lives.getLayoutBounds().getWidth())*0.5);
		score.setY(500);
		group.getChildren().add(score);
		this.score = score;
		
		Text message = new Text();
		message.setText("Press Enter to start");
		message.setX((SCENE_WIDTH-message.getLayoutBounds().getWidth())*0.5);
		message.setY((SCENE_HEIGHT-message.getLayoutBounds().getHeight())*0.5);
		group.getChildren().add(message);
		this.message = message;
		
		return group;
	}
	
	private Group addPauseMenu() {
		Group group = new Group();
		
		Rectangle frame = new Rectangle(700,400);
		frame.setFill(Color.BLACK);
		frame.setStroke(Color.WHITE);
		frame.setStrokeWidth(2.0);
		frame.setArcHeight(15);
		frame.setArcWidth(15);
		frame.setX((SCENE_WIDTH-frame.getLayoutBounds().getWidth())*0.5);
		frame.setY((SCENE_HEIGHT-frame.getLayoutBounds().getHeight())*0.5);
		group.getChildren().add(frame);
		
		
		Text label = new Text("Paused");
		label.setFont(Font.font(50));
		label.setFill(Color.WHITE);
		label.setX((SCENE_WIDTH-label.getLayoutBounds().getWidth())*0.5);
		label.setY(frame.getY()+label.getLayoutBounds().getHeight()+10);
		group.getChildren().add(label);
		
		Image resume = new Image(getClass().getResourceAsStream("misc/resume.png"));
		ImageView resumeButton = new ImageView(resume);
		resumeButton.setX(frame.getX()+100);
		resumeButton.setY(frame.getY()+frame.getLayoutBounds().getHeight()-250);
		resumeButton.setEffect(shadow);
		pauseOptions.add(resumeButton);
		
		Image close = new Image(getClass().getResourceAsStream("misc/close2.png"));
		ImageView closeButton = new ImageView(close);
		closeButton.setX(frame.getX()+frame.getLayoutBounds().getWidth()-closeButton.getLayoutBounds().getWidth()-100);
		closeButton.setY(frame.getY()+frame.getLayoutBounds().getHeight()-250);
		pauseOptions.add(closeButton);
		//Image exit = new Image(getClass().getResourceAsStream("misc/exit.png"));
		//closeButton.set
//		closeButton.setScaleX(0.35);
//		closeButton.setScaleY(0.35);
		group.getChildren().add(closeButton);
		group.getChildren().add(resumeButton);
		//System.out.println(closeButton.getLayoutX());
		
		return group;
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
	
	public void updateTime(int time) {
		this.time.setText(Integer.toString(controller.getLevel().timeRemaining));
	}
	
	public void updateMessage(int value) {
		System.out.print("dsdsd");
		System.out.println(value);
		if (value>0) {
			message.setText(Integer.toString(value));
		} else if (value==0) {
			message.setText("START!!");
		} else {
			System.out.println("ggddg");
			message.setVisible(false);
		}
		message.setX((SCENE_WIDTH-message.getLayoutBounds().getWidth())*0.5);
	}

	public void updatePauseScreen(boolean wasPaused) {
		// TODO Auto-generated method stub
		if (wasPaused) {
			gameView.setEffect(blur);
			setting.setVisible(true);
			
		} else {
			gameView.setEffect(null);
			setting.setVisible(false);
			
		}
		
	}
	
	public void cycleOptions(int option) {
		if (option == 0) {
			pauseOptions.get(option).setEffect(shadow);
			pauseOptions.get(option+1).setEffect(null);
		} else if (option == 1) {
			pauseOptions.get(option).setEffect(shadow);
			pauseOptions.get(option-1).setEffect(null);
		}
	}

}
