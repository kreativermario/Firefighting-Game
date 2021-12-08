package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

/**
* <h1>Vehicle</h1>
* Implementação da classe abstrata Vehicle
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public abstract class Vehicle extends GameElement implements Movable, ActiveElement, Directionable, Drivable {

	private boolean isActive;
	private Direction direction;
	
	/**
	 * Contrutor da classe Vehicle
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */
	
	public Vehicle(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		this.isActive = false;
		this.direction = null;
	}
	
	/**
	 * Contrutor da classe Vehicle, com valor lógico de isActive
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isActive boolean
	 */

	public Vehicle(String name, Point2D position, int layerValue, boolean isActive) {
		super(name, position, layerValue);
		this.isActive = isActive;
		this.direction = null;
	}
	
	/**
	 * Contrutor da classe Vehicle, com valor lógico de isActive e direção
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isActive boolean
	 * @param direction Direction
	 */
	 
	public Vehicle(String name, Point2D position, int layerValue, boolean isActive, Direction direction) {
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
	* Este metodo é usado para verificar se uma posicao esta dentro da grelha de jogo
	* @param p Point2D
	* @return boolean
	*/

	// Verifica se a posicao p está dentro da grelha de jogo
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
	* @param keycode integer
	*/
	public abstract void move(int keyCode);
	
	/**
	 * Setter setDirection
	 */
	
	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	* Getter getDirection
	* @param direction
	*/
	public Direction getDirection() {
		return direction;
	}

}