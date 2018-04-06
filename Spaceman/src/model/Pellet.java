package model;

import javafx.scene.shape.*;

public class Pellet {
	private double graphicalX;
	private double graphicalY;
	private Circle model;
	
	public Pellet(double x, double y, double radius) {
		graphicalX = x;
		graphicalY = y;
		model = new Circle(graphicalX, graphicalY, radius);
	}
	
	public double getX() {
		return graphicalX;
	}
	
	public double getY() {
		return graphicalY;
	}
	
	public Circle returnPellet ( ) {
		return model;
	}
	
}
