package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!


public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<ImageTile> tileList;	// Lista de imagens
	private Fireman fireman;			// Referencia para o bombeiro
	private static GameEngine INSTANCE;


	// Neste exemplo o setup inicial da janela que faz a interface com o utilizador e' feito no construtor 
	// Tambem poderia ser feito no main - estes passos tem sempre que ser feitos!
	private GameEngine() {
		 
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI
		
		tileList = new ArrayList<>();   
	}
	
	
	//Método de fábrica Singleton
	public static GameEngine getInstance() {
		if(INSTANCE == null)
			return new GameEngine();
		return INSTANCE;
	}
		
	
	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado (neste caso seria a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();              // obtem o codigo da tecla pressionada
		
//		if (key == KeyEvent.VK_ENTER) {            // se a tecla for ENTER, manda o bombeiro mover
//			fireman.move();			
//			Point2D position = fireman.getPosition();
//			// Verificar se tem pinheiro na posicao
//				// Queimar (burn)
//				// Aficionar um objeto tipo Fire no jogo (tileList) e no gui (addImage)
//			if(isItBurnableAtPosition(position)) {
//				Fire fire = new Fire(new Point2D(position.getX(), position.getY()));
//				addElement(fire);
//			}
//				
//		}
		
		if(Direction.isDirection(key)) {
			//TODO mudar isto para dentro do fireman
			Direction direction = Direction.directionFor(key);
			Point2D newPosition = fireman.getPosition().plus(direction.asVector());
			if(!isThereFireAtPosition(newPosition) ) {
				fireman.move(key);
				propagateFire();
				removeWater();
			}else {
				cleanFire(newPosition, direction);	
			}
				
			
			
		}
		
		
		//TODO debug
		System.out.println();
		for(int i = 0; i < tileList.size(); i++) {
			if(i % 10 == 0)
				System.out.println();
			System.out.print(tileList.get(i) + " ") ;
			
		}
		
		
		
		gui.update();                            // redesenha as imagens na GUI, tendo em conta as novas posicoes
	}
	
	
	
	public ImageTile getElement(Point2D position) {
		for(int i = 0; i < tileList.size(); i++) {		
			ImageTile element = tileList.get(i);
			if(element.getPosition().equals(position)) {
				return element;
			}
		}
		return null;
	}
	
	
	private void addFire(String type, Point2D position) {
		double chance = Math.random() * 100;
		System.out.println("PROPAGATE FIRE CHANCE IS  -->" + chance );
		switch(type) {
			case "eucaliptus":
				if(chance <= 10) {
					System.out.println("ADDING FIRE EUCALIPTUS --> " + position);
					addElement(new Fire("fire", position, 1));
				}
				break;
			case "pine":
				if(chance <= 5) {
					System.out.println("ADDING FIRE PINE --> " + position );
					addElement(new Fire("fire", position, 1));
				}
				break;
			case "grass":
				if(chance <= 15) {
					System.out.println("ADDING FIRE GRASS --> " + position );
					addElement(new Fire("fire", position, 1));
				}
				break;
		}
		
		
		
		
		
	}
	
	
	
	
	public void propagateFire() {
		
		for(int i = 0; i < tileList.size(); i++) {
			ImageTile element = tileList.get(i);
			Point2D position = element.getPosition();
		
			
			if(isThereFireAtPosition(position)) {
				System.out.println();
				System.out.println("I --> " + i + " | FIRE AT --> " + position);
				Direction UP = Direction.UP;
				Direction DOWN = Direction.DOWN;
				Direction LEFT = Direction.LEFT;
				Direction RIGHT = Direction.RIGHT;
				
				
				Point2D upPos = position.plus(UP.asVector());
				Point2D downPos = position.plus(DOWN.asVector());
				Point2D leftPos = position.plus(LEFT.asVector());
				Point2D rightPos = position.plus(RIGHT.asVector());
				
				//if(Math.random() * 100 == 15)
				
				if(isItBurnableAtPosition(upPos)) {
					System.out.println("I --> " + i + " | IS BURNABLE AT --> " + upPos);
					String type = getElement(upPos).getName();
					System.out.println("I --> " + i + " | TYPE BURNABLE IS --> " + type);
					addFire(type, upPos);
					
				}
				
				if(isItBurnableAtPosition(downPos)){
					String type = getElement(downPos).getName();
					addFire(type, downPos);
					
				}
				
				if(isItBurnableAtPosition(leftPos)){
					String type = getElement(leftPos).getName();
					addFire(type, leftPos);
					
				}
				
				if(isItBurnableAtPosition(rightPos)){
					String type = getElement(rightPos).getName();
					addFire(type, rightPos);
					
				}
						
				
			}
		}
		
		
		
	}
	
	
	//TODO isFireAtPosition ou isWater
	//TODO metodos devolver lista de objetos com determinado interface
	
	
	public void cleanFire(Point2D position, Direction direction) {
		
		if(isItBurnableAtPosition(position) && isThereFireAtPosition(position)) {
			switch(direction) {
				case UP:
					addElement(new Water("water_up", position, 2));
					break;
				case DOWN:
					addElement(new Water("water_down", position, 2));
					break;
				case LEFT:
					addElement(new Water("water_left", position, 2));
					break;
				case RIGHT:
					addElement(new Water("water_right", position, 2));
					break;
			}
			for(int i = 0; i < tileList.size(); i++) {
				ImageTile image = tileList.get(i);
				if(image.getPosition().equals(position) && image instanceof Fire) {
					removeElement(image);
				}
			}
		}
		
		
	}
	
	public void removeWater() {
		for(int i = 0; i < tileList.size(); i++) {
			ImageTile image = tileList.get(i);
			if(image instanceof Water) {
				removeElement(image);
			}
		}
	}
	
	
	// isThereFireAtPosition
	public boolean isThereFireAtPosition(Point2D position) {
		for(ImageTile element : tileList) {
			if(element.getPosition().equals(position) && element instanceof Fire)
				return true;
		}
		return false;
	}
	
	
	
	// isItBurnableAtPosition
	private boolean isItBurnableAtPosition(Point2D position) {
		for(ImageTile element : tileList) {
			if(element.getPosition().equals(position) && element instanceof Burnable)
				return true;
		}
		return false;
	}
	
	private boolean isThereElement(ImageTile image) {
		for(ImageTile element : tileList) {
			if(element.getClass() == image.getClass())
				return true;
		}
		return false;
	}
	
	
	
	// Adiciona elemento ao jogo
	public void addElement(ImageTile element) {

		if(!isThereElement(element)) {
			tileList.add(element);
			gui.addImage(element);
		}
		
		
	}
	
	// Remove elemento ao jogo
	public void removeElement(ImageTile element) {
		tileList.remove(element);
		gui.removeImage(element);
	}


	
	// Criacao dos objetos e envio das imagens para GUI
	public void start() {
		createTerrain();      // criar mapa do terreno
		//TODO Debug
		for(int i = 0; i < tileList.size(); i++) {
			if(i % 10 == 0)
				System.out.println();
			System.out.print(tileList.get(i) + " ") ;
			
		}
		sendImagesToGUI();    // enviar as imagens para a GUI
		//TODO DEBUG
		gui.setStatusMessage("Fireman v.1.0.0 Alkaline");
		gui.update();
	}
	
	
	/** Função auxiliar a createTerrain()
	 * 	Verifica se o elemento é uma das opções e
	 * 	adiciona ao mapa a carregar na GUI
	 * 	@param element char
	 * 	@param position Point2D
	 * */
	private void addFromChar(char element, Point2D position) {
		
		switch(element) {
			case 'p':
				tileList.add(new Pine("pine",position, 0));
				break;
			case 'e':
				tileList.add(new Eucaliptus("eucaliptus", position, 0));
				break;
			case 'm':
				tileList.add(new Grass("grass", position, 0));
				break;
			case '_':
				tileList.add(new Land("land", position, 0));
				break;
			default:
				throw new IllegalArgumentException("Erro a dar load ao mapa");
		}
						
	}
	
	
	/** Função auxiliar a createTerrain()
	 * 	Verifica se o elemento é uma das opções e
	 * 	adiciona ao mapa a carregar na GUI
	 * 	@param element String
	 * 	@param position Point2D
	 * */
	private void addFromString(String element, Point2D position) {
		
		switch(element) {
			case "Fireman":
				fireman = new Fireman("fireman", position, 3);
				tileList.add(fireman);
				break;
			case "Bulldozer":
				tileList.add(new Bulldozer("bulldozer", position, 3));
				break;
			case "Fire":
				tileList.add(new Fire("fire", position, 1));
				break;
			case "Burnt":
				tileList.add(new Burnt("burnt", position, 0));
				break;
			case "BurntEucaliptus":
				tileList.add(new BurntEucaliptus("burnteucaliptus", position, 0));
				break;
			case "BurntGrass":
				tileList.add(new BurntGrass("burntgrass", position, 0));
				break;
			case "BurntPine":
				tileList.add(new BurntPine("burntpine", position, 0));
				break;
			case "Eucaliptus":
				tileList.add(new Eucaliptus("eucaliptus", position, 0));
				break;
			case "Grass":
				tileList.add(new Grass("grass", position, 0));
				break;
			case "Land":
				tileList.add(new Land("land", position, 0));
				break;
			case "Pine":
				tileList.add(new Pine("pine", position, 0));
				break;
			case "Water":
				tileList.add(new Water("water", position, 2));
				break;
			default:
				throw new IllegalArgumentException("Erro a dar load ao mapa");
		}
	
	}
	
	
	
	private void createTerrain() {
		try {
			File fileName = new File("levels/example.txt");
			Scanner sc = new Scanner(fileName);
			int y = 0;
			while(sc.hasNextLine()) {
				if(y >= 10) {
					String [] line = sc.nextLine().split(" ");	
					int xMove = Integer.parseInt(line[1]);
					int yMove = Integer.parseInt(line[2]);
					Point2D position = new Point2D(xMove,yMove);
					String element = line[0];
					System.out.println("Y -> " + y + " " + element + " " + xMove + " " + yMove); 		
					addFromString(element, position);
					
				}else {
					String line = sc.nextLine();
					System.out.println("Y -> " + y + " " + line); 								//TODO desnecessário, debug only
					for(int x = 0; x < line.length(); x++) {
						char element = line.charAt(x);
						Point2D position = new Point2D(x,y);
						addFromChar(element, position);
					}
					y++;
					
				}
				
				
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
		
		
	}
	
		
	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}
}
