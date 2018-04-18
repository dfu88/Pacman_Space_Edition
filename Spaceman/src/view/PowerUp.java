package view;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;

public class PowerUp {
	private double graphicalX;
	private double graphicalY;
	private ImageView powerUp;
	private int nextRespawnTime;
	
	public PowerUp(double graphicalX, double graphicalY, Image img) {
		
		this.graphicalX = graphicalX;
		this.graphicalY = graphicalY;
		powerUp = new ImageView(img);
		powerUp.setX(graphicalX);
		powerUp.setY(graphicalY);
	}
	
	public double getGraphicalX() {
		return graphicalX;
	}
	
	public double getGraphicalY() {
		return graphicalY;
	}
	
	public ImageView returnPowerUp() {
		return powerUp;
	}
	
	public void setRespawnTime(int newTime) {
		nextRespawnTime = newTime;
	}
	
	public int getRespawnTime() {
		return nextRespawnTime;
	}
	
}
