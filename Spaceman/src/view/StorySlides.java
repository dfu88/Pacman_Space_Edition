package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import controller.LevelController;

import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;

//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;


public class StorySlides {

	private double SCENE_WIDTH = 1440;
	private double SCENE_HEIGHT = 900;

	private LevelController controller;

	private Scene scene;

	private Group root;
	private Group pauseMenu;
	private Group exitPopUp;

	private Group scenario1;
	private Group protagDisp;
	private Group otherCharDisp;

	private GaussianBlur blur;
	private DropShadow shadow;

	private AudioClip cycle;

	private ArrayList<ImageView> exitOptions;
	private Text currentDialogue;
	
	private ArrayList<String> dialogueList1;
	private ArrayList<String> dialogueList2;
	private ArrayList<String> dialogueList3;
	private int dialogueIndex = 0;

	public StorySlides (LevelController controller) {
		this.controller = controller;

		exitOptions = new ArrayList<ImageView>();

		blur = new GaussianBlur();
		shadow = new DropShadow(500, Color.YELLOW);

		URL url = this.getClass().getResource("sound/sound1.wav");
		cycle = new AudioClip(url.toString());

		//Setup Scene for game visuals
		root = new Group(); 
		scene = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT);
		scene.setFill(Color.BLACK);

		setUpKeyInput(scene);
	}

	public void generateScenario() {
		exitOptions.clear();
		root.getChildren().clear();

		Image bg = new Image(getClass().getResourceAsStream("bg/earthsurface.jpeg"));
		ImageView bgView = new ImageView(bg);
		//		bg.
		//		bg.setScaleY(0.7);
		root.getChildren().add(bgView);

		scenario1 = addScenario();
		root.getChildren().add(scenario1);

		pauseMenu = addPauseMenu();
		pauseMenu.setVisible(false);
		root.getChildren().add(pauseMenu);

		exitPopUp = addExitPopUp();
		exitPopUp.setVisible(false);
		root.getChildren().add(exitPopUp);
		
		protagDisp = addProtag();
		root.getChildren().add(protagDisp);		
		//add text here
		dialogueList1 = new ArrayList<String>();
		dialogueList1.add("hello");
		dialogueList1.add("i");
		dialogueList1.add("am");
		dialogueList1.add("bob");
	}	

	private Group addScenario() {
		// TODO Auto-generated method stub
		Group group = new Group();

		Rectangle dialogueBox = new Rectangle(SCENE_WIDTH*0.9,SCENE_HEIGHT/3);
		dialogueBox.setFill(Color.BLACK);
		dialogueBox.setStroke(Color.WHITE);
		dialogueBox.setStrokeWidth(2.0);
		dialogueBox.setArcHeight(15);
		dialogueBox.setArcWidth(15);
		dialogueBox.setX((SCENE_WIDTH-dialogueBox.getLayoutBounds().getWidth())*0.5);
		dialogueBox.setY((SCENE_HEIGHT-dialogueBox.getLayoutBounds().getHeight()));
		group.getChildren().add(dialogueBox);

		Text dialogue = new Text("asdasdd");
		dialogue.setFont(Font.font(25));
		dialogue.setFill(Color.WHITE);
		dialogue.setX((SCENE_WIDTH-dialogue.getLayoutBounds().getWidth())*0.5);
		dialogue.setY(dialogueBox.getY()+dialogue.getLayoutBounds().getHeight()); //??
		currentDialogue = dialogue;
		group.getChildren().add(dialogue);

		return group;
	}

	private Group addPauseMenu() {
		Group group = new Group();

		Rectangle frame = new Rectangle(SCENE_WIDTH,SCENE_HEIGHT/3);
		frame.setFill(Color.BLACK);
		frame.setStroke(Color.WHITE);
		frame.setStrokeWidth(2.0);
		frame.setArcHeight(15);
		frame.setArcWidth(15);
		frame.setX((SCENE_WIDTH-frame.getLayoutBounds().getWidth())*0.5);
		frame.setY((SCENE_HEIGHT-frame.getLayoutBounds().getHeight())*0.5);
		group.getChildren().add(frame);

		Image paused = new Image(getClass().getResourceAsStream("misc/paused.png"));
		ImageView pausedLabel = new ImageView(paused);
		pausedLabel.setX((SCENE_WIDTH-pausedLabel.getLayoutBounds().getWidth())*0.5);
		pausedLabel.setY(frame.getY()+pausedLabel.getLayoutBounds().getHeight()*0.25);
		//resumeButton.setEffect(shadow);
		group.getChildren().add(pausedLabel);

		Text label = new Text("Press 'P' to Resume the Level");
		label.setFont(Font.font(50));
		label.setFill(Color.WHITE);
		label.setX((SCENE_WIDTH-label.getLayoutBounds().getWidth())*0.5);
		label.setY(frame.getY()+frame.getLayoutBounds().getHeight()-label.getLayoutBounds().getHeight());
		group.getChildren().add(label);
		
		
		return group;
	}

	private Group addProtag() {
		// TODO Auto-generated method stub
		Group group = new Group();
		
		Image protag = new Image(getClass().getResourceAsStream("res/left1.png"));
		ImageView protagView = new ImageView(protag);
		protagView.setX(50);
		protagView.setY(SCENE_HEIGHT - 300);
		//resumeButton.setEffect(shadow);
		group.getChildren().add(protagView);
		
		//include images for facial exp eg.angry happy
		
		
		return group;
	}

	private Group addExitPopUp() {
		Group group = new Group();

		Rectangle frame = new Rectangle(SCENE_WIDTH/2.5,SCENE_HEIGHT/2.5);
		frame.setFill(Color.BLACK);
		frame.setStroke(Color.WHITE);
		frame.setStrokeWidth(2.0);
		frame.setArcHeight(15);
		frame.setArcWidth(15);
		frame.setX((SCENE_WIDTH-frame.getLayoutBounds().getWidth())*0.5);
		frame.setY((SCENE_HEIGHT-frame.getLayoutBounds().getHeight())*0.5);
		group.getChildren().add(frame);

		Text label = new Text("Are You Sure \nYou Want To Leave \nThis Level?");
		label.setFont(Font.font(50));
		label.setFill(Color.WHITE);
		label.setX((SCENE_WIDTH-label.getLayoutBounds().getWidth())*0.5);
		label.setY(frame.getY()+100);
		group.getChildren().add(label);

		Image no = new Image(getClass().getResourceAsStream("misc/no.png"));
		ImageView noLabel = new ImageView(no);
		noLabel.setEffect(shadow);
		exitOptions.add(noLabel);


		Image yes = new Image(getClass().getResourceAsStream("misc/yes.png"));
		ImageView yesLabel = new ImageView(yes);
		exitOptions.add(yesLabel);

		for (int i = 0; i < exitOptions.size(); i++) {
			exitOptions.get(i).setX(frame.getX()+(i+1)*50+i*yes.getWidth());
			exitOptions.get(i).setY(frame.getY()+frame.getLayoutBounds().getHeight()-no.getHeight()*1.5);
			group.getChildren().add(exitOptions.get(i));
		}
		//		pausedLabel.setX((SCENE_WIDTH-pausedLabel.getLayoutBounds().getWidth())*0.5);
		//		pausedLabel.setY(frame.getY()+pausedLabel.getLayoutBounds().getHeight()*0.25);
		//resumeButton.setEffect(shadow);
		//pauseOptions.add(pausedLabel);
		//		group.getChildren().add(pausedLabel);



		return group;
	}


	public Scene returnScene() {
		return scene;
	}

	public void updatePauseScreen(boolean wasPaused) {

		if (wasPaused) {
			scenario1.setEffect(blur);
			pauseMenu.setVisible(true);

		} else {
			scenario1.setEffect(null);
			pauseMenu.setVisible(false);

		}

	}

	public void updateExitScreen(boolean exitScreenOn) {

		if (exitScreenOn) {
			scenario1.setEffect(blur);
			exitPopUp.setVisible(true);

		} else {
			scenario1.setEffect(null);
			exitPopUp.setVisible(false);

		}

	}

	public void cycleOptions(int option) {
		if (option == 0) {
			exitOptions.get(option).setEffect(shadow);
			exitOptions.get(option+1).setEffect(null);
		} else if (option == 1) {
			exitOptions.get(option).setEffect(shadow);
			exitOptions.get(option-1).setEffect(null);
		}
	}


	public void playCycleSound( ) {
		cycle.play();
	}

	public void stopCycleClip( ) {
		if (cycle.isPlaying()) {
			cycle.stop();
		}
	}
	
	private void updateDialogue(String text) {
		currentDialogue.setText(text);
	}
	
	private void setUpKeyInput(Scene scene) {

		scene.setOnKeyPressed(new EventHandler <KeyEvent> () {
			

			public void handle(KeyEvent input) {

				//temp, trying to get cycle sound to work consistently
				stopCycleClip();

				if (input.getCode() == KeyCode.LEFT) {
					//When in exit screen, controls option selection instead
					if (exitPopUp.isVisible()) {
						if (controller.exitOption > 0) {
							controller.exitOption--;
							playCycleSound();
						}
						cycleOptions(controller.exitOption);

					} else {
						//spaceman.setKeyInput(0);

					}

				} else if(input.getCode() == KeyCode.RIGHT) {
					//When in exit screen controls option selection instead
					if (exitPopUp.isVisible()) {
						if (controller.exitOption < 1) {
							controller.exitOption++;
							playCycleSound();
						}
						cycleOptions(controller.exitOption);

					} else {
						//spaceman.setKeyInput(2);

					}

				} else if(input.getCode() == KeyCode.BACK_SPACE) {
					playCycleSound();
					if (dialogueIndex > 0) {
						dialogueIndex--;
						updateDialogue(dialogueList1.get(dialogueIndex));
						
					}
				} else if(input.getCode() == KeyCode.ENTER) {
				
					playCycleSound();
					//When in exit screen, executes selected options instead
					if (exitPopUp.isVisible()) {		
						//Quits the game if yes is selected, otherwise will go back to pause menu
						if (controller.exitOption == 1){
							controller.timeline.stop();

							//Resets initial level states //consider an init() func instead
							controller.resetToStartState();
							controller.levelWins = 0;
							//						paused = !paused;
						} else {
							pauseMenu.setEffect(null);
						}
						//					exitScreenOn = !exitScreenOn;
						//					currentView.updateExitScreen(exitScreenOn);
						exitPopUp.setVisible(false);
					} else  if (!pauseMenu.isVisible()){
						if (dialogueIndex < dialogueList1.size()-1) {
							dialogueIndex++;
							updateDialogue(dialogueList1.get(dialogueIndex));
						} else if (dialogueIndex == dialogueList1.size()-1) {
							//go back to game
							System.out.println("gasdsada");
						}
					}
				} else if(input.getCode() == KeyCode.P) {
					playCycleSound();
					//Toggles pause screen when not in the exit screen
					if (!exitPopUp.isVisible()) {
						controller.paused = !controller.paused;
						controlPause();
					}

				} else if (input.getCode() == KeyCode.ESCAPE) {
					//Turns on/off exit screen and pauses if not already
					if (!exitPopUp.isVisible()) {
						controller.paused = true;
						controlPause();
						pauseMenu.setEffect(blur);

					} else {
						pauseMenu.setEffect(null);
					}
					playCycleSound();
					exitPopUp.setVisible(!exitPopUp.isVisible());
					//.updateExitScreen(exitScreenOn);
				}
			}
		});
	}
	public void controlPause() {
		updatePauseScreen(controller.paused);
	}

	public void setScenario(int levelWins) {
		// TODO Auto-generated method stub
		
	}
}
