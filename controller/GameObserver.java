package controller;

import java.util.List;
import model.Change;


public interface GameObserver {
	
	public void update(List<Change> o);
}
