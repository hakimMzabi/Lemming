package app;

import model.Game;
import model.StartGame;
import view.Gui;

public class App {

	private static final int SCALE = 30;
	private static final int SPEED = 150;
	private static final int WIDTH = 30;
	private static final int HEIGTH = 14;
	private static Gui gui;
	public static void main(String[] args)throws Exception {
		
		StartGame start = new StartGame(HEIGTH, WIDTH, SCALE);
		
	}
}
