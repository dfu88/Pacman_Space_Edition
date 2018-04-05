package view;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Spaceman extends CharacterAnimate{

	private static final int[] ROTATION_DEGREE = new int[] {0, 90, 180, 270};
	private final int currentDirection;
	public ImageView imageView;

	public Spaceman(int x, int y) {
		// Intialise Spaceman grid position
		this.x = x;
		this.y = y;
		
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
		
		imageView = new ImageView(startImage);
		imageView.setX(x);
		imageView.setY(y);
		imageView.setImage(images[imageIndex]);
		imageView.setRotate(ROTATION_DEGREE[currentDirection]);
		
		getChildren().add(imageView);
		
	}

	@Override
	public void moveOneStep() {
		if (imageIndex < ANIMATION_STEP-1) {
			imageIndex++;
			currentImage = images[imageIndex];
			imageView.setImage(currentImage);
			
		} else {
			imageIndex = 0;
			currentImage = images[imageIndex];
			imageView.setImage(currentImage);
		}
	}

	private void moveLeft() {

	}

	private void moveRight() {

	}

	private void moveUp() {

	}

	private void moveDown() {

	}
}
