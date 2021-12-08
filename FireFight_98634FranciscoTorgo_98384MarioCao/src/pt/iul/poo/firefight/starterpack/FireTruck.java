package pt.iul.poo.firefight.starterpack;

import java.util.Iterator;
import java.util.List;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Isso sera' de evitar na versao a serio do projeto

/**
* <h1>FireTruck</h1>
* Implementação da classe FireTruck extende Vehicle
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class FireTruck extends Vehicle{
	
	/**
	 * Contrutor da classe FireTruck
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */
	
	public FireTruck(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	/**
	 * Contrutor da classe FireTruck, com valor lógico de isActive
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isActive boolean
	 */

	public FireTruck(String name, Point2D position, int layerValue, boolean isActive) {
		super(name, position, layerValue, isActive);
	}
	
	/**
	 * Contrutor da classe FireTruck, com valor lógico de isActive e direção
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isActive boolean
	 * @param direction Direction
	 */
	
	public FireTruck(String name, Point2D position, int layerValue, boolean isActive, Direction direction) {
		super(name, position, layerValue, isActive, direction);
	}
	
	/**
	 * Getter getName
	 * @return "firetruck" String,
	 * "firetruck_left" String,
	 * "firetruck_right" String
	 */
	@Override
	public String getName() {
		if(super.getDirection() != null) {
			switch(super.getDirection()) {
				case LEFT:
					return "firetruck_left";
				case RIGHT:
					return "firetruck_right";
			default:
				break;
			}	
		}
		return "firetruck";		
	}
	
	/**
	* Este metodo é usado para mover um objeto na interface grafica, neste caso FireTruck
	* @param keyCode integer traducao codigo para tela.
	*/
	
	// Move numa direcao
	@Override
	public void move(int keyCode) {
		Direction direction = Direction.directionFor(keyCode);
		Point2D newPosition = super.getPosition().plus(direction.asVector());
					
		if(canMoveTo(newPosition) ) {
			
			//Atualizar a direction do Bulldozer
			super.setDirection(direction);
			
			cleanFireRectangle(direction, newPosition);
			
			ge.getFireman().setPosition(newPosition);
			super.setPosition(newPosition);
			
			Fire.propagateFire(newPosition);
			Fire.addBurnTime(newPosition);
		}
	}
	
	/**
	 * Este método é usado para limpar o fogo na direção indicada, Firetruck 2x3
	 * @param direction
	 * @param position
	 */
	
	//TODO como assim perpendicular ao movimento?
	private void cleanFireRectangle(Direction direction, Point2D position) {
		List<Point2D> fires = position.getFrontRect(direction, 3, 2);
		
		Iterator<Point2D> it = fires.iterator();
		while(it.hasNext()) {
			Point2D setPos = it.next();
			Fire.cleanFire(setPos, direction);
		}
	}

	/**
	 * ToString FireTruck
	 * @return "FireTruck" String
	 */
	
	//TODO Debug
	@Override
	public String toString() {
		return "FireTruck";	
	}

}