package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;


/**
* <h1>FiremanBot</h1>
* Implementação da classe FiremanBot
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class FiremanBot extends Person{

	/**
	 * Contrutor da classe FiremanBot
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */
	
	public FiremanBot(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	/**
	 * Contrutor da classe FiremanBot, com direção
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param direction Direction
	 */
	
	public FiremanBot(String name, Point2D position, int layerValue, boolean isActive , Direction direction) {
		super(name, position, layerValue, isActive, direction);
	}
	
	/**
	 * Getter getName FiremanBot
	 * @return "firemanbot" String,
	 * 		   "firemanbot_left" String,
	 * 		   "firemanbot_right" String
	 */
	
	@Override
	public String getName() {
		if(super.getDirection() != null) {
			switch(super.getDirection()) {
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
	
	/**
	* Este metodo é usado para mover um objeto na interface grafica, neste caso, de forma automatica o FiremanBot
	* @param keyCode integer traducao codigo para tela
	*/
	
	public void move(int key) {
			
		List<ImageTile> fires = ge.selectObjectsList(e -> e instanceof Fire);			//Array com a lista de fogos
		
		if(!fires.isEmpty()) {										//Se houver fogos, procura o fogo mais próximo e vai andando até ele
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
			
			//Se houver fogo limpa
			if(ge.isThereObjectAtPosition(newPosition, e -> e instanceof Fire) ) {
				
				Fire.cleanFire(newPosition, direction);	
				
			}else if (canMoveTo(newPosition)){
				
				super.setDirection(direction);
				super.setPosition(newPosition);
			}
		}
	}
	
	/**
	 * Este método é usado par verificar se o FiremanBot esta dentro da grelha de jogo e entre outros
	 * @param p Point2D
	 * @return boolean
	 */
	
	@Override
	public boolean canMoveTo(Point2D p) {
		
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		if (ge.isThereObjectAtPosition(p, e -> e instanceof Person 
				|| e instanceof Vehicle)) return false;		//Nao deixar o firemanbot ir para uma posicao com um veiculo ou pessoa (Fireman ou FiremanBot)
		return true;
	}
	
	/**
	 * ToString FiremanBot
	 * @return "FiremanBot" String
	 */
	
	//TODO Debug
	@Override
	public String toString() {
		return "FiremanBot";	
	}

}