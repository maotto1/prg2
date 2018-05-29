package control;

import gamefield.AdversaryGameField;
import gamefield.MyGameField;
import gamefield.Ship;

public class GameControl {
	
	private final MyGameField myGameField;
	private final AdversaryGameField adversaryGameField;
	public final static int[] ALLOWED_SHIPS = new int[] {0,0,4,3,2,1}; 
	private int[] settedShips = new int[ALLOWED_SHIPS.length];
	private int lastUsedId;
	
	public GameControl() {
		myGameField = new MyGameField();
		adversaryGameField = new AdversaryGameField();
	}
	
	public void initialize() {
		lastUsedId = 0;
		for (int i=0; i< settedShips.length; i++)
			settedShips[i] = 0;
	}
	
	public boolean canThereBeAShip(int x0, int y0, int x1, int y1, int length) {
		if (settedShips.length > length || length <= 0 || settedShips[length] == ALLOWED_SHIPS[length])
			return false;
		return myGameField.fitShip(x0, y0, x1, y1);
	}

	public boolean isThereAShip(int startX, int startY) {
		// TODO Automatisch generierter Methodenstub
		return false;
	}

	public void removeShip(int x, int y) {
		int shipId = myGameField.findShipId(x, y);
		settedShips[myGameField.lengthOfShip(shipId)] -=1;
		myGameField.removeShip(shipId);
		
	}

	public void createShip(int startX, int startY, int lastX, int lastY, int length) {
		lastUsedId +=1;
		myGameField.setShip(new Ship(length, lastUsedId, myGameField, null), startX, startY, lastX, lastY, lastUsedId);
		settedShips[length] +=1;
		
	}

}
