package model;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lemming.Lemming;
import view.Gui;

public class StartGame {

	private Game game;
	private int height;
	private int widht;
	private int scale;
	private Gui gui;

	public StartGame(int h, int w, int s) throws Exception {

		this.height = h;
		this.widht = w;
		this.scale = s;
		this.game = new Game(widht, height, "1", 400);
		gui = new Gui(game, scale);
		game.run();

	}

}
