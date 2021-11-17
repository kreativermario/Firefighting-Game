package pt.iul.poo.firefight.starterpack;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
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
	
	
	//TODO Método para "apagar fogo"
	private void cleanFire(Point2D position) {
		if(isTherePineAtPosition(position)) {
			Fire fire = new Fire(new Point2D(position.getX(), position.getY()));
			addElement(fire);
		}
	}
	
	
	// O metodo update() e' invocado sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada um referencia para o objeto observado (neste caso seria a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();              // obtem o codigo da tecla pressionada
		
		if (key == KeyEvent.VK_ENTER) {            // se a tecla for ENTER, manda o bombeiro mover
			fireman.move();			
			Point2D position = fireman.getPosition();
			// Verificar se tem pinheiro na posicao
				// Queimar (burn)
				// Aficionar um objeto tipo Fire no jogo (tileList) e no gui (addImage)
			if(isTherePineAtPosition(position)) {
				Fire fire = new Fire(new Point2D(position.getX(), position.getY()));
				addElement(fire);
			}
				
		}
		
		if (key == KeyEvent.VK_UP)
			fireman.moveUp();
		if (key == KeyEvent.VK_DOWN)
			fireman.moveDown();
		if (key == KeyEvent.VK_LEFT)
			fireman.moveLeft();
		if (key == KeyEvent.VK_RIGHT)
			fireman.moveRight();
			
		
		gui.update();                            // redesenha as imagens na GUI, tendo em conta as novas posicoes
	}
	
	
	// isTherePineAtPosition
	public boolean isTherePineAtPosition(Point2D position) {
		for(ImageTile element : tileList) {
			if(element.getPosition().equals(position) && element instanceof Pine)
				return true;
		}
		return false;
	}
	
	
	// Adiciona elemento ao jogo
	public void addElement(ImageTile element) {
		tileList.add(element);
		gui.addImage(element);
	}

	
	// Criacao dos objetos e envio das imagens para GUI
	public void start() {
		createTerrain();      // criar mapa do terreno
		createMoreStuff();    // criar mais objetos (bombeiro, fogo,...)
		sendImagesToGUI();    // enviar as imagens para a GUI
	}

	
	//TODO carregar mapa pelo ficheiro
	// Criacao do terreno - so' pinheiros neste exemplo 
	private void createTerrain() {
		try {
			File fileName = new File("levels/example.txt");
			Scanner sc = new Scanner(fileName);
			int y = 0;
			while(sc.hasNextLine()) {
				if(y >=11){
					String [] line = sc.nextLine().split(" ");
					int xMove = Integer.parseInt(line[1]);
					int yMove = Integer.parseInt(line[2]);
					switch(line[0]) {
						case "Fireman":
							tileList.add(new Fireman(new Point2D(xMove,yMove)));
						case "Bulldozer":
							tileList.add(new Bulldozer(new Point2D(xMove,yMove)));
						case "Fire":
							tileList.add(new Fire(new Point2D(xMove, yMove)));
					}
					
				}else {
					String line = sc.nextLine();
					for(int x = 0; x < line.length(); x++) {
						char element = line.charAt(x);
						
						if(element == 'p')
							tileList.add(new Pine(new Point2D(x, y)));
						if(element == 'e')
							tileList.add(new Eucaliptus(new Point2D(x,y)));
						if(element == 'm')
							tileList.add(new Grass(new Point2D(x,y)));
						if(element == '_')
							tileList.add(new Land(new Point2D(x,y)));
						
					}
					y++;
				}
			
			}
			sc.close();
			
			
		} catch (FileNotFoundException e) {
			System.err.println("File snot found");
		}
		
//		for (int y=0; y<GRID_WIDTH; y++)
//			for (int x=0; x<GRID_HEIGHT; x++)
//				if(x % 2 == 0)
//					tileList.add(new Pine(new Point2D(x,y)));
//				else
//					tileList.add(new Land(new Point2D(x,y)));
	}
	
	
	//TODO criar os objetos do fim do ficheiro
	// Criacao de mais objetos - neste exemplo e' um bombeiro e dois fogos
		private void createMoreStuff() {
			fireman = new Fireman( new Point2D(5,5));
			tileList.add(fireman);
			
			tileList.add(new Fire(new Point2D(3,3)));
			tileList.add(new Fire(new Point2D(3,2)));
			
		}
	
		
	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes  
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}
}
