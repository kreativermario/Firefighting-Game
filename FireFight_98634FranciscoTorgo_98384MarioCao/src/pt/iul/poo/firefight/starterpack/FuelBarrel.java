package pt.iul.poo.firefight.starterpack;

import java.util.*;

import pt.iul.ista.poo.utils.Point2D;

/**
* <h1>FuelBarrel</h1>
* Implementação da classe FuelBarrel extende Burnable, Updatable, Combustible
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class FuelBarrel extends GameElement implements Burnable, Updatable, Combustible{
	public final double probability = 0.9;
	public final int INITIAL_BURN_TIME = 3;
	public final String BURNT_NAME = "burntfuelbarrel";
	private int burnTime;
	private boolean isBurnt;
	
	/**
	 * Contrutor da classe FuelBarrel
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */
	
	public FuelBarrel(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		burnTime = INITIAL_BURN_TIME;
		isBurnt = false;
	}
	
	/**
	 * Contrutor da classe FuelBarrel, com valor lógico de isBurnt
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isBurnt boolean
	 */
	
	public FuelBarrel(String name, Point2D position, int layerValue, boolean isBurnt) {
		super(name, position, layerValue);
		burnTime = INITIAL_BURN_TIME;
		this.isBurnt = isBurnt;
	}
	
	/**
	 * Este método é usado para definir a explosão do FuelBarrel
	 * @param nextMovablePosition
	 */
	
	public void explode(Point2D nextMovablePosition) {
		
		List<Point2D> wideNeighbours = this.getPosition().getWideNeighbourhoodPoints();
		Iterator<Point2D> it = wideNeighbours.iterator();
		GameEngine ge = GameEngine.getInstance();
		
		ge.addElement(GameElement.create("Explosion", this.getPosition()));	//Cria a explosao
		
		this.setBurnt(true);												//Coloca o barril a queimado
		
		while(it.hasNext()) {
			Point2D setPos = it.next();
			
			if(Fire.canSetFire(setPos, nextMovablePosition)) {

				ge.addElement(GameElement.create("Fire", setPos));			//Adiciona os fogos
			}	
		}

	}
	
	/**
	 * Getter getName Bulldozer
	 * @return BURNT_NAME, super.getName String
	 */
	
	@Override
	public String getName() {
		if(isBurnt) return BURNT_NAME ;	
		else return super.getName();
	}

	/**
	* ToString do FuelBarrel
	* @return "FBarrel" String
	*/
	
	//TODO Debug
	@Override
	public String toString() {
		return "FBarrel";	
	}

	/**
	 * Getter getProbability
	 * @return probabibility double
	 */
	
	@Override
	public double getProbability() {
		return probability;
	}

	/**
	 * Getter getBurnTime
	 * @return burnTime
	 */
	
	@Override
	public int getBurnTime() {
		return burnTime;
	}

	/**
	 * Setter setBurnTime
	 */
	
	@Override
	public void setBurnTime(int newBurn) {
		this.burnTime = newBurn;
	}

	/**
	 * Getter isBurnt
	 * @return isBurnt boolean
	 */
	
	@Override
	public boolean isBurnt() {
		return isBurnt;
	}
	
	/**
	 * Setter setBurnt
	 */
	
	@Override
	public void setBurnt(boolean isBurnt) {
		this.isBurnt = isBurnt;
	}

}