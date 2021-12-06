package pt.iul.poo.firefight.starterpack;

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
