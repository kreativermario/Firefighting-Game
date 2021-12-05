package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

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

public class Fireman extends GameElement implements Movable, ActiveElement, Directionable{
	//TODO Remover bulldozer
	private boolean isActive;
	private Direction direction;
	
	/**
	* Construtor Fireman
	*/
	public Fireman(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		this.isActive = true;
		this.direction = null;
	}
	
	/**
	* Construtor Fireman com atribuição de Active
	*/
	public Fireman(String name, Point2D position, int layerValue, boolean isActive) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.direction = null;
	}
	
	public Fireman(String name, Point2D position, int layerValue, boolean isActive, Direction direction) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.direction = direction;
	}
	

	
	@Override
	public String getName() {
		if(direction != null) {
			switch(this.direction) {
				case LEFT:
					return "fireman_left";
				case RIGHT:
					return "fireman_right";
				default:
					break;
			}	
		}
		return "fireman";
	}
	
	// Move numa direcao
	public void move(int keyCode) {
		
		Direction direction = Direction.directionFor(keyCode);
		Point2D newPosition = super.getPosition().plus(direction.asVector());
		GameEngine ge = GameEngine.getInstance();
		
		//Se houver fogo limpa
		if(ge.isThereObjectAtPosition(newPosition, e -> e instanceof Fire) ) {
			
			Fire.cleanFire(newPosition, direction);	
			
		}else if (canMoveTo(newPosition)){
			
			this.setDirection(direction);
			setPosition(newPosition);
			Fire.propagateFire(newPosition);
			Fire.addBurnTime(newPosition);
			
			//Se houver Bulldozer, ou seja, fireman vai entrar no Bulldozer
			if(ge.isThereObjectAtPosition(newPosition, e -> e instanceof Bulldozer)) {
				
				ge.removeImage(this);  //Retira o fireman do GUI mas nao do tileList
				
				this.setActive(false);	//Coloca o fireman a inativo
				
				((Bulldozer) ge.getObjectAtPosition(newPosition, e -> e instanceof Bulldozer)).setActive(true);		//Coloca o Bulldozer a ativo
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
	
	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	

	
}
