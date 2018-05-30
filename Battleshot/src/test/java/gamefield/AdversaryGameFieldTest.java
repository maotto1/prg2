package gamefield;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fieldState.KnownFieldState;
import fieldState.Response;
import model.AdversaryField;
import model.AdversaryGameField;
import model.Field;
import model.Shot;

public class AdversaryGameFieldTest {
	AdversaryGameField f;

	@Before
	public void setUp() throws Exception {
		f = new AdversaryGameField();
		f.treatShot(new Shot(2,2));
	}
	
	@Test
	public void getSunkedShipsTest() {
		assertEquals(0, f.getSunkedShips());
	}

	@Test
	public void treatResponseTest() {
		int sunked = f.getSunkedShips();
		f.treatReponse(Response.HIT_AND_SUNK);
		f.treatReponse(Response.HIT_AND_SUNK_LOOSED);
		assertEquals(sunked+2, f.getSunkedShips());
	}
	
	@Test
	public void getNeighbourFieldsTest() {
		AdversaryField field = new AdversaryField(0,0);
		ArrayList<Field<KnownFieldState>> n =   f.getNeighbours(field);
		assertTrue(n.size() == 2);
		assertArrayEquals(new int[] {0,1}, new int[] {n.get(0).getXCoordinate(), n.get(1).getXCoordinate()} );
		assertArrayEquals(new int[] {1,0}, new int[] {n.get(0).getYCoordinate(), n.get(1).getYCoordinate()} );
	}
}
