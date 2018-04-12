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
	}

	private void moveLeftOrRight(boolean mustMove) {
		
	}
	
	private void moveUpOrDown(boolean mustMove) {
		
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
			}
		}
	}

	private void moveYAxis() {
		int nextY = y + dy;
		if (levelController.checkMap(x,nextY) == 1) {
			imageIndex=0;
		} else {
			moveCounter++;
			if (moveCounter < ANIMATION_STEP) {
				graphicalY = graphicalY + (dy * MOVE_SPEED);
			} else {
				//levelController.updateMap(dx,dy,x,y);
				moveCounter = 0;
				y = y + dy;
				graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;
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
