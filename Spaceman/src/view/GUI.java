package view;

import java.net.URL;
import java.util.ArrayList;

import controller.InterfaceController;//>??
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
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
import javafx.scene.media.AudioClip;


public class GUI {

	private InterfaceController controller;

	private double SCENE_HEIGHT = 768;
	private double SCENE_WIDTH = 1024.0;
	private Scene scene;

	private int option = 0;
	private ArrayList<ImageView> optionList;
	
	private ArrayList<ImageView> multiplayerOptions;
	private ArrayList<ImageView> playerOptions;

	//	public Clip click;
	//	public Clip cycle;
	//	
	private AudioClip cycle;
	private AudioClip click;

	private Group menu;
	private Group subFrame;
	private Group multiMenu;
	private Group setting;
	
	private DropShadow shadow;
	private GaussianBlur blur;

	public GUI (InterfaceController controller) {
		this.controller = controller;

		AnchorPane root = new AnchorPane(); //Layout based on coordinate from edge
		//root.setStyle("-fx-background-color: null;"); //controls add default css so need to remove
		//addComponents(root); //add nodes to the layout (buttons, text etc)

		shadow = new DropShadow(50, Color.YELLOW);
		blur = new GaussianBlur();
		
		menu = addComponents();
		root.getChildren().add(menu);
		setKeyInput(root);
		
		subFrame = addSubFrame();
		//root.getChildren().add(subFrame);

		BackgroundImage bg = new BackgroundImage(new Image(getClass().getResourceAsStream("bg/test.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		root.setBackground(new Background(bg));
		scene = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT);



		//Load sound clips
		//https://stackoverflow.com/questions/22648793/how-to-play-a-lots-of-sound-effects 
		URL url = this.getClass().getResource("sound/laser1.wav");
		click = new AudioClip(url.toString());
		click.setVolume(0.4);

		url = this.getClass().getResource("sound/sound1.wav");
		cycle = new AudioClip(url.toString());


	}

	public Scene returnScene() {
		return scene;
	}

	private Group addComponents() {
		Group group = new Group();

		optionList = new ArrayList<ImageView>();
		//Setup home screen nodes
		double minDistFromNodes = 50.0;

		//Title
		//Prob implement custom fonts
		Image title = new Image(getClass().getResourceAsStream("bg/title.png"));
		ImageView titleView = new ImageView(title);
		titleView.setX((SCENE_WIDTH-title.getWidth())*0.5);
		titleView.setY(minDistFromNodes);
		titleView.setScaleX(0.75);
		titleView.setScaleY(0.75);
		group.getChildren().add(titleView);
		//not specific to title view i think
		titleView.setFocusTraversable(true); //IMPORTANT, this allows keyevent recognisition w/o the buttons
		//https://stackoverflow.com/questions/31320018/javafx-key-press-not-captured




		Image btn0 = new Image(getClass().getResourceAsStream("bg/btn0.png"));
		ImageView btn0View = new ImageView(btn0);
		btn0View.setEffect(shadow); //Initial choice
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
			optionList.get(i).setY((minDistFromNodes*(i+2)+title.getHeight()*titleView.getScaleY()+optionList.get(i).getLayoutBounds().getHeight()*i*0.4));
			//			optionList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			//			     @Override
			//			     public void handle(MouseEvent event) {
			//			         System.out.println("Tile pressed ");
			//			         optionList.get(option).setEffect(null);
			//			         optionList.get(0).get
			////			         for (int j = 0; j < optionList.size(); j++) {
			////			        	 if option = i;
			////			         }
			////			         optionList.get(i).setEffect(shadow);
			//			     }
			//			});

			group.getChildren().add(optionList.get(i));
		}
		//Can prob be a function
		//key event
		//		pane.setOnKeyPressed(new EventHandler <KeyEvent> () {
		//			@Override
		//			public void handle(KeyEvent event) {
		//				
		////				if (cycle.isRunning()) {
		////					cycle.stop();
		////					cycle.setFramePosition(0);
		////				}
		//			
		//				if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP ) {
		//					if (option> 0) {
		//						//cycle.loop(0); // use insteadof start();
		//						cycle.play();
		//						optionList.get(option).setEffect(null);
		//						option--;
		//					}
		//					
		//				} else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
		//					if (option < 5) {
		//						cycle.play();
		//						optionList.get(option).setEffect(null);
		//						option++;
		//					}
		//					
		//				} else if (event.getCode() == KeyCode.ENTER) {
		//					click.play();
		//					controller.executeProcess(option); 
		//				}
		//				
		//				optionList.get(option).setEffect(shadow);
		//			}
		//
		//		});
		return group;
	}
	private void setKeyInput(AnchorPane root) {
		root.setOnKeyPressed(new EventHandler <KeyEvent> () {
			@Override
			public void handle(KeyEvent event) {

				//					if (cycle.isRunning()) {
				//						cycle.stop();
				//						cycle.setFramePosition(0);
				//					}

				if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP ) {
					if (option> 0) {
						//cycle.loop(0); // use insteadof start();
						cycle.play();
						optionList.get(option).setEffect(null);
						option--;
					}

				} else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
					if (option < 5) {
						cycle.play();
						optionList.get(option).setEffect(null);
						option++;
					}

				} else if (event.getCode() == KeyCode.ENTER) {
					click.play();
					controller.executeProcess(option); 
				}

				optionList.get(option).setEffect(shadow);
			}

		});
	}

	private Group addSubFrame() {
		Group group = new Group();
		
		Rectangle frame = new Rectangle(900,600);
		frame.setFill(Color.BLACK);
		frame.setStroke(Color.WHITE);
		frame.setStrokeWidth(2.0);
		frame.setArcHeight(15);
		frame.setArcWidth(15);
		frame.setX((SCENE_WIDTH-frame.getLayoutBounds().getWidth())*0.5);
		frame.setY((SCENE_HEIGHT-frame.getLayoutBounds().getHeight())*0.5);
		group.getChildren().add(frame);
		
		multiMenu = addMultiplayerOptions(frame);
		group.getChildren().add(multiMenu);
		
		setting = addSettings(frame);
		setting.setVisible(false);
		group.getChildren().add(setting);
		
		
		return group;
	}
	
	private Group addMultiplayerOptions(Rectangle frame) {
		Group group = new Group();
		multiplayerOptions = new ArrayList<ImageView>();
		
		Text label = new Text("Select Number of Players");
		label.setFont(Font.font(50));
		label.setFill(Color.WHITE);
		label.setX((SCENE_WIDTH-label.getLayoutBounds().getWidth())*0.5);
		label.setY(frame.getY()+label.getLayoutBounds().getHeight()+10);
		group.getChildren().add(label);
		
		Image player2 = new Image(getClass().getResourceAsStream("misc/resume.png"));
		ImageView player2Disp = new ImageView(player2);
		player2Disp.setX(frame.getX()+(frame.getLayoutBounds().getWidth()-player2.getWidth())*0.5);
		player2Disp.setY(frame.getY()+(frame.getLayoutBounds().getHeight()-player2.getHeight())*0.5);
		player2Disp.setEffect(shadow);
		multiplayerOptions.add(player2Disp);
		group.getChildren().add(player2Disp);
		
		Image player3 = new Image(getClass().getResourceAsStream("misc/close2.png"));
		ImageView player3Disp = new ImageView(player3);
		player3Disp.setX(frame.getX()+(frame.getLayoutBounds().getWidth()-player2.getWidth())*0.5);
		player3Disp.setY(frame.getY()+(frame.getLayoutBounds().getHeight()-player2.getHeight())*0.5);
		//player3Disp.setEffect(shadow);
		player3Disp.setVisible(false);
		multiplayerOptions.add(player3Disp);
		group.getChildren().add(player3Disp);
		
		Image close = new Image(getClass().getResourceAsStream("misc/close2.png"));
		ImageView closeBtn = new ImageView(close);
		closeBtn.setScaleX(0.3);
		closeBtn.setScaleY(0.3);
		closeBtn.setX((frame.getLayoutBounds().getWidth()-90));
		closeBtn.setY(frame.getY()-50);
		//closeBtn.setEffect(shadow);
		//System.out.println(frame.getX());
		multiplayerOptions.add(closeBtn);
		group.getChildren().add(closeBtn);
		
		return group;
	}
	
	private Group addSettings(Rectangle frame) {
		Group group = new Group();
		
		Text label = new Text("Choose a Theme");
		label.setFont(Font.font(50));
		label.setFill(Color.WHITE);
		label.setX((SCENE_WIDTH-label.getLayoutBounds().getWidth())*0.5);
		label.setY(frame.getY()+label.getLayoutBounds().getHeight()+10);
		group.getChildren().add(label);
		
		return group;
	}

}
