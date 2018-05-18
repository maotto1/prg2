package de.htw.ai.prg2.Battleshot;


import static org.junit.Assert.assertEquals;

import fieldState.Response;
import game.Shot;
import gamefield.AdversaryField;
import gamefield.AdversaryGameField;
import gamefield.GameField;
import gamefield.MyGameField;
import gamefield.OwnField;
import gamefield.Ship;
import gui.SetShipScreen;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {	
    	int x = 51 % GameField.FIELD_SIZE[0] ;
    	
        //new SetShipScreen();
    	AdversaryGameField f= new AdversaryGameField();
    	f.treatShot(new Shot(2,2));
    	AdversaryField field = new AdversaryField(0,0);
    	f.getNeighbours(field);
    	
    	Ship ship = new Ship(2, 11111, new MyGameField(), new OwnField[] {new OwnField(3, 2) , new OwnField(4, 2)});
    	ship.treatShot(new Shot(4,2));
    	ship.treatShot(new Shot(3,2));
    
    			
    }

        
        
  
}
