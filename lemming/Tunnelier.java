package lemming;


import model.Change;
import model.Coordinate;
import model.Observable;

public class Tunnelier extends Observable implements StateLemming {

	private Lemming lemming;
	public Tunnelier (Lemming lemming) { 
		this.lemming = lemming;
	}
	
	@Override
	public void move() {
		Coordinate last = lemming.getBody();
		Coordinate next = new Coordinate(last.getX() + lemming.getDirection().x , last.getY() + lemming.getDirection().y);
		Coordinate lastDown = new Coordinate(last.getX() , last.getY()+1);
		System.out.println("option Tunnelier est activatee");
		if(lemming.getGame().isOut(lastDown) )  // if lastDown is out lemming die
			lemming.die();
		
		else if( lemming.lavetst(lastDown)) {  // if lastDown = lave
			lemming.die();
		}
		else if( lemming.espacetst(lastDown)){ // if lastDown is void
			next = lastDown;
			lemming.setHauteur(lemming.getHauteur() +1);
		} 
		
		if( lemming.lavetst(next) )  // if next = lavE
			lemming.die();
		
		if(lemming.tstBloqueur(next)){
			System.out.println("Succeed"); 
			next=lemming.oppositeDirection(lemming.getDirection(),next);
		
		}	else if(lemming.testTelporteur(next)){  // if lastDown = Teleporter
			next=lemming.teleportation(next);
			
		
		}else if(lemming.espacetst(next)){
			lemming.setBody(next);
		
		}else  if( lemming.testExploseur(next)){
			lemming.activeExploseur(next);
		
		}else  if( lemming.testGenerateur(next)){
			lemming.activeGenerateur(next);
		}
		
		else  if( lemming.testDestructible(next)){
			lemming.destruction(next);
	
		} else if (next.equals(Coordinate.getEndCoordinate(lemming.getGame()))) {// if next = EndBlock

			lemming.setIsArrived(true);
		 
		}else {
			next=lemming.oppositeDirection(lemming.getDirection(),next);
		}
		
		// if AltitudeCounter >= 5 => die
		if( !lastDown.equals(Coordinate.getEndCoordinate(lemming.getGame() )) && lemming.getHauteur() >=5 
			&& !lemming.espacetst(lastDown)) 
			lemming.die();
		if( lemming.ifArrived() && lemming.ifAlive() ){
		
			lemming.addChange(last,Change.ChangeType.ESPACE);
			lemming.addChange(next,Change.ChangeType.LEMMING);
			lemming.setBody(next);
			lemming.changeStateTo(new Marcheur(lemming));
		}
		lemming.notifyObserver();
	}	

}

