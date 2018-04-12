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
import model.ShortestPath;

public class Alien extends CharacterAnimate{

	public LevelController levelController;
	public LevelVisuals levelView;
	public ImageView imageView;
	private double graphicalX;
	private double graphicalY;
	private static final int TRAPPED = 0;

	public Alien(LevelController levelController, LevelVisuals levelView, int x, int y, int dx, int dy) {
		this.levelController = levelController;
		this.levelView = levelView;

		// Intialise Alien grid position
		this.x = x;
		this.y = y;
		graphicalX = x*TILE_WIDTH + GRAPHICAL_X_OFFSET;
		graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;

		// Intialise Alien current direction
		this.dx = dx;
		this.dy = dy;

		Image startImage = new Image(getClass().getResourceAsStream("res/left2.png"));
		images = new Image[] {
				startImage,
				new Image(getClass().getResourceAsStream("res/left1.png")),
				new Image(getClass().getResourceAsStream("res/round.png")),
				new Image(getClass().getResourceAsStream("res/left1.png"))
		};
		imageIndex = 0;
		currentImage = images[imageIndex];

		imageView = new ImageView(startImage);
		imageView.setX(graphicalX);
		imageView.setY(graphicalY);
		imageView.setImage(images[imageIndex]);

		getChildren().add(imageView);

		// remove later when movement logic is completed
		status = MOVING;
	}

	// If moving in y-axis, move in x-axis or continue
	private void moveLeftOrRight(boolean mustMove) {
		//Initialise shortest path algorithm
		ShortestPath moveRank = new ShortestPath(levelController);
		int moveLeft = Integer.MAX_VALUE, moveRight = Integer.MAX_VALUE, moveContinue = Integer.MAX_VALUE;
		if (dy == -1) {
			moveContinue = moveRank.computeShortest(x, y-1, levelView.spaceman.x, levelView.spaceman.y);
		} else if (dy == 1) {
			moveContinue = moveRank.computeShortest(x, y+1, levelView.spaceman.x, levelView.spaceman.y);
		}
		moveLeft = moveRank.computeShortest(x-1, y, levelView.spaceman.x, levelView.spaceman.y);
		moveRight = moveRank.computeShortest(x+1, y, levelView.spaceman.x, levelView.spaceman.y);
		
		System.out.println(moveLeft);
		System.out.println(moveRight);
		System.out.println(moveContinue);

		if (moveLeft < moveRight && moveLeft < moveContinue) {
			dx = -1;
			dy = 0;
			System.out.println("LEFTRIGHT--LEFT");
		} else if (moveRight < moveLeft && moveRight < moveContinue) {
			dx = 1;
			dy = 0;
			System.out.println("LEFTRIGHT--RIGHT");
		} else {
			System.out.println("LEFTRIGHT--CONTINUE");
			return;
		}
	}

	// If moving in x-axis, move in y-axis or continue
	private void moveUpOrDown(boolean mustMove) {
		//Initialise shortest path algorithm
		ShortestPath moveRank = new ShortestPath(levelController);
		int moveUp = Integer.MAX_VALUE, moveDown = Integer.MAX_VALUE, moveContinue = Integer.MAX_VALUE;
		if (dx == -1) {
			moveContinue = moveRank.computeShortest(x-1, y, levelView.spaceman.x, levelView.spaceman.y);
		} else if (dx == 1) {
			moveContinue = moveRank.computeShortest(x+1, y, levelView.spaceman.x, levelView.spaceman.y);
		}
		moveUp = moveRank.computeShortest(x, y-1, levelView.spaceman.x, levelView.spaceman.y);
		moveDown = moveRank.computeShortest(x, y+1, levelView.spaceman.x, levelView.spaceman.y);

		System.out.println(moveUp);
		System.out.println(moveDown);
		System.out.println(moveContinue);
		
		if (moveUp < moveDown && moveUp < moveContinue) {
			dx = 0;
			dy = -1;
			System.out.println("UPDOWN--UP");

		} else if (moveDown < moveUp && moveDown < moveContinue) {
			dx = 0;
			dy = 1;
			System.out.println("UPDOWN--DOWN");
		} else {
			System.out.println("UPDOWN--CONTINUE");
			return;
		}
	}

	private void moveXAxis() {
		//Wallcheck logic: If next destination is wall, do not move, else move as normal
		int nextX = x + dx;
		if (levelController.checkMap(nextX, y) == 1) {
			//must change direction
			moveUpOrDown(true);
		} else {
			moveCounter++;
			if (moveCounter < ANIMATION_STEP) {
				graphicalX = graphicalX + (dx * MOVE_SPEED);
			} else {
				//levelController.updateMap(dx,dy,x,y);

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
				moveUpOrDown(false);
			}
		}
	}

	private void moveYAxis() {
		int nextY = y + dy;
		if (levelController.checkMap(x,nextY) == 1) {
			moveLeftOrRight(true);
		} else {
			moveCounter++;
			if (moveCounter < ANIMATION_STEP) {
				graphicalY = graphicalY + (dy * MOVE_SPEED);
			} else {
				//levelController.updateMap(dx,dy,x,y);
				moveCounter = 0;
				y = y + dy;
				graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;
				moveLeftOrRight(false);
			}
		}
	}

	public void moveOneStep() {
		if (imageIndex < images.length-1) {
			imageIndex++;
			currentImage = images[imageIndex];
			imageView.setImage(currentImage);
		} else {
			imageIndex = 0;
			currentImage = images[imageIndex];
			imageView.setImage(currentImage);
		}

		if (status == MOVING) {
			if (dx != 0 && dy == 0) {
				moveXAxis();
			} 
			if (dx == 0 && dy != 0) {
				moveYAxis();
			}

			imageView.setX(graphicalX);
			imageView.setY(graphicalY);
		}
	}
}
