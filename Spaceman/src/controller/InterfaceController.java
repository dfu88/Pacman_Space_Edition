package controller;

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
	
	public GUI getGUI() {
		return startMenu;
	}
	
	public MainApp getMainApp() {
		return game;
	}
	
	public void executeProcess(int option) {
		if (option == 0) {
			System.out.println("420");
			lvlCtrl.setLevel(option);
			
		} else if (option == 1) {
			System.out.println("69");
			lvlCtrl.setLevel(option);
			
		} else if (option == 2) {
			System.out.println("42069");
			lvlCtrl.setLevel(option);
			
		} else if (option == 3) {
			System.out.println("1269");
			lvlCtrl.setLevel(option);
			
		} else if (option == 4) {
			System.out.println("nice");
			lvlCtrl.setLevel(option);
			
		} else if (option == 5) {
			System.out.println("xd");
			
			
		} else if (option == 69) {
			System.out.println("sets pink to player");
			//lvlCtrl.addGhost(0);
//			lvlCtrl.setGhostPlayer(0);
			lvlCtrl.ghostPlayerPink = true;
			
		} else if (option == 420) {
			System.out.println("sets red to player");
			//lvlCtrl.addGhost(1);
//			lvlCtrl.setGhostPlayer(1);
			lvlCtrl.ghostPlayerRed = true;
		}
	}
	
	public void showHome() {
		game.changeScene(startMenu.returnScene());
	}
}
