package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public interface Movable {
	public void move(int keyCode);
	public boolean canMoveTo(Point2D p);
	public void setPosition(Point2D position);
}
