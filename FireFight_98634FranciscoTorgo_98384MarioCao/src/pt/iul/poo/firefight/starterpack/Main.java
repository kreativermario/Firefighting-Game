package pt.iul.poo.firefight.starterpack;


public class Main {
	public static void main(String[] args) {
		
		// Cria uma instancia de GameEngine e depois inicia o jogo
		// Podera' vir a ficar diferente caso defina GameEngine como solitao
		
		GameEngine game = GameEngine.getInstance();
		game.start();
		
	

	}
}