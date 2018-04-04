//package controller;
//
//import view.GUI;
//import model.Map;
//
//import javafx.application.Application;
//import javafx.stage.Stage;
//import javafx.scene.*;
//import javafx.scene.paint.Color;
//
//
//import javafx.scene.layout.AnchorPane;//for testing
//
//
//public class GameController extends Application {
//
//	@Override
//	public void start(Stage primaryStage) {
//		GUI menu = new GUI();
//		menu.createGUI();
//		changeScene(primaryStage, menu.returnScene()); //this should prob be done in the GUI class
//		
//		//should prob create level class which contains the map and char models
//		//temp just attempting to generate the map visually atm
//		Map classic = new Map(1);
//		//check map data is initilised
//		for (int i = 0; i < 21; i++) {
//			for (int j = 0;j<21;j++) {
//				int a = classic.getData(i, j);
//				System.out.print(a);
//			}
//			System.out.println("\n");
//		}
//		
//		AnchorPane testPane = new AnchorPane();
//		Scene test = new Scene(testPane,1440,980);
//		test.setFill(Color.LIGHTGREEN);
//		
////		System.out.print(menu.buttonPressed);
////		//while (true) {
////			if (menu.buttonPressed == 1) {
////				System.out.print("420");
////				changeScene(primaryStage,test);
////			}
////		//}
//	}
//	//not sure if this method is high coupling
//	private void changeScene(Stage primaryStage, Scene scene) {
//		primaryStage.setScene(scene);
//		primaryStage.setResizable(false);
//		primaryStage.show();
//	}
//	
//	public static void main(String[] args) {
//		launch(args);
//		
//		
//	}
//}
