package pt.iul.poo.firefight.starterpack;

import java.util.*;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;


/**
* <h1>Fire</h1>
* Implementação da classe Fire
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public class Fire extends GameElement implements Combustible{

	
	/**
	 * Construtor da classe Fire
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */
	public Fire(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	
	/**
	 * ToString Fire
	 * @return "Fire" String
	 */
	//TODO Debug
	@Override
	public String toString() {
		return "Fire";	
	}
	
	/**
	 * Este metodo é usado para ver se é possivel colocar fogo num objeto autorizado numa dada posição
	 * @param setPos Point2D
	 * @param nextMovablePosition Point2D
	 * @return boolean
	 */
	
	public static boolean canSetFire(Point2D setPos, Point2D nextMovablePosition) {
		Point2D firemanPos = ge.getFireman().getPosition();

		return ge.isThereObjectAtPosition(setPos, e -> e instanceof Burnable) && //Se for burnable
					!ge.isThereObjectAtPosition(setPos, e -> e instanceof Fire) 	//Nao existe fogo na posicao
					&& !setPos.equals(firemanPos) && !setPos.equals(nextMovablePosition)	//A posicao nao for a posicao atual e futura do Movable
					&& !ge.isThereObjectAtPosition(setPos, e -> e instanceof ActiveElement);
		
	}
	
	/**
	 * Este método é usado para criar propagação do fogo
	 * @param nextMovablePosition Point2D
	 */
	
	public static void propagateFire(Point2D nextMovablePosition) {
		
		List<ImageTile> fires = ge.selectObjectsList(e -> e instanceof Fire);		//Lista de fogos

		for(int i = 0; i < fires.size(); i++) {
			ImageTile element =  fires.get(i);
			Point2D position = element.getPosition();			//Posicao do fogo

			List<Point2D> burnPos = position.getNeighbourhoodPoints();		//Posicoes vizinhas do fogo UP, DOWN, LEFT, RIGHT
			
			Iterator<Point2D> it = burnPos.iterator();
					
			while(it.hasNext()) {
				Point2D setPos = it.next();		
				
				if(canSetFire(setPos, nextMovablePosition) == true) {			//Se conseguir colocar fogo
					Burnable burnable = ge.getObjectAtPosition(setPos, e -> e instanceof Burnable);			//Elemento burnable nessa posicao
				
					if(burnable.isBurnt() == false) {									//Só ateia fogo se o elemento não tiver queimado
							addFire(burnable, setPos, nextMovablePosition);	
					}
						
				}
				
			}		
			
		}
	}
	
	/**
	 * Este metodo é utilizado para adicionar fogo numa determinada posição autorizada
	 * @param element Burnable
	 * @param position Point2D
	 * @param nextMovablePosition Point2D
	 */
	
	public static void addFire(Burnable element, Point2D position, Point2D nextMovablePosition) {
	    double chance = Math.random() * 1;

		double probability = element.getProbability();
		
		System.out.println("PROBABILITY -> " + probability + " | CHANCE -> " + chance);	//TODO DEBUG
		
		
		if(chance <= probability) {						//Se a probabilidade for maior igual que a chance, vai-se atear fogo
			if(element instanceof FuelBarrel) {			//Se o elemento for um fuel barrel, explode
				
				((FuelBarrel) element).explode(nextMovablePosition);
				
			}else {										//Se o elemento for os outros Burnable, ateia fogo
				
				ImageTile fire = GameElement.create("Fire", position);
				ge.addElement(fire);
				
				
			}
			
			
		}
		
		
	}	
	
	/**
	 * Este método é usado para remover fogo de uma determinada posicão
	 * @param position Point2D
	 * @param direction Direction
	 */
	
	public static void cleanFire(Point2D position, Direction direction) {
		
		ImageTile image = ge.getObjectAtPosition(position, e -> e instanceof Burnable);				//Arranja o elemento Burnable naquela posicao
		
		if(image != null && ge.isThereObjectAtPosition(position, e -> e instanceof Fire)) {			//Se o elemento existir e há fogo na posicao
			
			ImageTile water = Water.create(position, direction);			
			
			ge.getScore().givePoints(image);														//Dar os pontos
			
			ge.addElement(water);																	//Colocar a agua
			
			ge.removeElement(ge.getObjectAtPosition(position, e -> e instanceof Fire));  			//Remover o fogo
		}
	}
	
	/**
	 * Este método é usado para obter a coluna que contem mais fogos
	 * @return maxRow integer
	 */
	
	public static int getLargestFireRow() {
		
		List<ImageTile> originalFires = ge.selectObjectsList(e -> e instanceof Fire);
		
		List<Integer> fires = new ArrayList<>();
		
		for(ImageTile fire : originalFires) {
			fires.add(fire.getPosition().getX());		//Colunas dos fogos
		}
		
		Map<Integer, Integer> posCount = new HashMap<>();				//Mapa para manter as contagens de fogos por coluna
		
		for(Integer x : fires) {
			Integer count = posCount.get(x);
			if(count == null) {
				count = 0;
			}
			count++;
			posCount.put(x, count);
				
		}
		Integer maxRow = Collections.max(posCount.entrySet(), 
							Map.Entry.comparingByValue()).getKey();	//Obtem a coluna com maior count de fogos
		return maxRow;
	}
	
	
	/**
	 * Este método é usado para adicionar BurnTime
	 * @param nextMovablePosition Point2D
	 */
	
	public static void addBurnTime(Point2D nextMovablePosition) {
		List<ImageTile> originalFires = ge.selectObjectsList(e -> e instanceof Combustible);	//Inclui fogos e fuel barrel
		List<Point2D> posWithFires = new ArrayList<>();
		
		//Criou-se Array com as posicoes dos fogos
		for(ImageTile fire : originalFires) {
			posWithFires.add(fire.getPosition());
		}
		
		Iterator<Point2D> it = posWithFires.iterator();
		
		//Percorrer posicoes com fogos e aceder aos Updatable
		while(it.hasNext()) {
			Point2D pos = it.next();
		
			Updatable element = ge.getObjectAtPosition(pos, e -> e instanceof Updatable);
			int burntime = element.getBurnTime();
			
			if(--burntime == 0 && ((Burnable) element).isBurnt() == false) {		//Se o elemento tiver burntime = 0 e nao tiver queimado
																					// entao ele vai queima-lo (colocar imagem a burnt)
				
				if(!(element instanceof FuelBarrel)) {								//Fuel barrel não tem fogo logo nao se remove o fogo
					ge.removeElement(ge.getObjectAtPosition(pos, e -> e instanceof Fire));		
				}
				
				if(element instanceof FuelBarrel){									//Fuel barrel consumido vai explodir sem verificar as probabilidades
					
					((FuelBarrel) element).explode(nextMovablePosition);
					
				}
				
				//Coloca a imagem a queimado
				((Burnable) element).setBurnt(true);
				
				//Retirar score
				ge.getScore().decreaseValue((ImageTile) element);
				
				
			}else { //Decrementa o burntime
				element.setBurnTime(burntime);
			}	
		}
			
	}
	
}