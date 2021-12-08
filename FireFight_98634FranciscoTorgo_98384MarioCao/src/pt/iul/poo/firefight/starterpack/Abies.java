package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

/**
* <h1>Abies</h1>
* Implementação da classe Abies
* Extenção de GameElement e implementação das classes Burnable e Updatable
* Define Abies
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class Abies extends GameElement implements Burnable, Updatable{

	public final double probability = Probability.ABIES.getProbability();		
	public final int INITIAL_BURN_TIME = 20;									
	public final String BURNT_NAME = "burntabies";								
	public int burnTime;
	public boolean isBurnt;
	
	/**
	 * Contrutor da classe Abies
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */
	
	public Abies(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		burnTime = INITIAL_BURN_TIME;
		isBurnt = false;
	}
	
	/**
	 * Construtor da classe Abies, com valor lógico isBurnt
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 * @param isBurnt boolean
	 */
	
	public Abies(String name, Point2D position, int layerValue, boolean isBurnt) {
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
	* ToString do Burnt
	* @return "Abies" String
	*/
	
	//TODO Debug
	@Override
	public String toString() {
		return "Abies";	
	}

	/**
	 * Getter Probabilidade
	 * @return double probability
	 */
	
	@Override
	public double getProbability() {
		return probability;
	}

	/**
	 * Getter BurnTime
	 * @return burnTime integer
	 */
	
	@Override
	public int getBurnTime() {
		return burnTime;
	}

	/**
	 * Setter BurnTime
	 */
	
	@Override
	public void setBurnTime(int newBurn) {
		this.burnTime = newBurn;
	}

	/**
	 * Getter isBrunt
	 * @return isBurnt boolean
	 */
	@Override
	public boolean isBurnt() {
		return isBurnt;
	}
	
	/**
	 * Setter isBurnt
	 */
	
	@Override
	public void setBurnt(boolean isBurnt) {
		this.isBurnt = isBurnt;
	}

}