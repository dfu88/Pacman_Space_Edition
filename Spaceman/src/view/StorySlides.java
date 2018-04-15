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

	private Group scenario;
	private Group protagDisp;
	private Group otherCharDisp;
	
	private ImageView char1;
	private ImageView char2;

	private GaussianBlur blur;
	private DropShadow shadow;

	private AudioClip cycle;

	private ArrayList<ImageView> exitOptions;
	private Text currentDialogue;
	
	private ArrayList<String> currentDialogueSet;
	private ArrayList<ArrayList<String>> listOfDialogue;

	private int dialogueIndex = -1;

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
		
		
		listOfDialogue = new ArrayList<ArrayList<String>>();
		currentDialogueSet = new ArrayList<String>();
		setUpDialogue();
	}

	public void generateScenario(int levelWins) {
		exitOptions.clear();
		root.getChildren().clear();

		Image bg = new Image(getClass().getResourceAsStream("bg/earthsurface.jpeg"));
		ImageView bgView = new ImageView(bg);
		//		bg.
		//		bg.setScaleY(0.7);
		root.getChildren().add(bgView);



		pauseMenu = addPauseMenu();
		pauseMenu.setVisible(false);
		root.getChildren().add(pauseMenu);

		exitPopUp = addExitPopUp();
		exitPopUp.setVisible(false);
		root.getChildren().add(exitPopUp);

		otherCharDisp = addChar();
//		otherCharDisp.setVisible(false);
		root.getChildren().add(otherCharDisp);
		
		protagDisp = addProtag();
		protagDisp.setVisible(false);
		root.getChildren().add(protagDisp);	

		
		
//		otherCharDisp2 = addChar();
//		otherCharDisp2.setVisible(false);
//		root.getChildren().add(otherCharDisp2);

		scenario = addScenario();
		root.getChildren().add(scenario);
		
		currentDialogueSet = listOfDialogue.get(levelWins);


	}	


	private void setUpDialogue() {
		//Opening Scene
		ArrayList<String> dialogueSet1 = new ArrayList<String>();
		dialogueSet1.add("All was peaceful in the world. In our planet \'Meow Meow Nebula\'\n"  
				+ "\"420 Supreme\", the people lived their lives with pride and joy as usual.\n");
		dialogueSet1.add("However, that wouldn't last for long. Over the past few years, the 'NRG'"
				+ "\ncore powering our planet has been dying out. Explorers have gone out to space to"
				+ "try recover this 'NRG' essence to restore the crystal. Unfortuantely, none of them have"
				+ "returned to this day.");

		dialogueSet1.add("Bob here was just your everyday kid who did what other cool and hip youngster do."
				+ "\nOne day when he was watching episode 69 of \"My Little Horsey\", he heard a loud \nnoise!");

		dialogueSet1.add("*KKKAAAABOOOOOOMASDLKKANALSLSASASHGASSA* (sound effects btw)");
		dialogueSet1.add("Bob: \"What was that?\"");
		dialogueSet1.add("Bob looked up into the near distance where he saw chaos brewing."
				+ "\nPeople were panicking in the confusion where there was bright light glowing.");
		dialogueSet1.add("Bob: \"No way, the crystal powering our world blew up! "
				+ "We've got to find \nmore 'NRG' to fix our crystal.\"");
		dialogueSet1.add("Bob knew that if there was a time that \'Meow Meow Nebula 420 Supreme\'"
				+ " \nneeded a hero. \nIt was now.");
		dialogueSet1.add("Bob: \"It's Go Time Lads!\"");
		dialogueSet1.add("Bob flew out to outerspace to collect 'NRG' essence, however"
				+ " something \nstopped him at 'No Pac-Man Lands' on his way there...");

		//Second scene
		ArrayList<String> dialogueSet2 = new ArrayList<String>();
		dialogueSet2.add("Finally, after getting through the \"No Pac-Man Lands\","
				+ " Bob landed safely \non the closet planet \'Lu-E Viton\' where the \'NRG\'"
				+ " essence was originally found.");
		dialogueSet2.add("Bob: \"Ugh! That was rough.\"");
		dialogueSet2.add("Bob: \"What were those things that attacked us? We've never found any \nother lifeforms"
				+ " in our solar system! Hopefully, I don't run into those things again."
				+ "\nI don't think my gear can take any more hits.\"");
		dialogueSet2.add("Mysterious Being: *Loud Obnoxious Unintelligible Sounds*");
		dialogueSet2.add("Bob: \"Guess I spoke too soon\"");

		//Third scene
		ArrayList<String> dialogueSet3 = new ArrayList<String>();
		dialogueSet3.add("After successfully fending off those strange creatures, Bob's equipment"
				+ "\nand ship were damaged.");
		dialogueSet3.add("Bob: \"Heck! I can't get back home unless I find material to fix my stuff."
				+ "\nNo wonder why our planet never had another \'NRG\' core. The outer space is way "
				+ "too dangerous with all those creatures out there. It's almost as if they're out to get us.\"");
		dialogueSet3.add("Mysterious Being: \"Mwuahahah. Right you are!\"");
		dialogueSet3.add("Bob: \"Huh? Who there?\"");
		dialogueSet3.add("Steve the Conqueror: \"AHAHA! I am Steve the Conqueror and WE are here to"
				+ "\nsteal that 'NRG' essence from you, just like the others!\"");
		dialogueSet3.add("Bob: \"Others? You mean all those missing explorers are your doing?\"");
		dialogueSet3.add("Steve the Conqueror: \"And you're next, my dude.\"");

		//Conclusion
		ArrayList<String> dialogueSet4 = new ArrayList<String>();
		dialogueSet4.add("Bob successfully defeated Steve and his army of aliens. With that, Bob "
				+ "can \nfinally return home and restore the \'NRG\' crystal and save the planet");
		dialogueSet4.add("Steve the Defeated: \"Arggghhh! I-Impossible! How could I be defeated by"
				+ " a kid?\"");
		dialogueSet4.add("Bob: No matter how bad the situation is, Heroes will always find a way"
				+ "\nto save the day!\"");
		dialogueSet4.add("Bob: \"Remember kids, stay in school and don't do Java.\"");
		dialogueSet4.add("The End");
		
		listOfDialogue.add(dialogueSet1);
		listOfDialogue.add(dialogueSet2);
		listOfDialogue.add(dialogueSet3);
		listOfDialogue.add(dialogueSet4);
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

		Text dialogue = new Text("Press Enter for next dialogue. \n Backspace for previous dialogue.");
		dialogue.setFont(Font.font(30));
		dialogue.setFill(Color.WHITE);

		//maybe centre instead
		dialogue.setX(dialogueBox.getX()+20);
		dialogue.setY(dialogueBox.getY()+50); //??
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

		Image protag = new Image(getClass().getResourceAsStream("res/hBeast.png"));
		ImageView protagView = new ImageView(protag);
		protagView.setX(50);
		protagView.setY(SCENE_HEIGHT - 700);
		//		protagView.setScaleX(-10);
		//		protagView.setScaleY(10);
		//resumeButton.setEffect(shadow);
		group.getChildren().add(protagView);

		//include images for facial exp eg.angry happy


		return group;
	}

	private Group addChar() {
		// TODO Auto-generated method stub
		Group group = new Group();
		
		Image hidden = new Image(getClass().getResourceAsStream("res/hiddenChar.png"));
		ImageView hiddenChar = new ImageView(hidden);
		hiddenChar.setX(SCENE_WIDTH-hidden.getWidth()-50);
		hiddenChar.setY(SCENE_HEIGHT - 700);
		//		protagView.setScaleX(-10);
		//		protagView.setScaleY(10);
		//resumeButton.setEffect(shadow);
		hiddenChar.setVisible(false);
		char2 = hiddenChar;
		group.getChildren().add(hiddenChar);
		
		Image steve = new Image(getClass().getResourceAsStream("res/pepe.png"));
		ImageView steveView = new ImageView(steve);
		steveView.setX(SCENE_WIDTH-steve.getWidth()-50);
		steveView.setY(SCENE_HEIGHT - 700);
		//		protagView.setScaleX(-10);
		//		protagView.setScaleY(10);
		//resumeButton.setEffect(shadow);
		steveView.setVisible(false);
		char1 = steveView;
		group.getChildren().add(steveView);
		
		

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
			scenario.setEffect(blur);
			pauseMenu.setVisible(true);

		} else {
			scenario.setEffect(null);
			pauseMenu.setVisible(false);

		}

	}

	public void updateExitScreen(boolean exitScreenOn) {

		if (exitScreenOn) {
			scenario.setEffect(blur);
			exitPopUp.setVisible(true);

		} else {
			scenario.setEffect(null);
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
						updateDialogue(currentDialogueSet.get(dialogueIndex));

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
						if (dialogueIndex < currentDialogueSet.size()-1) {
							dialogueIndex++;
							updateDialogue(currentDialogueSet.get(dialogueIndex));
						} else if (dialogueIndex == currentDialogueSet.size()-1) {
							//go back to game
							System.out.println("gasdsada");
							dialogueIndex = -1; //reset after you switch scenes
							if (controller.levelWins != 3) {
								controller.setLevel(1);
							} else {
								controller.resetToStartState();
							}
						}
						//maybe add function for setting focus of characters
						if (currentDialogue.getText().replaceAll(" ","").toLowerCase().contains("bob")) {
							protagDisp.setEffect(null);
							protagDisp.setVisible(true);
							otherCharDisp.setEffect(blur);
						} else if (currentDialogue.getText().replaceAll(" ","").toLowerCase().contains("steve")) {
							otherCharDisp.setEffect(null);
							//otherCharDisp.setVisible(true);
							char1.setVisible(true);
							protagDisp.setEffect(blur);
						} else if(currentDialogue.getText().replaceAll(" ","").toLowerCase().contains("mysteriousbeing")) {
							otherCharDisp.setEffect(null);
							//otherCharDisp.setVisible(true);
							char2.setVisible(true);
							protagDisp.setEffect(blur);
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
