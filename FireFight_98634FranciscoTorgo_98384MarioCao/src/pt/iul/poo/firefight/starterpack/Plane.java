package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Plane extends GameElement{

	public Plane(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	
	// Move numa direcao
	public void move() {
		Direction UP = Direction.UP;
		
		GameEngine ge = GameEngine.getInstance();	
		
		for(int i = 0; i <= 2; i++) {
			Point2D newPosition = super.getPosition().plus(UP.asVector());
			Point2D botPosition = super.getPosition().plus(Direction.DOWN.asVector());
			if(canMoveTo(newPosition)) {
				Fire.cleanFire(newPosition, UP);
				Fire.cleanFire(botPosition, UP);	
				setPosition(newPosition);
			}else {
				ge.removeElement(ge.getPlane());
			}
			
		}

	
	}
	
	

	// Verifica se a posicao p esta' dentro da grelha de jogo
	public boolean canMoveTo(Point2D p) {
		
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}
	
	public void setPosition(Point2D position) {
		 super.setPosition(position);
	}
	
	
	//TODO Debug
	@Override
	public String toString() {
		return "Fireman";	
	}
}
