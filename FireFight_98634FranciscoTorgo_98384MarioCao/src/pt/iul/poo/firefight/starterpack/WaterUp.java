package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class WaterUp extends GameElement {

	public WaterUp(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}	
	
	//TODO Debug
	public String toString() {
		return "Water";	}
}
