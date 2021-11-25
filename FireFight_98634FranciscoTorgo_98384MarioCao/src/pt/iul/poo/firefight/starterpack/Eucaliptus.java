package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Eucaliptus extends GameElement implements Burnable {
	public final double probability = 0.10;
	
	public Eucaliptus(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	@Override
	public String getName() {
		return "eucaliptus";
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
}
