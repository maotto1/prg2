package gamefield;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fieldState.Response;
import model.MyGameField;
import model.OwnField;
import model.Ship;
import model.Shot;

public class ShipTest {

	Ship ship; 
	
	@Before
	public void setUp() throws Exception {
		ArrayList<OwnField> list = new ArrayList<OwnField>();
		list.add(new OwnField(3, 2));
		list.add(new OwnField(4, 2));
		ship = new Ship(2, 11111, new MyGameField(), list);
	}

	@Test
	public void treatShotTest() {
		assertEquals(Response.HIT, ship.treatShot(new Shot(4,2)));
		assertEquals(Response.HIT_AND_SUNK, ship.treatShot(new Shot(3,2)));
	}

}
