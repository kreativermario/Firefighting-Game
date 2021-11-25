package pt.iul.poo.firefight.starterpack;

import java.util.Iterator;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends GameElement {

	private static GameEngine ge = GameEngine.getInstance();
	
	public Fire(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	@Override
	public String getName() {
		return "fire";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	//TODO Debug
	@Override
	public String toString() {
		return "Fire";	
	}
	
	public static void propagateFire() {
		
		for(int i = 0; i < ge.getFireList().size(); i++) {
			ImageTile element =  ge.getFireList().get(i);
			Point2D position = element.getPosition();
		
			

			System.out.println();
			System.out.println("I --> " + i + " | FIRE AT --> " + position);
			
			List<Point2D> burnPos = position.getNeighbourhoodPoints();
			
			Iterator<Point2D> it = burnPos.iterator();
			while(it.hasNext()) {
				Point2D nextPos = it.next();
				if(ge.isItBurnableAtPosition(nextPos) && !ge.isThereFireAtPosition(nextPos) && !nextPos.equals(ge.getFireman().getPosition())) {
					System.out.println();
					
					ImageTile burnable = ge.getElement(nextPos);
					Burnable element1 = (Burnable) burnable;
					System.out.println(element1.getClass().getSimpleName());
					addFire(element1, nextPos);
				}
				
			}		
			
		}
	}
	
	public static void addFire(Burnable element, Point2D position) {
	    double chance = Math.random() * 1;
		System.out.println("PROPAGATE FIRE CHANCE IS  -->" + chance );
		System.out.println(element.getClass().getSimpleName());
		double probability = element.getProbability();
		System.out.println(probability);
		if(chance <= probability) {
			ge.addElement(new Fire("fire", position, 1));
			System.out.println("FIRE!!!!!");
		}
		

	}	
	
	public static void cleanFire(Point2D position, Direction direction) {
		
		if(ge.isItBurnableAtPosition(position) && ge.isThereFireAtPosition(position)) {
			ImageTile obj = null;
			switch(direction) {
				case UP:
					obj = new Water("water_up", position, 2);
					break;
				case DOWN:
					obj = new Water("water_down", position, 2);
					break;
				case LEFT:
					obj = new Water("water_left", position, 2);
					break;
				case RIGHT:
					obj = new Water("water_right", position, 2);
					break;
			}
			ge.addElement(obj);
			ge.removeFire(position);
		}
	}
}
