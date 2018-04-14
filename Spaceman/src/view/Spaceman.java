package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import controller.LevelController;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class Spaceman extends CharacterAnimate{

	public LevelController levelController;
	//private LevelVisuals view;
	private static final int[] ROTATION_DEGREE = new int[] {0, 90, 180, 270};
	private int rotationIndex;
	public ImageView imageView;
	private double graphicalX;
	private double graphicalY;
	private int keyInput;
	private int currentRotation;
	
	//public Clip pelletSound;
	
	private AudioClip pelletSound;
	
	public Spaceman(LevelController levelController, int x, int y) {
	//public Spaceman(LevelController levelController, int x, int y) {
		keyInput = -1;
		
		//this.view = view;
		this.levelController = levelController;

		// Intialise Spaceman grid position
		this.x = x;
		this.y = y;
		graphicalX = x*TILE_WIDTH + GRAPHICAL_X_OFFSET;
		graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;

		// Intialise Spaceman moving left
		dx = -1;
		dy = 0;

//		Image startImage = new Image(getClass().getResourceAsStream("res/left2.png")); 
//		images = new Image[] {
//				startImage,
//				new Image(getClass().getResourceAsStream("res/left1.png")),
//				new Image(getClass().getResourceAsStream("res/round.png")),
//				new Image(getClass().getResourceAsStream("res/left1.png"))
//		};
		
		Image startImage = new Image(getClass().getResourceAsStream("res/spacepac2.png")); 
		images = new Image[] {
				startImage,
				new Image(getClass().getResourceAsStream("res/spacepac.png")),
				new Image(getClass().getResourceAsStream("res/spacepac3.png")),
				new Image(getClass().getResourceAsStream("res/spacepac.png"))
		};
		imageIndex = 0;
		currentImage = images[imageIndex];
		rotationIndex = MOVE_LEFT;
		currentRotation = ROTATION_DEGREE[rotationIndex];

		imageView = new ImageView(startImage);
		imageView.setX(graphicalX);
		imageView.setY(graphicalY);
		imageView.setImage(images[imageIndex]);
		imageView.setRotate(currentRotation);

		getChildren().add(imageView);

		// remove later when movement logic is completed
		status = MOVING;
		
		URL url = this.getClass().getResource("sound/boop.wav");
		pelletSound = new AudioClip(url.toString());

	}

	@Override
	public void moveOneStep() {
		levelController.respawnCollectables();
		if (imageIndex == 0) {
			changeCurrentDirection(keyInput);
		}
		
		if (imageIndex < images.length-1) {
			imageIndex++;
			currentImage = images[imageIndex];
			imageView.setImage(currentImage);
//			imageView.setX(graphicalX);
//			imageView.setY(graphicalY);
//			imageView.setRotate(currentRotation);
			//levelController.respawnCollectables();

		} else {
			imageIndex = 0;
			currentImage = images[imageIndex];
			imageView.setImage(currentImage);
//			imageView.setX(graphicalX);
//			imageView.setY(graphicalY);
//			imageView.setRotate(currentRotation);
			
			
		}
		
		if (status == MOVING) {
//			if (pelletSound.isRunning()) {
//				pelletSound.stop();
//				pelletSound.setFramePosition(0);//reset sound to 0 seconds
//			}
			if (pelletSound.isPlaying()) {
				pelletSound.stop();
			}
			if (dx != 0 && dy == 0) {
				moveXAxis();
			} 
			if (dx == 0 && dy != 0) {
				moveYAxis();
			}
			
			imageView.setX(graphicalX);
			imageView.setY(graphicalY);
			imageView.setRotate(currentRotation);
		}
	}
	
	public void playPelletSound( ) {
		pelletSound.play();
	}
	
	private void moveXAxis() {
		//Wallcheck logic: If next destination is wall, do not move, else move as normal
		int nextX = x + dx;
		if (levelController.checkMap(nextX, y) == 1) {
			//pause animation here
			imageIndex=0;
		} else {
			//start();
			//if (levelController.checkMap(nextX, y) == 2) {
				//levelController.updateMap(dx,dy,x,y);
			//}
			
			moveCounter++;
			if (moveCounter < ANIMATION_STEP) {
				graphicalX = graphicalX + (dx * MOVE_SPEED);
			} else {
				levelController.updateMap(dx,dy,x,y);
				
				moveCounter = 0;
				nextX = x + dx;
				// HARDCODED VALUES FOR TUNNEL X COORDINATE - USE GRID SIZE
				if (nextX <= 1  && dx == -1 ) {
					x = 19;
				} else if (nextX >= 19 && dx == 1 ) {
					x = 1;
				} else {
					x = x + dx;
				}
				graphicalX = x*TILE_WIDTH + GRAPHICAL_X_OFFSET;
				
				// this switches status flag so moveXAxis() and moveYAxis aren't called until keyinput changes
//				nextX = x + dx;
//				if (levelController.checkMap(nextX, y) == 1) {
//					status = STOPPED;
//				}
			}
		}
	}

	private void moveYAxis() {
		int nextY = y + dy;
		if (levelController.checkMap(x,nextY) == 1) {
			imageIndex=0;
		} else {
			//if (levelController.checkMap(x, nextY) == 2) {
				//levelController.updateMap(dx,dy,x,y);
			//}
			
			moveCounter++;
			if (moveCounter < ANIMATION_STEP) {
				graphicalY = graphicalY + (dy * MOVE_SPEED);
			} else {
				levelController.updateMap(dx,dy,x,y);
				moveCounter = 0;
				y = y + dy;
				graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;
				
				
				// this switches status flag so moveXAxis() and moveYAxis aren't called until keyinput changes
//				int nextY = y + dy;
//				if (levelController.checkMap(x,nextY) == 1) {
//					status = STOPPED;
//				}
			}
		}
		//old stuff
//		moveCounter++;
//		if (moveCounter < ANIMATION_STEP) {
//			graphicalY = graphicalY + (dy * MOVE_SPEED);
//		} else {
//			moveCounter = 0;
//			y = y + dy;
//			graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;
//			
//			int nextY = y + dy;
//			if (levelController.checkMap(x,nextY) == 1) {
//				status = STOPPED;
//			}
//		}
	}

	private void moveLeft() {
		// Prevent invalid direction changes
		int nextX = x - 1;
		if (levelController.checkMap(nextX, y) == 1) {
			return;
		}
		
		// Change direction
		dx = -1;
		dy = 0;
		
		rotationIndex = MOVE_LEFT;
		currentRotation = ROTATION_DEGREE[rotationIndex];
		status = MOVING;
	}

	private void moveRight() {
		// Prevent invalid direction changes
		int nextX = x + 1;
		if (levelController.checkMap(nextX, y) == 1) {
			return;
		}
		// Change direction
		dx = 1;
		dy = 0;
		rotationIndex = MOVE_RIGHT;
		currentRotation = ROTATION_DEGREE[rotationIndex];
		status = MOVING;
	}

	private void moveUp() {
		// Prevent invalid direction changes
		int nextY = y - 1;
		if (levelController.checkMap(x,nextY) == 1) {
			return;
		}
		// Change direction
		dx = 0;
		dy = -1;
		rotationIndex = MOVE_UP;
		currentRotation = ROTATION_DEGREE[rotationIndex];
		status = MOVING;
	}

	private void moveDown() {
		// Prevent invalid direction changes
		int nextY = y + 1;
		if (levelController.checkMap(x,nextY) == 1) {
			return;
		}
		// Change direction
		dx = 0;
		dy = 1;
		rotationIndex = MOVE_DOWN;
		currentRotation = ROTATION_DEGREE[rotationIndex];
		status = MOVING;
	}

	public void setKeyInput(int keyInput) {
		this.keyInput  = keyInput;
	}

	private void changeCurrentDirection(int keyInput) {
		if (keyInput == MOVE_LEFT) {
			moveLeft();
		} else if (keyInput == MOVE_RIGHT) {
			moveRight();
		} else if (keyInput == MOVE_UP) {
			moveUp();
		} else if (keyInput == MOVE_DOWN) {
			moveDown();
		}
	}

}
