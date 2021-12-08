package pt.iul.poo.firefight.starterpack;

/**
* <h1>Probability</h1>
* Implementação do Enumerado Probability
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public enum Probability {
	ABIES(0.05),EUCALIPTUS(0.10), PINE(0.05), GRASS(0.15);
	
	private double probability;
	
	private Probability(double d) {
		this.probability = d;
	} 
	
	public double getProbability() {
		return probability;
	}
	
	
	
}
