package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;


/**
* <h1>Fireman</h1>
* Implementação da classe Fireman que extende Person
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class Fireman extends Person{

	
	/**
	* Construtor da classe Fireman
	* @param name String
	* @param position Point2D
	* @param layerValue integer
	*/
	
	public Fireman(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	/**
	 * Contrutor da classe Fireman, com valor lógico de isActive
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isActive boolean
	 */
	
	public Fireman(String name, Point2D position, int layerValue, boolean isActive) {
		super(name, position, layerValue, isActive);
	}
	
	/**
	 * Contrutor da classe Fireman, com valor lógico de isActive e direção
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isActive boolean
	 * @param direction Direction
	 */
	
	public Fireman(String name, Point2D position, int layerValue, boolean isActive, Direction direction) {
		super(name, position, layerValue, isActive, direction);
	}
	

	/**
	 * ToString Fireman
	 * @return "fireman" String
	 */
	
	@Override
	public String getName() {
		if(super.getDirection() != null) {
			switch(super.getDirection()) {
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
	
	/**
	* Este método é usado para mover um objeto na interface grafica, neste caso Fireman
	* @param keyCode integer traducao codigo para tela.
	*/
	
	// Move numa direcao
	public void move(int keyCode) {
		
		Direction direction = Direction.directionFor(keyCode);
		Point2D newPosition = super.getPosition().plus(direction.asVector());
		
		//Se houver fogo limpa
		if(ge.isThereObjectAtPosition(newPosition, e -> e instanceof Fire) ) {
			
			Fire.cleanFire(newPosition, direction);	
		
		//Se poder mover para a nova posição
		}else if (canMoveTo(newPosition)){
			
			super.setDirection(direction);
			super.setPosition(newPosition);
			
			Fire.propagateFire(newPosition);
			Fire.addBurnTime(newPosition);
			
			//Se houver Vehicle, ou seja, fireman vai entrar no veiculo
			if(ge.isThereObjectAtPosition(newPosition, e -> e instanceof Vehicle)) {
				
				getInto(newPosition);
				
			}
				
		}
	
	}
	
	/**
	 * Este método é usado para introduzir o Fireman em elementos Drivable
	 * @param newPosition Point2D
	 */
	
	private void getInto(Point2D newPosition) {
		
		ge.removeImage(this);  //Retira o fireman do GUI mas nao do tileList
		
		super.setActive(false);	//Coloca o fireman a inativo
		
		((ActiveElement) ge.getObjectAtPosition(newPosition, e -> e instanceof Vehicle)).setActive(true);		//Coloca o Drivable a ativo	
		
		
	}
		
	/**
	 * ToString Fireman
	 * @return "Fireman" String
	 */
	
	//TODO Debug
	@Override
	public String toString() {
		return "Fireman";	
	}

}