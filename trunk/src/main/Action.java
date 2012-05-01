package main;

public class Action {
	private int integ;
	private int moveX;
	private int moveY;
	
	Action (int x, int y){
		moveX=x;
		moveY=y;
	}

	public int getInt(){
		return this.integ;
	}
}
