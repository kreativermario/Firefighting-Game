package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

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

/**
* <h1>GameEngine</h1>
* Implementação da classe GameEngine
* Incluio metodos principais de funcionamento do jogo
* 
* @author Mario Cao-N98384
* @author Francisco Trogo-N98634
* @since 2021-11-01
*/

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<ImageTile> tileList;	// Lista de imagens
	private Fireman fireman;			// Referencia para o bombeiro
	private boolean inBulldozer = false;
	private String playerName;
	private Plane plane;
	private static GameEngine INSTANCE;	//Obter a instancia atual do GameEngine
	private Score score;
	
	
	// Neste exemplo o setup inicial da janela que faz a interface com o utilizador e' feito no construtor 
	// Tambem poderia ser feito no main - estes passos tem sempre que ser feitos!
	private GameEngine() {
		 
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI
		
		tileList = new ArrayList<>();   	   //Criar lista imagens
	}
	
	/**
	* Este metodo fabrica cria um Singleton
	*/

	public static GameEngine getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new GameEngine();
			return INSTANCE;
		}
		return INSTANCE;
	}
	
	/**
	* Este metodo é invocado sempre que o utilizador carrega numa tecla
	* @param source Observed Referencia para o objeto observado (neste caso seria a GUI).
	*/	
	
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();              // obtem o codigo da tecla pressionada
		//TODO remove
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
		
		Water.removeWater();
		if(!tileList.contains(plane))
			plane = null;
		switch(key) {
			case KeyEvent.VK_P:
				if(plane == null) {
					Point2D initPos = new Point2D(Fire.getLargestFireRow(), 9);
					this.plane = new Plane("plane", initPos, 4);
					addElement(plane);
				}
				break;
			case KeyEvent.VK_ENTER:
				if(inBulldozer) {
					gui.addImage(fireman);
					fireman.setBulldozer(null);
					inBulldozer = false;
				}
				break;
			default:
				if(Direction.isDirection(key)) {
					score.setScoreValue(-10);
					if(plane != null) {
						plane.move();
					}
					if(inBulldozer == false) {
						fireman.move(key);
					}else if(inBulldozer == true && fireman.getBulldozer() != null) {
						fireman.getBulldozer().move(key);
					}
					Fire.addBurnTime();

				}
		
		
		}
		
		//TODO Remove
		debug();
			
		gui.update();                            // redesenha as imagens na GUI, tendo em conta as novas posicoes
	}
	
	
	//TODO remove debug
	public void debug() {
		//TODO debug
		System.out.println();
		for(int i = 0; i < tileList.size(); i++) {
			if(i % 10 == 0)
				System.out.println();
			System.out.print(tileList.get(i) + " ") ;
			
		}
		System.out.println();
		List<ImageTile> fireList = selectObjectsList(e -> e instanceof Fire);
		System.out.println("FireList count --> " + fireList.size());
		System.out.println();
		for(int i = 0; i < fireList.size(); i++) {
			System.out.print(fireList.get(i).getPosition() + " ") ;
			
		}
		boolean existsDup = false;
		for (int i = 0; i < fireList.size(); i++) { 
			for (int j = i + 1 ; j < fireList.size(); j++) { 
				if (fireList.get(i).getPosition().equals(fireList.get(j).getPosition())) { 
					existsDup = true;
					
				}
			}
		}
		System.out.println();
		System.out.println("DUPE --> " + existsDup);
		System.out.println();
		gui.setStatusMessage("Fire Count: " + fireList.size());
		if(fireList.size() == 0) {
			gui.setMessage("Game Over!");
			score.saveToFile(playerName);
			
			
		}
		System.out.println();
		if(fireman.getBulldozer() != null) {
			System.out.println("FIREMAN IN BULLDOZER");
		}else {
			System.out.println("FIREMAN NOT IN BULLDOZER");
		}
		
		System.out.println();
		
		if(plane != null) {
			System.out.println("PLANE EXISTS");
		}else {
			System.out.println("PLANE DOES NOT EXIST");
		}
		
		System.out.println();
		System.out.println(score.getScoreValue());
		gui.setStatusMessage("Score: " + score.getScoreValue());
		
	}
	
	
	
	public <T> T getObjectAtPosition(Point2D position, Predicate<GameElement> filtro) {
		for(ImageTile image : tileList) {
			GameElement element = (GameElement) image;
			if(filtro.test(element) && element.getPosition().equals(position))
				return (T) element;
		}
		return null;
	}

	
	public boolean isThereObjectAtPosition(Point2D position, Predicate<GameElement> filtro) {
		for(ImageTile image : tileList) {
			GameElement element = (GameElement) image;
			if(filtro.test(element) && element.getPosition().equals(position))
				return true;
		}
		return false;
		
	}
	

	public void removeImage(ImageTile image) {
		gui.removeImage(image);
	}
	
	
	public <T> List<T> selectObjectsList(Predicate<GameElement> filtro){
		List<T> list = new ArrayList<>();
		for(ImageTile image : tileList) {
			GameElement element = (GameElement) image;
			if(filtro.test(element))
				list.add((T) image);
		}
		return list;
	}
	

	/**
	* Getter do Fireman
	* @return fireman.
	*/

	public Fireman getFireman() {
		return fireman;
	}

	public boolean isInBulldozer() {
		return inBulldozer;
	}

	public void setInBulldozer(boolean inBulldozer) {
		this.inBulldozer = inBulldozer;
	}
	

	public Plane getPlane() {
		return plane;
	}
	
	
	
	
	public Score getScore() {
		return score;
	}

	/**
	* Este metodo é invocado para verificar se existe um elemento numa determinada posicao
	* @param image ImageTile Imagem do elemento a verificar.
	* @return boolean Quando existe true, em contrario false.
	*/
	private boolean isThereElement(ImageTile image) {
		for(ImageTile element : tileList) {
			if(element.getPosition().equals(image.getPosition()) && element.getName().equals(image.getName()) && element.getLayer() == element.getLayer())
				return true;
		}
		return false;
	}
	

	/**
	* Este metodo é invocado para adicionar um determinado elemento ao jogo
	* @param element ImageTile Elemento a adicionar.
	*/

	public void addElement(ImageTile element) {
		if(!isThereElement(element)) {
			tileList.add(element);
			gui.addImage(element);
		}
	}
	
	/**
	* Este metodo é invocado para remover um determinado elemento do jogo
	* @param element ImageTile Elemento a remover.
	*/

	public void removeElement(ImageTile element) {
		tileList.remove(element);
		gui.removeImage(element);
		gui.update();
	}

	/**
	* Este metodo é invocado para criar objetos e enviar imagens para o GUI
	*/

	public void start() {
		createTerrain();      // criar mapa do terreno
		//TODO Debug
		for(int i = 0; i < tileList.size(); i++) {
			if(i % 10 == 0)
				System.out.println();
			System.out.print(tileList.get(i) + " ") ;
		}
		this.score = new Score();
		for(ImageTile image : tileList) {
			if(image instanceof Burnable)
				score.setInitValue(image);
		}
		sendImagesToGUI();    // enviar as imagens para a GUI
		gui.update();
		this.playerName = JOptionPane.showInputDialog("Introduza o nickname: ");
		
	}
	
	
	/** Função auxiliar a createTerrain()
	 * 	Verifica se o elemento é uma das opções e
	 * 	adiciona ao mapa a carregar na GUI
	 * 	@param element char
	 * 	@param position Point2D
	 * */
	private void addFromChar(char element, Point2D position) {
		GameElement obj = null;
		switch(element) {
			case 'p':
				obj  = new Pine("pine",position, 0);
				break;
			case 'e':
				obj = new Eucaliptus("eucaliptus", position, 0);
				break;
			case 'm':
				obj = new Grass("grass", position, 0);
				break;
			case '_':
				obj = new Land("land", position, 0);
				break;
			default:
				throw new IllegalArgumentException("Erro a dar load ao mapa");
		}
		if(obj != null && !tileList.contains(obj))
			tileList.add(obj);
						
	}
	
	
	/** Função auxiliar a createTerrain()
	 * 	Verifica se o elemento é uma das opções e
	 * 	adiciona ao mapa a carregar na GUI
	 * 	@param element String
	 * 	@param position Point2D
	 * */
	private void addFromString(String element, Point2D position) {
		GameElement obj = null;
		switch(element) {
			case "Fireman":
				fireman = new Fireman("fireman", position, 3);
				obj = fireman;
				break;
			case "Bulldozer":
				obj = new Bulldozer("bulldozer", position, 3);
				break;
			case "Fire":
				obj = new Fire("fire", position, 1);
				break;
			case "Burnt":
				obj = new Burnt("burnt", position, 0);
				break;
			case "BurntEucaliptus":
				obj = new Eucaliptus("burnteucaliptus", position, 0, true);
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
				throw new IllegalArgumentException("Erro a dar load ao mapa");
		}
		if(obj != null && !tileList.contains(obj))
			tileList.add(obj);
		
	}
	
	
	/** 
	 * 	Dá load de um mapa que é ficheiro txt
	 * 	para o tileList (Vetor) e GUI.
	 * */
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
