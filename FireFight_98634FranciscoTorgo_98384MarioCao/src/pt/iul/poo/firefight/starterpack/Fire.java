package pt.iul.poo.firefight.starterpack;

import java.util.*;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que est� definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Fire extends GameElement implements Combustible{

	private static GameEngine ge = GameEngine.getInstance();
	
	public Fire(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	//TODO Debug
	@Override
	public String toString() {
		return "Fire";	
	}
	
	
	public static boolean canSetFire(Point2D setPos, Point2D nextMovablePosition) {
		Point2D firemanPos = ge.getFireman().getPosition();

		return ge.isThereObjectAtPosition(setPos, e -> e instanceof Burnable) && 
					!ge.isThereObjectAtPosition(setPos, e -> e instanceof Fire) 
					&& !setPos.equals(firemanPos) && !setPos.equals(nextMovablePosition)
					&& !ge.isThereObjectAtPosition(setPos, e -> e instanceof Bulldozer);
		
	}
	
	
	
	public static void propagateFire(Point2D nextMovablePosition) {
		
		List<ImageTile> fires = ge.selectObjectsList(e -> e instanceof Fire);

		for(int i = 0; i < fires.size(); i++) {
			ImageTile element =  fires.get(i);
			Point2D position = element.getPosition();

			List<Point2D> burnPos = position.getNeighbourhoodPoints();
			Iterator<Point2D> it = burnPos.iterator();
			
			
			while(it.hasNext()) {
				Point2D setPos = it.next();
				
				if(canSetFire(setPos, nextMovablePosition) == true) {
					Burnable burnable = ge.getObjectAtPosition(setPos, e -> e instanceof Burnable);
				
					if(burnable.isBurnt() == false) {
							addFire(burnable, setPos, nextMovablePosition);	
					}
						
				}
				
			}		
			
		}
	}
	
	public static void addFire(Burnable element, Point2D position, Point2D nextMovablePosition) {
	    double chance = Math.random() * 1;

		double probability = element.getProbability();
		
		//TODO DEBUG
		System.out.println("PROBABILITY -> " + probability + " | CHANCE -> " + chance);
		if(element instanceof FuelBarrel) {
			((FuelBarrel) element).explode(nextMovablePosition);
		}else if(!(element instanceof FuelBarrel) && chance <= probability) {
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
	
	
	
	
	public static void addBurnTime(Point2D nextMovablePosition) {
		List<ImageTile> originalFires = ge.selectObjectsList(e -> e instanceof Combustible);
		List<Point2D> posWithFires = new ArrayList<>();
		for(ImageTile fire : originalFires) {
			posWithFires.add(fire.getPosition());
		}
		Iterator<Point2D> it = posWithFires.iterator();
		while(it.hasNext()) {
			Point2D pos = it.next();
		
			Updatable element = ge.getObjectAtPosition(pos, e -> e instanceof Updatable);
			int burntime = element.getBurnTime();
			
			if(--burntime == 0 && ((Burnable) element).isBurnt() == false) {
				if(!(element instanceof FuelBarrel)) {
					ge.removeElement(ge.getObjectAtPosition(pos, e -> e instanceof Fire));
				}
				if(element instanceof Eucaliptus) {
					ge.addElement(new Eucaliptus("eucaliptus", pos, 0, true));
				}
				if(element instanceof Grass) {
					ge.addElement(new Grass("grass", pos, 0, true));
				}
				if(element instanceof Pine) {
					ge.addElement(new Pine("pine", pos, 0, true));
				}
				if(element instanceof Abies) {
					ge.addElement(new Abies("abies", pos, 0, true));
				}
				if(element instanceof FuelBarrel){
					//TODO remove?
					((FuelBarrel) element).explode(nextMovablePosition);
					
				}
				
				ge.removeElement( (ImageTile) element);
				ge.getScore().decreaseValue((ImageTile) element);
				
				
			}else {
				element.setBurnTime(burntime);
			}
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
