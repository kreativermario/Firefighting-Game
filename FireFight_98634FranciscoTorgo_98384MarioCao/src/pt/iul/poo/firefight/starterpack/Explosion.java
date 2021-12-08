package pt.iul.poo.firefight.starterpack;


import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

/**
* <h1>Explosion</h1>
* Implementação da classe Explosion
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class Explosion extends GameElement implements Disappears{
	
	/**
	 * Contrutor da classe Explosion
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */
	
	public Explosion(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}	
	
	/**
	 * ToString Explosion
	 * @return "Explosion" String
	 */
	
	//TODO Debug
	public String toString() {
		return "Explosion";
	}

}