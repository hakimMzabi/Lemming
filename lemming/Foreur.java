package lemming;

import model.Change;
import model.Coordinate;
import model.Observable;

import lemming.Lemming;
import lemming.StateLemming;
import lemming.Marcheur;

public class Foreur extends Observable implements StateLemming {
	private Lemming lemming;

	public Foreur(Lemming lemming) {
		this.lemming = lemming;
	}

	@Override
	public void move() {

		Coordinate last = lemming.getBody();
		Coordinate next = new Coordinate(last.getX() + lemming.getDirection().x,last.getY() + lemming.getDirection().y);
		Coordinate lastDown = new Coordinate(last.getX(), last.getY() + 1);
		Coordinate lastUp = new Coordinate(last.getX(), last.getY() - 1);
		System.out.println("option Foreur est activatee");
		
		boolean testForeur = false;

		if (!testForeur) {

			if (lemming.getGame().isOut(lastDown)) // if lastDown is out lemming die
				lemming.die();

			else if (lemming.lavetst(lastDown)) { // if lastDown = lave
				lemming.die();
			} else if (lemming.espacetst(lastDown)) { // if lastDown is void
				next = lastDown;
				lemming.setHauteur(lemming.getHauteur() + 1);
			}

			if (lemming.testExploseur(lastDown))
				lemming.activeExploseur(lastDown);

			if (lemming.testDestructible(lastDown)) // if lastDown = Destructible destroy the block and next = lastDown
				testForeur = true;

			if (lemming.lavetst(next)) // if next = lavE
				lemming.die();

			if (lemming.tstBloqueur(next)) {
				System.out.println("Succeed");
				next = lemming.oppositeDirection(lemming.getDirection(), next);
			}

			else if (lemming.espacetst(next)) {
				lemming.setBody(next);
			} else if (lemming.testTelporteur(lastDown)) { // if lastDown = Teleporter
				next = lemming.teleportation(lastDown);

			} else if (lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame()))) {// if lastDown = EndBlock

				lemming.setIsArrived(true);
			} else if (next.equals(Coordinate.getEndCoordinate(lemming.getGame()))) {// if next = EndBlock

				lemming.setIsArrived(true);

			} else {
				next = lemming.oppositeDirection(lemming.getDirection(), next);
			}

		}
		if (testForeur) {
			lemming.cmpDePas();
			if (lemming.testDestructible(lastDown)) {
				lemming.destructionDown(lastDown);
				next = lastDown;
				lemming.setBody(next);

			} else { // if next = void
				testForeur = false;
				next = lastDown;
			}

		}
		if (lemming.getNbPas() >= 5)
			testForeur = false;

		if (!testForeur && !lemming.ifArrived() && lemming.ifAlive()) {
			lemming.addChange(last, Change.ChangeType.ESPACE);
			lemming.addChange(next, Change.ChangeType.LEMMING);
			lemming.setBody(next);
			lemming.changeStateTo(new Marcheur(lemming));

		} else if (testForeur && !lemming.ifArrived() && lemming.ifAlive() && !lemming.isCndTochange()) {
			lemming.addChange(last, Change.ChangeType.ESPACE);
			lemming.addChange(next, Change.ChangeType.FOREUR);
			lemming.setBody(next);

		} else
			lemming.addChange(last, Change.ChangeType.ESPACE);

		lemming.notifyObserver();
	}

}
