package pt.iul.poo.firefight.starterpack;

/**
* <h1>Scoreboard</h1>
* Implementação do enumerado Scoreboard
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

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
