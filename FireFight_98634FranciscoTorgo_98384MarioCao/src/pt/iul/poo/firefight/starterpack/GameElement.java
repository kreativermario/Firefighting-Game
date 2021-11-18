package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {

	private Point2D position;
	private int layerValue;
	private String name;
	
	public GameElement(String name, Point2D position, int layerValue) {
		this.name = name;
		this.position = position;
		this.layerValue = layerValue;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	public String getName(){
		return name;
	}
	
	public int getLayer(){
		return layerValue;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;		
	}

}