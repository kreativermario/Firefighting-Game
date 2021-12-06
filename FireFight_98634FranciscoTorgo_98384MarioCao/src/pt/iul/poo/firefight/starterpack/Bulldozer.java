package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Isso sera' de evitar na versao a serio do projeto

/**
* <h1>Bulldozer</h1>
* Implementação da classe Bulldozer
* Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
* Tem atributos e metodos repetidos em relacao ao que está definido noutras classes
* 
* @author Mario Cao-N98384
* @author Francisco Trogo-N98634
* @since 2021-11-01
*/

public class Bulldozer extends GameElement implements Movable, ActiveElement, Directionable, Drivable{
	
	private static final GameEngine ge = GameEngine.getInstance();
	private boolean isActive;
	private Direction dir;
	
	
	public Bulldozer(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		isActive = false;
		this.dir = null;
	}
	
	
	public Bulldozer(String name, Point2D position, int layerValue, boolean isActive, Direction direction) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.dir = direction;
	}
	
	public Bulldozer(String name, Point2D position, int layerValue, boolean isActive) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.dir = null;
	}
	
	@Override
	public String getName() {
		if(this.dir != null) {
			switch(this.dir) {
				case UP:
					return "bulldozer_up";
				case DOWN:
					return "bulldozer_down";
				case LEFT:
					return "bulldozer_left";
				case RIGHT:
					return "bulldozer_right";
			}	
		}
		return "bulldozer";		
	}
	

	/**
	* Este metodo é usado para mover um objeto na interface grafica, neste caso Bulldozer
	* @param keyCode traducao codigo para tela.
	*/
	
	// Move numa direcao
	public void move(int keyCode) {
		Direction direction = Direction.directionFor(keyCode);
		Point2D newPosition = super.getPosition().plus(direction.asVector());
					
		if(canMoveTo(newPosition) ) {
			
			//Atualizar a direction do Bulldozer
			this.setDirection(direction);
			
			this.demolish(newPosition);
			ge.getFireman().setPosition(newPosition);
			this.setPosition(newPosition);
			
			Fire.propagateFire(newPosition);
			Fire.addBurnTime(newPosition);
			
		}
	
	}
	
	private void demolish(Point2D newPosition) {
		
		ImageTile image =  ge.getObjectAtPosition(newPosition, e -> e instanceof Burnable);
		if(!ge.isThereObjectAtPosition(newPosition, e -> e instanceof Fire) && image != null) {
			ge.removeElement(image);
			ge.addElement(GameElement.create("Land", newPosition));
		}
		
	}

	/**
	* Este metodo é usado para verificar se uma posicao esta dentro da grelha de jogo
	* @param p Point2D.
	*/

	// Verifica se a posicao p esta' dentro da grelha de jogo
	public boolean canMoveTo(Point2D p) {
		
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		if(ge.isThereObjectAtPosition(p, e -> e instanceof Fire)) return false;
		if(ge.isThereObjectAtPosition(p, e -> e instanceof FuelBarrel && ((FuelBarrel) e).isBurnt() == false)) return false;
		if(ge.isThereObjectAtPosition(p, e -> e instanceof Drivable)) return false;
		return true;
		
	}
	
	/**
	* Setter da posicao
	* @param position Point2D.
	*/
	
	public void setPosition(Point2D position) {
		 super.setPosition(position);
	}
		
	
	//TODO Debug
	@Override
	public String toString() {
		return "Bulldozer";	
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