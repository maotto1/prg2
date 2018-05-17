package game;

import gamefield.GameField;

public class Shot {
	
	private final int x,y;
	
	Shot(int x, int y){
		x = Math.abs(x);
		y = Math.abs(y);
		if (x >= GameField.FIELD_SIZE[0] || y >= GameField.FIELD_SIZE[1]) {
			x = x % GameField.FIELD_SIZE[0];
			y = y % GameField.FIELD_SIZE[1];
		}
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
