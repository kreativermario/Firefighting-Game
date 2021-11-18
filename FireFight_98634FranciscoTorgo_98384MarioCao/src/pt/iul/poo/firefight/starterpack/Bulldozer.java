package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

// Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
// Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
// Isso sera' de evitar na versao a serio do projeto

public class Bulldozer extends GameElement{

	public Bulldozer(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}
	
	// // Move numa direcao aleatoria 
	// public void move() {
		
	// 	boolean hasMoved = false;
		
	// 	while (hasMoved == false)  {
		
	// 		Direction randDir = Direction.random();
	// 		Point2D newPosition = super.getPosition().plus(randDir.asVector());
			
	// 		if (canMoveTo(newPosition) ) {
	// 			setPosition(newPosition);
	// 			hasMoved = true;
	// 		}
	// 	}
	// }
	
	// // Move para a esquerda
	// public void moveLeft() {
		
	// 	boolean hasMoved = false;
		
	// 	while (hasMoved == false)  {
		
	// 		Direction leftDir = Direction.LEFT;
	// 		Point2D newPosition = super.getPosition().plus(leftDir.asVector());
			
	// 		if (canMoveTo(newPosition) ) {
	// 			setPosition(newPosition);
	// 			hasMoved = true;
	// 		}
	// 	}
	// }
	
	// // Move para cima
	// public void moveUp() {
		
	// 	boolean hasMoved = false;
		
	// 	while (hasMoved == false)  {
		
	// 		Direction upDir = Direction.UP;
	// 		Point2D newPosition = this.getPosition().plus(upDir.asVector());
			
	// 		if (canMoveTo(newPosition) ) {
	// 			setPosition(newPosition);
	// 			hasMoved = true;
	// 		}
	// 	}
	// }
	
	// // Move para baixo
	// public void moveDown() {
		
	// 	boolean hasMoved = false;
		
	// 	while (hasMoved == false)  {
		
	// 		Direction downDir = Direction.DOWN;
	// 		Point2D newPosition = super.getPosition().plus(downDir.asVector());
			
	// 		if (canMoveTo(newPosition) ) {
	// 			setPosition(newPosition);
	// 			hasMoved = true;
	// 		}
	// 	}
	// }
	
	
	// // Move à direita
	// public void moveRight() {
		
	// 	boolean hasMoved = false;
		
	// 	while (hasMoved == false)  {
		
	// 		Direction rightDir = Direction.RIGHT;
	// 		Point2D newPosition = super.getPosition().plus(rightDir.asVector());
			
	// 		if (canMoveTo(newPosition) ) {
	// 			setPosition(newPosition);
	// 			hasMoved = true;
	// 		}
	// 	}
	// }

	// // Verifica se a posicao p esta' dentro da grelha de jogo
	// public boolean canMoveTo(Point2D p) {
		
	// 	if (p.getX() < 0) return false;
	// 	if (p.getY() < 0) return false;
	// 	if (p.getX() >= GameEngine.GRID_WIDTH) return false;
	// 	if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
	// 	return true;
	// }
	
	
	public void setPosition(Point2D position) {
		 super.setPosition(position);
	}
	
	//TODO Debug
	@Override
	public String toString() {
		return "Bulldozer";	
	}
}
