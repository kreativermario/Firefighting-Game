package pt.iul.poo.firefight.starterpack;

public enum Scoreboard {
	ABIES(15),EUCALIPTUS(50), PINE(25), GRASS(100);
	
	private int value;
	
	private Scoreboard(int value) {
		this.value = value;
	} 
	
	public int getValue() {
		return value;
	}
}
