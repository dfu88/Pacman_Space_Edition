package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import controller.InterfaceController;//>??

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.text.*;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;

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
	private ArrayList<ImageView> optionList;
	
	public Clip click;
	public Clip cycle;


	public GUI (InterfaceController controller) {
		this.controller = controller;

		AnchorPane root = new AnchorPane(); //Layout based on coordinate from edge
		//root.setStyle("-fx-background-color: null;"); //controls add default css so need to remove
		addComponents(root); //add nodes to the layout (buttons, text etc)
		
		BackgroundImage bg = new BackgroundImage(new Image(getClass().getResourceAsStream("bg/test.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		root.setBackground(new Background(bg));
		scene = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT);
		
		//Get Sounds and store them in field variables
		try {
			URL url = this.getClass().getResource("sound/laser.wav");
			AudioInputStream sound = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(sound);
			click = clip; 
			
			url = this.getClass().getResource("sound/sound1.wav");
			AudioInputStream sound2 = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(sound2);
			cycle = clip;
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch blockaudioIn
			e.printStackTrace();
		}
	}

	public Scene returnScene() {
		return scene;
	}

	private void addComponents(AnchorPane pane) {
		
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
		pane.getChildren().add(titleView);
		//not specific to title view i think
		titleView.setFocusTraversable(true); //IMPORTANT, this allows keyevent recognisition w/o the buttons
											//https://stackoverflow.com/questions/31320018/javafx-key-press-not-captured
		
		DropShadow shadow = new DropShadow(50, Color.YELLOW);
		
		
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
			pane.getChildren().add(optionList.get(i));
		}
		
		//Can prob be a function
		//key event
		pane.setOnKeyPressed(new EventHandler <KeyEvent> () {
			@Override
			public void handle(KeyEvent event) {
				
				if (cycle.isRunning()) {
					cycle.stop();
					cycle.setFramePosition(0);
				}
			
				if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP ) {
					if (option> 0) {
						cycle.loop(0); // use insteadof start();
						optionList.get(option).setEffect(null);
						option--;
					}
					
				} else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
					if (option < 5) {
						cycle.loop(0);
						optionList.get(option).setEffect(null);
						option++;
					}
					
				} else if (event.getCode() == KeyCode.ENTER) {
					System.out.print("Generate gameoption");
					System.out.println(option);
					click.setFramePosition(0);
					click.loop(0);
					controller.executeProcess(option); 
				}
				
				optionList.get(option).setEffect(shadow);
				
			}

		});
	}
}
