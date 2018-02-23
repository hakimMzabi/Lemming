package model;


public class Change {

	public static enum ChangeType {
		SORTIE, ENTREE , DESTRUCTIBLE , INDESTRUCTIBLE , BLOQUEUR , TUNNELIER , FOREUR , BOMBEUR , CHARPENTIER , GRIMPEUR ,PARACHUTISTE , LEMMING
		,LAVE , TELEPORTEUR , ESPACE, EXPLOSEUR, GENERATEUR, TELEPORTEUR2 
	}

	private Coordinate coordinate;
	private ChangeType changeType;

	public Change (Coordinate coordinate, ChangeType changeType) {
		
		this.coordinate = coordinate;
		this.changeType = changeType;
	}

	public ChangeType getChangeType() {
		return changeType;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}
}
