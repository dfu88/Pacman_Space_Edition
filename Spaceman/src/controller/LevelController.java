package controller;

import model.Level;
import view.Alien;
import view.LevelVisuals;
import view.Spaceman;
import view.StorySlides;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
//import java.util.Timer;
//import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LevelController {

	private InterfaceController interfaceCtrl;
	private LevelVisuals currentView;
	private StorySlides scenarioDisp;
	private Level levelModel;
	
	public Timeline timeline;
	public int startTimer = 3;
	public int timeElapsed = 0;
	
	public boolean paused = false;
	//private int pauseMenuOption = 0;
	
	//private boolean exitScreenOn = false;
	public int exitOption = 0;
	
	private int currentMode;
	public boolean ghostPlayerRed = false;
	public boolean ghostPlayerPink = false;
	
	public int levelWins = 0;

	public LevelController(InterfaceController controller) {
		interfaceCtrl = controller;
		currentView = new LevelVisuals(this);
		scenarioDisp = new StorySlides(this);
		levelModel = new Level();
		
		timeline = makeTimeline();
		
		//maybe make this a function
//		currentView.returnScene().setOnKeyPressed(new EventHandler <KeyEvent> () {
//			public void handle(KeyEvent input) {
//				
//				//temp, trying to get cycle sound to work consistently
//				currentView.stopCycleClip();
//				
//				if (input.getCode() == KeyCode.LEFT) {
//					//When in exit screen, controls option selection instead
//					if (exitScreenOn) {
//						if (exitOption > 0) {
//							exitOption--;
//							currentView.playCycleSound();
//						}
//						currentView.cycleOptions(exitOption);
//							
//					} else {
//						currentView.spaceman.setKeyInput(0);
//						
//					}
//					
//				} else if(input.getCode() == KeyCode.RIGHT) {
//					//When in exit screen controls option selection instead
//					if (exitScreenOn) {
//						if (exitOption < 1) {
//							exitOption++;
//							currentView.playCycleSound();
//						}
//						currentView.cycleOptions(exitOption);
//						
//					} else {
//						currentView.spaceman.setKeyInput(2);
//						
//					}
//					
//				} else if(input.getCode() == KeyCode.UP) {
//					currentView.spaceman.setKeyInput(1);
//					
//				} else if(input.getCode() == KeyCode.DOWN) {
//					currentView.spaceman.setKeyInput(3);
//					
//				} else if(input.getCode() == KeyCode.PAGE_DOWN) {
//					
//					//Sets time to 0 and stops the game when not in endless mode
//					if (currentMode != 3) {
//						timeElapsed = levelModel.getTimeLimit();
//						currentView.updateTime(levelModel.getTimeLimit() - timeElapsed);
//						currentView.spaceman.stop();
//						
//						//added with createghostPlayer
//						currentView.red.stop();
//						currentView.pink.stop();
//
//						currentView.blue.stop();
//						currentView.orange.stop();
//						//disp gameover screen?
//					}
//					
//				} else if(input.getCode() == KeyCode.ENTER) {
//					currentView.playCycleSound();
//					//When in exit screen, executes selected options instead
//					if (exitScreenOn) {		
//						//Quits the game if yes is selected, otherwise will go back to pause menu
//						if (exitOption == 1){
//
//							currentView.spaceman.stop();
//
//							currentView.red.stop();
//							currentView.pink.stop();
//							currentView.blue.stop();
//							currentView.orange.stop();
//							timeline.stop();
//							
//							//Resets initial level states //consider an init() func instead
//							startTimer = 3;
//							exitOption = 0;
//							timeElapsed = 0;
//							ghostPlayerRed = false;
//							ghostPlayerPink = false;
//							currentView.resetCountdown();
//							controller.showHome();
//							paused = !paused;
//						}
//						exitScreenOn = !exitScreenOn;
//						currentView.updateExitScreen(exitScreenOn);
//					} else {
//						timeline.play();
//					}
//				} else if(input.getCode() == KeyCode.P) {
//					currentView.playCycleSound();
//					
//					//Toggles pause screen when not in the exit screen
//					if (!exitScreenOn) {
//						paused = !paused;
//						currentView.controlPause();//add currentview.
//					}
//					
//				} else if (input.getCode() == KeyCode.ESCAPE) {
//					//Turns on/off exit screen and pauses if not already
//					if (!exitScreenOn) {
//						paused = true;
//						currentView.controlPause(); //added currentView.
//
//					}
//					currentView.playCycleSound();
//					exitScreenOn = !exitScreenOn;
//					currentView.updateExitScreen(exitScreenOn);
//				}
//			}
//		});
	}
	
	//consider seperating timelines for time and countdown
	private Timeline makeTimeline() {
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (levelModel.getTimeLimit() != timeElapsed) {
					//Play initial CountDown sound
					if (startTimer == 3) { //seems to be synchronised with the countdown here
						currentView.playCountdown();
					}
					
					//While still in CountDown State
					if (startTimer >= -1) {
						currentView.updateMessage(startTimer);
						if ((startTimer == -1)) {
							currentView.spaceman.start();
							//player or ai ones
							currentView.red.start();
							currentView.pink.start();
							currentView.blue.start();
							currentView.orange.start();

						}
						startTimer--;
					
					//In game timer
					} else if (levelModel.getTimeLimit() != timeElapsed) {
						currentView.spaceman.start();
						timeElapsed++;
						currentView.updateTime(levelModel.getTimeLimit());
						currentView.updateLives(levelModel.lives);
//						respawnCollectables();
						
					}
				
				//When time runs out
				} else {
					currentView.updateMessage(-1);
					timeline.stop();
					currentView.pauseCountdown();
					//prob stop sounds here
					//gameover screen
				}
			}

		});
		timeline.getKeyFrames().add(keyFrame);

		return timeline;
	}
	
	public Level getLevel() {
		return levelModel;
	}
	public void setLevel(int type){
		//levelModel.makeMaps();
		//currentMode = type;
		//levelModel.setMap(type);
		levelModel.initLevel(type, levelWins);
		currentView.generateMap();
		if (currentMode == 3) {
			currentView.updateTime(-1);
		}
		interfaceCtrl.getMainApp().changeScene(currentView.returnScene()); // possible dont call getmainAPp()
	}																		//create method in intCtrller to change scenes
	
	public int checkMap(int x, int y) {
		return levelModel.getCurrentMap().getData(y, x);
	}
	
	public void updateMap(int dx, int dy,int posX, int posY) {
		
		if (levelModel.getCurrentMap().getData(posY+dy, posX+dx) == 2) {
			if (currentView.hideCorrespondingPellet(posX+dx, posY + dy)) {
			
				levelModel.addPoints(10);
				currentView.updateScore(levelModel.getScore());
				//levelModel.getCurrentMap().updateData(dx, dy, posX, posY);
			}
			
		} else if (levelModel.getCurrentMap().getData(posY+dy, posX+dx) == 3) {
			//do power up stuff
			if (currentView.hideCorrespondingPowerUp(posX+dx, posY + dy)) {
				currentView.red.changeToFrightMode();
				currentView.pink.changeToFrightMode();
				currentView.blue.changeToFrightMode();
				currentView.orange.changeToFrightMode();
				levelModel.addPoints(50);
				currentView.updateScore(levelModel.getScore());
				levelModel.getCurrentMap().updateData(dx, dy, posX, posY);//this doesnt do anyhting btw
			}
		}
		//levelModel.getCurrentMap().updateData(dx, dy, posX, posY);  no need to change map array
		//this function is messing up the tunnel because its removing tele
		//but if using updateData function then must be in the if statements
	}
	
	public int getTimeElapsed() {
		return timeElapsed;
	}
	
	public int getCountdown() {
		return startTimer;
	}
	
	public int getMode() {
		return currentMode;
	}
	
	public int getTimeLimit() {
		return levelModel.getTimeLimit();
	}
			
	public void respawnCollectables() {
		currentView.respawnPellet();
	}
	
//	public void addGhost(int ghostType) {
//		if (ghostType == 0) {
//			currentView.createGhostPlayer(0);
//		} else if (ghostType == 1) {
//			currentView.createGhostPlayer(1);
//		}  else if (ghostType == 2) {
//			currentView.createGhostPlayer(2);
//		}
//	}
//	private void controlPause() {
//		//paused = !paused;
//		//exitOption = 0;
//		
//		
//		if (paused) {
//			//Pauses the game
//			currentView.pauseCountdown();
//			timeline.pause();
//			currentView.spaceman.pause();
//			currentView.blue.pause();
//			
//			//added with createghostPlayer
//			currentView.red.pause();
//			currentView.pink.pause();
//		
//		} else {
//			//Resumes the level is counted was started
//			if (startTimer != 3) {
//				timeline.play();
//
//				//Resumes Countdown Sound if interrupted
//				if (startTimer>= 0) {
//					currentView.playCountdown();
//				}
//
//				//maybe make a bool var isCountdown isntead for clarity
//				//Spaceman starts moving when not in Countdown Stage and there is time remaining
//				if (levelModel.getTimeLimit()!=timeElapsed & startTimer<= -2) { 
//					currentView.spaceman.start();
//					currentView.blue.start();
//
//					//added with createghostPlayer
//					currentView.red.start();
//					currentView.pink.start();
//				}
//			}
//		}
//		
//		currentView.updatePauseScreen(paused);
//	}
	
	public void resetToStartState() {
		startTimer = 3;
		exitOption = 0;
		timeElapsed = 0;
//		levelWins = 0;
		ghostPlayerRed = false;
		ghostPlayerPink = false;
		currentView.resetCountdown();
		interfaceCtrl.showHome();
		paused = false;
	}


	private boolean ifSpacemanMetAlien (Alien alien) {
		int alienX = alien.getX();
		int spacemanX = currentView.spaceman.getX();
		int alienY = alien.getY();
		int spacemanY = currentView.spaceman.getY();
		
		if (alienX == spacemanX && alienY == spacemanY) {
			return true;
		} else {
			return false;
		}
	}
	
	private void consumeAlien(Alien alien) {
		levelModel.addPoints(200);
		alien.stop();
		alien.resetAlien();
		alien.start();
	}
	
	public void checkSpacemanAndAliens () {
		Alien[] aliens = currentView.aliens;
		for (Alien i : aliens) {
			if (ifSpacemanMetAlien(i)) {
				if (i.frightenedFlag) {
					consumeAlien(i);
				} else {
					levelModel.minusLives(1);
					currentView.updateLives(levelModel.lives);
					if (levelModel.lives > 0) {
						currentView.stopAllChars();
						currentView.playCycleSound();
						timeline.pause();
						currentView.spaceman.resetSpaceman();
						currentView.red.resetAlien();
						currentView.pink.resetAlien();
						currentView.blue.resetAlien();
						currentView.orange.resetAlien();
//						currentView.generateMap();
						currentView.countDownView = currentView.addCountDown();
						currentView.root.getChildren().add(currentView.countDownView);
						startTimer = 3;
						currentView.resetCountdown();
						currentView.countDownView.setVisible(true);
					}
					else if (levelModel.lives == 0) {
						currentView.stopAllChars();
						timeline.pause();
						currentView.spaceman.resetSpaceman();
						currentView.red.resetAlien();
						currentView.pink.resetAlien();
						currentView.blue.resetAlien();
						currentView.orange.resetAlien();
						resetToStartState();
						levelWins =  0;
						setLevel(getMode());
						currentView.gameOverPopUp.setVisible(true);
					}
				}
			}
		}
	}
		
	public void playStory(int levelWins) {
		// TODO Auto-generated method stub
		//currentMode = mode;
		//scenarioDisp.setScenario(levelWins);
		scenarioDisp.generateScenario(levelWins);
		interfaceCtrl.changeScene(scenarioDisp.returnScene());
	}

	public void setMode(int mode) {
		// TODO Auto-generated method stub
		currentMode = mode;
	}
	
	public void setBgView(Image image) {
		currentView.setBg(image);
	}
}
