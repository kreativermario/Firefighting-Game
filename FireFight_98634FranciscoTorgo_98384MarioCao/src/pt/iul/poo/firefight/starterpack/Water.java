package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;


/**
* <h1>Water</h1>
* Implementação da classe Water
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class Water extends GameElement implements Disappears, Directionable{
	
	private Direction direction;
	
	/**
	 * Contrutor da classe Water
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */

	public Water(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		this.direction = null;
	}	
	
	/**
	 * Contrutor da classe Water, com a direção
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param direction Direction
	 */

	public Water(String name, Point2D position, int layerValue, Direction direction) {
		super(name, position, layerValue);
		this.direction = direction;
	}
	
	/**
	* Este método é usado para criar o objeto numa determinada posição
	* @param position Point2D
	* @param direction Direction
	* @return obj ImageTile
	*/

	public static ImageTile create( Point2D position, Direction direction) {
		ImageTile obj = GameElement.addFromString("Water", position);
		((Water) obj).setDirection(direction);
		return obj;
		
	}
	
	/**
	* Getter getName
	* return "water" String
	*/

	@Override
	public String getName() {
		if(this.direction != null) {
			switch(this.direction) {
				case UP:
					return "water_up";
				case DOWN:
					return "water_down";
				case LEFT:
					return "water_left";
				case RIGHT:
					return "water_right";				
			}	
		}
		return "water";
	}
	
	/**
	* ToString Water
	* @return "Water" String
	*/
	//TODO Debug
	public String toString() {
		return "Water";	}
	
	/**
	* Setter setDirection
	* @param direction Direction
	*/
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}