package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Plane extends GameElement implements Movable{

	public Plane(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	
	// Move numa direcao
	public void move(int keyCode) {
		Direction direction = Direction.directionFor(keyCode);
		Point2D newPosition = super.getPosition().plus(direction.asVector());
		GameEngine ge = GameEngine.getInstance();
		
		if(ge.isThereFireAtPosition(newPosition) ) {
			
			Fire.cleanFire(newPosition, direction);	
			
		}else if (canMoveTo(newPosition)){
			
			if(ge.isThereBulldozer(newPosition)) {
				ge.removeElement(ge.getFireman());
			}else {
				setPosition(newPosition);
				Fire.propagateFire();
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
