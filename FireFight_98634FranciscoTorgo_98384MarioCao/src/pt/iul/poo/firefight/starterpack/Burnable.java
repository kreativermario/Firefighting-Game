package pt.iul.poo.firefight.starterpack;

/**
* <h1>Burnable</h1>
* Implementação da interface Bulldozer
* Tem os atributos necessarios de implementar
* 
* @author Mario Cao-N98384
* @author Francisco Trogo-N98634
* @since 2021-11-01
*/

public interface Burnable {
	public String getName();
	public int getLayer();
	public double getProbability();
	public boolean isBurnt();
	public void setBurnt(boolean isBurnt);
}