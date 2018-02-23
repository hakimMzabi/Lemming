package lemming;

import java.util.List;
import java.util.Random;

import block.Block;
import block.Block.Type;
import block.Destructible;
import block.Espace;
import block.Exploseur;
import block.Generateur;
import block.Indestructible;
import block.Lave;
import block.Sortie;
import block.Teleporteur;
import controller.GameObserver;
import model.Change;
import model.Coordinate;
import model.Direction;
import model.Game;
import model.Observable;
import block.Teleporteur;

public class Lemming extends Observable implements GameObserver {

	public static enum LemmingType {
		BLOQUEUR, TUNNELIER, FOREUR, BOMBEUR, CHARPENTIER, GRIMPEUR, PARACHUTISTE, LEMMING
	}

	private Direction direction;
	private int hauteur;
	private Coordinate body;
	private boolean isAlive;
	private boolean isArrived;
	private boolean cndTochange;
	private Game game;
	private int lemmingAction;
	private StateLemming state;
	private int lid;
	private LemmingType type;
	private int pas = 0;

	public Lemming(Game game) throws Exception {

		state = new Marcheur(this);

		cndTochange = true;
		this.game = game;
		int x = game.getStartBlockCoor().getX();
		int y = game.getStartBlockCoor().getY();
		this.isArrived = false;
		this.isAlive = true;

		this.body = new Coordinate(x, y);
		this.direction = Direction.Right;
		registerObserver(game);
		this.type = Lemming.LemmingType.LEMMING;
		addChanges(new Change(this.body, Change.ChangeType.LEMMING));
		notifyObserver();
	}

	public Coordinate getBody() {
		return body;
	}

	public LemmingType getLemmingType() {
		return type;
	}

	public void setLemmingType(LemmingType type2) {
		this.type = type2;
	}

	public void setBody(Coordinate body) {
		this.body = body;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction dir) {
		this.direction = dir;
	}

	public int getHauteur() {
		return hauteur;
	}

	public void setHauteur(int h) {
		this.hauteur = h;
	}

	public void setIsArrived(boolean st) {
		this.isArrived = st;
	}

	public void cmpDePas() {
		pas++;
	}

	public int getNbPas() {
		return pas;
	}

	public boolean isCndTochange() {
		return cndTochange;
	}

	public void changeStateTo(StateLemming st) {
		state = st;
	}

	public int getId() {
		return lid;
	}

	public void notifyObsever() {
		notifyObserver();
	}

	public void move() {
		state.move();

		notifyObserver();

	}

	public void setCndTochange(boolean cndTochange) {
		this.cndTochange = cndTochange;
	}

	public boolean ifArrived() {
		return isArrived;
	}

	public Game getGame() {
		return game;
	}

	public boolean ifAlive() {
		return isAlive;
	}

	public void die() {
		isAlive = false;

	}

	public void addChange(Coordinate c, Change.ChangeType type) {
		addChanges(new Change(c, type));
	}

	public boolean lavetst(Coordinate c) {
		if (game.getBlockList().get(c.getY() * game.getWidth() + c.getX() + c.getY()).getClass().equals(Lave.class))
			return true;
		return false;
	}

	public boolean espacetst(Coordinate c) {

		if (game.getBlockList().get(c.getY() * game.getWidth() + c.getX() + c.getY()).getClass().equals(Espace.class))
			return true;

		return false;
	}

	public boolean testDestructible(Coordinate c) {
		Destructible b = new Destructible(c.getX(), c.getY(), game, Type.DESTRUCTIBLE);
		Exploseur d = new Exploseur(c.getX(), c.getY(), game, Type.EXPLOSEUR);
		if (game.getBlockList().get(c.getY() * game.getWidth() + c.getX() + c.getY()).getClass().equals(b.getClass())
				|| game.getBlockList().get(c.getY() * game.getWidth() + c.getX() + c.getY()).getClass()
						.equals(d.getClass()))
			return true;
		return false;
	}

	public void activeBomber() {
		Destructible d = new Destructible(body.getX(), body.getY(), game, Type.DESTRUCTIBLE);
		List<Coordinate> around = d.getAround();

		for (int i = 0; i < around.size(); i++) {
			if (testDestructible(around.get(i)))
				destruction(around.get(i));
		}

	}

	public void destruction(Coordinate c) {
		Espace a = new Espace(c.getX(), c.getY(), game, Block.Type.ESPACE);
		game.getBlockList().set(c.getY() * game.getWidth() + c.getX() + c.getY(), a);
		addChanges(new Change(c, Change.ChangeType.ESPACE));
	}

	public void escalier(Coordinate c) {
		Destructible escalier = new Destructible(c.getX(), c.getY(), game, Type.DESTRUCTIBLE);
		game.getBlockList().set(c.getY() * game.getWidth() + c.getX() + c.getY(), escalier);
		addChanges(new Change(c, Change.ChangeType.DESTRUCTIBLE));

	}

	public void setType(LemmingType type) {
		this.type = type;
	}

	@Override
	public void update(List<Change> o) {

		for (int i = 0; i < o.size(); i++) {
			addChanges(o.get(i));
		}
		notifyObserver();

	}

	public Coordinate oppositeDirection(Direction d, Coordinate last) {
		if (direction == Direction.Right)
			direction = Direction.Left;
		else
			direction = Direction.Right;
		Coordinate next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
		return next;
	}

	public boolean tstBloqueur(Coordinate c) {
		for (Lemming l : game.getLemmingList()) {
			if (l.getBody().equals(c) && l.state.getClass().equals(Bloqueur.class)) {
				return true;
			}
		}
		return false;

	}

	public void destructionDown(Coordinate c) {
		Espace a = new Espace(c.getX(), c.getY(), game, Block.Type.ESPACE);
		game.getBlockList().set(c.getY() * game.getWidth() + c.getX() + c.getY(), a);
		addChanges(new Change(c, Change.ChangeType.ESPACE));

	}

	public boolean testExploseur(Coordinate c) {
		if (game.getBlockList().get(c.getY() * game.getWidth() + c.getX() + c.getY()).getClass()
				.equals(Exploseur.class))
			return true;
		return false;
	}

	public void activeExploseur(Coordinate c) {
		Exploseur ex = (Exploseur) game.getBlockList().get(c.getY() * 30 + c.getX() + c.getY());
		Espace vide = new Espace(c.getX(), c.getY(), game, Type.ESPACE);
		game.getBlockList().set(c.getY() * game.getWidth() + c.getX() + c.getY(), vide);
		addChange(c, Change.ChangeType.ESPACE);
		for (int i = 0; i < ex.getAround().size(); i++) {
			for (Lemming l : game.getLemmingList()) {
				if (l.inAround(ex.getAround().get(i)))
					l.die();
			}
		}
	}

	public boolean inAround(Coordinate c) {
		if (body.equals(c))
			return true;
		return false;
	}

	public boolean testGenerateur(Coordinate c) {
		if (game.getBlockList().get(c.getY() * game.getWidth() + c.getX() + c.getY()).getClass()
				.equals(Generateur.class))
			return true;
		return false;
	}

	public void activeGenerateur(Coordinate c) {
		Generateur gen = (Generateur) game.getBlockList().get(c.getY() * 30 + c.getX() + c.getY());
		Espace vide = new Espace(c.getX(), c.getY(), game, Type.ESPACE);
		game.getBlockList().set(c.getY() * game.getWidth() + c.getX() + c.getY(), vide);
		addChange(c, Change.ChangeType.ESPACE);

		Indestructible a = null;
		for (Block b : game.getBlockList()) {
			if (b.getClass() == Sortie.class)
				a = (Sortie) b;
		}
		Destructible b = new Destructible(a.getBlockCoordinate().getX() - 1, a.getBlockCoordinate().getY(), game,Type.DESTRUCTIBLE);
		
		game.getBlockList().set(b.getBlockCoordinate().getY() * game.getWidth() + b.getBlockCoordinate().getX()+ b.getBlockCoordinate().getY(), b);
		
		Destructible s = new Destructible(game.getStartBlockCoor().getX() + 1, game.getStartBlockCoor().getY(), game,Type.DESTRUCTIBLE);
		
		game.getBlockList().set(s.getBlockCoordinate().getY() * game.getWidth() + s.getBlockCoordinate().getX()+ s.getBlockCoordinate().getY(), s);
	}

	public boolean testTelporteur(Coordinate c) {
		if (game.getBlockList().get(c.getY() * game.getWidth() + c.getX() + c.getY()).getClass()
				.equals(Teleporteur.class))
			return true;
		else
			return false;
	}

	public Coordinate teleportation(Coordinate c) {

		Coordinate c1 = game.getBlockList().get(20).getBlockCoordinate();
		return c1;
	}
}
