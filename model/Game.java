package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import block.Block;
import block.Block.Type;
import block.Destructible;
import block.Espace;
import block.Exploseur;
import block.Generateur;
import block.Indestructible;
import block.Lave;
import block.Teleporteur;
import controller.GameObserver;
import lemming.Lemming;
import lemming.Lemming.LemmingType;
import block.Sortie;
import model.Observable;
import block.StartBlock;

public class Game extends Observable implements GameObserver {

	private int height;
	private int width;
	private int scale;
	private int stepCmp;
	private final int nbLemmings = 5;
	private int blockerCounter;
	private List<Block> blockList;
	private List<Lemming> lemmingList;
	private int conditonToWin = 0;
	private int sleep;
	private LemmingType type;

	public Game(int width, int height, String f, int slp) throws Exception {
		this.sleep = slp;
		blockerCounter = 0;
		stepCmp = 0;
		this.width = width;
		this.height = height;
		blockList = map(f);
		lemmingList = new ArrayList<Lemming>();
		this.type = LemmingType.LEMMING;

	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public List<Block> getBlockList() {
		return blockList;
	}

	public boolean testStarter(List<Block> list, Coordinate c) {
		// Down
		if (c.getY() < height - 1)
			if (list.get((c.getY() + 1) * width + c.getX() + c.getY() + 1).getClass().equals(Espace.class))
				return true;
		// Up
		if (c.getY() > 0)
			if (list.get((c.getY() - 1) * width + c.getX() + c.getY() - 1).getClass().equals(Espace.class))
				return true;
		// Right
		if (c.getX() < width - 1)
			if (list.get(c.getY() * width + c.getX() + 1 + c.getY()).getClass().equals(Espace.class))
				return true;
		// Left
		if (c.getX() > 0)
			if (list.get(c.getY() * width + c.getX() - 1 + c.getY()).getClass().equals(Espace.class))
				return true;

		return false;
	}

	private List<Block> map(String f) throws Exception {
		List<Block> m = new ArrayList<Block>();
		Teleporteur tel = new Teleporteur(10, 10, this, Type.TELEPORTEUR);
		File file = new File(f);
		FileReader fr;
		StartBlock startB = null;
		int nbEntree = 0;
		int nbSortie = 0;
		String str = "";

		try {
			fr = new FileReader(file);
			int read = 0;
			while ((read = fr.read()) != -1)
				str += (char) read;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int x = 0; // column counter
		int y = 0; // line counter
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if ((int) c == 10) {
				// Return to the line
				x = -1;
				y++;
			} else if ((int) c == 73) { // I
				// Indestructible block

				Indestructible b = new Indestructible(x, y, this, Block.Type.INDESTRUCTIBLE);
				m.add(b);
				Coordinate coor = new Coordinate(x, y);
				addChanges(new Change(coor, Change.ChangeType.INDESTRUCTIBLE));

			} else if ((int) c == 84) { // T

				// Teleporteur coord1
				tel = new Teleporteur(x, y, this, Block.Type.TELEPORTEUR);
				m.add(tel);
				Coordinate coor = new Coordinate(x, y);
				addChanges(new Change(coor, Change.ChangeType.TELEPORTEUR));
				// System.out.println(x);
			} else if ((int) c == 80) { // P

				// Teleporter coord2
				Indestructible t2 = new Indestructible(x, y, this, Block.Type.TELEPORTEUR2);
				m.add(20, t2);
				;
				Coordinate coor = new Coordinate(x, y);
				addChanges(new Change(coor, Change.ChangeType.TELEPORTEUR2));

			} else if ((int) c == 66) { // B

				// Destructible block
				Destructible b = new Destructible(x, y, this, Block.Type.DESTRUCTIBLE);
				m.add(b);
				Coordinate coor = new Coordinate(x, y);
				addChanges(new Change(coor, Change.ChangeType.DESTRUCTIBLE));
			} else if ((int) c == 87) {// W

				Generateur b = new Generateur(x, y, this, Block.Type.GENERATEUR);
				m.add(b);
				Coordinate coor = new Coordinate(x, y);
				addChanges(new Change(coor, Change.ChangeType.GENERATEUR));

			} else if ((int) c == 88) {// X
				//Exploseur
				Exploseur b = new Exploseur(x, y, this, Block.Type.EXPLOSEUR);
				m.add(b);
				Coordinate coor = new Coordinate(x, y);
				addChanges(new Change(coor, Change.ChangeType.EXPLOSEUR));

			} else if ((int) c == 71) { // G
				// lave block
				Lave b = new Lave(x, y, this, Block.Type.LAVE);
				m.add(b);
				Coordinate coor = new Coordinate(x, y);
				addChanges(new Change(coor, Change.ChangeType.LAVE));

			} else {
				if ((int) c == 69) { // S
					// entry

					StartBlock b = new StartBlock(x, y, this, Block.Type.ENTREE);
					m.add(b);
					Coordinate coor = new Coordinate(x, y);
					addChanges(new Change(coor, Change.ChangeType.ENTREE));
					startB = b;
					nbEntree++;

				} else if ((int) c == 83) { // E
					// exit
					Sortie b = new Sortie(x, y, this, Block.Type.SORTIE);
					m.add(b);
					Coordinate coor = new Coordinate(x, y);
					addChanges(new Change(coor, Change.ChangeType.SORTIE));
					nbSortie++;

				} else {
					// void

					Espace b = new Espace(x, y, this, Block.Type.ESPACE);
					m.add(b);
					Coordinate coor = new Coordinate(x, y);
					addChanges(new Change(coor, Change.ChangeType.ESPACE));

				}
			}
			x++;
		}

		if (nbEntree == 0)
			throw new Exception("Map Error : StartBlock Missing !!");
		else if (nbEntree > 1)
			throw new Exception("You must have one StartBlock ! ");

		if (nbSortie == 0)
			throw new Exception("Map Error : EndBlock Missing !!");
		else if (nbSortie > 1)
			throw new Exception("You must have one EndBlock !");

		if (!testStarter(m, startB.getBlockCoordinate()))
			throw new Exception("Map Error : StartBlock !!");

		return m;
	}

	@Override
	public void update(List<Change> changes) {
		for (Change c : changes) {

			addChanges(c);
		}
		notifyObserver();
	}

	public boolean isOut(Coordinate c) {
		if (c.getX() < 0 || c.getY() < 0)
			return true;
		if (c.getX() >= width || c.getY() >= height)
			return true;
		return false;
	}

	public void changeDirection(Lemming l, Direction direction) {
		l.setDirection(direction);
	}

	public Coordinate getStartBlockCoor() {

		Coordinate entree = null;
		for (Block b : this.getBlockList()) {
			if (b.getType() == Block.Type.ENTREE)
				entree = new Coordinate(b.getBlockCoordinate().getX(), b.getBlockCoordinate().getY());
		}
		return entree;
	}

	// add a new Lemming

	public void addLemming() throws Exception {
		Lemming lemming = new Lemming(this);
		lemmingList.add(lemming);

		notifyObserver();
	}

	public List<Lemming> getLemmingList() {
		return lemmingList;
	}

	public void step() throws Exception {

		for (int i = 0; i < this.lemmingList.size(); i++) {

			this.lemmingList.get(i).move();

			if (!this.lemmingList.get(i).ifAlive()) {
				this.lemmingList.remove(i);
			} else {
				if (this.lemmingList.get(i).ifArrived()) {
					this.lemmingList.remove(i);
					conditonToWin++;
				}
			}
			notifyObserver();
			try {
				Thread.sleep(sleep);
				
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

	}

	public boolean win() {
		if (conditonToWin >= 3) {
			System.out.println("Level completed");

			JOptionPane.showMessageDialog(null, "YOU WIN !!! Good job");
			return true;
		}
		System.out.println("Level failed");
		JOptionPane.showMessageDialog(null, "YOU LOSE !!! Try again");
		return false;
	}

	public void run() throws Exception {

		int nbLemming = 0;
		while (lemmingList.size() - blockerCounter != 0 || stepCmp == 0) {
			if (nbLemming < nbLemmings)
				if ((stepCmp + 2) % 2 == 0) { // A Lemming born each 2 steps
					addLemming();
					nbLemming++;
					notifyObserver();
				}
			step();
			stepCmp++;
		}
		win();

	}
}
