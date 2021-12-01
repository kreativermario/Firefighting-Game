package pt.iul.poo.firefight.starterpack;

import java.util.Iterator;
import java.util.List;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

//Esta classe de exemplo esta' definida de forma muito basica, sem relacoes de heranca
//Tem atributos e metodos repetidos em relacao ao que está definido noutras classes 
//Isso sera' de evitar na versao a serio do projeto

public class Water extends GameElement {
	
	public Water(String name, Point2D position, int layerValue) {
		super(name, position, layerValue);
	}	
	
	//TODO Debug
	public String toString() {
		return "Water";	}
	
	
	public static void removeWater() {
		GameEngine ge = GameEngine.getInstance();
		List<ImageTile> waters = ge.selectObjectsList(e -> e instanceof Water);
		
		
		for(int i = 0; i < waters.size(); i++) {
			ImageTile image = waters.get(i);
				ge.removeElement(image);
		}
	}	
	
	
}
