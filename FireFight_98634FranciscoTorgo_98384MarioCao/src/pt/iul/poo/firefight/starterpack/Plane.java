package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

/**
* <h1>Plane</h1>
* Implementação da classe Plane
* Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
* Tem atributos e metodos repetidos em relacao ao que está definido noutras classes
* 
* @author Mario Cao-N98384
* @author Francisco Trogo-N98634
* @since 2021-11-01
*/

public class Plane extends GameElement implements ActiveElement{
	
	private static GameEngine ge = GameEngine.getInstance();	
	private boolean isActive;
	
	/**
	* Construtor Plane
	*/

	public Plane(String name, Point2D position, int layerValue){
		super(name, position, layerValue);
		isActive = true;
	}
	
	
	public static void init() {
		Point2D initPos = new Point2D(Fire.getLargestFireRow(), 9);
		Plane plane = new Plane("plane", initPos, 4);
		ge.addElement(plane);
	}
	
	
	
	
	// Move numa direcao
	public void move() {
		Direction UP = Direction.UP;
		
		
		
		for(int i = 0; i <= 2; i++) {
			Point2D newPosition = super.getPosition().plus(UP.asVector());
			Point2D botPosition = super.getPosition().plus(Direction.DOWN.asVector());
			if(canMoveTo(newPosition)) {
				Fire.cleanFire(newPosition, UP);
				Fire.cleanFire(botPosition, UP);	
				setPosition(newPosition);
			}else {
				ge.removeElement(this);
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
