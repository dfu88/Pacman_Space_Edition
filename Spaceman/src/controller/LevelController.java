package controller;

import model.Level;

import view.LevelVisuals;
import view.Spaceman;
import javafx.scene.*;
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
	private Level levelModel;
	
	private Timeline timeline;
	private int startTimer = 3;
	
	private boolean paused = false;
	private int pauseMenuOption = 0;

	public LevelController(InterfaceController controller) {
		interfaceCtrl = controller;
		currentView = new LevelVisuals(this);
		levelModel = new Level();
		
		timeline = makeTimeline();
		
		//maybe make this a function
		currentView.returnScene().setOnKeyPressed(new EventHandler <KeyEvent> () {
			public void handle(KeyEvent input) {
				
				//temp, trying to get cycle sound to work consistently
				currentView.stopCycleClip();
				
				if (input.getCode() == KeyCode.LEFT) {
					//When in pause screen controls option selection instead
					if (paused) {
						if (pauseMenuOption > 0) {
							pauseMenuOption--;
							currentView.playCycleSound();
//							currentView.playCycle();
						}
						currentView.cycleOptions(pauseMenuOption);
							
					} else {
						currentView.spaceman.setKeyInput(0);
						
					}
					
					
				} else if(input.getCode() == KeyCode.RIGHT) {
					//When in pause screen controls option selection instead
					if (paused) {
						if (pauseMenuOption < 1) {
							pauseMenuOption++;
							//currentView.playCycle();
							currentView.playCycleSound();
						}
						
						currentView.cycleOptions(pauseMenuOption);
						
					} else {
						currentView.spaceman.setKeyInput(2);
						
					}
					
				} else if(input.getCode() == KeyCode.UP) {
					currentView.spaceman.setKeyInput(1);
					
				} else if(input.getCode() == KeyCode.DOWN) {
					currentView.spaceman.setKeyInput(3);
					
				} else if(input.getCode() == KeyCode.PAGE_DOWN) {
					levelModel.timeRemaining = 0;
					currentView.updateTime(levelModel.timeRemaining);
					currentView.spaceman.stop();
					//disp gameover screen?
					
				} else if(input.getCode() == KeyCode.ENTER) {
					currentView.playCycleSound();
					//When in pause screen controls option selection instead
					if (paused) {
						//Resumes the game
						if (pauseMenuOption == 0) {
							//Resumes the Countdown Sound if paused during it
							if (startTimer>= 0) {
								currentView.playCountdown(); 
							}
							
						//maybe make a bool var isCountdown isntead for clarity
							//Spaceman starts moving when not in CountDown stage and there is time left
							if (levelModel.timeRemaining>0 & startTimer<= -2) { 
								currentView.spaceman.start();
							}
							
							timeline.play();
							currentView.updatePauseScreen(!paused);
						
						//Quits the game
						} else if (pauseMenuOption == 1){
							
							currentView.spaceman.stop();
							timeline.stop();
							
							//Resets initial level states //consider an init() func instead
							startTimer = 3;
							pauseMenuOption = 0;
							
							controller.showHome();
						}
						
						paused = !paused;
					
					//Start CountDown
					} else {
						
						timeline.play();
					}

				} else if(input.getCode() == KeyCode.P) {
					currentView.playCycleSound();
					paused = !paused;
					pauseMenuOption = 0;
					
					//Pauses the game
					if (paused) {
						currentView.pauseCountdown();
						timeline.pause();
						currentView.spaceman.pause();
					
					//Resumes the game
					} else {
						
						timeline.play();
						
						//Resumes Countdown Sound if interrupted
						if (startTimer>= 0) {
							currentView.playCountdown();

						}
						
						//maybe make a bool var isCountdown isntead for clarity
						//Spaceman starts moving when not in Countdown Stage and there is time remaining
						if (levelModel.timeRemaining>0 & startTimer<= -2) { 
							currentView.spaceman.start();
						}
					}
					
					currentView.updatePauseScreen(paused);
					
				}
			}
		});
	}
	
	//consider seperating timelines for time and countdown
	private Timeline makeTimeline() {
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (levelModel.timeRemaining !=0) {
					//Play initial CountDown sound
					if (startTimer == 3) { //seems to be synchronised with the countdown here
						currentView.playCountdown();
					}
					
					//While still in CountDown State
					if (startTimer >= -1) {
						currentView.updateMessage(startTimer);
						if ((startTimer == 0)) {
							currentView.spaceman.start();
						}
						startTimer--;
					
					//In game timer
					} else if (levelModel.timeRemaining > 0) {
						levelModel.timeRemaining--;
						currentView.updateTime(levelModel.timeRemaining);
						
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
		levelModel.setMap(type);
		currentView.generateMap();
		interfaceCtrl.getMainApp().changeScene(currentView.returnScene()); // possible dont call getmainAPp()
	}																		//create method in intCtrller to change scenes
	
	public int checkMap(int x, int y) {
		return levelModel.getCurrentMap().getData(y, x);
	}
	
	public void updateMap(int dx, int dy,int posX, int posY) {
		
		if (levelModel.getCurrentMap().getData(posY+dy, posX+dx) == 2) {
			currentView.hideCorrespondingPellet(posX+dx, posY + dy);
			
			levelModel.addPoints(100);
			currentView.updateScore(levelModel.getScore());
			levelModel.getCurrentMap().updateData(dx, dy, posX, posY);
			
		} else if (levelModel.getCurrentMap().getData(posY+dy, posX+dx) == 3) {
			//do power up stuff
			currentView.hideCorrespondingPowerUp(posX+dx, posY + dy);

			levelModel.getCurrentMap().updateData(dx, dy, posX, posY);
		}
		//levelModel.getCurrentMap().updateData(dx, dy, posX, posY);  no need to change map array
		//this function is messing up the tunnel because its removing tele
		//but if using updateData function then must be in the if statements
	}
	
	
	
	public int getCountdown() {
		return startTimer;
	}
}
