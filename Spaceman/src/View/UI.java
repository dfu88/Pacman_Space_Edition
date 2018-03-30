package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javafx.scene.shape.*;
import javafx.scene.text.*;

public class UI extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		//try {

		//NOTE: bottom and right anchor panes probably not needed if the window is not resizable

		//NOTE FOR SOME REASON SETFILL DOES NOT WORK WITH BUTTONS
		//solved: https://stackoverflow.com/questions/36275060/transparent-scene-and-stage-does-not-work-with-buttons-in-javafx

		//Level
		AnchorPane rootLarge = new AnchorPane();
		rootLarge.setStyle("-fx-background-color: null;");
		Scene level = new Scene(rootLarge, 1440, 990);
		level.setFill(Color.LIGHTGREEN);

		//Group root = new Group();
		AnchorPane root = new AnchorPane();
		root.setStyle("-fx-background-color: null;"); //controls add default css so need to remove
		double sceneWidth = 1024.0;
		double sceneHeight = 768.0;
		Scene home = new Scene(root,sceneWidth,sceneHeight);
		home.setFill(Color.DARKBLUE);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		//Setup home screen nodes
		double minWidthFromNodes = 40.0;
		double minHeightFromNodes = 40.0;

		//Title
		//Prob implement custom fonts
		Text title = new Text();
		title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,50));//font not changing
		title.setText("Pacman: SPACE Edition");
		AnchorPane.setTopAnchor(title, minHeightFromNodes);
		AnchorPane.setLeftAnchor(title, (sceneWidth-title.getLayoutBounds().getWidth())*0.5);
		AnchorPane.setRightAnchor(title, (sceneWidth-title.getLayoutBounds().getWidth())*0.5);
		AnchorPane.setBottomAnchor(title, sceneHeight - minHeightFromNodes);

		//Buttons
		//Could use shape/image instead of button
		Button button1 = new Button("Story Mode");
		//should prevent changing size when window size changes but doesn't
		//dont need minmax if not resizble i think
		button1.setPrefWidth(300.0);
		button1.setMaxWidth(300.0);
		button1.setMinWidth(300.0);
		button1.setPrefHeight(50.0);
		button1.setMaxHeight(50.0);
		button1.setMinHeight(50.0);

		AnchorPane.setTopAnchor(button1, minHeightFromNodes*2+title.getLayoutBounds().getHeight());
		AnchorPane.setBottomAnchor(button1, sceneHeight-title.getLayoutBounds().getHeight()-button1.getPrefHeight()-(minHeightFromNodes)*2);
		AnchorPane.setLeftAnchor(button1, (sceneWidth-button1.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button1, (sceneWidth-button1.getPrefWidth())*0.5);

		button1.setOnAction(event -> primaryStage.setScene(level));

		Button button2 = new Button("Classic Mode");
		//should prevent changing size when window size changes but doesn't
		//dont need minmax if not resizble i think
		button2.setPrefWidth(300.0);
		button2.setMaxWidth(300.0);
		button2.setMinWidth(300.0);
		button2.setPrefHeight(50.0);
		button2.setMaxHeight(50.0);
		button2.setMinHeight(50.0);

		AnchorPane.setTopAnchor(button2, minHeightFromNodes*3+title.getLayoutBounds().getHeight()+button2.getPrefHeight());
		AnchorPane.setBottomAnchor(button2, sceneHeight-title.getLayoutBounds().getHeight()-button1.getPrefHeight()*2-(minHeightFromNodes)*3);
		AnchorPane.setLeftAnchor(button2, (sceneWidth-button2.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button2, (sceneWidth-button2.getPrefWidth())*0.5);

		button2.setOnAction(event -> System.out.println("Test Button 2"));

		Button button3 = new Button("Multiplayer Mode");
		//should prevent changing size when window size changes but doesn't
		//dont need minmax if not resizble i think
		button3.setPrefWidth(300.0);
		button3.setMaxWidth(300.0);
		button3.setMinWidth(300.0);
		button3.setPrefHeight(50.0);
		button3.setMaxHeight(50.0);
		button3.setMinHeight(50.0);

		AnchorPane.setTopAnchor(button3, minHeightFromNodes*4+title.getLayoutBounds().getHeight()+button2.getPrefHeight()*2);
		AnchorPane.setBottomAnchor(button3, sceneHeight-title.getLayoutBounds().getHeight()-button1.getPrefHeight()*3-(minHeightFromNodes)*4);
		AnchorPane.setLeftAnchor(button3, (sceneWidth-button3.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button3, (sceneWidth-button3.getPrefWidth())*0.5);


		Button button4 = new Button("Endless Mode");
		//should prevent changing size when window size changes but doesn't
		//dont need minmax if not resizble i think
		button4.setPrefWidth(300.0);
		button4.setMaxWidth(300.0);
		button4.setMinWidth(300.0);
		button4.setPrefHeight(50.0);
		button4.setMaxHeight(50.0);
		button4.setMinHeight(50.0);

		AnchorPane.setBottomAnchor(button4, sceneHeight-title.getLayoutBounds().getHeight()-button1.getPrefHeight()*4-(minHeightFromNodes)*5);
		AnchorPane.setLeftAnchor(button4, (sceneWidth-button4.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button4, (sceneWidth-button4.getPrefWidth())*0.5);



		Button button5 = new Button("Random Mode");
		//should prevent changing size when window size changes but doesn't
		//dont need minmax if not resizble i think
		button5.setPrefWidth(300.0);
		button5.setMaxWidth(300.0);
		button5.setMinWidth(300.0);
		button5.setPrefHeight(50.0);
		button5.setMaxHeight(50.0);
		button5.setMinHeight(50.0);

		AnchorPane.setTopAnchor(button5, minHeightFromNodes*6+title.getLayoutBounds().getHeight()+button2.getPrefHeight()*4);
		AnchorPane.setBottomAnchor(button5, sceneHeight-title.getLayoutBounds().getHeight()-button1.getPrefHeight()*5-(minHeightFromNodes)*6);
		AnchorPane.setLeftAnchor(button5, (sceneWidth-button5.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button5, (sceneWidth-button5.getPrefWidth())*0.5);


		Button button6 = new Button("Leaderboards");
		//should prevent changing size when window size changes but doesn't
		//dont need minmax if not resizble i think
		button6.setPrefWidth(300.0);
		button6.setMaxWidth(300.0);
		button6.setMinWidth(300.0);
		button6.setPrefHeight(50.0);
		button6.setMaxHeight(50.0);
		button6.setMinHeight(50.0);

		AnchorPane.setTopAnchor(button6, minHeightFromNodes*7+title.getLayoutBounds().getHeight()+button2.getPrefHeight()*5);
		AnchorPane.setBottomAnchor(button6, sceneHeight-title.getLayoutBounds().getHeight()-button1.getPrefHeight()*6-(minHeightFromNodes)*7);
		AnchorPane.setLeftAnchor(button6, (sceneWidth-button6.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button6, (sceneWidth-button6.getPrefWidth())*0.5);


		//Add the nodes to the scene
		root.getChildren().add(title);
		root.getChildren().add(button1);
		root.getChildren().add(button2);
		root.getChildren().add(button3);
		root.getChildren().add(button4);
		root.getChildren().add(button5);
		root.getChildren().add(button6);







		primaryStage.setTitle("PACMAN: SPACE Edition");
		primaryStage.setScene(home);
		primaryStage.setResizable(false);//temp fix to resizing issue
		primaryStage.show();

		//} catch(Exception e) {
		//	e.printStackTrace();
		//}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
