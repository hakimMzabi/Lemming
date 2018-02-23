package lemming;

import java.util.Observable;

import model.Change;
import model.Coordinate;

public class Parachutiste extends Observable implements StateLemming {
	Lemming lemming;

	public Parachutiste(Lemming lemming) {
		this.lemming = lemming;
	}

	@Override
	public void move() {
		Coordinate last = lemming.getBody();
		Coordinate next = new Coordinate(last.getX() + lemming.getDirection().x,last.getY() + lemming.getDirection().y);
		Coordinate nextUp = new Coordinate(last.getX() + lemming.getDirection().x,last.getY() + lemming.getDirection().y - 1);
		Coordinate lastUp = new Coordinate(last.getX(), last.getY() - 1);
		Coordinate lastDown = new Coordinate(last.getX(), last.getY() + 1);
		boolean testPara = true;
 
		System.out.println("option Parachutiste est activatee");
		lemming.setHauteur(0);

		if (testPara) {
			if (lemming.getGame().isOut(lastDown)) // if lastDown is out lemming die
				lemming.die(); 

			if (lemming.lavetst(lastDown)) // if lastDown = lave
				lemming.die();
			
			if(lemming.testTelporteur(lastDown))  // if lastDown = Teleporteur
				next=lemming.teleportation(lastDown);
		
			
			 if (lemming.lavetst(next)) // if next = lavE lemming.die();
			  
			  if (lemming.tstBloqueur(next)) { System.out.println("Succeed");
			  	next =lemming.oppositeDirection(lemming.getDirection(), next); }
			  
			  if (lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame()))) {// iflastDown = EndBlock
			  
			  lemming.setIsArrived(true); 
			  
			  } if(next.equals(Coordinate.getEndCoordinate(lemming.getGame()))) {// if next = EndBlock
			  
				  lemming.setIsArrived(true); 
			  }
			 
			if (lemming.espacetst(next) && !lemming.espacetst(lastDown)) {
					lemming.setBody(next);
			}	

			else if (lemming.espacetst(lastDown)) {
				System.out.println("je descend doucement");
				lemming.cmpDePas();
				
				
				if (lemming.getNbPas() % 2 == 0) { // reduire la vitesse
					lemming.setBody(lastDown);
				}
				if (lemming.getNbPas() % 2 == 1) {
					lemming.setBody(last);
				}
			}else if (!lemming.espacetst(lastDown) && lemming.getNbPas() != 0) {
				testPara = false;
			}else {
				next = lemming.oppositeDirection(lemming.getDirection(), next);
			}

			lemming.addChange(last, Change.ChangeType.ESPACE);
			lemming.addChange(next, Change.ChangeType.PARACHUTISTE);
			
 		}
		
		if (lemming.ifAlive() && !lemming.ifArrived()) {

			if (!testPara) {
				
				lemming.changeStateTo(new Marcheur(lemming));
				lemming.addChange(next, Change.ChangeType.LEMMING);
			}
		} else
			lemming.addChange(last, Change.ChangeType.ESPACE);

		lemming.notifyObserver();

	}
}
