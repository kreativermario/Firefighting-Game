package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Pine extends GameElement implements Burnable {
	
	public final double probability = 0.05;
	public final int burnTime = 10;

	public Pine(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
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
	public int burnTime() {
		return burnTime;
	}
	
	


}