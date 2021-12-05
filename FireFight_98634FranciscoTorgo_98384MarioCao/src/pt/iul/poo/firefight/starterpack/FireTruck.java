package pt.iul.poo.firefight.starterpack;

import java.util.Iterator;
import java.util.List;

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

public class FireTruck extends GameElement implements Movable, ActiveElement, Directionable, Drivable{
	
	private static final GameEngine ge = GameEngine.getInstance();
	private boolean isActive;
	private Direction dir;
	
	
	public FireTruck(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		isActive = false;
		this.dir = null;
	}
	
	
	public FireTruck(String name, Point2D position, int layerValue, boolean isActive, Direction direction) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.dir = direction;
	}
	
	public FireTruck(String name, Point2D position, int layerValue, boolean isActive) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.dir = null;
	}
	
	@Override
	public String getName() {
		if(this.dir != null) {
			switch(this.dir) {
				case LEFT:
					return "firetruck_left";
				case RIGHT:
					return "firetruck_right";
			}	
		}
		return "firetruck";		
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
			
			cleanFire(direction, newPosition);
			ge.getFireman().setPosition(newPosition);
			setPosition(newPosition);
			
			Fire.propagateFire(newPosition);
			Fire.addBurnTime(newPosition);
		}
	
	}
	
	
	//TODO como assim perpendicular ao movimento?
	private void cleanFire(Direction direction, Point2D position) {
		List<Point2D> fires = position.getFrontRect(direction, 3, 2);
		
		Iterator<Point2D> it = fires.iterator();
		while(it.hasNext()) {
			Point2D setPos = it.next();
			Fire.cleanFire(setPos, direction);
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