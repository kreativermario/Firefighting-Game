package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public abstract class BurnableElement extends GameElement implements Burnable, Updatable{

	public BurnableElement(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
		// TODO Auto-generated constructor stub
	}

}
