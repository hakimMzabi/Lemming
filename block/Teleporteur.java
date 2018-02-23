package block;

import model.Coordinate;
import model.Game;

public class Teleporteur extends Indestructible {

	private Coordinate coord1;

	public Teleporteur(int x, int y, Game g, Type type2) {
		super(x, y, g, type2);

	}

	public void setCoord1(int x, int y) {
		this.coord1 = new Coordinate(x, y);
	}

}
