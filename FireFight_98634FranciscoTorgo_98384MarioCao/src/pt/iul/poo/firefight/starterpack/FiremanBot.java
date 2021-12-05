package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

/**
* <h1>Fireman</h1>
* Implementação da classe Fireman
* Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
* Tem atributos e metodos repetidos em relacao ao que está definido noutras classes
* 
* @author Mario Cao-N98384
* @author Francisco Trogo-N98634
* @since 2021-11-01
*/

public class FiremanBot extends GameElement implements ActiveElement, Directionable{
	//TODO Remover bulldozer
	private boolean isActive;
	private Direction dir;
	
	/**
	* Construtor Fireman
	*/
	public FiremanBot(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		this.isActive = true;
		this.dir = null;
	}
	
	/**
	* Construtor Fireman com atribuição de Active
	*/
	public FiremanBot(String name, Point2D position, int layerValue, boolean isActive) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.dir = null;
	}
	
	public FiremanBot(String name, Point2D position, int layerValue, boolean isActive, Direction direction) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.dir = direction;
	}
	

	
	@Override
	public String getName() {
		if(dir != null) {
			switch(this.dir) {
				case LEFT:
					return "firemanbot_left";
				case RIGHT:
					return "firemanbot_right";
				default:
					break;
			}	
		}
		return "firemanbot";
	}
	
	// Move numa direcao
	public void move() {
			
		List<ImageTile> fires = ge.selectObjectsList(e -> e instanceof Fire);
		
		
		if(!fires.isEmpty()) {
			double minDistance = 0;
			Point2D minPos = null;
			
			for(int i = 0; i < fires.size(); i++) {
				Point2D firePos = fires.get(i).getPosition();
				double distance = this.getPosition().distanceTo(firePos);
				if(i == 0) {
					minPos = firePos;
					minDistance = distance;
				}
				if(distance <= minDistance) {
					minDistance = distance;
					minPos = firePos;
				}
					
				
			}
			
			
			Direction direction = this.getPosition().directionTo(minPos);
			
			Point2D newPosition = super.getPosition().plus(direction.asVector());
			GameEngine ge = GameEngine.getInstance();
			
			//Se houver fogo limpa
			if(ge.isThereObjectAtPosition(newPosition, e -> e instanceof Fire) ) {
				
				Fire.cleanFire(newPosition, direction);	
				
			}else if (canMoveTo(newPosition)){
				
				this.setDirection(direction);
				setPosition(newPosition);

			}
			
		
			
		}
		
	
	
	}
	
	

	// Verifica se a posicao p esta' dentro da grelha de jogo
	public boolean canMoveTo(Point2D p) {
		
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		if (ge.isThereObjectAtPosition(p, e -> e instanceof Fireman || e instanceof Bulldozer)) return false;
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
	
	@Override
	public void setDirection(Direction direction) {
		this.dir = direction;
	}
	

	
}
