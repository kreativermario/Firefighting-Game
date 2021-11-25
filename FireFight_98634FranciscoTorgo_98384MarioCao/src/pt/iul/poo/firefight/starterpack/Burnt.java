package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

//Isso sera' de evitar na versao a serio do projeto

/**
* <h1>Burnt</h1>
* Implementação da classe Burnt
* Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
* Tem atributos e metodos repetidos em relacao ao que está definido noutras classes
* 
* @author Mario Cao-N98384
* @author Francisco Trogo-N98634
* @since 2021-11-01
*/

public class Burnt extends GameElement {

	public Burnt(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	/**
	* ToString do Burnt
	* @return String com "Burnt".
	*/

	//TODO Debug
	@Override
	public String toString() {
		return "Burnt";	
	}
}