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
	private int alienType;
	private int frightModeCounter;
	private int chaseModeCounter;
	private int scatterModeCounter;	
	private int trapCounter;
	private int spawnX;
	private int spawnY;
	private double graphicalX;
	private double graphicalY;
	private boolean frightenedFlag = false;
	private int initialTrapTime;
	private static final int TRAPPED = 2;
	private static final int FRIGHT_MODE_MAX_TIME = 160;
	private final Image[] FRIGHTENED_IMAGES = new Image[] {
			new Image(getClass().getResourceAsStream("res/frightalien1.png")),
			new Image(getClass().getResourceAsStream("res/frightalien2.png")),
			new Image(getClass().getResourceAsStream("res/frightalien1.png")),
			new Image(getClass().getResourceAsStream("res/frightalien2.png"))
	};
	private final Image[] FLASHING_IMAGES = new Image[] {
			new Image(getClass().getResourceAsStream("res/frightalien1.png")),
			new Image(getClass().getResourceAsStream("res/left1.png")),
			new Image(getClass().getResourceAsStream("res/frightalien1.png")),
			new Image(getClass().getResourceAsStream("res/left1.png"))
	};
	private final Image[] RED_IMAGES = new Image[] {
			new Image(getClass().getResourceAsStream("res/redalien1.png")),
			new Image(getClass().getResourceAsStream("res/redalien2.png")),
			new Image(getClass().getResourceAsStream("res/redalien1.png")),
			new Image(getClass().getResourceAsStream("res/redalien2.png"))
	};
	private final Image[] PINK_IMAGES = new Image[] {
			new Image(getClass().getResourceAsStream("res/pinkalien1.png")),
			new Image(getClass().getResourceAsStream("res/pinkalien2.png")),
			new Image(getClass().getResourceAsStream("res/pinkalien1.png")),
			new Image(getClass().getResourceAsStream("res/pinkalien2.png"))
	};
	private final Image[] BLUE_IMAGES = new Image[] {
			new Image(getClass().getResourceAsStream("res/bluealien1.png")),
			new Image(getClass().getResourceAsStream("res/bluealien2.png")),
			new Image(getClass().getResourceAsStream("res/bluealien1.png")),
			new Image(getClass().getResourceAsStream("res/bluealien2.png"))
	};
	private final Image[] ORANGE_IMAGES = new Image[] {
			new Image(getClass().getResourceAsStream("res/orangealien1.png")),
			new Image(getClass().getResourceAsStream("res/orangealien2.png")),
			new Image(getClass().getResourceAsStream("res/orangealien1.png")),
			new Image(getClass().getResourceAsStream("res/orangealien2.png"))
	};

	public Alien(int alienType, LevelController levelController, LevelVisuals levelView, int x, int y, int dx, int dy, int initialTrapTime) {
		this.levelController = levelController;
		this.levelView = levelView;

		this.alienType = alienType;
		frightModeCounter = 0;
		chaseModeCounter = 0;
		scatterModeCounter = 0;	
		trapCounter = 0;
		this.initialTrapTime = initialTrapTime;

		// Intialise Alien grid position
		this.x = x;
		this.y = y;
		spawnX = x;
		spawnY = y;
		graphicalX = x*TILE_WIDTH + GRAPHICAL_X_OFFSET;
		graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;

		// Intialise Alien current direction
		this.dx = dx;
		this.dy = dy;

		//Image startImage = new Image(getClass().getResourceAsStream("res/left2.png"));
		if (alienType == 0) {
			images = RED_IMAGES;
		} else if (alienType == 1) {
			images = PINK_IMAGES;
		} else if (alienType == 2) {
			images = BLUE_IMAGES;
		} else if (alienType == 3) {
			images = ORANGE_IMAGES;
		}

		imageIndex = 0;
		currentImage = images[imageIndex];

		imageView = new ImageView(currentImage);
		imageView.setX(graphicalX);
		imageView.setY(graphicalY);
		imageView.setImage(images[imageIndex]);

		getChildren().add(imageView);

		// make alien slightly slower than spaceman
		timeline.setRate(0.95);

		// remove later when movement logic is completed
		status = TRAPPED;
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

		if (!frightenedFlag) {
			if (moveLeft < moveRight && moveLeft < moveContinue) {
				dx = -1;
				dy = 0;
				System.out.println("LEFTRIGHT--LEFT");
			} else if (moveRight < moveLeft && moveRight < moveContinue) {
				dx = 1;
				dy = 0;
				System.out.println("LEFTRIGHT--RIGHT");
			} else {
				if (mustMove) {
					double chance = Math.random();
					if (chance < 0.5) {
						dx = -1;
						dy = 0;
					} else if (chance >= 0.5) {
						dx = 1;
						dy = 0;
					}
				} else {
					System.out.println("UPDOWN--CONTINUE");
					return;
				}
			}
		} else if (frightenedFlag) {
			if (moveLeft != Integer.MAX_VALUE && moveRight == Integer.MAX_VALUE && moveContinue == Integer.MAX_VALUE) {
				dx = -1;
				dy = 0;
				System.out.println("LEFTRIGHT--LEFT");
			} else if (moveRight != Integer.MAX_VALUE && moveLeft == Integer.MAX_VALUE && moveContinue == Integer.MAX_VALUE) {
				dx = 1;
				dy = 0;
				System.out.println("LEFTRIGHT--RIGHT");
			} else if (moveContinue != Integer.MAX_VALUE && moveLeft == Integer.MAX_VALUE && moveRight == Integer.MAX_VALUE) {
				return;
			} else {
				if (mustMove) {
					double chance = Math.random();
					if (chance < 0.5) {
						dx = -1;
						dy = 0;
					} else if (chance >= 0.5) {
						dx = 1;
						dy = 0;
					}
				} else {
					System.out.println("UPDOWN--CONTINUE");
					return;
				}
			}
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

		if (!frightenedFlag) {
			if (moveUp < moveDown && moveUp < moveContinue) {
				dx = 0;
				dy = -1;
				System.out.println("UPDOWN--UP");

			} else if (moveDown < moveUp && moveDown < moveContinue) {
				dx = 0;
				dy = 1;
				System.out.println("UPDOWN--DOWN");
			} else {
				if (mustMove) {
					double chance = Math.random();
					if (chance < 0.5) {
						dx = 0;
						dy = -1;
					} else if (chance >= 0.5) {
						dx = 0;
						dy = 1;
					}
				} else {
					System.out.println("UPDOWN--CONTINUE");
					return;
				}
			}
		} else if (frightenedFlag) {
			if (moveUp != Integer.MAX_VALUE && moveDown == Integer.MAX_VALUE && moveContinue == Integer.MAX_VALUE) {
				dx = 0;
				dy = -1;
				System.out.println("UPDOWN--UP");

			} else if (moveDown != Integer.MAX_VALUE && moveUp == Integer.MAX_VALUE && moveContinue == Integer.MAX_VALUE) {
				dx = 0;
				dy = 1;
				System.out.println("UPDOWN--DOWN");
			} else if (moveContinue != Integer.MAX_VALUE && moveUp == Integer.MAX_VALUE && moveDown == Integer.MAX_VALUE) {
				return;
			} else {
				if (mustMove) {
					double chance = Math.random();
					if (chance < 0.5) {
						dx = 0;
						dy = -1;
					} else if (chance >= 0.5) {
						dx = 0;
						dy = 1;
					}
				} else {
					System.out.println("UPDOWN--CONTINUE");
					return;
				}
			}
		}
	}

	private void moveXAxis() {
		//Wallcheck logic: If next destination is wall, do not move, else move as normal
		int nextX = x + dx;
		if (levelController.checkMap(nextX, y) == 1 || levelController.checkMap(nextX, y) == 9) {
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
	
	private void moveCageXAxis() {
		//Wallcheck logic: If next destination is wall, do not move, else move as normal
		int nextX = x + dx;
		if (levelController.checkMap(nextX, y) == 1 || levelController.checkMap(nextX, y) == 9) {
			//must change direction
			dx = -(dx);
			dy = 0;
		} else {
			moveCounter++;
			if (moveCounter < ANIMATION_STEP) {
				graphicalX = graphicalX + (dx * MOVE_SPEED);
			} else {
				//levelController.updateMap(dx,dy,x,y);

				moveCounter = 0;
				trapCounter++;
				x = x + dx;

				graphicalX = x*TILE_WIDTH + GRAPHICAL_X_OFFSET;
				//moveUpOrDown(false);
			}
		}
	}

	private void moveYAxis() {
		int nextY = y + dy;
		if (levelController.checkMap(x,nextY) == 1 || levelController.checkMap(x, nextY) == 9) {
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

	public void resetAlien() {
		moveCounter = 0;
		trapCounter = 0;
		chaseModeCounter = 0;
		imageIndex = 0;

		// Intialise Alien grid position
		this.x = spawnX;
		this.y = spawnY;
		graphicalX = x*TILE_WIDTH + GRAPHICAL_X_OFFSET;
		graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;

		// Intialise Alien current direction
		this.dx = -1;
		this.dy = 0;

		//Image startImage = new Image(getClass().getResourceAsStream("res/left2.png"));
		if (alienType == 0) {
			images = RED_IMAGES;
		} else if (alienType == 1) {
			images = PINK_IMAGES;
		} else if (alienType == 2) {
			images = BLUE_IMAGES;
		} else if (alienType == 3) {
			images = ORANGE_IMAGES;
		}
		imageIndex = 0;
		currentImage = images[imageIndex];

		imageView.setX(graphicalX);
		imageView.setY(graphicalY);
	}

	public void changeToFrightMode() {
		frightModeCounter = 0;
		frightenedFlag = true;

		images = FRIGHTENED_IMAGES;
		// make it move slower
		timeline.stop();
		timeline.setRate(0.6);
		timeline.play();
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
		
		if (status == TRAPPED) {
			

			if (trapCounter > initialTrapTime && x == spawnX && y == spawnY) {
				// go out of the cage
				x = spawnX;
				y = spawnY - 2;
				graphicalX = x*TILE_WIDTH + GRAPHICAL_X_OFFSET;
				graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;

				moveCounter = 0;
				dx = -1;
				dy = 0;
				status = MOVING;;
			} else {
				moveCageXAxis();
			}
			imageView.setX(graphicalX);
			imageView.setY(graphicalY);
		}

		if (status == MOVING ) {
			if (dx != 0 && dy == 0) {
				moveXAxis();
			} 
			if (dx == 0 && dy != 0) {
				moveYAxis();
			}

			imageView.setX(graphicalX);
			imageView.setY(graphicalY);
		}
		
		if (frightenedFlag) {

			frightModeCounter++;

			if (frightModeCounter == FRIGHT_MODE_MAX_TIME - 30) {
				images = FLASHING_IMAGES;
			}
			else if (frightModeCounter > FRIGHT_MODE_MAX_TIME) {
				frightenedFlag = false;
				if (alienType == 0) {
					images = RED_IMAGES;
				} else if (alienType == 1) {
					images = PINK_IMAGES;
				} else if (alienType == 2) {
					images = BLUE_IMAGES;
				} else if (alienType == 3) {
					images = ORANGE_IMAGES;
				}

				timeline.stop();
				timeline.setRate(0.95);
				timeline.play();
			}
		}
	}
}
