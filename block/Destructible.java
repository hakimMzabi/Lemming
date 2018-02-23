package block;


import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.Game;

public class Destructible extends Block {
	public Destructible(int x, int y, Game g,Type type2) {
		super(x, y, g, type2);
		
		// TODO Auto-generated constructor stub
	}


	public List<Coordinate> getAround(){	//get around  2 case
		List<Coordinate> around =new ArrayList<Coordinate>();
		for(int i=-2;i<2;i++){
			for(int j=-2 ;j<2 ;j++){
				if(cood.getX()+i >0 && cood.getY()+j>0)
					around.add( new Coordinate(cood.getX()+i, cood.getY()+j) );
			}
		}
		return around;
	}
	
	
	

}
