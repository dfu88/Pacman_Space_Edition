package view;

import java.net.URL;
import java.util.ArrayList;

import controller.LevelController;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Person;

public class Leaderboard {

	private double SCENE_WIDTH = 1440;
	private double SCENE_HEIGHT = 900;

	private LevelController controller;

	private Scene scene;

	private Group root;
	private Group exitPopUp;
	
	private AudioClip cycle;

	private TableView table;

	private ObservableList<Person> data;

	private GaussianBlur blur;
	private DropShadow shadow;
	private ArrayList<ImageView> exitOptions;

	public Leaderboard() {
		this.controller = controller;

//		table = new TableView();

		blur = new GaussianBlur();
		shadow = new DropShadow(500, Color.YELLOW);
		exitOptions = new ArrayList<ImageView>();
		URL url = this.getClass().getResource("sound/sound1.wav");
		cycle = new AudioClip(url.toString());
		
		//Setup Scene for game visuals
		root = new Group(); 
		scene = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT);
		scene.setFill(Color.BLACK);
		
		setUpKeyInput(scene);
	}
	
	public void generateLeaderboard() {
		
	}
	
	private Group addTable() {
		Group group = new Group();
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
	
	private void setUpKeyInput(Scene scene) {
	
	}
	
	public Scene returnScene() {
		return scene;
	}

}
