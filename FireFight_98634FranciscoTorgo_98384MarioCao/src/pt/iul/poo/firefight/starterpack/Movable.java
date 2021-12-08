package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

/**
* <h1>Movable</h1>
* Implementação da interface Movable
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public interface Movable {
	public void move(int keyCode);
	public boolean canMoveTo(Point2D p);
	public void setPosition(Point2D position);
}
