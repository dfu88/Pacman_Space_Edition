package view;

import java.util.ArrayList;

import controller.InterfaceController;//>??

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
//import javafx.scene.shape.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;


public class GUI {

	private InterfaceController controller;

	private double SCENE_HEIGHT = 768;
	private double SCENE_WIDTH = 1024.0;
	private Scene scene;

	private int option = 0;
	private ArrayList<Button> listOptions;

	private ArrayList<ImageView> optionList;


	public GUI (InterfaceController controller) {
		this.controller = controller;

		AnchorPane root = new AnchorPane(); //Layout based on coordinate from edge
		//root.setStyle("-fx-background-color: null;"); //controls add default css so need to remove
		addComponents(root); //add nodes to the layout (buttons, text etc)
		
		BackgroundImage bg = new BackgroundImage(new Image(getClass().getResourceAsStream("bg/test.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		root.setBackground(new Background(bg));
		scene = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT);
		//scene.setFill(Color.DARKBLUE);
//		listOptions = new ArrayList<Button>();
//		optionList = new ArrayList<ImageView>();
	}

	public Scene returnScene() {
		return scene;
	}

	private void addComponents(AnchorPane pane) {
		
		
		listOptions = new ArrayList<Button>();
		optionList = new ArrayList<ImageView>();
		//Setup home screen nodes
		double minWidthFromNodes = 40.0;
		double minHeightFromNodes = 50.0;

		//Title
		//Prob implement custom fonts
		Image title = new Image(getClass().getResourceAsStream("bg/title.png"));
		ImageView titleView = new ImageView(title);
		titleView.setX((SCENE_WIDTH-title.getWidth())*0.5);
		titleView.setY(minHeightFromNodes);
		titleView.setScaleX(0.75);
		titleView.setScaleY(0.75);
//		Text title = new Text();
//		title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,50));//font not changing
//		title.setFill(Color.AZURE);
//		title.setText("Pacman: SPACE Edition");
		titleView.setFocusTraversable(true); //IMPORTANT, this allows keyevent recognisition w/o the buttons
											//https://stackoverflow.com/questions/31320018/javafx-key-press-not-captured
		
		DropShadow shadow = new DropShadow(50, Color.YELLOW);
		
		
		Image btn0 = new Image(getClass().getResourceAsStream("bg/btn0.png"));
		ImageView btn0View = new ImageView(btn0);
		optionList.add(btn0View);
		
		Image btn1 = new Image(getClass().getResourceAsStream("bg/btn1.png"));
		ImageView btn1View = new ImageView(btn1);
		optionList.add(btn1View);
		
		Image btn2 = new Image(getClass().getResourceAsStream("bg/btn2.png"));
		ImageView btn2View = new ImageView(btn2);
		optionList.add(btn2View);
		
		Image btn3 = new Image(getClass().getResourceAsStream("bg/btn3.png"));
		ImageView btn3View = new ImageView(btn3);
		optionList.add(btn3View);
		
		Image btn4 = new Image(getClass().getResourceAsStream("bg/btn4.png"));
		ImageView btn4View = new ImageView(btn4);
		optionList.add(btn4View);
		
		Image btn5 = new Image(getClass().getResourceAsStream("bg/btn5.png"));
		ImageView btn5View = new ImageView(btn5);
		optionList.add(btn5View);
		
		for (int i = 0; i < optionList.size(); i++) {
			optionList.get(i).setScaleX(0.4);
			optionList.get(i).setScaleY(0.4);
			optionList.get(i).setX((SCENE_WIDTH-optionList.get(i).getLayoutBounds().getWidth())*0.5);
			optionList.get(i).setY((minHeightFromNodes*(i+2)+title.getHeight()*titleView.getScaleY()+optionList.get(i).getLayoutBounds().getHeight()*i*0.4));
//			optionList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//			     @Override
//			     public void handle(MouseEvent event) {
//			         System.out.println("Tile pressed ");
//			         optionList.get(option).setEffect(null);
//			         for (int j = 0; j < optionList.size(); j++) {
//			        	 if option = i;
//			         }
//			         optionList.get(i).setEffect(shadow);
//			         //event.consume();
//			     }
//			});
			pane.getChildren().add(optionList.get(i));
		}
		
		
		btn0View.setEffect(shadow);
		pane.getChildren().add(titleView);
//		pane.getChildren().add(btn0View);
//		pane.getChildren().add(btn1View);
//		pane.getChildren().add(btn2View);
//		pane.getChildren().add(btn3View);
//		pane.getChildren().add(btn4View);
//		pane.getChildren().add(btn5View);
		
//		AnchorPane.setTopAnchor(title, minHeightFromNodes);
//		AnchorPane.setLeftAnchor(title, (SCENE_WIDTH-title.getWidth())*0.5);
////		AnchorPane.setLeftAnchor(title, (SCENE_WIDTH-title.getLayoutBounds().getWidth())*0.5);
////		AnchorPane.setRightAnchor(title, (SCENE_WIDTH-title.getLayoutBounds().getWidth())*0.5);
//		AnchorPane.setBottomAnchor(title, SCENE_HEIGHT - minHeightFromNodes);

		//Buttons
		//Could use shape/image instead of button
		Button button1 = new Button("Story Mode");
		button1.setPrefWidth(300.0);
		button1.setPrefHeight(50.0);
		
		AnchorPane.setTopAnchor(button1, minHeightFromNodes*2+title.getHeight());
		AnchorPane.setBottomAnchor(button1, SCENE_HEIGHT-title.getHeight()-button1.getPrefHeight()-(minHeightFromNodes)*2);
		//AnchorPane.setTopAnchor(button1, minHeightFromNodes*2+title.getLayoutBounds().getHeight());
		//AnchorPane.setBottomAnchor(button1, SCENE_HEIGHT-title.getLayoutBounds().getHeight()-button1.getPrefHeight()-(minHeightFromNodes)*2);
		AnchorPane.setLeftAnchor(button1, (SCENE_WIDTH-button1.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button1, (SCENE_WIDTH-button1.getPrefWidth())*0.5);

		button1.setOnAction(event -> {controller.executeProcess(0);});
		listOptions.add(button1);


		Button button2 = new Button("Classic Mode");
		button2.setPrefWidth(300.0);
		button2.setPrefHeight(50.0);
		
		AnchorPane.setTopAnchor(button2, minHeightFromNodes*3+title.getHeight()+button2.getPrefHeight());
		AnchorPane.setBottomAnchor(button2, SCENE_HEIGHT-title.getHeight()-button1.getPrefHeight()*2-(minHeightFromNodes)*3);
		//AnchorPane.setTopAnchor(button2, minHeightFromNodes*3+title.getLayoutBounds().getHeight()+button2.getPrefHeight());
		//AnchorPane.setBottomAnchor(button2, SCENE_HEIGHT-title.getLayoutBounds().getHeight()-button1.getPrefHeight()*2-(minHeightFromNodes)*3);
		AnchorPane.setLeftAnchor(button2, (SCENE_WIDTH-button2.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button2, (SCENE_WIDTH-button2.getPrefWidth())*0.5);

		button2.setOnAction(event -> {controller.executeProcess(1);});
		listOptions.add(button2);

		Button button3 = new Button("Multiplayer Mode");
		button3.setPrefWidth(300.0);
		button3.setPrefHeight(50.0);

		AnchorPane.setTopAnchor(button3, minHeightFromNodes*4+title.getHeight()+button2.getPrefHeight()*2);
		AnchorPane.setBottomAnchor(button3, SCENE_HEIGHT-title.getHeight()-button1.getPrefHeight()*3-(minHeightFromNodes)*4);
		//AnchorPane.setTopAnchor(button3, minHeightFromNodes*4+title.getLayoutBounds().getHeight()+button2.getPrefHeight()*2);
		//AnchorPane.setBottomAnchor(button3, SCENE_HEIGHT-title.getLayoutBounds().getHeight()-button1.getPrefHeight()*3-(minHeightFromNodes)*4);
		AnchorPane.setLeftAnchor(button3, (SCENE_WIDTH-button3.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button3, (SCENE_WIDTH-button3.getPrefWidth())*0.5);

		listOptions.add(button3);
		button3.setOnAction(event -> {controller.executeProcess(2);});


		Button button4 = new Button("Endless Mode");
		button4.setPrefWidth(300.0);
		button4.setPrefHeight(50.0);

		AnchorPane.setBottomAnchor(button4, SCENE_HEIGHT-title.getHeight()-button1.getPrefHeight()*4-(minHeightFromNodes)*5);
		//AnchorPane.setBottomAnchor(button4, SCENE_HEIGHT-title.getLayoutBounds().getHeight()-button1.getPrefHeight()*4-(minHeightFromNodes)*5);
		AnchorPane.setLeftAnchor(button4, (SCENE_WIDTH-button4.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button4, (SCENE_WIDTH-button4.getPrefWidth())*0.5);

		button4.setOnAction(event -> {controller.executeProcess(3);});
		listOptions.add(button4);

		Button button5 = new Button("Random Mode");
		button5.setPrefWidth(300.0);
		button5.setPrefHeight(50.0);
		
		AnchorPane.setTopAnchor(button5, minHeightFromNodes*6+title.getHeight()+button2.getPrefHeight()*4);
		AnchorPane.setBottomAnchor(button5, SCENE_HEIGHT-title.getHeight()-button1.getPrefHeight()*5-(minHeightFromNodes)*6);
		//AnchorPane.setTopAnchor(button5, minHeightFromNodes*6+title.getLayoutBounds().getHeight()+button2.getPrefHeight()*4);
		//AnchorPane.setBottomAnchor(button5, SCENE_HEIGHT-title.getLayoutBounds().getHeight()-button1.getPrefHeight()*5-(minHeightFromNodes)*6);
		AnchorPane.setLeftAnchor(button5, (SCENE_WIDTH-button5.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button5, (SCENE_WIDTH-button5.getPrefWidth())*0.5);

		button5.setOnAction(event -> {controller.executeProcess(4);});
		listOptions.add(button5);

		Button button6 = new Button("Leaderboards");
		button6.setPrefWidth(300.0);
		button6.setPrefHeight(50.0);
		
		
		AnchorPane.setTopAnchor(button6, minHeightFromNodes*7+title.getHeight()+button2.getPrefHeight()*5);
		AnchorPane.setBottomAnchor(button6, SCENE_HEIGHT-title.getHeight()-button1.getPrefHeight()*6-(minHeightFromNodes)*7);
		//AnchorPane.setTopAnchor(button6, minHeightFromNodes*7+title.getLayoutBounds().getHeight()+button2.getPrefHeight()*5);
		//AnchorPane.setBottomAnchor(button6, SCENE_HEIGHT-title.getLayoutBounds().getHeight()-button1.getPrefHeight()*6-(minHeightFromNodes)*7);
		AnchorPane.setLeftAnchor(button6, (SCENE_WIDTH-button6.getPrefWidth())*0.5);
		AnchorPane.setRightAnchor(button6, (SCENE_WIDTH-button6.getPrefWidth())*0.5);

		button6.setOnAction(event -> {controller.executeProcess(5);});
		listOptions.add(button6);

		//key event
		pane.setOnKeyPressed(new EventHandler <KeyEvent> () {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP ) {
					if (option> 0) {
						optionList.get(option).setEffect(null);
						//listOptions.get(option).setVisible(true);
						option--;
						System.out.print("changed option (up)");
						System.out.println(option);

					}
				} else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
					if (option < 5) {
						optionList.get(option).setEffect(null);
						//listOptions.get(option).setVisible(true);
						option++;
						System.out.print("changed option (down)");
						System.out.println(option);
						//button1.setVisible(false);
					}
				} else if (event.getCode() == KeyCode.ENTER) {
					System.out.print("Generate gameoption");
					System.out.println(option);
					controller.executeProcess(option); //prob change process in controller to 0 to 5 instead of 1 to 6
				}
				//listOptions.get(option).setVisible(false);
				optionList.get(option).setEffect(shadow);
			}

		});

		//pane.getChildren().add(title);
		//pane.getChildren().add(titleView);
//		pane.getChildren().add(button1);
//		pane.getChildren().add(button2);
//		pane.getChildren().add(button3);
//		pane.getChildren().add(button4);
//		pane.getChildren().add(button5);
//		pane.getChildren().add(button6);
	}
}
