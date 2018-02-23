package model;

import java.util.ArrayList;

import java.util.List;
import controller.GameObserver;



public abstract class Observable {
	
	private ArrayList<Change> changes = new ArrayList<>();
	private ArrayList<GameObserver> observers = new ArrayList<>();	
	
	protected void addChanges(Change c) {
		changes.add(c);
	}
	
	public void registerObserver(GameObserver ob) {
		observers.add(ob);
	}
	
	public void unregisterObserver(GameObserver ob) {
		observers.remove(ob);
	}
	
	public List<Change> getChanges() {
		
		return changes;
	}
	
	public void  notifyObserver() {
		List<Change> ch= getChanges();
		for(GameObserver obs : observers){
			obs.update(ch);
		}
	}
	
	
	
}
