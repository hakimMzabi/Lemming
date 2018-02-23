package model;

public enum Direction {
	Left(-1, 0) , Right(1, 0) , Up(0, 1) , Down(0, -1);  
	
	public int x;
	public int y;
	
	Direction (int x,int y){
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
