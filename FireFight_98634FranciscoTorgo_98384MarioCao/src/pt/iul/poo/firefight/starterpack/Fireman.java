package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

/**
* <h1>Fireman</h1>
* Implementa��o da classe Fireman
* Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
* Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes
* 
* @author Mario Cao-N98384
* @author Francisco Trogo-N98634
* @since 2021-11-01
*/

public class Fireman extends GameElement implements Movable, ActiveElement{
	//TODO Remover bulldozer
	private boolean isActive;
	
	/**
	* Construtor Fireman
	*/
	public Fireman(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		this.isActive = true;
	}
	
	/**
	* Construtor Fireman com atribui��o de Active
	*/
	public Fireman(String name, Point2D position, int layerValue, boolean isActive) {
		super(name, position, layerValue);
		this.isActive = isActive;
	}
	
	
	// Move numa direcao
	public void move(int keyCode) {
		Direction direction = Direction.directionFor(keyCode);
		Point2D newPosition = super.getPosition().plus(direction.asVector());
		GameEngine ge = GameEngine.getInstance();
		
		if(ge.isThereObjectAtPosition(newPosition, e -> e instanceof Fire) ) {
			
			Fire.cleanFire(newPosition, direction);	
			
		}else if (canMoveTo(newPosition)){
			
			if(ge.isThereObjectAtPosition(newPosition, e -> e instanceof Bulldozer)) {
				setPosition(newPosition);
				ge.removeImage(this);
				this.setActive(false);
				((Bulldozer) ge.getObjectAtPosition(newPosition, e -> e instanceof Bulldozer)).setActive(true);
			}else {
				setPosition(newPosition);
				Fire.propagateFire(newPosition);
				Fire.addBurnTime(newPosition);
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



	@Override
	public boolean isActive() {
		return isActive;
	}


	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
}
