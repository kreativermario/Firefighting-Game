package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

/**
* <h1>Bulldozer</h1>
* Implementação da classe Bulldozer que extende Vehicle
* Define Bulldozer
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class Bulldozer extends Vehicle{
	
	
	/**
	 * Contrutor da classe Buldozer
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */
	
	public Bulldozer(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}

	/**
	 * Contrutor da classe Buldozer, com valor lógico de isActive
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isActive boolean
	 */
	
	public Bulldozer(String name, Point2D position, int layerValue, boolean isActive) {
		super(name, position, layerValue, isActive);
	}
	
	/**
	 * Contrutor da classe Buldozer, com valor lógico de isActive e direção
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isActive boolean
	 * @param direction Direction
	 */
	
	public Bulldozer(String name, Point2D position, int layerValue, boolean isActive, Direction direction) {
		super(name, position, layerValue, isActive, direction);
	}
	
	/**
	 * Getter getName Bulldozer
	 * @return "bulldozer" String,
	 * 		   "bulldozer_up" String,
	 * 		   "bulldozer_down" String,
	 * 		   "bulldozer_left" String,
	 * 		   "bulldozer_right" String
	 */
	
	@Override
	public String getName() {
		if(super.getDirection() != null) {
			switch(super.getDirection()) {
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
	* @param keyCode integer traducao codigo para tela
	*/
	
	// Move numa direcao
	@Override
	public void move(int keyCode) {
		Direction direction = Direction.directionFor(keyCode);
		Point2D newPosition = super.getPosition().plus(direction.asVector());
					
		if(canMoveTo(newPosition) ) {
			
			//Atualizar a direction do Bulldozer
			this.setDirection(direction);
			
			this.demolish(newPosition);
			
			//Mover o bulldozer e o fireman (que está invisivel)
			ge.getFireman().setPosition(newPosition);
			this.setPosition(newPosition);
			
			Fire.propagateFire(newPosition);
			Fire.addBurnTime(newPosition);
		}
	}
	
	
	/**
	 * Este método é usado para implementar alteração de terreno do Bulldozer para Land, quando valido
	 * @param newPosition Point2D
	 */
	
	private void demolish(Point2D newPosition) {
		
		ImageTile image =  ge.getObjectAtPosition(newPosition, e -> e instanceof Burnable);
		if(!ge.isThereObjectAtPosition(newPosition, e -> e instanceof Fire) && image != null) {
			ge.removeElement(image);
			ge.addElement(GameElement.create("Land", newPosition));
		}
	}

	/**
	 * ToString Bulldozer
	 * @return "Bulldozer" String
	 */
	
	//TODO Debug
	@Override
	public String toString() {
		return "Bulldozer";	
	}

}