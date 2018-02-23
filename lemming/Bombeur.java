package lemming;

import lemming.Lemming.LemmingType;
import model.Change;
import model.Coordinate;
import model.Observable;

public class Bombeur extends Observable implements StateLemming {
	private Lemming lemming;

	public Bombeur(Lemming lemming) {
		this.lemming = lemming;
	}

	@Override
	public void move() {

		Coordinate last = lemming.getBody();
		Coordinate next = new Coordinate(last.getX() + lemming.getDirection().x,last.getY() + lemming.getDirection().y);
		Coordinate nextUp = new Coordinate(last.getX() + lemming.getDirection().x,last.getY() + lemming.getDirection().y - 1);
		Coordinate lastUp = new Coordinate(last.getX(), last.getY() - 1);
		Coordinate lastDown = new Coordinate(last.getX(), last.getY() + 1);
		System.out.println("option Bombeur est activatee");
		lemming.setCndTochange(false);

		lemming.cmpDePas();

		if (lemming.getGame().isOut(lastDown)) // if lastDown is out lemming die
			lemming.die();

		else if (lemming.espacetst(lastDown)) { // if lastDown is void
			next = lastDown;
			lemming.setHauteur(lemming.getHauteur() + 1);

		} else if (lemming.lavetst(lastDown)) // if lastDown = lava
			lemming.die();

		
		else if(lemming.testTelporteur(lastDown))  // if lastDown = Teleporter
			next=lemming.teleportation(lastDown);
	
		else if(lemming.testTelporteur(next))  // if lastDown = Teleporter
			next=lemming.teleportation(next);
	
		else if (lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame())))// if lastDown = EndBlock
			lemming.ifArrived();

		else if (next.equals(Coordinate.getEndCoordinate(lemming.getGame())))// if next = EndBlock
			lemming.ifArrived();

		else if (lemming.lavetst(next)) // if next = lava
			lemming.die();

		else if (lemming.tstBloqueur(next) || lemming.getGame().isOut(next)) // if next = lemming Bloker or is out
			next = lemming.oppositeDirection(lemming.getDirection(), next);

		else if (lemming.espacetst(next)) // if next = void
			lemming.setBody(next);

		else if (last.getY() == 0 && !lemming.espacetst(next)) // if bodyUp is Out and next != void
			next = lemming.oppositeDirection(lemming.getDirection(), next); // change direction

		else if (lemming.espacetst(nextUp) && lemming.espacetst(lastUp)) // if next !=void but nextUp = void and
																			// lastUp=void
			next = nextUp;

		else
			next = lemming.oppositeDirection(lemming.getDirection(), next); // change direction

		if (lemming.ifAlive()) {

			// if AltitudeCounter >= 5 => die
			if (!lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame())) && lemming.getHauteur() >= 5
					&& !lemming.espacetst(lastDown))
				lemming.die();

			if (!lemming.espacetst(lastDown))
				lemming.setHauteur(0); // rest AltitudeCounter
			if (lemming.getNbPas() >= 3) {
				lemming.activeBomber();
				lemming.die();
				lemming.addChange(last, Change.ChangeType.ESPACE);

			}
			if (!lemming.ifArrived() && lemming.ifAlive()) {
				lemming.addChange(last, Change.ChangeType.ESPACE);
				lemming.addChange(next, Change.ChangeType.BOMBEUR);
				lemming.setType(LemmingType.BOMBEUR);
				lemming.setBody(next);

			} else
				lemming.addChange(last, Change.ChangeType.ESPACE);

			lemming.notifyObserver();
		}

	}
}
