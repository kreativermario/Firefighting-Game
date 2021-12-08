package pt.iul.poo.firefight.starterpack;

/**
* <h1>Burnable</h1>
* Implementação da interface Burnable
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public interface Burnable {
	public double getProbability();
	public boolean isBurnt();
	public void setBurnt(boolean isBurnt);
}