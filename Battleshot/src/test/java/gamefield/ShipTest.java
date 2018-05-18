package gamefield;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fieldState.Response;
import game.Shot;

public class ShipTest {

	Ship ship; 
	
	@Before
	public void setUp() throws Exception {
		ship = new Ship(2, 11111, new MyGameField(), new OwnField[] {new OwnField(3, 2) , new OwnField(4, 2)});
	}

	@Test
	public void treatShotTest() {
		assertEquals(Response.HIT, ship.treatShot(new Shot(4,2)));
		assertEquals(Response.HIT_AND_SUNK, ship.treatShot(new Shot(3,2)));
	}

}
