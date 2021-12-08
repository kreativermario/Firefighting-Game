package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

/**
* <h1>Burnt</h1>
* Implementação da classe Burnt
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class Burnt extends GameElement {

	/**
	 * Construtor da classe Burnt
	 * @param name
	 * @param position
	 * @param layerValue
	 */
	
	public Burnt(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	/**
	* ToString do Burnt
	* @return "Burnt" String
	*/

	//TODO Debug
	@Override
	public String toString() {
		return "Burnt";	
	}
}