package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import controller.LevelController;
import model.ShortestPath;

public class Alien extends CharacterAnimate{

	public LevelController levelController;
	public LevelVisuals levelView;
	public ImageView imageView;
	private int keyInput;
	private int alienType;
	private int frightModeCounter;
	private int chaseModeCounter;
	private int scatterModeCounter;	
	private int trapCounter;
	private int spawnX;
	private int spawnY;
	private double graphicalX;
	private double graphicalY;
	private boolean isPlayer;
	public boolean frightenedFlag = false;
	private int initialTrapTime;
	private static final int TRAPPED = 2;
	private static final int FRIGHT_MODE_MAX_TIME = 80;
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

	public Alien(int alienType, LevelController levelController, LevelVisuals levelView, int x, int y, int dx, int dy, int initialTrapTime, boolean isPlayer) {
		this.levelController = levelController;
		this.levelView = levelView;

		keyInput = -1;
		
		this.alienType = alienType;
		frightModeCounter = 0;
		chaseModeCounter = 0;
		scatterModeCounter = 0;	
		trapCounter = 0;
		this.initialTrapTime = initialTrapTime;
		this.isPlayer = isPlayer;

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

	public double getGraphicalX() {
		return graphicalX;
	}

	public double getGraphicalY() {
		return graphicalY;
	}

	/* If moving in y-axis, move in x-axis or continue */
	private void moveLeftOrRight(boolean mustMove) {
		int targetX = 0, targetY = 0;
		/* Each alien type has a different personality based on how it determines it target tile */
		// alienType = 0, targets spaceman directly
		if (alienType == 0 || frightenedFlag) {
			targetX = levelView.spaceman.getX();
			targetY = levelView.spaceman.getY();
		} 
		// alienType = 1, targets two tiles in front of spaceman
		else if (alienType == 1) {
			targetX = levelView.spaceman.getX();
			if (dy == -1) {
				targetY = levelView.spaceman.getY() - 2;
			} else if (dy == 1) {
				targetY = levelView.spaceman.getY() + 2;
			}
		} 
		// alienType = 2, targets 2*length of vector from red ghost to spaceman
		else if (alienType == 2) {
			targetX = Math.abs(2*(levelView.red.getX() - levelView.spaceman.getX()));
			if (dy == -1) {
				targetY =  Math.abs(2*(levelView.red.getY() - levelView.spaceman.getY() - 2));
			} else if (dy == 1) {
				targetY = Math.abs(2*(levelView.red.getY() - levelView.spaceman.getY() + 2));
			}
		} 
		// alienType = 3, targets spaceman directly, until it gets close to spaceman
		else if (alienType == 3) {
			targetX = levelView.spaceman.getX();
			targetY = levelView.spaceman.getY();
		}

		//Initialise shortest path algorithm
		ShortestPath moveRank = new ShortestPath(levelController);
		int moveLeft = Integer.MAX_VALUE, moveRight = Integer.MAX_VALUE, moveContinue = Integer.MAX_VALUE;
		if (dy == -1) {
			moveContinue = moveRank.computeShortest(x, y-1, targetX, targetY);
		} else if (dy == 1) {
			moveContinue = moveRank.computeShortest(x, y+1, targetX, targetY);
		}
		moveLeft = moveRank.computeShortest(x-1, y, targetX, targetY);
		moveRight = moveRank.computeShortest(x+1, y, targetX, targetY);
		
		boolean closeToSpaceman = false;
		// Change flag for alienType = 3, when to close to spaceman
		if (alienType == 3 && (moveLeft <= 8 || moveRight <= 8 || moveContinue <= 8)) {
			closeToSpaceman = true;
		}

		// Move away randomly when alien is frightened or when alienType = 3 is too close to spaceman
		if (frightenedFlag || closeToSpaceman) {
			if (moveLeft != Integer.MAX_VALUE && moveRight == Integer.MAX_VALUE && moveContinue == Integer.MAX_VALUE) {
				dx = -1;
				dy = 0;
			} else if (moveRight != Integer.MAX_VALUE && moveLeft == Integer.MAX_VALUE && moveContinue == Integer.MAX_VALUE) {
				dx = 1;
				dy = 0;
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
					return;
				}
			}
		} 
		// Move based on shortest path algorithm
		else if (!frightenedFlag) {
			if (moveLeft < moveRight && moveLeft < moveContinue) {
				dx = -1;
				dy = 0;
			} else if (moveRight < moveLeft && moveRight < moveContinue) {
				dx = 1;
				dy = 0;
			} else {
				if (mustMove) {
					if (levelController.checkMap(x-1, y) != 1 && levelController.checkMap(x-1, y) != 9) {
						dx = -1;
						dy = 0;
					} else {
						dx = 1;
						dy = 0;
					}
				} else {
					return;
				}
			}
		} 
	}

	/* If moving in x-axis, move in y-axis or continue */
	private void moveUpOrDown(boolean mustMove) {
		int targetX = 0, targetY = 0;
		/* Each alien type has a different personality based on how it determines it target tile */
		// alienType = 0, targets spaceman directly
		if (alienType == 0 || frightenedFlag) {
			targetX = levelView.spaceman.getX();
			targetY = levelView.spaceman.getY();
		} else if (alienType == 1) {
			targetY = levelView.spaceman.getY();
			if (dx == -1) {
				targetX = levelView.spaceman.getX() - 2;
			} else if (dx == 1) {
				targetX = levelView.spaceman.getX() + 2;
			}
		} else if (alienType == 2) {
			targetY = Math.abs(2*(levelView.red.getY() - levelView.spaceman.getY()));
			if (dx == -1) {
				targetX =  Math.abs(2*(levelView.red.getX() - levelView.spaceman.getX() - 2));
			} else if (dx == 1) {
				targetX = Math.abs(2*(levelView.red.getX() - levelView.spaceman.getX() + 2));
			}
		} else if (alienType == 3) {
			targetX = levelView.spaceman.getX();
			targetY = levelView.spaceman.getY();
		}

		//Initialise shortest path algorithm
		ShortestPath moveRank = new ShortestPath(levelController);
		int moveUp = Integer.MAX_VALUE, moveDown = Integer.MAX_VALUE, moveContinue = Integer.MAX_VALUE;
		if (dx == -1) {
			moveContinue = moveRank.computeShortest(x-1, y, targetX, targetY);
		} else if (dx == 1) {
			moveContinue = moveRank.computeShortest(x+1, y, targetX, targetY);
		}
		moveUp = moveRank.computeShortest(x, y-1, targetX, targetY);
		moveDown = moveRank.computeShortest(x, y+1, targetX, targetY);

		boolean closeToSpaceman = false;
		// Change flag for alienType = 3, when to close to spaceman
		if (alienType == 3 && (moveUp <= 8 || moveDown <= 8 || moveContinue <= 8)) {
			closeToSpaceman = true;
		}

		// Move away randomly when alien is frightened
		if (frightenedFlag || closeToSpaceman) {
			if (moveUp != Integer.MAX_VALUE && moveDown == Integer.MAX_VALUE && moveContinue == Integer.MAX_VALUE) {
				dx = 0;
				dy = -1;
			} else if (moveDown != Integer.MAX_VALUE && moveUp == Integer.MAX_VALUE && moveContinue == Integer.MAX_VALUE) {
				dx = 0;
				dy = 1;
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
					return;
				}
			}
		}
		// Move based on shortest path algorithm
		else if (!frightenedFlag) {
			if (moveUp < moveDown && moveUp < moveContinue) {
				dx = 0;
				dy = -1;
			} else if (moveDown < moveUp && moveDown < moveContinue) {
				dx = 0;
				dy = 1;
			} else {
				if (mustMove) {
					if (levelController.checkMap(x, y-1) != 1 && levelController.checkMap(x, y-1) != 9) {
						dx = 0;
						dy = -1;
					} else {
						dx = 0;
						dy = 1;
					}
				} else {
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
			if (!isPlayer) {
				moveUpOrDown(true);
			}
		} else {
			moveCounter++;
			if (moveCounter < ANIMATION_STEP) {
				graphicalX = graphicalX + (dx * MOVE_SPEED);
			} else {
				//levelController.updateMap(dx,dy,x,y);

				moveCounter = 0;
				nextX = x + dx;
				// HARDCODED VALUES FOR TUNNEL X COORDINATE - USE GRID SIZE
				if (nextX < 1  && dx == -1 ) {
					x = 19;
				} else if (nextX > 19 && dx == 1 ) {
					x = 1;
				} else {
					x = x + dx;
				}

				graphicalX = x*TILE_WIDTH + GRAPHICAL_X_OFFSET;
				if (!isPlayer) {
					moveUpOrDown(false);
				}
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
			}
		}
	}

	private void moveYAxis() {
		int nextY = y + dy;
		if (levelController.checkMap(x,nextY) == 1 || levelController.checkMap(x, nextY) == 9) {
			if (!isPlayer) {
				moveLeftOrRight(true);
			}
		} else {
			moveCounter++;
			if (moveCounter < ANIMATION_STEP) {
				graphicalY = graphicalY + (dy * MOVE_SPEED);
			} else {
				//levelController.updateMap(dx,dy,x,y);
				moveCounter = 0;
				y = y + dy;
				graphicalY = y*TILE_HEIGHT + GRAPHICAL_Y_OFFSET;
				if (!isPlayer) {
					moveLeftOrRight(false);
				}
			}
		}
	}

	public void resetAlien() {
		moveCounter = 0;
		trapCounter = 0;
		chaseModeCounter = 0;

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

		status = TRAPPED;
		frightenedFlag = false;
		timeline.setRate(0.95);
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
		
		if (imageIndex == 0 && this.isRunning()) {
			if (isPlayer && status != TRAPPED) {
				changeCurrentDirection(keyInput);
			}			
		}
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
	
	private void moveLeft() {
		// Prevent invalid direction changes
		int nextX = x - 1;
		if (levelController.checkMap(nextX, y) == 1 || levelController.checkMap(nextX, y) == 9) {
			return;
		}
		
		// Change direction
		dx = -1;
		dy = 0;
		
		status = MOVING;
	}

	private void moveRight() {
		// Prevent invalid direction changes
		int nextX = x + 1;
		if (levelController.checkMap(nextX, y) == 1 || levelController.checkMap(nextX, y) == 9) {
			return;
		}
		// Change direction
		dx = 1;
		dy = 0;

		status = MOVING;
	}

	private void moveUp() {
		// Prevent invalid direction changes
		int nextY = y - 1;
		if (levelController.checkMap(x,nextY) == 1 || levelController.checkMap(x,nextY) == 9) {
			return;
		}
		// Change direction
		dx = 0;
		dy = -1;

		status = MOVING;
	}

	private void moveDown() {
		// Prevent invalid direction changes
		int nextY = y + 1;
		if (levelController.checkMap(x,nextY) == 1 || levelController.checkMap(x,nextY) == 9) {
			return;
		}
		// Change direction
		dx = 0;
		dy = 1;

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
