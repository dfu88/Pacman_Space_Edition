package view;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;

public class PowerUp {
	private double graphicalX;
	private double graphicalY;
	private ImageView powerUp;
	private Circle model;
	private int nextRespawnTime;
	private ArrayList<Image> imgList;
	
	public PowerUp(double graphicalX, double graphicalY, Image img) {
//	public PowerUp(double graphicalX, double graphicalY, double radius) {
		
		this.graphicalX = graphicalX;
		this.graphicalY = graphicalY;
		//image = new IMa
		powerUp = new ImageView(img);
//		System.out.println("asas");
		powerUp.setX(graphicalX);
		powerUp.setY(graphicalY);
//		model = new Circle(graphicalX, graphicalY, radius);
	}
	
	public double getGraphicalX() {
		return graphicalX;
	}
	
	public double getGraphicalY() {
		return graphicalY;
	}
	
//	public Circle returnPowerUp() {
//		return model;
//	}
	
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
