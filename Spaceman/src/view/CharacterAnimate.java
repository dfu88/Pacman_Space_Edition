package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.util.Duration;

public abstract class CharacterAnimate extends Parent{

	// animation frames total and movement distance
	protected static final int ANIMATION_STEP = 4;
	protected static final int MOVE_SPEED = 45 / ANIMATION_STEP;
	protected static final double GRAPHICAL_X_OFFSET = 247.5;
	protected static final double GRAPHICAL_Y_OFFSET = 17.5;
	protected static final int TILE_WIDTH = 45;
	protected static final int TILE_HEIGHT = 45;
	

	protected static final int MOVING = 1;
	protected static final int STOPPED = 0;
	protected static final int MOVE_LEFT = 0;
	protected static final int MOVE_UP = 1;
	protected static final int MOVE_RIGHT = 2;
	protected static final int MOVE_DOWN = 3;

	protected int status;

	protected Image[] images;
	protected int imageIndex;
	protected Image currentImage;

	// character position in the gtid
	protected int x;
	protected int y;

	// move character by either x or y
	protected int dx;
	protected int dy;

	protected int moveCounter;

	protected Timeline timeline;

	public CharacterAnimate() {
		imageIndex = 0;
		moveCounter = 0;
		dx = 0;
		dy = 0;
		timeline = makeTimeline();
	}

	public abstract void moveOneStep();

	private Timeline makeTimeline() {
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(60), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				moveOneStep();
			}

		});
		timeline.getKeyFrames().add(keyFrame);

		return timeline;
	}

	public void stop() {
		timeline.stop();
	}

	public void pause() {
		timeline.pause();
	}

	public void start() {
		timeline.play();
	}

	public boolean isRunning() {
		return timeline.getStatus() == Animation.Status.RUNNING;
	}

	public boolean isPaused() {

		return timeline.getStatus() == Animation.Status.PAUSED;
	}
}
