package lemming;

import java.util.Observable;

import block.Block.Type;
import block.Destructible;
import block.Espace;
import model.Change;
import model.Coordinate;

public class Charpentier extends Observable implements StateLemming {
	private Lemming lemming;

	public Charpentier(Lemming lemming) {
		this.lemming = lemming;
	}

	@Override
	public void move() {
		Coordinate last = lemming.getBody();
		Coordinate next = new Coordinate(last.getX() + lemming.getDirection().x,last.getY() + lemming.getDirection().y);
		Coordinate nextUp = new Coordinate(last.getX() + lemming.getDirection().x,last.getY() + lemming.getDirection().y - 1);
		Coordinate lastUp = new Coordinate(last.getX(), last.getY() - 1);
		Coordinate lastDown = new Coordinate(last.getX(), last.getY() + 1);

		System.out.println("option Charpentier est activatee");
		
		if(lemming.getGame().isOut(lastDown) )  // if lastDown is out lemming die
			lemming.die();
		
		else if( lemming.lavetst(lastDown)) {  // if lastDown = lave
			lemming.die();
		}
		else if( lemming.espacetst(lastDown)){ // if lastDown is void
			next = lastDown;
			lemming.setHauteur(lemming.getHauteur() +1);
		}
		else if(lemming.testTelporteur(lastUp)){  // if lastDown = Teleporter
			next=lemming.teleportation(lastUp);
		}
		if( lemming.lavetst(next) )  // if next = lavE
			lemming.die();
		
		else if(lemming.espacetst(nextUp)){
			lemming.escalier(next); 
			next = nextUp;
			lemming.cmpDePas();
		}
		else {
			lemming.changeStateTo(new Marcheur(lemming));
			if(!lemming.espacetst(nextUp)) {
				next=lemming.oppositeDirection(lemming.getDirection(),next);
			}
		}
	

		if(lemming.getNbPas() > 4) lemming.changeStateTo(new Marcheur(lemming)); 
		
		// if AltitudeCounter >= 5 => die
		if (!lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame())) && lemming.getHauteur() >= 5
				&& !lemming.espacetst(lastDown))
			lemming.die();

		if( lemming.ifAlive() && !lemming.ifArrived()){
			
			lemming.addChange(next,Change.ChangeType.LEMMING); 
			lemming.setBody(next);
		}else
			lemming.addChange(last,Change.ChangeType.ESPACE);

		lemming.notifyObserver();
	}

}
