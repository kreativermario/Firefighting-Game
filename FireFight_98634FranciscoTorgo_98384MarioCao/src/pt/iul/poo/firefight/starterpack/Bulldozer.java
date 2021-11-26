package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Isso sera' de evitar na versao a serio do projeto

/**
* <h1>Bulldozer</h1>
* Implementação da classe Bulldozer
* Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
* Tem atributos e metodos repetidos em relacao ao que está definido noutras classes
* 
* @author Mario Cao-N98384
* @author Francisco Trogo-N98634
* @since 2021-11-01
*/

public class Bulldozer extends GameElement implements Movable{
	
	private static final GameEngine ge = GameEngine.getInstance();
	
	public Bulldozer(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}

	/**
	* Este metodo é usado para mover um objeto na interface grafica, neste caso Bulldozer
	* @param keyCode traducao codigo para tela.
	*/
	
	// Move numa direcao
	public void move(int keyCode) {
		Direction direction = Direction.directionFor(keyCode);
		Point2D newPosition = super.getPosition().plus(direction.asVector());
		

		if(canMoveTo(newPosition) && !ge.isThereFireAtPosition(newPosition)) {
			ImageTile obj = null;
			switch(direction) {
				case UP:
					obj = new Bulldozer("bulldozer_up", newPosition, 3);
					break;
				case DOWN:
					obj = new Bulldozer("bulldozer_down", newPosition, 3);
					break;
				case LEFT:
					obj = new Bulldozer("bulldozer_left", newPosition, 3);
					break;
				case RIGHT:
					obj = new Bulldozer("bulldozer_right", newPosition, 3);
					break;
			}
			ge.removeElement(ge.getBulldozer());
			ge.setBulldozer(obj);
			ge.addElement(obj);
			demolish(newPosition);
			setPosition(newPosition);
			Fire.propagateFire(newPosition);
		}
	
	}
	
	public void demolish(Point2D newPosition) {
		
		ImageTile image =  ge.getElement(newPosition);

		if(!ge.isThereFireAtPosition(newPosition) && !(image instanceof Land)) {
			ge.removeElement(image);
			ge.addElement(new Land("land", newPosition, 0));
		}
		
		
		
	}
	
	
	
	
	
	
	/**
	* Este metodo é usado para verificar se uma posicao esta dentro da grelha de jogo
	* @param p Point2D.
	*/

	// Verifica se a posicao p esta' dentro da grelha de jogo
	public boolean canMoveTo(Point2D p) {
		
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}
	
	/**
	* Setter da posicao
	* @param position Point2D.
	*/
	
	public void setPosition(Point2D position) {
		 super.setPosition(position);
	}
		
	
	//TODO Debug
	@Override
	public String toString() {
		return "Bulldozer";	
	}
}
