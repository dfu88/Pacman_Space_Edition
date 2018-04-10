package controller;

import model.Level;

import view.LevelVisuals;
import view.Spaceman;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;

public class LevelController {

	private InterfaceController interfaceCtrl;
	private LevelVisuals currentView;
	private Level levelModel;
	//public Spaceman spaceman;

	public LevelController(InterfaceController controller) {
		interfaceCtrl = controller;
		currentView = new LevelVisuals(this);
		levelModel = new Level();

		Timer countdown = new Timer();
		TimerTask startGame = new TimerTask() {
			public void run() {
				//if var < 3 then schedule again?
				currentView.spaceman.start();
			}
		};
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
				} else if(input.getCode() == KeyCode.H) {
					controller.showHome();
				} else if(input.getCode() == KeyCode.ENTER) {
					currentView.spaceman.start();
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
			System.out.println(levelModel.getScore()); //temp
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
}
