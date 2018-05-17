package game;

import gamefield.AdversaryGameField;
import gamefield.MyGameField;

public class Player {
	MyGameField playerGameField;
	AdversaryGameField adversaryField;
	
	public Player() {
		adversaryField = new AdversaryGameField();
		playerGameField = new MyGameField();
	}
	
	public void initialize() {
		
	}
}
