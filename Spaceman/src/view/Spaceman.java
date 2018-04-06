package view;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Spaceman extends CharacterAnimate{

	private static final int[] ROTATION_DEGREE = new int[] {0, 90, 180, 270};
	private int currentDirection;
	public ImageView imageView;
	private double graphicalX;
	private double graphicalY;
	private int keyInput;
	private int currentRotation;

	public Spaceman(int x, int y) {
		keyInput = -1;
		
		// Intialise Spaceman grid position
		this.x = x;
		this.y = y;
		graphicalX = x*TILE_WIDTH + GRAPHICAL_X_OFFSET;
		graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;

		// Intialise Spaceman moving left
		currentDirection = MOVE_LEFT;
		xDirection = -1;
		yDirection = 0;

		Image startImage = new Image(getClass().getResourceAsStream("res/left1.png")); 
		images = new Image[] {
				startImage,
				new Image(getClass().getResourceAsStream("res/left2.png")),
				startImage,
				new Image(getClass().getResourceAsStream("res/round.png"))
		};
		imageIndex = 0;
		currentImage = images[imageIndex];
		currentRotation = ROTATION_DEGREE[currentDirection];

		imageView = new ImageView(startImage);
		imageView.setX(graphicalX);
		imageView.setY(graphicalY);
		imageView.setImage(images[imageIndex]);
		imageView.setRotate(currentRotation);

		getChildren().add(imageView);

		// remove later when movement logic is completed
		status = MOVING;

	}

	@Override
	public void moveOneStep() {

		if (imageIndex == 0) {
			changeCurrentDirection(keyInput);
		}

		if (status == MOVING) {

			if (xDirection != 0) {
				moveXAxis();
			} 
			if (yDirection != 0) {
				moveYAxis();
			}

			if (imageIndex < ANIMATION_STEP-1) {
				imageIndex++;
				currentImage = images[imageIndex];
				imageView.setImage(currentImage);
				imageView.setX(graphicalX);
				imageView.setY(graphicalY);
				imageView.setRotate(currentRotation);

			} else {
				imageIndex = 0;
				currentImage = images[imageIndex];
				imageView.setImage(currentImage);
				imageView.setX(graphicalX);
				imageView.setY(graphicalY);
				imageView.setRotate(currentRotation);
			}
		}
	}

	private void moveXAxis() {
		moveCounter++;
		if (moveCounter < ANIMATION_STEP) {
			graphicalX = graphicalX + (xDirection * MOVE_SPEED);
		} else {
			moveCounter = 0;
			x = x + xDirection;
			graphicalX = x*TILE_WIDTH + GRAPHICAL_X_OFFSET;
		}
	}

	private void moveYAxis() {
		moveCounter++;
		if (moveCounter < ANIMATION_STEP) {
			graphicalY = graphicalY + (yDirection * MOVE_SPEED);
		} else {
			moveCounter = 0;
			y = y + yDirection;
			graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;
		}
	}

	private void moveLeft() {

		xDirection = -1;
		yDirection = 0;
		currentDirection = MOVE_LEFT;
		currentRotation = ROTATION_DEGREE[currentDirection];
		status = MOVING;
	}

	private void moveRight() {
		xDirection = 1;
		yDirection = 0;
		currentDirection = MOVE_RIGHT;
		currentRotation = ROTATION_DEGREE[currentDirection];
		status = MOVING;
	}

	private void moveUp() {
		xDirection = 0;
		yDirection = -1;
		currentDirection = MOVE_UP;
		currentRotation = ROTATION_DEGREE[currentDirection];
		status = MOVING;
	}

	private void moveDown() {
		xDirection = 0;
		yDirection = 1;
		currentDirection = MOVE_DOWN;
		currentRotation = ROTATION_DEGREE[currentDirection];
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
