package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.*;
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
* @author Francisco Torgo-N98634
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
	* @return INSTANCE GameEngine
	*/

	public static GameEngine getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new GameEngine();
		}
		return INSTANCE;
	}
	
	/**
	* Este metodo é invocado sempre que o utilizador carrega numa tecla
	* @param source Observed Referencia para o objeto observado (neste caso seria a GUI)
	*/	
	
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();              // obtem o codigo da tecla pressionada
		
		removeDisappear(); 			//Remove água e explosões
		
		switch(key) {
			case KeyEvent.VK_P:				//Chama o aviao na coluna com mais fogos
				
				List<ActiveElement> planeList = selectObjectsList(e -> e instanceof ActiveElement && e instanceof Plane);
				ActiveElement plane = getActive(planeList);
				
				if(plane == null) {
					Plane.init();		//Inicializa o aviao
				}
				
				break;
				
				
			case KeyEvent.VK_ENTER:			//Sair do veiculo
				List<ActiveElement> drivableList = selectObjectsList(e -> e instanceof ActiveElement && e instanceof Drivable);
				ActiveElement drivable  = (ActiveElement) getActive(drivableList);
				
				//Se o veiculo tiver ativo
				if(drivable != null) {
					
					gui.addImage(fireman);			//Colocar o fireman a visivel
					fireman.setActive(true);		//Colocar o fireman a ativo
					drivable.setActive(false);		//Colocar o veiculo a inativo
					
				}
				break;
			default:
				if(Direction.isDirection(key)) {

					score.setScoreValue(REMOVE_SCORE_PER_PLAY);		//Remove x score por cada jogada
					
					List<ActiveElement> activeList = selectObjectsList(e -> e instanceof ActiveElement && 
							e instanceof Movable && ((ActiveElement) e).isActive() == true);
					
					Iterator<ActiveElement> it = activeList.iterator();
					
					while(it.hasNext()) {	
						Movable object = (Movable) it.next();
						if(object instanceof Plane) {
							object.move(KeyEvent.VK_UP);
						}else {
				
							object.move(key);
							
						}
					}	
				}
		}
		
		//Dá display do highest score do player se já jogou anteriormente
		playerHighestScore = Score.getPlayerHighscore(playerName, levelNumber);
		
		if(playerHighestScore != null) {		//Se já jogou anteriormente
			
			int highestScore = playerHighestScore.getScoreValue();
			gui.setStatusMessage("Level nº " + levelNumber + " | " + "Score: " 
			+ score.getScoreValue() + " | Player Name: " + playerName + " | Your Highscore: " + highestScore);
			
		}else {									//Primeira vez que joga
			
			gui.setStatusMessage("Level nº " + levelNumber + " | " + "Score: " + score.getScoreValue() + " | Player Name: " + playerName);
			
		}
		
		//debug();
		
		checkLevelOver();						 // Verifica se o nivel ja acabou
		
		gui.update();                            // redesenha as imagens na GUI, tendo em conta as novas posicoes
	}
	

	
	/**
	* Este método é usado para controlar debug
	*/
	
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
	
	/**
	* Getter getActive
	* @param activeList List<ActiveElement>
	* @return activeElement ActiveElement
	*/

	private static ActiveElement getActive(List<ActiveElement> activeList) {
		for(ActiveElement activeElement : activeList) {
			if(activeElement.isActive() == true) {
				return activeElement;
			}
				
		}
		return null;
	}
	
	
	/**
	* Este método é utilizado para remover Agua e Explosoes (Que implementem a interface Disappears)
	*/
	
	private void removeDisappear() {
		List<ImageTile> gifs = selectObjectsList(e -> e instanceof Disappears);
		for(int i = 0; i < gifs.size(); i++) {
			ImageTile image = gifs.get(i);
				removeElement(image);
		}
	}
	
	/**
	 * Função que devolve o objeto numa certa posição de determinada instância dado um Predicate
	 * @param position Point2D
	 * @param filtro Predicate<GameElement>
	 * @return (T) element T
	 * */

	public <T> T getObjectAtPosition(Point2D position, Predicate<GameElement> filtro) {
		Iterator<ImageTile> it = tileList.iterator();
		while(it.hasNext()) {
			ImageTile image = it.next();
			GameElement element = (GameElement) image;
			if(filtro.test(element) && element.getPosition().equals(position))
				return (T) element;
		}
		return null;
	}
	
	
	/**
	 * Função que devolve uma lista de objetos de uma certa instância dado um Predicate<GameElement>
	 * @param filtro Predicate<GameElement>
	 * @return (T) image List<T>
	 * */

	public <T> List<T> selectObjectsList(Predicate<GameElement> filtro){
		List<T> list = new ArrayList<>();
		Iterator<ImageTile> it = tileList.iterator();
		while(it.hasNext()) {
			ImageTile image = it.next();
			GameElement element = (GameElement) image;
			if(filtro.test(element))
				list.add((T) image);
		}
		
		return list;
	}
	
	/**
	 * Função que diz se o objeto de uma determinada instancia existe numa certa posição
	 * @param position Point2D
	 * @param filtro Predicate<GameElement>
	 * @return boolean
	 * */

	public boolean isThereObjectAtPosition(Point2D position, Predicate<GameElement> filtro) {
		
		Iterator<ImageTile> it = tileList.iterator();
		while(it.hasNext()) {
			ImageTile image = it.next();
			GameElement element = (GameElement) image;
			if(filtro.test(element) && element.getPosition().equals(position))
				return true;	
		}
		return false;
	}
	

	/**
	 * Procedimento que remove uma imagem do GUI
	 * @param image ImageTile;
	 */
	
	public void removeImage(ImageTile image) {
		gui.removeImage(image);
	}
	

	/**
	* Getter Fireman
	* @return fireman Fireman
	*/

	public Fireman getFireman() {
		return fireman;
	}
	

	/**
	 * Getter do numero de nível do mapa
	 * @return levelNumber integer
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
	* @param image ImageTile Imagem do elemento a verificar
	* @return boolean Quando existe true, em contrario false
	*/

	public boolean isThereElement(ImageTile image) {
		for(ImageTile element : tileList) {
			if(element.getPosition().equals(image.getPosition()) 
					&& element.getName().equals(image.getName()) 
					&& element.getLayer() == element.getLayer())
				return true;
		}
		return false;
	}
	
	/**
	* Este metodo é invocado para adicionar um determinado elemento ao jogo
	* @param element ImageTile Elemento a adicionar
	*/

	public void addElement(ImageTile element) {
		if(!isThereElement(element)) {
			tileList.add(element);
			gui.addImage(element);
		}
	}
	
	/**
	* Este metodo é invocado para remover um determinado elemento do jogo
	* @param element ImageTile Elemento a remover
	*/

	public void removeElement(ImageTile element) {
		tileList.remove(element);
		gui.removeImage(element);
		gui.update();
	}
	
	
	/* Método que termina o jogo
	 * 
	 * */
	
	private void gameOver() {
		gui.dispose();
		System.exit(0);
		
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
		
		this.score = new Score();			//Inicializa o score
		for(ImageTile image : tileList) {
			if(image instanceof Burnable)		//Inicializa o score de acordo com a imagem Burnable
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
	* Este método é usado pra verificar se o nivel já está concluido
	*/
	
	private void checkLevelOver() {
		List<ImageTile> fireList = selectObjectsList(e -> e instanceof Fire);
		
		//Se nao houver mais fogos --> Level over ou game over!
		if(fireList.size() == 0) {
			
			playerHighestScore = null;		//Reset do high score do player para cada nivel
			
			gui.setMessage("Level Over!");
			score.saveToFile(playerName);			//Guarda o score do player
			
			List<Score> top5 = Score.getTop5Score(levelNumber);		
			
			String str = "TOP SCORE: " + System.lineSeparator();
			
			for(int i = 1; i <= top5.size(); i++) {
				Score score = top5.get(i-1);
				str += i + ". " + score.getPlayerName() + " - " + score.getScoreValue() + System.lineSeparator();
			}
			
			gui.setMessage(str);			//Display dos top 5 
			Score.saveTop5File(top5);		//Guarda os top 5
			
			nextLevel();					//Proximo nivel
		}
	}
	
	/**
	* Método que muda o nível/mapa ou acaba o jogo
	*/

	private void nextLevel() {
		gui.clearImages();			//Limpa a tela
		tileList.clear();			//Limpa o array de ImageTile
		this.fireman = null;
		
		if(levelNumber >= levels.size()) {		//Se o numero de niveis superar o numero de ficheiros de niveis entao jogo acabou
			gui.setMessage("Game Over! All levels done!");
			gameOver();
		}else {									//Dá load ao proximo mapa
			start();
		}
			
	}
	
	/**
	* Método utilizado para dar set do Fireman
	*/

	public void setFireman(Fireman fireman) {
		this.fireman = fireman;
	}
	
	/** 
	 * 	Método fábrica
	 * 	Verifica se o elemento é uma das opções e
	 * 	cria o objeto
	 * 	@param element char
	 * 	@param position Point2D
	 *  @return obj ImageTile
	*/

	private ImageTile addFromChar(char element, Point2D position) {
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
			return obj;
		return null;				
	}
	
	/** 
	 * 	Dá load de um mapa que é ficheiro txt
	 * 	para o tileList (Vetor) e GUI.
	*/

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
			
					System.out.println("Y -> " + y + " " + element + " " + xMove + " " + yMove); 			//TODO Debug
					
					ImageTile image = GameElement.addFromString(element, position);
					
					if(image != null)
						tileList.add(image);	
					
				}else {
					String line = sc.nextLine();
					
					System.out.println("Y -> " + y + " " + line); 								//TODO desnecessário, debug only
					
					for(int x = 0; x < line.length(); x++) {
						char element = line.charAt(x);
						Point2D position = new Point2D(x,y);
						ImageTile image = addFromChar(element, position);
						if(image != null)
							tileList.add(image);						
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
	* Este método é utilizado para obter o prefixo do nome de um ficheiro
	* @param directoryPath String
	* @param prefix String
	* @return fileArray List<String>
	*/

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

	/**
	* Este método é utilizado para enviar mensagens para o GUI
	*/

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}

}