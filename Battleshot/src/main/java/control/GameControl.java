package control;

import java.util.ArrayList;
import fieldState.Response;
import fieldState.SetState;
import gui.GameScreen;
import model.AdversaryGameField;
import model.GameField;
import model.MyGameField;
import model.Shot;

public class GameControl {
	
	private final MyGameField myGameField;
	private final AdversaryGameField adversaryGameField;
	public final static int[] ALLOWED_SHIPS = new int[] {0,0,4,3,2,1}; 
	private int[] settedShips = new int[ALLOWED_SHIPS.length];
	private int lastUsedId;
	private boolean myTurn = true;
	private GameScreen gameScreen;
	
	public GameControl() {
		myGameField = new MyGameField();
		adversaryGameField = new AdversaryGameField();
	}
	
	public void initialize() {
		lastUsedId = 0;
		for (int i=0; i< settedShips.length; i++)
			settedShips[i] = 0;
	}
	
	/**
	 * checkt, ob ein Schiff der behaupteten Länge (wird nicht verifiziert) noch eingefügt werden kann
	 * prüft, ob Platz dafür auf Spielfeld vorhanden. 
	 */
	public boolean canThereBeAShipOfLength(int x0, int y0, int x1, int y1, int length) {
		if (length >= settedShips.length || length <= 0) 
			return false;
		if (settedShips[length] == ALLOWED_SHIPS[length])
			return false;
		return myGameField.fitShip(x0, y0, x1, y1);
	}
	
	public boolean canThereBeAShip(int x0, int y0, int x1, int y1, int minLength) {
		int directionX = 0;
		int directionY = 0;
		if (minLength == 1 && myGameField.getFieldStates().get(x0).get(y0) != SetState.SHIP  &&  myGameField.getFieldStates().get(x0).get(y0) != SetState.BLOCKED ) {
			// Abkürzung: verzichte auf Prüfung, ob a) mindestens 2er Shiff reinpasst und b) ein solches noch zu vergeben
			return true;
		}
		else if (minLength > 1) {	
			if (x0 == x1) {
				if (y0 > y1)
					directionY = -1;
				else if (y0 < y1)
					directionY = 1;
			}
			else if (y0 == y1) {
				if (x0 > x1)
					directionX = -1;
				else if (x0 < x1)
					directionX = 1;
			}
		}
		for (int length = minLength; length< ALLOWED_SHIPS.length; length++ ) {
			if (canThereBeAShipOfLength(x0, y0, x1, y1,length))
				return true;
			x1 += directionX;
			y1 += directionY;
			if (x1<0 || y1 <0 || x1>= GameField.FIELD_SIZE[0] || y1>= GameField.FIELD_SIZE[0])
				return false;	
		}
		return false ; 
	}


	public boolean isThereAShip(int x, int y) {
		if (myGameField.getFieldStates().get(x).get(y) == SetState.SHIP ) {
			return true;
		}
		return false;
	}

	public void removeShip(int x, int y) {
		int shipId = myGameField.findShipId(x, y);
		settedShips[myGameField.lengthOfShip(shipId)] -=1;
		myGameField.removeShip(shipId);
		
	}

	public void createShip(int startX, int startY, int lastX, int lastY, int length) {
		lastUsedId +=1;
		if (myGameField.setShip(startX, startY, lastX, lastY, lastUsedId, length))
			System.out.println("Schiff von "+ startX +" "+ startY +" nach "+ lastX + " "+ lastY + " wurde gesetzt." );
		settedShips[length] +=1;
		
	}
	
	public ArrayList<ArrayList<SetState>> getSetStates(){
		return myGameField.getFieldStates();
	}
	
	/**
	 * 
	 * @param size must be smaller than 6
	 * @return
	 */
	public int getNumberOfSettedShipsOfSize(int size) {
		return settedShips[size];
	}

	public void fire(Shot shot) {
		if (myTurn) {
			adversaryGameField.treatShot(shot);
			// to do : Network 
			// testweise Annahme: antwort ist wasser
			Response response = Response.WATER;
			adversaryGameField.treatReponse(response);
			gameScreen.refreshAdversaryField(adversaryGameField.getFieldStates());
			
			// if (response == Response.HIT_AND_SUNK_LOOSED)   Spiel beenden
		}
		
	}
	
	
	public void startMatch() {
		gameScreen = new GameScreen(this);
		gameScreen.refreshMyField(myGameField.getFieldStates());
		gameScreen.setVisible(true);
		gameScreen.setTitle("Match");
		
	}

}
