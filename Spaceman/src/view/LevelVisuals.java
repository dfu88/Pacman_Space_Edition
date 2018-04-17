package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import controller.LevelController;

import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
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
	
	public Group root;
	private Group pauseMenu;
	private Group exitPopUp;
	private Group gameView;
	public Group countDownView;
	public Group gameOverPopUp;
	private Group timeComponent;
	
	private ImageView bgView;
	
	private GaussianBlur blur;
	private DropShadow shadow;
	
	private Clip countdown;
	private AudioClip cycle;
	private AudioClip win1;
	
	private AudioClip death;
	private AudioClip gOver;
	
	private Text score;
	private Text time;
	private Text lives;
	private Text message;
	
	public Spaceman spaceman;
	
	public Alien red;
	public Alien pink;
	public Alien blue;
	public Alien orange;
	public Alien[] aliens;
	
	private Pellet currentPellet;

	private ArrayList<Pellet> pelletsRendered;
	private ArrayList<Integer> despawnIndex;

	private ArrayList<PowerUp> powerUpsRendered;
	private ArrayList<ImageView> exitOptions;
	
	private ArrayList<Image> powerUpImgs;
	
	private int pelletsCollected = 0;
	
	public LevelVisuals (LevelController controller) {
		this.controller = controller;
		
		exitOptions = new ArrayList<ImageView>();
		
		pelletsRendered = new ArrayList<Pellet>();
		powerUpsRendered = new ArrayList<PowerUp>();
		
		despawnIndex = new ArrayList<Integer>();
		
		
		
		powerUpImgs = setUpPowerImages();
		System.out.print(powerUpImgs.size());
		
		blur = new GaussianBlur();
		shadow = new DropShadow(500, Color.YELLOW);

		//Countdown must be Clip instead of AudioClip since I need to pause it
		URL url = this.getClass().getResource("sound/countdown.wav");
		AudioInputStream sound;
		try {
			sound = AudioSystem.getAudioInputStream(url);
			Clip soundClip = AudioSystem.getClip();
			soundClip.open(sound);
			countdown = soundClip;
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		url = this.getClass().getResource("sound/sound1.wav");
		cycle = new AudioClip(url.toString());
		
		url = this.getClass().getResource("sound/win1.wav");
		win1 = new AudioClip(url.toString());
		
		url = this.getClass().getResource("sound/gover.wav"); //maybe move sounds to controller
		gOver = new AudioClip(url.toString());
		
		url = this.getClass().getResource("sound/death.wav");
		death = new AudioClip(url.toString());

		//Setup Scene for game visuals
		root = new Group(); 
		scene = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT);
		scene.setFill(Color.LIGHTBLUE);

		setUpKeyInput(scene);
		//		BackgroundImage bg = new BackgroundImage(new Image(getClass().getResourceAsStream("bg/test.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		//		root.setBackground(new Background(bg));
	}

	public void generateMap() {
		pelletsRendered.clear();
		powerUpsRendered.clear();
		despawnIndex.clear();
		exitOptions.clear();
		root.getChildren().clear();
		pelletsCollected = 0;
		
		Image bg = new Image(getClass().getResourceAsStream("bg/earthsurface.jpeg"));
		bgView = new ImageView(bg);
//		bg.
//		bg.setScaleY(0.7);
		root.getChildren().add(bgView);

		gameView = addGameComponents();
		gameView.setEffect(blur);
		root.getChildren().add(gameView);

		countDownView = addCountDown();
		root.getChildren().add(countDownView);

		
		
		gameOverPopUp = addGameOverPopUp();
		gameOverPopUp.setVisible(false);
		root.getChildren().add(gameOverPopUp);
		
		pauseMenu = addPauseMenu();
		pauseMenu.setVisible(false);
		root.getChildren().add(pauseMenu);
		
		exitPopUp = addExitPopUp();
		exitPopUp.setVisible(false);
		root.getChildren().add(exitPopUp);
		
		
	}	

	private Group addGameComponents() {
		Group group = new Group();

		int startX = 0, startY = 0, tunnelXLeft = 0, tunnelXRight = 0, alienX = 0, alienY = 0;

		for (int row = 0; row < 21; row++) {
			for (int col = 0; col < 21; col++) {

				int currentElement = controller.getLevel().getCurrentMap().getData(row, col);
				//Walls
				if (currentElement == 1) {
					Rectangle wall = new Rectangle(mapOffsetX+tileWidth*col, mapOffsetY+tileHeight*row, tileWidth, tileHeight);
					wall.setFill(Color.LIGHTGREY); //fill
					wall.setStroke(Color.LIGHTGREY);//outline
					group.getChildren().add(wall);	
				} 
				//Pellets
				else if (currentElement == 2) {
					Pellet pellet = new Pellet(mapOffsetX+tileWidth*(col+0.5), mapOffsetY+tileHeight*(0.5+row), tileWidth*0.125);
					//we can have a class 'Theme' to have a combination of preset colours to use
					pellet.returnPellet().setFill(Color.PEACHPUFF); 
					group.getChildren().add(pellet.returnPellet());
					pelletsRendered.add(pellet);
				} 
				//Magic Pellet	
				else if (currentElement == 3) {
					PowerUp powerUp = new PowerUp(mapOffsetX+tileWidth*(col), mapOffsetY+tileHeight*(row), powerUpImgs.get(currentElement-3) );
//					PowerUp powerUp = new PowerUp(mapOffsetX+tileWidth*(col+0.5), mapOffsetY+tileHeight*(0.5+row), tileWidth*0.325);
					//we can have a class 'Theme' to have a combination of preset colours to use
//					powerUp.returnPowerUp().setFill(Color.YELLOW); 
					group.getChildren().add(powerUp.returnPowerUp());
//					group.getChildren().add(powerUp.returnPowerUp());
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
				//Set Alien spawn x and y loaction
				else if (currentElement == 8) {
					alienX = col;
					alienY = row;
				}
				//Gate
				else if (currentElement == 9) {
					Rectangle wall = new Rectangle(mapOffsetX+tileWidth*col, mapOffsetY+tileHeight*row, tileWidth, tileHeight);
					wall.setFill(Color.WHITE); //fill
					wall.setStroke(Color.WHITE);//outline
					group.getChildren().add(wall);	
				}
			}
		}
		spaceman = new Spaceman(controller, startX, startY);
		//spaceman = new Spaceman(controller, startX, startY);
		group.getChildren().add(spaceman);

		//Add Aliens after map added to scene
		red = new Alien(0,controller,this,alienX,alienY,-1,0,1,controller.ghostPlayerRed);
		pink = new Alien(1,controller,this,alienX,alienY,-1,0,15,controller.ghostPlayerPink);
		blue = new Alien(2,controller,this,alienX,alienY,-1,0,35,false);
		orange = new Alien(3,controller,this,alienX,alienY,-1,0,60,false);
		aliens = new Alien[] {red,pink,blue,orange};
		group.getChildren().addAll(aliens);
		
		//red.start();

		//Add tunnel wall cover after Spaceman added to scene - CHANGE MAGIC NUMBERS
//		Rectangle tunnelWallLeft = new Rectangle(mapOffsetX+tileWidth*tunnelXLeft, mapOffsetY+tileHeight*0, tileWidth, tileHeight*20);
//		tunnelWallLeft.setFill(Color.LIGHTBLUE); //fill
//		tunnelWallLeft.setStroke(Color.LIGHTBLUE);//outline
//		group.getChildren().add(tunnelWallLeft);
//		Rectangle tunnelWallRight = new Rectangle(mapOffsetX+tileWidth*tunnelXRight, mapOffsetY+tileHeight*0, tileWidth, tileHeight*20);
//		tunnelWallRight.setFill(Color.LIGHTBLUE); //fill
//		tunnelWallRight.setStroke(Color.LIGHTBLUE);//outline
//		group.getChildren().add(tunnelWallRight);



		//Add parameter displays
		Text livesLabel= new Text("Lives:");
		livesLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,50));
		livesLabel.setFill(Color.WHITE);
		livesLabel.setX((mapOffsetX-livesLabel.getLayoutBounds().getWidth())*0.5);
		livesLabel.setY(100.0);
		group.getChildren().add(livesLabel);
		
		Text lives = new Text();
		lives.setText(Integer.toString(controller.getLevel().lives));
		lives.setFont(Font.font("Comic Sans MS",50));
		lives.setFill(Color.WHITE);
		lives.setX((mapOffsetX-livesLabel.getLayoutBounds().getWidth())*0.5);
		lives.setY(100+livesLabel.getLayoutBounds().getHeight()+10);
		group.getChildren().add(lives);
		this.lives = lives;

		Text score = new Text("0");
		score.setText(Integer.toString(controller.getLevel().getScore()));
		score.setFont(Font.font("Comic Sans MS",50));
		score.setFill(Color.WHITE);
		score.setX((mapOffsetX-livesLabel.getLayoutBounds().getWidth())*0.5);
		score.setY(500);
		group.getChildren().add(score);
		this.score = score;

		timeComponent = new Group();
		Text timeLabel = new Text("Time:");
		timeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,50));
		timeLabel.setFill(Color.WHITE);
		timeLabel.setX(SCENE_WIDTH-mapOffsetX + ((mapOffsetX-timeLabel.getLayoutBounds().getWidth())*0.5));
		timeLabel.setY(100.0);
		timeComponent.getChildren().add(timeLabel);

		Text time = new Text();
		time.setText(Integer.toString(controller.getLevel().getTimeLimit()-controller.getTimeElapsed()));
		time.setFont(Font.font("Comic Sans MS",50));
		time.setFill(Color.WHITE);
		time.setX(SCENE_WIDTH-mapOffsetX + ((mapOffsetX-time.getLayoutBounds().getWidth())*0.5));
		time.setY(100+timeLabel.getLayoutBounds().getHeight()+10);
		timeComponent.getChildren().add(time);
		this.time = time;

		group.getChildren().add(timeComponent);

		return group;
	}

	public Group addCountDown() {
		Group group = new Group();
		Rectangle frame = new Rectangle(SCENE_WIDTH,SCENE_HEIGHT/4);
		frame.setFill(Color.BLACK);
		frame.setStroke(Color.WHITE);
		frame.setStrokeWidth(2.0);
		//		frame.setArcHeight(15);
		//		frame.setArcWidth(15);
		frame.setX((SCENE_WIDTH-frame.getLayoutBounds().getWidth())*0.5);
		frame.setY((SCENE_HEIGHT-frame.getLayoutBounds().getHeight())*0.5);
		group.getChildren().add(frame);

		Text message = new Text("Press Enter to start");
		message.setFont(Font.font("Comic Sans MS",100));
		message.setFill(Color.WHITE);
		message.setX((SCENE_WIDTH-message.getLayoutBounds().getWidth())*0.5);
		message.setY((SCENE_HEIGHT-message.getLayoutBounds().getHeight())*0.5+100);
		group.getChildren().add(message);
		this.message = message;

		return group;
	}

	private Group addPauseMenu() {
		Group group = new Group();

		Rectangle frame = new Rectangle(SCENE_WIDTH,SCENE_HEIGHT/3);
		frame.setFill(Color.BLACK);
		frame.setStroke(Color.WHITE);
		frame.setStrokeWidth(2.0);
		frame.setArcHeight(15);
		frame.setArcWidth(15);
		frame.setX((SCENE_WIDTH-frame.getLayoutBounds().getWidth())*0.5);
		frame.setY((SCENE_HEIGHT-frame.getLayoutBounds().getHeight())*0.5);
		group.getChildren().add(frame);
		
		Image paused = new Image(getClass().getResourceAsStream("misc/paused.png"));
		ImageView pausedLabel = new ImageView(paused);
		pausedLabel.setX((SCENE_WIDTH-pausedLabel.getLayoutBounds().getWidth())*0.5);
		pausedLabel.setY(frame.getY()+pausedLabel.getLayoutBounds().getHeight()*0.25);
		//resumeButton.setEffect(shadow);
		group.getChildren().add(pausedLabel);

		Text label = new Text("Press 'P' to Resume the Level");
		label.setFont(Font.font(50));
		label.setFill(Color.WHITE);
		label.setX((SCENE_WIDTH-label.getLayoutBounds().getWidth())*0.5);
		label.setY(frame.getY()+frame.getLayoutBounds().getHeight()-label.getLayoutBounds().getHeight());
		group.getChildren().add(label);

		return group;
	}
	
	private Group addGameOverPopUp() {
		Group group = new Group();

		Rectangle frame = new Rectangle(SCENE_WIDTH,SCENE_HEIGHT/3);
		frame.setFill(Color.BLACK);
		frame.setStroke(Color.WHITE);
		frame.setStrokeWidth(2.0);
		frame.setArcHeight(15);
		frame.setArcWidth(15);
		frame.setX((SCENE_WIDTH-frame.getLayoutBounds().getWidth())*0.5);
		frame.setY((SCENE_HEIGHT-frame.getLayoutBounds().getHeight())*0.5);
		group.getChildren().add(frame);
		
		Image gameOver = new Image(getClass().getResourceAsStream("misc/gameover.png"));
		ImageView gameOverLabel = new ImageView(gameOver);
		gameOverLabel.setX((SCENE_WIDTH-gameOverLabel.getLayoutBounds().getWidth())*0.5);
		gameOverLabel.setY(frame.getY()+gameOverLabel.getLayoutBounds().getHeight()*0.25);
		//resumeButton.setEffect(shadow);
		group.getChildren().add(gameOverLabel);

		Text label = new Text("Press 'Enter' To Play Again");
		label.setFont(Font.font(50));
		label.setFill(Color.WHITE);
		label.setX((SCENE_WIDTH-label.getLayoutBounds().getWidth())*0.5);
		label.setY(frame.getY()+frame.getLayoutBounds().getHeight()-label.getLayoutBounds().getHeight());
		group.getChildren().add(label);

		return group;
	}
	
	private Group addExitPopUp() {
		Group group = new Group();

		Rectangle frame = new Rectangle(SCENE_WIDTH/2.5,SCENE_HEIGHT/2.5);
		frame.setFill(Color.BLACK);
		frame.setStroke(Color.WHITE);
		frame.setStrokeWidth(2.0);
		frame.setArcHeight(15);
		frame.setArcWidth(15);
		frame.setX((SCENE_WIDTH-frame.getLayoutBounds().getWidth())*0.5);
		frame.setY((SCENE_HEIGHT-frame.getLayoutBounds().getHeight())*0.5);
		group.getChildren().add(frame);
		
		Text label = new Text("Are You Sure \nYou Want To Leave \nThis Level?");
		label.setFont(Font.font(50));
		label.setFill(Color.WHITE);
		label.setX((SCENE_WIDTH-label.getLayoutBounds().getWidth())*0.5);
		label.setY(frame.getY()+100);
		group.getChildren().add(label);
		
		Image no = new Image(getClass().getResourceAsStream("misc/no.png"));
		ImageView noLabel = new ImageView(no);
		noLabel.setEffect(shadow);
		exitOptions.add(noLabel);
		
		
		Image yes = new Image(getClass().getResourceAsStream("misc/yes.png"));
		ImageView yesLabel = new ImageView(yes);
		exitOptions.add(yesLabel);
		
		for (int i = 0; i < exitOptions.size(); i++) {
			exitOptions.get(i).setX(frame.getX()+(i+1)*50+i*yes.getWidth());
			exitOptions.get(i).setY(frame.getY()+frame.getLayoutBounds().getHeight()-no.getHeight()*1.5);
			group.getChildren().add(exitOptions.get(i));
		}
//		pausedLabel.setX((SCENE_WIDTH-pausedLabel.getLayoutBounds().getWidth())*0.5);
//		pausedLabel.setY(frame.getY()+pausedLabel.getLayoutBounds().getHeight()*0.25);
		//resumeButton.setEffect(shadow);
		//pauseOptions.add(pausedLabel);
//		group.getChildren().add(pausedLabel);

		

		return group;
	}
	
//	private Group addPauseMenu() {
//		Group group = new Group();
//
//		Rectangle frame = new Rectangle(700,400);
//		frame.setFill(Color.BLACK);
//		frame.setStroke(Color.WHITE);
//		frame.setStrokeWidth(2.0);
//		frame.setArcHeight(15);
//		frame.setArcWidth(15);
//		frame.setX((SCENE_WIDTH-frame.getLayoutBounds().getWidth())*0.5);
//		frame.setY((SCENE_HEIGHT-frame.getLayoutBounds().getHeight())*0.5);
//		group.getChildren().add(frame);
//
//
//		Text label = new Text("Paused");
//		label.setFont(Font.font(50));
//		label.setFill(Color.WHITE);
//		label.setX((SCENE_WIDTH-label.getLayoutBounds().getWidth())*0.5);
//		label.setY(frame.getY()+label.getLayoutBounds().getHeight()+10);
//		group.getChildren().add(label);
//
//		Image resume = new Image(getClass().getResourceAsStream("misc/resume.png"));
//		ImageView resumeButton = new ImageView(resume);
//		resumeButton.setX(frame.getX()+100);
//		resumeButton.setY(frame.getY()+frame.getLayoutBounds().getHeight()-250);
//		resumeButton.setEffect(shadow);
//		pauseOptions.add(resumeButton);
//
//		Image close = new Image(getClass().getResourceAsStream("misc/close2.png"));
//		ImageView closeButton = new ImageView(close);
//		closeButton.setX(frame.getX()+frame.getLayoutBounds().getWidth()-closeButton.getLayoutBounds().getWidth()-100);
//		closeButton.setY(frame.getY()+frame.getLayoutBounds().getHeight()-250);
//		pauseOptions.add(closeButton);
//
//		group.getChildren().add(closeButton);
//		group.getChildren().add(resumeButton);
//
//		return group;
//	}
	

	public Scene returnScene() {
		return scene;
	}

	public boolean hideCorrespondingPellet(int charX, int charY) {
		for (int index = 0; index < pelletsRendered.size(); index++) {
			//Hides corresponding pellet matching destination of spaceman
			currentPellet = pelletsRendered.get(index);
			//Respawn pellets at set time
			if (currentPellet.getRespawnTime() == controller.getTimeElapsed()) {
				pelletsRendered.get(index).returnPellet().setVisible(true);
			} 

			if ((currentPellet.getGraphicalX() - mapOffsetX)/tileWidth -0.5 == charX) {
				if ((currentPellet.getGraphicalY() - mapOffsetY)/tileHeight -0.5 == charY) {

					if (currentPellet.returnPellet().isVisible()) {
						pelletsRendered.get(index).returnPellet().setVisible(false);
						spaceman.playPelletSound();
						pelletsCollected++;
						
						
						if (controller.getMode() == 3) {
							pelletsRendered.get(index).setRespawnTime(controller.getTimeElapsed()+10);
							despawnIndex.add(index);
							
						} else if (pelletsCollected == pelletsRendered.size()) {
							//end game, next level here
							stopAllChars();
							controller.timeline.stop();
							//disp screen
							//play soud clip
							win1.play();
							//do nothing while sound clip is playing
							//while (win1.isPlaying()); //remove incase of crash?
							//change level
							controller.resetToStartState();
							controller.levelWins++;
							controller.setLevel(controller.getMode());
							//increase difficulty here?
							
							
							
							//testing scenes
							if (controller.getMode() == 1) {
								controller.playStory(controller.levelWins);
							}
							
						}
						
						//Endless mode: set respawn
						

						return true;
					}
				}
			}

		}
		currentPellet = null;
		return false;
	}

	public void respawnPellet() {

//		for (int i = 0; i < despawnIndex.size(); i++) {
//			//Respawn pellets at set time
//			if (pelletsRendered.get(despawnIndex.get(i)).getRespawnTime() <= controller.getTimeElapsed()) {
//				pelletsRendered.get(despawnIndex.get(i)).returnPellet().setVisible(true);
//				despawnIndex.remove(i);
//				//break;
//				return;
//			} 
//		}
		
		if (!despawnIndex.isEmpty()) {
			if (pelletsRendered.get(despawnIndex.get(0)).getRespawnTime() <= controller.getTimeElapsed()) {
				pelletsRendered.get(despawnIndex.get(0)).returnPellet().setVisible(true);
				despawnIndex.remove(0);
				//break;
				return;
			} 
		}
	}

	//change for powerup after making powerup class
	public boolean hideCorrespondingPowerUp(int charX, int charY) {
		for (int index = 0; index < powerUpsRendered.size(); index++) {
			//Hides corresponding power up matching destination of spaceman
//			if ((powerUpsRendered.get(index).getGraphicalX() - mapOffsetX)/tileWidth -0.5 == charX) {
//				if ((powerUpsRendered.get(index).getGraphicalY() - mapOffsetY)/tileHeight -0.5 == charY) {
			if ((powerUpsRendered.get(index).getGraphicalX() - mapOffsetX)/tileWidth  == charX) {
				if ((powerUpsRendered.get(index).getGraphicalY() - mapOffsetY)/tileHeight  == charY) {		
					if (powerUpsRendered.get(index).returnPowerUp().isVisible()) {
						powerUpsRendered.get(index).returnPowerUp().setVisible(false);
						return true;
					}
					

				}
			}
		}
		return false;
	}

	public void updateLives(int lives) {
		this.lives.setText(Integer.toString(controller.getLevel().lives));
	}
	
	public void updateScore(int score) {
		this.score.setText(Integer.toString(controller.getLevel().getScore()));
	}

	public void updateTime(int time) {
		if (time <= -1) {
			timeComponent.setVisible(false);
		} else {
			this.time.setText(Integer.toString(controller.getLevel().getTimeLimit()-controller.getTimeElapsed()));
		}
	}

	//Controls countDown display
	public void updateMessage(int value) {
		gameView.setEffect(null);
		if (value>0) {
			message.setText(Integer.toString(value));

		} else if (value==0) {
			message.setText("START!!");

		} else {
			countDownView.setVisible(false);
			//countdown.setFramePosition(0);
		}
		message.setX((SCENE_WIDTH-message.getLayoutBounds().getWidth())*0.5);
	}

	public void updatePauseScreen(boolean wasPaused) {

		if (wasPaused) {
			gameView.setEffect(blur);
			pauseMenu.setVisible(true);

		} else {
			gameView.setEffect(null);
			pauseMenu.setVisible(false);

		}

	}
	
	public void updateExitScreen(boolean exitScreenOn) {

		if (exitScreenOn) {
			gameView.setEffect(blur);
			exitPopUp.setVisible(true);

		} else {
			gameView.setEffect(null);
			exitPopUp.setVisible(false);

		}

	}

	public void cycleOptions(int option) {
		if (option == 0) {
			exitOptions.get(option).setEffect(shadow);
			exitOptions.get(option+1).setEffect(null);
		} else if (option == 1) {
			exitOptions.get(option).setEffect(shadow);
			exitOptions.get(option-1).setEffect(null);
		}
	}


	public void playCountdown() {
		countdown.start();; //for soem reason loop(0) works better than start()
	}

	public void pauseCountdown() {
		countdown.stop();
	}

	public void resetCountdown() {
		countdown.setFramePosition(0);
	}


	public void playCycleSound( ) {
		cycle.play();
	}

	public void stopCycleClip( ) {
		if (cycle.isPlaying()) {
			cycle.stop();
		}
	}
	
	private void setUpKeyInput(Scene scene) {
		
	scene.setOnKeyPressed(new EventHandler <KeyEvent> () {
		public void handle(KeyEvent input) {
			if (controller.startTimer >= -1 && controller.startTimer <3) {
				return; //stop key input buffering which caused bugs
			}
			//temp, trying to get cycle sound to work consistently
			stopCycleClip();
			
			if (input.getCode() == KeyCode.LEFT) {
				//When in exit screen, controls option selection instead
				if (exitPopUp.isVisible()) {
					if (controller.exitOption > 0) {
						controller.exitOption--;
						playCycleSound();
					}
					cycleOptions(controller.exitOption);
						
				} else {
					spaceman.setKeyInput(0);
					
				}
				
			} else if(input.getCode() == KeyCode.RIGHT) {
				//When in exit screen controls option selection instead
				if (exitPopUp.isVisible()) {
					if (controller.exitOption < 1) {
						controller.exitOption++;
						playCycleSound();
					}
					cycleOptions(controller.exitOption);
					
				} else {
					spaceman.setKeyInput(2);
					
				}
				
			} else if(input.getCode() == KeyCode.UP) {
				spaceman.setKeyInput(1);
				
			} else if(input.getCode() == KeyCode.DOWN) {
				spaceman.setKeyInput(3);
				
			} else if(input.getCode() == KeyCode.PAGE_DOWN) {
				
				//Sets time to 0 and stops the game when not in endless mode
				if (controller.getMode() != 3 & controller.startTimer <= -1) { //doesnt seem to stop when countdown hasnt started
					controller.timeElapsed = controller.getTimeLimit();
					updateTime(controller.getTimeLimit() - controller.timeElapsed);
//					spaceman.stop();
//					blue.stop(); //changed from red
//					
//					//added with createghostPlayer
//					red.stop();
//					pink.stop();
					stopAllChars();
					controller.timeline.stop();
					//disp gameover screen here
					playGameOver();
//					spaceman.resetSpaceman();
//					red.resetAlien();
//					pink.resetAlien();
//					blue.resetAlien();
//					orange.resetAlien();
//					controller.resetToStartState();
//					controller.levelWins =  0;
//					controller.setLevel(controller.getMode()); //idk?? dont need i think
					gameOverPopUp.setVisible(true);
				}
				
			} else if(input.getCode() == KeyCode.ENTER) {
				playCycleSound();
				//When in exit screen, executes selected options instead
				if (exitPopUp.isVisible()) {		
					//Quits the game if yes is selected, otherwise will go back to pause menu
					if (controller.exitOption == 1){
//						spaceman.stop();
//						blue.stop(); //was red
//						
//						//added with createghostPlayer
//						red.stop();
//						pink.stop();
						stopAllChars();
						controller.timeline.stop();
						
						//Resets initial level states //consider an init() func instead
						controller.resetToStartState();
						controller.levelWins = 0;
//						paused = !paused;
					} else {
						pauseMenu.setEffect(null);
					}
//					exitScreenOn = !exitScreenOn;
//					currentView.updateExitScreen(exitScreenOn);
					exitPopUp.setVisible(false);
				} else if (gameOverPopUp.isVisible()) {
					gameOverPopUp.setVisible(false);
					//controller.timeline.play();
					//new
					spaceman.resetSpaceman();
					red.resetAlien();
					pink.resetAlien();
					blue.resetAlien();
					orange.resetAlien();
					controller.resetToStartState();
					controller.levelWins =  0;
					controller.setLevel(controller.getMode());
				} else if (!pauseMenu.isVisible()){
					controller.timeline.play();
				}
			} else if(input.getCode() == KeyCode.P) {
				playCycleSound();
				//Toggles pause screen when not in the exit screen
				if (!exitPopUp.isVisible()) {
					controller.paused = !controller.paused;
					controlPause();
				}
				
			} else if (input.getCode() == KeyCode.ESCAPE) {
				//Turns on/off exit screen and pauses if not already
				if (!exitPopUp.isVisible()) {
					controller.paused = true;
					controlPause();
					pauseMenu.setEffect(blur);
						
				} else {
					pauseMenu.setEffect(null);
				}
				playCycleSound();
				exitPopUp.setVisible(!exitPopUp.isVisible());
				//.updateExitScreen(exitScreenOn);
			} else if (input.getCode() == KeyCode.W) {
				if (controller.ghostPlayerPink) {
					pink.setKeyInput(1);
				}
			} else if (input.getCode() == KeyCode.A) {
				if (controller.ghostPlayerPink) {
					pink.setKeyInput(0);
				}
			} else if (input.getCode() == KeyCode.S) {
				if (controller.ghostPlayerPink) {
					pink.setKeyInput(3);
				}
			} else if (input.getCode() == KeyCode.D) {
				if (controller.ghostPlayerPink) {
					pink.setKeyInput(2);
				}
			} else if (input.getCode() == KeyCode.I) {
				if (controller.ghostPlayerRed) {
					red.setKeyInput(1);
				}
			} else if (input.getCode() == KeyCode.J) {
				if (controller.ghostPlayerRed) {
					red.setKeyInput(0);
				}
			} else if (input.getCode() == KeyCode.K) {
				if (controller.ghostPlayerRed) {
					red.setKeyInput(3);
				}
			} else if (input.getCode() == KeyCode.L) {
				if (controller.ghostPlayerRed) {
					red.setKeyInput(2);
				}
			}
		}
	});
}
	public void controlPause() {
		//paused = !paused;
		//exitOption = 0;
		
		
		if (controller.paused) {
			//Pauses the game
			pauseCountdown();
			controller.timeline.pause();

//			spaceman.pause();
//			blue.pause();
//			orange.pause();
//			//added with createghostPlayer
//			red.pause();
//			pink.pause();
			stopAllChars();

		
		} else {
			//Resumes the level is counted was started
			if (controller.startTimer != 3) {
				controller.timeline.play();

				//Resumes Countdown Sound if interrupted
				if (controller.startTimer>= 0) {
					playCountdown();
				}

				//maybe make a bool var isCountdown isntead for clarity
				//Spaceman starts moving when not in Countdown Stage and there is time remaining
				if (controller.getTimeLimit()!=controller.timeElapsed & controller.startTimer<= -2) { 

					startAllChars();
//					spaceman.start();
//					blue.start();
//
//					//added with createghostPlayer
//					red.start();
//					pink.start();
				}
			}
		}
		
		updatePauseScreen(controller.paused);
	}
	
	private ArrayList<Image> setUpPowerImages() {
		 ArrayList<Image> imgList = new  ArrayList<Image>();
		 Image img = new Image(getClass().getResourceAsStream("res/starxd.png"));
		 imgList.add(img);
		 
		 img = new Image(getClass().getResourceAsStream("res/heart.png"));
		 imgList.add(img);
		 
		 img = new Image(getClass().getResourceAsStream("res/sheild.png"));
		 imgList.add(img);
		 
		return imgList;
	}
//	public void checkSpacemanAndAliens() {
//		// TODO Auto-generated method stub
//		
//	}

	public void stopAllChars() {
		spaceman.stop();
		red.stop();
		pink.stop();
		blue.stop();
		orange.stop();
	}
	
	public void startAllChars() {
		spaceman.start();
		pink.start();
		red.start();
		blue.start();
		orange.start();
	}
	
	public void setBg(Image image) {
		bgView.setImage(image);
	}
	
	public void playGameOver() {
		gOver.play();
	}
	
	public void playDeathSound() {
		death.play();
		//while (death.isPlaying()); //maybe reomoves?
	}
}
