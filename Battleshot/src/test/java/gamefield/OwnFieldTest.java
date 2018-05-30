package gamefield;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fieldState.KnownFieldState;
import fieldState.SetState;
import model.OwnField;

public class OwnFieldTest {

	OwnField f;
	
	@Before
	public void setUp() throws Exception {
		f = new OwnField(2,24);
	}

	@Test
	public void getXCoordinateTest() {
		assertEquals(2, f.getXCoordinate());
	}
	
	@Test
	public void getStateTest() {
		OwnField g = new OwnField(2,24);
		assertEquals(SetState.WATER, g.getState());
	}
	
	@Test
	public void getYCoordinate() {
		assertEquals(4, f.getYCoordinate());	
	}
	
	@Test
	public void getIdTest() {
		OwnField g = new OwnField(2,24);
		assertEquals(-1, g.getId());
	}
	
	@Test
	public void changeStateTest() {
		assertFalse(f.changeState(SetState.SHIP, -1));
		assertFalse(f.changeState(SetState.SHIP));
		assertTrue(f.changeState(SetState.BLOCKED, 123));
		assertTrue(f.changeState(SetState.SHIP, 123));
		assertTrue(f.changeState(SetState.WATER, 123));
		assertEquals(-1, f.getId());
	}

}
