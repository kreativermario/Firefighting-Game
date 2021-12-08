package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

/**
* <h1>Pine</h1>
* Implementação da classe Pine
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class Pine extends GameElement implements Burnable, Updatable{
	
	public final double probability = Probability.PINE.getProbability();
	public final int INITIAL_BURN_TIME = 10;
	public final String BURNT_NAME = "burntpine";
	private int burnTime;
	private boolean isBurnt;

	public Pine(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		this.burnTime = INITIAL_BURN_TIME;
		this.isBurnt = false;
	}
	
	public Pine( String name, Point2D position, int layerValue, boolean isBurnt) {
		super(name, position, layerValue);
		this.burnTime = INITIAL_BURN_TIME;
		this.isBurnt = isBurnt;
	}
	
	@Override
	public String getName() {
		if(isBurnt) return BURNT_NAME;
		else return super.getName();
	}
	
	//TODO Debug
	@Override
	public String toString() {
		return "Pine";	
	}

	@Override
	public double getProbability() {
		return probability;
	}

	@Override
	public int getBurnTime() {
		return burnTime;
	}

	@Override
	public void setBurnTime(int newBurn) {
		this.burnTime = newBurn;
	}
	
	@Override
	public boolean isBurnt() {
		return isBurnt;
	}
	
	@Override
	public void setBurnt(boolean isBurnt) {
		this.isBurnt = isBurnt;
	}

	


}