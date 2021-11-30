package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Eucaliptus extends GameElement implements Burnable {
	public final double probability = 0.10;
	public final int INITIAL_BURN_TIME = 5;
	public final String BURNT_NAME = "burnteucaliptus";
	public int burnTime;
	public boolean isBurnt;
	
	public Eucaliptus(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		burnTime = INITIAL_BURN_TIME;
		isBurnt = false;
	}
	
	public Eucaliptus(String name, Point2D position, int layerValue, boolean isBurnt) {
		super(name, position, layerValue);
		burnTime = INITIAL_BURN_TIME;
		this.isBurnt = isBurnt;
	}
	
	@Override
	public String getName() {
		if(isBurnt) return BURNT_NAME ;	
		else return super.getName();
	}

	/**
	* ToString do Burnt
	* @return String com "Eucaliptus".
	*/
	
	//TODO Debug
	@Override
	public String toString() {
		return "Eucaliptus";	
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
	public void setBurnt(boolean burnt) {
		// TODO Auto-generated method stub
		
	}
}
