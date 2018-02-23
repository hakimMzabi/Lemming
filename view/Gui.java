package view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Game;
import view.GamePanel;

public class Gui {
	GamePanel gamePanel;
	
	public Gui(Game game, int scale) {
		gamePanel = new GamePanel(game, scale);
		
	}

}
