package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Water extends GameElement implements Disappears, Directionable{
	
	private Direction direction;
	
	public Water(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		this.direction = null;
	}	
	
	public Water(String name, Point2D position, int layerValue, Direction direction) {
		super(name, position, layerValue);
		this.direction = direction;
	}
	
	public static ImageTile create( Point2D position, Direction direction) {
		ImageTile obj = GameElement.addFromString("Water", position);
		((Water) obj).setDirection(direction);
		return obj;
		
	}
	
	@Override
	public String getName() {
		if(this.direction != null) {
			switch(this.direction) {
				case UP:
					return "water_up";
				case DOWN:
					return "water_down";
				case LEFT:
					return "water_left";
				case RIGHT:
					return "water_right";				
			}	
		}
		return "water";
	}
	
	//TODO Debug
	public String toString() {
		return "Water";	}
	
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	
	
}
