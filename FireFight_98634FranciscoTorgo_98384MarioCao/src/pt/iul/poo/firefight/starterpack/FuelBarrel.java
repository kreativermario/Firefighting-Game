package pt.iul.poo.firefight.starterpack;

import java.util.*;

import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class FuelBarrel extends GameElement implements Burnable, Updatable, Combustible{
	public final double probability = 0.9;
	public final int INITIAL_BURN_TIME = 3;
	public final String BURNT_NAME = "burntfuelbarrel";
	public int burnTime;
	public boolean isBurnt;
	
	public FuelBarrel(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		burnTime = INITIAL_BURN_TIME;
		isBurnt = false;
	}
	
	public FuelBarrel(String name, Point2D position, int layerValue, boolean isBurnt) {
		super(name, position, layerValue);
		burnTime = INITIAL_BURN_TIME;
		this.isBurnt = isBurnt;
	}
	
	
	public void explode(Point2D nextMovablePosition) {
		
		List<Point2D> wideNeighbours = this.getPosition().getWideNeighbourhoodPoints();
		Iterator<Point2D> it = wideNeighbours.iterator();
		GameEngine ge = GameEngine.getInstance();
		ge.addElement(new Explosion("explosion", this.getPosition(), 3));
		this.setBurnt(true);
		//ge.addElement(new FuelBarrel("fuelbarrel", this.getPosition(), 0, true));
		while(it.hasNext()) {
			Point2D setPos = it.next();
			
			if(Fire.canSetFire(setPos, nextMovablePosition)) {
				//TODO fabrica objetos
				ge.addElement(new Fire("fire", setPos, 1));
			}
			
			
		}
		
		
		
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
		return "FBarrel";	
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
