package block;

import model.*;

public class Block extends Observable{
	public static enum Type {
		SORTIE, ENTREE , DESTRUCTIBLE , INDESTRUCTIBLE ,LAVE , TELEPORTEUR , ESPACE , EXPLOSEUR , GENERATEUR,TELEPORTEUR2 }
	protected Coordinate cood;
	protected Game game;
	protected Type type;
	
	public Block(int x,int y,Game g,Type type2) {
		this.cood = new Coordinate(x, y);
		this.game = g;
		this.type = type2;
		registerObserver(game);
	}
	
	public Block getBlock () {
		return this;
	}
	
	public Coordinate getBlockCoordinate () {
		return cood;
	}
	public Type getType() {
		return type; 
	}
	
 public void setType (Type type2) {
	 this.type = type2;
 } 
	
}
