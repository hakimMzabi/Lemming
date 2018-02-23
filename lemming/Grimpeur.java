package lemming;

import model.Change;
import model.Coordinate;
import model.Observable;

public class Grimpeur extends Observable implements StateLemming {
	private Lemming lemming;

	public Grimpeur(Lemming lem) {
		lemming = lem;

	}

	@Override
	public void move() {

		Coordinate last = lemming.getBody();
		Coordinate next = new Coordinate(last.getX() + lemming.getDirection().x,last.getY() + lemming.getDirection().y);
		Coordinate lastUp = new Coordinate(last.getX(), last.getY() - 1);
		Coordinate nextUp = new Coordinate(last.getX(), last.getY() + lemming.getDirection().y - 1);
		Coordinate lastDown = new Coordinate(last.getX(), last.getY() + 1);
		Coordinate nextDown = new Coordinate(last.getX() + lemming.getDirection().x,
				last.getY() + lemming.getDirection().y + 1);
	

		if (lemming.getGame().isOut(lastDown)) // if lastDown is out lemming die
			lemming.die();

		else if (lemming.lavetst(lastDown)) { // if lastDown = lave
			lemming.die();
		}

		 if (lemming.lavetst(next)) // if next = lavE
			lemming.die();
	
		 if(lemming.testTelporteur(lastUp)){  // if lastDown = Teleporter
			next=lemming.teleportation(lastUp);
		 }
		if (lemming.tstBloqueur(next)) {

			next = lemming.oppositeDirection(lemming.getDirection(), next);
		}
		if (lemming.espacetst(next) && !lemming.espacetst(nextDown)) {
			lemming.setBody(next);
			System.out.println("marcher");

		} else if (lemming.espacetst(nextUp) && !lemming.espacetst(next)) {
			System.out.println("monter");
			next = nextUp;
			lemming.setBody(next);
			lemming.cmpDePas();

		} else {
			System.out.println("up");

			lemming.addChange(next, Change.ChangeType.LEMMING);
			lemming.changeStateTo(new Marcheur(lemming));
		}
		// if AltitudeCounter >= 5 => die
		if (!lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame())) && lemming.getHauteur() >= 5
				&& !lemming.espacetst(lastDown))
			lemming.die();

		if (!lemming.ifArrived() && lemming.ifAlive()) {
			lemming.addChange(last, Change.ChangeType.ESPACE);
			lemming.addChange(next, Change.ChangeType.LEMMING);
			lemming.setBody(next);
		}

		lemming.notifyObserver();
	}
}
