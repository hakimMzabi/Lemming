package model;

import block.Block;
import block.Sortie;
import block.StartBlock;
import block.Espace;

public class Coordinate {
	
	private int y;
	private int x;

	public Coordinate(int x, int y) {
		this.y = y;
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

		//Start Coordinate
	
	//End Coordinate
	public static Coordinate getEndCoordinate(Game game){
		for(Block b : game.getBlockList()){
			if(b.getClass().equals(block.Sortie.class))
				return b.getBlockCoordinate();
		}
		return null;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
