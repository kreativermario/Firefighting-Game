package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

/**
* <h1>GameElement</h1>
* Implementação da classe abstrata GameElement
* 
* @author Mario Cao-N98384
* @author Francisco Torgo-N98634
* @since 2021-11-01
*/

public abstract class GameElement implements ImageTile {

	private Point2D position;
	private int layerValue;
	private String name;
	
	public static final GameEngine ge = GameEngine.getInstance();
	
	/**
	 * Construtor da classe GameElement
	 * @param name String
	 * @param position Point2D
	 * @param layerValue integer
	 */
	
	public GameElement(String name, Point2D position, int layerValue) {
		this.name = name;
		this.position = position;
		this.layerValue = layerValue;
	}
	
	/**
	 * Método fábrica
	 * 
	 * @param name String
	 * @param position Point2D
	 * @return obj ImageTile
	 */
	
	public static ImageTile create(String name, Point2D position) {
		ImageTile obj = null;
		switch(name) {
			case "Abies":
				obj = new Abies("abies", position, 0);
				break;
			case "Bulldozer":
				obj = new Bulldozer("bulldozer", position, 3);
				break;
			case "Burnt":
				obj = new Burnt("burnt", position, 0);
				break;
			case "BurntAbies":
				obj = new Abies("abies", position, 0, true);
				break;
			case "BurntEucaliptus":
				obj = new Eucaliptus("burnteucaliptus", position, 0, true);
				break;
			case "BurntFuelBarrel":
				obj = new FuelBarrel("fuelbarrel", position, 0, true);
				break;
			case "BurntGrass":
				obj = new Grass("burntgrass", position, 0, true);
				break;
			case "BurntPine":
				obj = new Pine("burntpine", position, 0, true);
				break;		
			case "Eucaliptus":
				obj = new Eucaliptus("eucaliptus", position, 0);
				break;
			case "Explosion":
				obj = new Explosion("explosion", position, 3);
				break;
			case "Fire":
				obj = new Fire("fire", position, 1);
				break;
			case "FiremanBot":
				obj = new FiremanBot("firemanbot", position, 3);
				break;
			case "Fireman":
				obj = new Fireman("fireman", position, 3);
				break;
			case "FuelBarrel":
				obj = new FuelBarrel("fuelbarrel", position, 0);
				break;	
			case "FireTruck":
				obj = new FireTruck("fueltruck", position, 3);
				break;
			case "Grass":
				obj = new Grass("grass", position, 0);
				break;
			case "Land":
				obj = new Land("land", position, 0);
				break;
			case "Pine":
				obj = new Pine("pine", position, 0);
				break;	
			case "Water":
				obj = new Water("water", position, 2);
				break;
			default:
				throw new IllegalArgumentException("Erro a criar ImageTile");
		}
		return obj;			
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	public String getName(){
		return name;
	}
	
	public int getLayer(){
		return layerValue;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;		
	}
	
	
	/** Método Fábrica para o load do Mapa
	 * 	Verifica se o elemento é uma das opções e
	 * 	cria o objeto
	 * 	@param element String
	 * 	@param position Point2D
	 *  @return obj ImageTile
	 * */
	public static ImageTile addFromString(String element, Point2D position) {
		ImageTile obj = null;
		switch(element) {
			case "Fireman":
				if(!ge.isThereObjectAtPosition(position, e -> e instanceof Fire)) {
					Fireman fireman = new Fireman("fireman", position, 3);
					ge.setFireman(fireman);  
					obj = fireman;
				}else throw new IllegalArgumentException("Fireman não pode spawnar em cima de um fogo");
				break;
			case "Bulldozer":
				if(!ge.isThereObjectAtPosition(position, e -> e instanceof Fire)) {
					obj = new Bulldozer("bulldozer", position, 3);
				}else throw new IllegalArgumentException("Bulldozer não pode spawnar em cima de um fogo");	
				break;
			case "Fire":
				if(!ge.isThereObjectAtPosition(position, e -> e instanceof Fireman) 
						&& !ge.isThereObjectAtPosition(position, e -> e instanceof Bulldozer)) {
					obj = new Fire("fire", position, 1);
				}else throw new IllegalArgumentException("Fogo não pode spawnar em cima de um fireman ou bulldozer");	
				break;
			default:
				obj = create(element, position);
		}
		if(obj != null && !ge.isThereElement(obj))
			return obj;
		return null;
		
	}
	
	
	
}