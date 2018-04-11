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
	}
}
