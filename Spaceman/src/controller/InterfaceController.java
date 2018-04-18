package controller;

import javafx.scene.Scene;
import view.GUI;

public class InterfaceController {
	
	private MainApp game;
	private LevelController lvlCtrl;
	private GUI startMenu;
	
	public InterfaceController(MainApp instance) {
		game = instance;
		startMenu = new GUI(this);
		lvlCtrl = new LevelController(this);	
	}
	
	public GUI getGUI() { //do i need
		return startMenu;
	}
	
	public MainApp getMainApp() { //do i need
		return game;
	}
	
	//Controls what is generated and displayed based on button pressed
	public void executeProcess(int option) {
		//Classic Mode
		if (option == 0) {
			lvlCtrl.setMode(option);
			lvlCtrl.setLevel(option);
		
		//Story Mode
		} else if (option == 1) {
			lvlCtrl.setMode(option);
			lvlCtrl.playStory(lvlCtrl.levelWins);
		
		//Multiplayer Mode
		} else if (option == 2) {
			lvlCtrl.setMode(option);
			lvlCtrl.setLevel(option);
		
		//Endless Mode
		} else if (option == 3) {
			lvlCtrl.setMode(option);
			lvlCtrl.setLevel(option);
		
		//Long map mode
		} else if (option == 4) {
			lvlCtrl.setMode(option);
			lvlCtrl.setLevel(option);
		
		//Leader boards
		} else if (option == 5) {
			System.out.println("xd");
			lvlCtrl.setMode(option);
			lvlCtrl.showLeaderboard(0);
			
		
			
		//Setup flags for multiplayer mode
		} else if (option == 69) {
			lvlCtrl.ghostPlayerPink = true;
			
		} else if (option == 420) {
			lvlCtrl.ghostPlayerRed = true;
		}
	}
	
	public void showHome() {
		game.changeScene(startMenu.returnScene());
	}
	
	/*	This function will change the scene of the stage to the scene passed in by
	 * calling a function from MainApp
	Inputs:	Scene scene: the scene you want the stage to display	*/
	public void changeScene(Scene scene) {
		game.changeScene(scene);
	}
}
