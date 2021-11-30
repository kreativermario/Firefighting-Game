package pt.iul.poo.firefight.starterpack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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
	
	//TODO Debug
	@Override
	public String toString() {
		return "Fire";	
	}
	
	public static void propagateFire(Point2D nextMovablePosition) {
		
		List<ImageTile> fires = ge.selectObjectsList(e -> e instanceof Fire);
		for(int i = 0; i < fires.size(); i++) {
			ImageTile element =  fires.get(i);
			Point2D position = element.getPosition();

			List<Point2D> burnPos = position.getNeighbourhoodPoints();
			Iterator<Point2D> it = burnPos.iterator();
			Point2D movablePos = null;
			
			movablePos = ge.getFireman().getPosition();
			
			while(it.hasNext()) {
				Point2D setPos = it.next();
				
				if(ge.isThereObjectAtPosition(setPos, e -> e instanceof Burnable) && !ge.isThereObjectAtPosition(setPos, e -> e instanceof Fire) 
						&& !setPos.equals(movablePos) && !setPos.equals(nextMovablePosition)) {

					
					Burnable burnable = ge.getObjectAtPosition(setPos, e -> e instanceof Burnable);
					if(burnable.isBurnt() == false)
						addFire(burnable, setPos);	
				}
				
			}		
			
		}
	}
	
	private static void addFire(Burnable element, Point2D position) {
	    double chance = Math.random() * 1;

		double probability = element.getProbability();

		if(chance <= probability) {
			ge.addElement(new Fire("fire", position, 1));
		}
		

	}	
	
	public static void cleanFire(Point2D position, Direction direction) {
		
		if(ge.isThereObjectAtPosition(position, e -> e instanceof Burnable) && ge.isThereObjectAtPosition(position, e -> e instanceof Fire)) {
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
			ImageTile image = ge.getObjectAtPosition(position, e -> e instanceof Burnable);
			ge.getScore().givePoints(image);
			ge.addElement(obj);
			ge.removeElement(ge.getObjectAtPosition(position, e -> e instanceof Fire));  
		}
	}
	
	
	public static int getLargestFireRow() {
		
		List<ImageTile> originalFires = ge.selectObjectsList(e -> e instanceof Fire);
		
		List<Integer> fires = new ArrayList<>();
		for(ImageTile fire : originalFires) {
			fires.add(fire.getPosition().getX());
		}
		Map<Integer, Integer> posCount = new HashMap<>();
		for(Integer x : fires) {
			Integer count = posCount.get(x);
			if(count == null) {
				count = 0;
			}
			count++;
			posCount.put(x, count);
				
		}
		Integer maxEntry = Collections.max(posCount.entrySet(), Map.Entry.comparingByValue()).getKey();
		return maxEntry;

	}
	
	
	public static void addBurnTime() {
		List<ImageTile> originalFires = ge.selectObjectsList(e -> e instanceof Fire);
		List<Point2D> posWithFires = new ArrayList<>();
		for(ImageTile fire : originalFires) {
			posWithFires.add(fire.getPosition());
		}
		Iterator<Point2D> it = posWithFires.iterator();
		while(it.hasNext()) {
			Point2D pos = it.next();
		
			Updatable element = ge.getObjectAtPosition(pos, e -> e instanceof Updatable);
			int burntime = element.getBurnTime();
			if(--burntime == 0) {
				ge.removeElement(ge.getObjectAtPosition(pos, e -> e instanceof Fire));
				if(element instanceof Eucaliptus) {
					ge.addElement(new Eucaliptus("eucaliptus", pos, 1, true));
				}
				if(element instanceof Grass) {
					ge.addElement(new Grass("grass", pos, 1, true));
				}
				if(element instanceof Pine) {
					ge.addElement(new Pine("pine", pos, 1, true));
				}
				ge.getScore().decreaseValue((ImageTile) element);
				ge.removeElement( (ImageTile) element);
				
			}else {
				element.setBurnTime(burntime);
			}
			
		}
		//TODO REMOVE
		System.out.println(posWithFires);
		
		
	}
	
	
	
	
	
	
	
	
	
}
