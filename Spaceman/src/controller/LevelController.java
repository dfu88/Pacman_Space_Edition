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
	//public Spaceman spaceman;
	private Timeline timeline;
	private int startTimer;

	public LevelController(InterfaceController controller) {
		interfaceCtrl = controller;
		currentView = new LevelVisuals(this);
		levelModel = new Level();
		timeline = makeTimeline();
		startTimer = 3;
		//Timer countdown = new Timer();
		//TimerTask startGame = new TimerTask() {
			//public void run() {
				//if var < 3 then schedule again?
				//currentView.spaceman.start();
			//}
		//};
		
		//levelModel.makeMaps();

		currentView.returnScene().setOnKeyPressed(new EventHandler <KeyEvent> () {
			public void handle(KeyEvent input) {
				if (input.getCode() == KeyCode.LEFT) {
					currentView.spaceman.setKeyInput(0);
				} else if(input.getCode() == KeyCode.RIGHT) {
					currentView.spaceman.setKeyInput(2);
				} else if(input.getCode() == KeyCode.UP) {
					currentView.spaceman.setKeyInput(1);
				} else if(input.getCode() == KeyCode.DOWN) {
					currentView.spaceman.setKeyInput(3);
				} else if(input.getCode() == KeyCode.PAGE_DOWN) {
					levelModel.timeRemaining = 0;
					currentView.updateTime(levelModel.timeRemaining);
					currentView.spaceman.stop();
					
				} else if(input.getCode() == KeyCode.H) {
					currentView.spaceman.stop();
					timeline.stop();
					startTimer = 3;
					controller.showHome();
				} else if(input.getCode() == KeyCode.ENTER) {
					timeline.play();
					//currentView.spaceman.start();
					///countdown.schedule(startGame,1000l); //starts after 3 seconds prob use timeline instead
					//currentView.spaceman.start();//enter to start timer goes before this
				}
			}
		});
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
			//update score visual?
			//System.out.println(levelModel.getScore()); //temp
			levelModel.getCurrentMap().updateData(dx, dy, posX, posY);
		} else if (levelModel.getCurrentMap().getData(posY+dy, posX+dx) == 3) {
			//do power up stuff
			currentView.hideCorrespondingPowerUp(posX+dx, posY + dy);
			System.out.println("lol");
			levelModel.getCurrentMap().updateData(dx, dy, posX, posY);
		}
		//levelModel.getCurrentMap().updateData(dx, dy, posX, posY);  no need to change map array
		//this function is messing up the tunnel because its removing tele
		//but if using updateData function then must be in the if statements
	}
	
	private Timeline makeTimeline() {
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//moveOneStep();
				if (levelModel.timeRemaining !=0) {
					if (startTimer >= -1) {
						currentView.updateMessage(startTimer);
						if ((startTimer == 0)) {
							currentView.spaceman.start();
						}
						startTimer--;
						System.out.println(startTimer);
						//if (startTimer == 0) {
							//currentView.updateMessage(startTimer);
							
						//}
					} else if (levelModel.timeRemaining > 0) {
						levelModel.timeRemaining--;
						currentView.updateTime(levelModel.timeRemaining);
						//System.out.println(levelModel.timeRemaining);
					} else {
						System.out.println("Gameover");
						timeline.stop(); //not stopping as intended
					}
				} else {
					System.out.println("assas");
					//int temp = -1;
					currentView.updateMessage(-1);
					System.out.println("OwO");
					timeline.stop();
				}
			}

		});
		timeline.getKeyFrames().add(keyFrame);

		return timeline;
	}
	
	public int getCountdown() {
		return startTimer;
	}
}
