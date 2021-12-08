package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

/**
* <h1>Person</h1>
* Implementação da classe abstrata Person implementa Movable, ActiveElement, Directionable
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public abstract class Person extends GameElement implements Movable, ActiveElement, Directionable {

	private boolean isActive;
	private Direction direction;
	
	/**
	 * Contrutor da classe Person
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */

	public Person(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		this.isActive = true;
		this.direction = null;
	
	}
	
	/**
	 * Contrutor da classe Person, com valor lógico de isActive
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isActive boolean
	 */
	
	public Person(String name, Point2D position, int layerValue, boolean isActive) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.direction = null;
	}
	
	/**
	 * Contrutor da classe Person, com valor lógico de isActive e direção
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isActive boolean
	 * @param direction Direction
	 */

	public Person(String name, Point2D position, int layerValue, boolean isActive, Direction direction) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.direction = direction;
	}
	
	/**
	* Setter setPosition
	* @param position Point2D
	*/

	@Override
	public void setPosition(Point2D position) {
		 super.setPosition(position);
	}
	
	
	/**
	* Este método é utilizado para
	*/
	public abstract void move(int keyCode);
	
	
	/**
	* Este metodo é usado para verificar se a Person pode mover para posicao p
	* @param p Point2D
	* @return boolean
	*/

	public boolean canMoveTo(Point2D p) {
		
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		if (ge.isThereObjectAtPosition(p, e -> e instanceof Person)) return false;
		return true;
		
	}
	
	
	/**
	 * Getter isActive
	 * @return isActive boolean
	 */
	
	@Override
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Setter setActive
	 */
	
	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	/**
	 * Setter setDirection
	 */
	
	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	* Getter getDirection
	*/
	
	public Direction getDirection() {
		return direction;
	}
	
}