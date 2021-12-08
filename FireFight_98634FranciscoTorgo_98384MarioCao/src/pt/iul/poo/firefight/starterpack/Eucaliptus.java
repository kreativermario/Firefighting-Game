package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

/**
* <h1>Eucaliptus</h1>
* Implementação da classe Eucaliptus
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class Eucaliptus extends GameElement implements Burnable, Updatable{
	
	public final double PROBABILITY = Probability.EUCALIPTUS.getProbability();
	public final int INITIAL_BURN_TIME = 5;
	public final String BURNT_NAME = "burnteucaliptus";
	private int burnTime;
	private boolean isBurnt;
	
	/**
	 * Construtor da classe Eucaliptus
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */
	
	public Eucaliptus(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		burnTime = INITIAL_BURN_TIME;
		isBurnt = false;
	}
	
	
	/**
	 * Construtor da classe Eucaliptus, com valor lógico isBurnt
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isBurnt boolean
	 */
	public Eucaliptus(String name, Point2D position, int layerValue, boolean isBurnt) {
		super(name, position, layerValue);
		burnTime = INITIAL_BURN_TIME;
		this.isBurnt = isBurnt;
	}
	
	
	/**
	 * Getter Name
	 * @return BURNT_NAME, super.getName() String
	 */
	@Override
	public String getName() {
		if(isBurnt) return BURNT_NAME ;	
		else return super.getName();
	}

	
	/**
	* ToString do Eucaliptus
	* @return "Eucaliptus" String
	*/
	//TODO Debug
	@Override
	public String toString() {
		return "Eucaliptus";	
	}

	/**
	 * Getter getProbability
	 * @return PROBABILITY double
	 */
	
	@Override
	public double getProbability() {
		return PROBABILITY;
	}

	/**
	 * Getter getBurnTime
	 * @return burntime integer
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
	 * 
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