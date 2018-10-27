package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import controller.LevelController;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Person;

public class Leaderboard {

	private double SCENE_WIDTH = 1024;
	private double SCENE_HEIGHT = 768;

	private LevelController levelController;

	private Scene scene;

	//Nodes in this scene
	private AnchorPane root;
	private BorderPane mainPane;
	private TabPane tabPane;
	private Group exitPopUp;

	private AudioClip cycle;

	private TableView<Person> tableClassic, tableEndless, tableMulti;

	private ObservableList<Person>  finalClassic, finalEndless, finalMulti;
	private ArrayList<Person> dataClassic, dataEndless, dataMulti;

	private GaussianBlur blur;
	private DropShadow shadow;
	private ArrayList<ImageView> exitOptions;

	public Leaderboard(LevelController levelController) {
		this.levelController = levelController;

		blur = new GaussianBlur();
		shadow = new DropShadow(500, Color.YELLOW);
		exitOptions = new ArrayList<ImageView>();
		
		//Initialise Sound Clips
		URL url = this.getClass().getResource("sound/sound1.wav");
		cycle = new AudioClip(url.toString());

		dataClassic = new ArrayList<Person>();
		dataEndless = new ArrayList<Person>();
		dataMulti = new ArrayList<Person>();

		//Setup nodes and add to scene
		root = new AnchorPane(); 
		BackgroundImage bg = new BackgroundImage(new Image(getClass().getResourceAsStream("bg/test.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		root.setBackground(new Background(bg));
		scene = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT);

		setUpKeyInput(scene);
	}

	/*
	 * Generates the leaderboard by calling the add functions for each child node
	 */
	public void generateLeaderboard() {
		exitOptions.clear();
		root.getChildren().clear();

		readData();

		finalClassic = FXCollections.observableArrayList(dataClassic);
		finalEndless = FXCollections.observableArrayList(dataEndless);
		finalMulti = FXCollections.observableArrayList(dataMulti);

		Image title = new Image(getClass().getResourceAsStream("bg/leaderboard.png"));
		ImageView titleView = new ImageView(title);
		titleView.setX((SCENE_WIDTH-title.getWidth())*0.5);
		titleView.setY(50);
		titleView.setScaleX(0.75);
		titleView.setScaleY(0.75);
		root.getChildren().add(titleView);
		mainPane = addLeaderboard();

		final VBox vbox = new VBox();
		vbox.setSpacing(50);
		vbox.setPadding(new Insets(200,0,0,100));
		vbox.getChildren().addAll(mainPane);
		root.getChildren().add(vbox);

		exitPopUp = addExitPopUp();
		exitPopUp.setVisible(false);
		root.getChildren().add(exitPopUp);
	}

	/*
	 * Creates the leaderboard table itself and returns it
	 */
	private BorderPane addLeaderboard() {
		// Classic Table
		tableClassic = new TableView<Person>();
		tableClassic.setItems(finalClassic);
		tableClassic.setPrefSize(SCENE_WIDTH-200, SCENE_HEIGHT-300);
		tableClassic.setEditable(false);
		//Number col
		TableColumn<Person,String> numberClassic = new TableColumn<Person,String>("#");
		numberClassic.setPrefWidth(75);
		numberClassic.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Person, String> p) {
				return new ReadOnlyObjectWrapper(tableClassic.getItems().indexOf(p.getValue())+1);
			}
		});
		//Name col
		TableColumn<Person,String> nameClassic = new TableColumn<Person,String>("Name");
		nameClassic.setMinWidth(400);
		nameClassic.setPrefWidth(525);
		nameClassic.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		//Score col
		TableColumn<Person,Number> scoreClassic = new TableColumn<Person,Number>("Score");
		scoreClassic.setMinWidth(200);
		scoreClassic.setPrefWidth(224);
		scoreClassic.setCellValueFactory(new PropertyValueFactory<Person, Number>("score"));
		tableClassic.getColumns().addAll(numberClassic,nameClassic,scoreClassic);

		// Endless Table
		tableEndless = new TableView<Person>();
		tableEndless.setItems(finalEndless);
		tableEndless.setPrefSize(SCENE_WIDTH-200, SCENE_HEIGHT-300);
		tableEndless.setEditable(false);
		//Number col
		TableColumn<Person,String> numberEndless = new TableColumn<Person,String>("#");
		numberEndless.setPrefWidth(75);
		numberEndless.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Person, String> p) {
				return new ReadOnlyObjectWrapper(tableEndless.getItems().indexOf(p.getValue())+1);
			}
		});
		//Name col
		TableColumn<Person,String> nameEndless = new TableColumn<Person,String>("Name");
		nameEndless.setMinWidth(400);
		nameEndless.setPrefWidth(525);
		nameEndless.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		//Score col
		TableColumn<Person,Number> scoreEndless = new TableColumn<Person,Number>("Score");
		scoreEndless.setMinWidth(200);
		scoreEndless.setPrefWidth(224);
		scoreEndless.setCellValueFactory(new PropertyValueFactory<Person, Number>("score"));
		tableEndless.getColumns().addAll(numberEndless,nameEndless,scoreEndless);

		// Multidimension Table
		tableMulti = new TableView<Person>();
		tableMulti.setItems(finalMulti);
		tableMulti.setPrefSize(SCENE_WIDTH-200, SCENE_HEIGHT-300);
		tableMulti.setEditable(false);
		//Number col
		TableColumn<Person,String> numberMulti = new TableColumn<Person,String>("#");
		numberMulti.setPrefWidth(75);
		numberMulti.setCellValueFactory(new Callback<CellDataFeatures<Person, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Person, String> p) {
				return new ReadOnlyObjectWrapper(tableMulti.getItems().indexOf(p.getValue())+1);
			}
		});
		//Name col
		TableColumn<Person,String> nameMulti = new TableColumn<Person,String>("Name");
		nameMulti.setMinWidth(400);
		nameMulti.setPrefWidth(525);
		nameMulti.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		//Score col
		TableColumn<Person,Number> scoreMulti = new TableColumn<Person,Number>("Score");
		scoreMulti.setMinWidth(200);
		scoreMulti.setPrefWidth(224);
		scoreMulti.setCellValueFactory(new PropertyValueFactory<Person, Number>("score"));
		tableMulti.getColumns().addAll(numberMulti,nameMulti,scoreMulti);

		tabPane = new TabPane();
		mainPane = new BorderPane();
		Tab tabClassic = new Tab();
		tabClassic.setText("Classic Mode");
		tabClassic.setContent(tableClassic);
		tabPane.getTabs().add(tabClassic);
		Tab tabEndless = new Tab();
		tabEndless.setText("Endless Mode");
		tabEndless.setContent(tableEndless);
		tabPane.getTabs().add(tabEndless);
		Tab tabMulti = new Tab();
		tabMulti.setText("Warp Mode");
		tabMulti.setContent(tableMulti);
		tabPane.getTabs().add(tabMulti);
		mainPane.setCenter(tabPane);

		return mainPane;
	}

	/*
	 * Reads the highscore text files and creates a person object and adds it to the data arrayList
	 */
	private void readData() {
		//Read highscoreClassic.txt
		dataClassic.clear();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/view/res/highScoreClassic.txt"));
			String line = reader.readLine();
			while (line != null) {
				if (line.contains(",")) {
					String name, score;
					String[] parts = line.split(",");
					name = parts[0];
					score = parts[1];
					Person person = new Person(name,score);
					dataClassic.add(person);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Read highscoreEndless.txt
		dataEndless.clear();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/view/res/highScoreEndless.txt"));
			String line = reader.readLine();
			while (line != null) {
				if (line.contains(",")) {
					String name, score;
					String[] parts = line.split(",");
					name = parts[0];
					score = parts[1];
					Person person = new Person(name,score);
					dataEndless.add(person);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Read highscoreMulti.txt
		dataMulti.clear();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/view/res/highScoreMulti.txt"));
			String line = reader.readLine();
			while (line != null) {
				if (line.contains(",")) {
					String name, score;
					String[] parts = line.split(",");
					name = parts[0];
					score = parts[1];
					Person person = new Person(name,score);
					dataMulti.add(person);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Writes the new score into the relevant text file if new score is greater
	 * than the current top 5 for the game mode
	 */
	public void writeData(String name, int score, int gameMode) {
		readData();
		String player1 = "", player2 = "", player3 = "", player4 = "", player5 = "";
		int score1 = 0, score2 = 0, score3 = 0, score4 = 0, score5 = 0;
		String playerName = name;
		int playerScore = score;
		File file = new File(getClass().getResource("res/highscoreClassic.txt").getFile());;
		
		if (gameMode == 0) {
			player1 = dataClassic.get(0).getName();
			score1 = dataClassic.get(0).getScore();
			player2 = dataClassic.get(1).getName();
			score2 = dataClassic.get(1).getScore();
			player3 = dataClassic.get(2).getName();
			score3 = dataClassic.get(2).getScore();
			player4 = dataClassic.get(3).getName();
			score4 = dataClassic.get(3).getScore();
			player5 = dataClassic.get(4).getName();
			score5 = dataClassic.get(4).getScore();
			//file = new File(getClass().getResource("res/highscoreClassic.txt").getFile());
		} else if (gameMode == 3) {
			player1 = dataEndless.get(0).getName();
			score1 = dataEndless.get(0).getScore();
			player2 = dataEndless.get(1).getName();
			score2 = dataEndless.get(1).getScore();
			player3 = dataEndless.get(2).getName();
			score3 = dataEndless.get(2).getScore();
			player4 = dataEndless.get(3).getName();
			score4 = dataEndless.get(3).getScore();
			player5 = dataEndless.get(4).getName();
			score5 = dataEndless.get(4).getScore();
			file = new File(getClass().getResource("res/highscoreEndless.txt").getFile());
		} else if (gameMode == 4) {
			player1 = dataMulti.get(0).getName();
			score1 = dataMulti.get(0).getScore();
			player2 = dataMulti.get(1).getName();
			score2 = dataMulti.get(1).getScore();
			player3 = dataMulti.get(2).getName();
			score3 = dataMulti.get(2).getScore();
			player4 = dataMulti.get(3).getName();
			score4 = dataMulti.get(3).getScore();
			player5 = dataMulti.get(4).getName();
			score5 = dataMulti.get(4).getScore();
			file = new File(getClass().getResource("res/highscoreMulti.txt").getFile());
		}
		
		if (playerScore > score1) {
			//Store score
			int copyScore = score1;
			score1 = playerScore;
			playerScore = copyScore;
			//Store name
			String copyName = player1;
			player1 = playerName;
			playerName = copyName;
		} 
		if (playerScore > score2) {
			//Store score
			int copyScore = score2;
			score2 = playerScore;
			playerScore = copyScore;
			//Store name
			String copyName = player2;
			player2 = playerName;
			playerName = copyName;
		}
		if (playerScore > score3) {
			//Store score
			int copyScore = score3;
			score3 = playerScore;
			playerScore = copyScore;
			//Store name
			String copyName = player3;
			player3 = playerName;
			playerName = copyName;
		} 
		if (playerScore > score4) {
			//Store score
			int copyScore = score4;
			score4 = playerScore;
			playerScore = copyScore;
			//Store name
			String copyName = player4;
			player4 = playerName;
			playerName = copyName;
		} 
		if (playerScore > score5) {
			//Store score
			int copyScore = score5;
			score5 = playerScore;
			playerScore = copyScore;
			//Store name
			String copyName = player5;
			player5 = playerName;
			playerName = copyName;
		}
		
		
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(player1+","+score1);
			output.newLine();
			output.write(player2+","+score2);
			output.newLine();
			output.write(player3+","+score3);
			output.newLine();
			output.write(player4+","+score4);
			output.newLine();
			output.write(player5+","+score5);
			output.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}

	private Group addExitPopUp() {
		Group group = new Group();

		Rectangle frame = new Rectangle(1440/2.5,900/2.5);
		frame.setFill(Color.BLACK);
		frame.setStroke(Color.WHITE);
		frame.setStrokeWidth(2.0);
		frame.setArcHeight(15);
		frame.setArcWidth(15);
		frame.setX((SCENE_WIDTH-frame.getLayoutBounds().getWidth())*0.5);
		frame.setY((SCENE_HEIGHT-frame.getLayoutBounds().getHeight())*0.5);
		group.getChildren().add(frame);

		Text label = new Text("Are You Sure \nYou Want To Exit \nThe Leaderboard?");
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

	private void playCycleSound( ) {
		cycle.play();
	}

	private void stopCycleClip( ) {
		if (cycle.isPlaying()) {
			cycle.stop();
		}
	}

	private void setUpKeyInput(Scene scene) {
		EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
			public void handle(KeyEvent input) {
				if (input.getCode() == KeyCode.ESCAPE) {
					playCycleSound();
					// Turns on/off the exit pop up
					exitPopUp.setVisible(!exitPopUp.isVisible());
				} else if (input.getCode() == KeyCode.LEFT) {
					if (exitPopUp.isVisible()) {
						// Cycles the yes or no options for the exit pop up
						if (levelController.exitOption > 0) {
							levelController.exitOption--;
							playCycleSound();
						}
						cycleOptions(levelController.exitOption);

					}
				} else if (input.getCode() == KeyCode.RIGHT) {
					if (exitPopUp.isVisible()) {
						// Cycles the yes or no options for the exit pop up
						if (levelController.exitOption < 1) {
							levelController.exitOption++;
							playCycleSound();
						}
						cycleOptions(levelController.exitOption);

					}
				} else if (input.getCode() == KeyCode.ENTER) {
					if (exitPopUp.isVisible()) {		
						//Quits the game if yes is selected, otherwise will go back to pause menu
						if (levelController.exitOption == 1){
							levelController.timeline.stop();

							//Resets initial level states //consider an init() func instead
							levelController.resetToStartState();
						} else {
							exitPopUp.setVisible(false);
						}
					}
				}
			}
		};
		scene.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
	}

	public Scene returnScene() {
		return scene;
	}

}
