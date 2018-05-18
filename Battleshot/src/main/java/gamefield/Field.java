package gamefield;

import fieldState.State;

public abstract class Field<T extends State> {
	
	protected final int xCoordinate, yCoordinate;
	protected T state;
	
	/**
	 * auto correction of invalid inserts
	 * @param x
	 * @param y
	 */
	Field(int x, int y){
		x = Math.abs(x);
		y = Math.abs(y);
		if (x >= GameField.FIELD_SIZE[0] || y >= GameField.FIELD_SIZE[1]) {
			x = x % GameField.FIELD_SIZE[0];
			y = y % GameField.FIELD_SIZE[1];
		}
		xCoordinate = x; 
		yCoordinate = y;
		state = null;
	}
	
	public int getXCoordinate() {
		return xCoordinate;	
	}
	
	public int getYCoordinate() {
		return yCoordinate;	
	}
	
	public T getState(){
		return this.state;
	}
	
	/**
	 * unvollständig in Bezug auf gegnerisches
	 * @param shipId only necessary if state = Ship
	 * 
	 */
	abstract public boolean changeState(T state, int... shipId);
	
}
