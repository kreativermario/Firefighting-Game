package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
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
	public static final int REMOVE_SCORE_PER_PLAY = -10;
	public static final String LEVEL_DIR = "levels";
	public static final String LEVEL_PREFIX = "level";
	private static GameEngine INSTANCE;	//Obter a instancia atual do GameEngine
	
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<ImageTile> tileList;	// Lista de imagens
	private Fireman fireman;			// Referencia para o bombeiro
	private String playerName;
	private Score score;
	private Score playerHighestScore = null;
	private List<String> levels = getFilenameWithPrefix(LEVEL_DIR + "/", LEVEL_PREFIX);
	private boolean gameOver = false;
	private int levelNumber = 0;
	
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
	* Método Singleton 
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
		
		
		List<ActiveElement> planeList = selectObjectsList(e -> e instanceof ActiveElement && e instanceof Plane);
		ActiveElement plane = getActive(planeList);
		
		removeDisappear(); 			//Remove água e explosões
		
		switch(key) {
			case KeyEvent.VK_P:
				
				if(plane == null) {
					Plane.init();
				}
				break;
			case KeyEvent.VK_ENTER:
				List<ActiveElement> bulldozerList = selectObjectsList(e -> e instanceof ActiveElement && e instanceof Bulldozer);
				ActiveElement bulldozer = (ActiveElement) getActive(bulldozerList);
				
				if(bulldozer != null) {
					gui.addImage(fireman);
					fireman.setActive(true);
					bulldozer.setActive(false);
				}
				break;
			default:
				if(Direction.isDirection(key)) {
					score.setScoreValue(REMOVE_SCORE_PER_PLAY);
					List<ActiveElement> activeList = selectObjectsList(e -> e instanceof ActiveElement && 
							e instanceof Movable);
					Movable object = (Movable) getActive(activeList);
					
					object.move(key);
					
					Plane plane1 = (Plane) plane;
					if(plane1 != null) {
						plane1.move();
					}
					
					//TODO ver se coloco aqui ou no Fireman
					//Fire.addBurnTime();

				}
		
		
		}
		playerHighestScore = Score.getPlayerHighscore(playerName, levelNumber);
		if(playerHighestScore != null) {		
			int highestScore = playerHighestScore.getScoreValue();
			gui.setStatusMessage("Level nº " + levelNumber + " | " + "Score: " 
			+ score.getScoreValue() + " | Player Name: " + playerName + " | Your Highscore: " + highestScore);
		}else {
			gui.setStatusMessage("Level nº " + levelNumber + " | " + "Score: " + score.getScoreValue() + " | Player Name: " + playerName);
		}
		
		//TODO Remove
		//debug();
		levelOver();
			
		
		
		
		
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
	
		System.out.println();
		List<ActiveElement> activeList = selectObjectsList(e -> e instanceof ActiveElement && e instanceof Bulldozer);
		ActiveElement bulldozer = (ActiveElement) getActive(activeList);
		
		if(bulldozer != null) {
			System.out.println("FIREMAN IN BULLDOZER");
		}else {
			System.out.println("FIREMAN NOT IN BULLDOZER");
		}
		

		System.out.println();
		System.out.println(score.getScoreValue());
		
		
	}
	
	
	private static ActiveElement getActive(List<ActiveElement> activeList) {
		for(ActiveElement activeElement : activeList) {
			if(activeElement.isActive() == true) {
				return activeElement;
			}
				
		}
		return null;
	}
	
	
	
	
	/**
	 * Função que devolve o objeto numa certa posição de determinada instância dado um Predicate
	 * @param position Point2D
	 * @param filtro Predicate<GameElement> filtro
	 * @return element T
	 * */
	public <T> T getObjectAtPosition(Point2D position, Predicate<GameElement> filtro) {
		for(ImageTile image : tileList) {
		
			GameElement element = (GameElement) image;
			if(filtro.test(element) && element.getPosition().equals(position))
				return (T) element;
		}
		return null;
	}
	
	
	private void removeDisappear() {
		List<ImageTile> gifs = selectObjectsList(e -> e instanceof Disappears);
		for(int i = 0; i < gifs.size(); i++) {
			ImageTile image = gifs.get(i);
				removeElement(image);
		}
	}
	
	
	
	
	
	/**
	 * Função que diz se o objeto de uma determinada instancia existe numa certa posição
	 * @param position Point2D
	 * @param filtro Predicate<GameElement> filtro
	 * @return boolean
	 * */
	public boolean isThereObjectAtPosition(Point2D position, Predicate<GameElement> filtro) {
		for(ImageTile image : tileList) {
			GameElement element = (GameElement) image;
			if(filtro.test(element) && element.getPosition().equals(position))
				return true;
		}
		return false;
		
	}
	
	
	private void levelOver() {
		List<ImageTile> fireList = selectObjectsList(e -> e instanceof Fire);
		//TODO DO NOT DELETE USE FOR LEVELS!!!!
		if(fireList.size() == 0) {
			playerHighestScore = null;		//Reset do high score do player para cada nivel
			gui.setMessage("Level Over!");
			score.saveToFile(playerName);
			List<Score> top5 = Score.getTop5Score(levelNumber);
			//TODO DEBUG
			System.out.println(top5);
			String str = "TOP SCORE: " + System.lineSeparator();
			for(int i = 1; i <= top5.size(); i++) {
				Score score = top5.get(i-1);
				str += i + ". " + score.getPlayerName() + " - " + score.getScoreValue() + System.lineSeparator();
			}
			
			gui.setMessage(str);
			Score.saveTop5File(top5);
			
			nextLevel();
		}
		
	}
	
	
	
	/**
	 * Procedimento que remove uma imagem do GUI
	 * @param image ImageTile;
	 * */
	public void removeImage(ImageTile image) {
		gui.removeImage(image);
	}
	
	
	
	/**
	 * Função que devolve uma lista de objetos de uma certa instância dado um Predicate<GameElement>
	 * @param filtro Predicate<GameElement> filtro
	 * @return list List<T>
	 * */
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
	* @return fireman Fireman
	*/
	public Fireman getFireman() {
		return fireman;
	}


	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Getter do numero de nível do mapa
	 * @return levelNumber int
	 * */
	public int getLevelNumber() {
		return levelNumber;
	}
	
	
	/**
	 * Getter do Score
	 * @return score Score
	 * */
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
		//Só pede o nickname no primeiro nível
		if(levelNumber == 1) {
			this.playerName = JOptionPane.showInputDialog("Introduza o nickname: ");
		}
		
		
	}
	
	/**
	 * Método que muda o nível/mapa ou acaba o jogo
	 * */
	private void nextLevel() {
		gui.clearImages();
		tileList.clear();
		this.fireman = null;
		if(levelNumber >= levels.size()) {
			this.gameOver = true;
			gui.setMessage("Game Over! All levels done!");
			gui.dispose();
		}else {
			start();
		}
			
	}

	
	/** 
	 * 	Método fábrica
	 * 	Verifica se o elemento é uma das opções e
	 * 	adiciona ao mapa a carregar na GUI
	 * 	@param element char
	 * 	@param position Point2D
	 * */
	private void addFromChar(char element, Point2D position) {
		GameElement obj = null;
		switch(element) {
			case 'a':
				obj = new Abies("abies", position, 0);
				break;
			case 'b':
				obj = new FuelBarrel("fuelbarrel", position, 0);
				break;
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
	
	
	/** Método Fábrica
	 * 	Verifica se o elemento é uma das opções e
	 * 	adiciona ao mapa a carregar na GUI
	 * 	@param element String
	 * 	@param position Point2D
	 * */
	public void addFromString(String element, Point2D position) {
		GameElement obj = null;
		switch(element) {
			case "Fireman":
				if(!isThereObjectAtPosition(position, e -> e instanceof Fire)) {
					fireman = new Fireman("fireman", position, 3);
					obj = fireman;
				}else throw new IllegalArgumentException("Fireman não pode spawnar em cima de um fogo");
				break;
			case "Bulldozer":
				if(!isThereObjectAtPosition(position, e -> e instanceof Fire)) {
					obj = new Bulldozer("bulldozer", position, 3);
				}else throw new IllegalArgumentException("Bulldozer não pode spawnar em cima de um fogo");	
				break;
			case "Fire":
				if(!isThereObjectAtPosition(position, e -> e instanceof Fireman) 
						&& !isThereObjectAtPosition(position, e -> e instanceof Bulldozer)) {
					obj = new Fire("fire", position, 1);
				}else throw new IllegalArgumentException("Fogo não pode spawnar em cima de um fireman ou bulldozer");	
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
			case "Explosion":
				obj = new Explosion("explosion", position, 3);
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
			case "Abies":
				obj = new Abies("abies", position, 0);
				break;
			case "FuelBarrel":
				obj = new FuelBarrel("fuelbarrel", position, 0);
				break;
			case "BurntFuelBarrel":
				obj = new FuelBarrel("fuelbarrel", position, 0, true);
				break;
			case "BurntAbies":
				obj = new Abies("abies", position, 0, true);
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
			File fileName = new File(LEVEL_DIR + "/" + levels.get(levelNumber));
			levelNumber++;
			Scanner sc = new Scanner(fileName);
			int y = 0;
			while(sc.hasNextLine()) {
				if(y >= 10) {
					String [] line = sc.nextLine().split(" ");	
					int xMove = Integer.parseInt(line[1]);
					int yMove = Integer.parseInt(line[2]);
					Point2D position = new Point2D(xMove,yMove);
					String element = line[0];
					//TODO Debug
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
	
	
	/**
	 * Função que devolve uma lista de nomes de ficheiros dado um diretorio
	 * @param directoryPath String Nome do directorio de ficheiros
	 * @return fileNames List<String> Retorna uma lista com
	 * o nome dos ficheiros com o directorio. Exemplo: "levels/exemplo.txt"
	 * */
//	public static List<String> fileNames(String directoryPath) {
//
//		
//		File file = new File(directoryPath);
//		
//		FilenameFilter filter = new FilenameFilter() {
//	        @Override
//	        public boolean accept(File f, String name) {
//	            return name.endsWith(".txt");
//	        }
//	    };
//	    
//	    String[] results = file.list(filter);
//	    List<String> fileNames = new ArrayList<>();
//	    for(String result : results) {
//	    	fileNames.add(directoryPath + result); 
//	    }
//	    return fileNames;
//	}
	
	public static List<String> getFilenameWithPrefix(String directoryPath, String prefix) {
		File directory = new File(directoryPath);
		FilenameFilter filter = new FilenameFilter() {
	        @Override
	        public boolean accept(File dir, String name) {
	        	return name.matches( "level\\d+.txt" );
	        }
	    };
	    
		File[] files = directory.listFiles(filter);
		List<String> fileArray = new ArrayList<>();
		for(File f : files) {
			fileArray.add(f.getName());
		}
		
		return fileArray;
	}

	
		
	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}
}