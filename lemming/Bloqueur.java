package lemming;

import lemming.Lemming.LemmingType;
import model.Change;
import model.Coordinate;
import model.Observable;

public class Bloqueur extends Observable implements StateLemming {

	private Lemming lemming;
	
	public Bloqueur (Lemming lemming) {
		this.lemming = lemming;
	}
	
	@Override
	public void move() {
	    Coordinate last = lemming.getBody();
	    Coordinate posfix = new Coordinate(last.getX(),last.getY());
	    System.out.println("option Bloqueur est activatee");
	    lemming.addChange(last,Change.ChangeType.BLOQUEUR);
	    lemming.setType(LemmingType.BLOQUEUR);
		lemming.notifyObserver();
	    
		
	}

}
