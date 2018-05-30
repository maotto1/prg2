package gamefield;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fieldState.KnownFieldState;
import model.AdversaryField;

public class AdversaryFieldTest {
	
	AdversaryField f;
	
	@Before
	public void setUp() throws Exception {
		f = new AdversaryField(2,24);
	}

	@Test
	public void getXCoordinateTest() {
		assertEquals(2, f.getXCoordinate());
		
	}
	
	@Test
	public void getStateTest() {
		AdversaryField g = new AdversaryField(2,24);
		assertEquals(KnownFieldState.UNKNOWN, g.getState());
		
	}
	
	@Test
	public void getYCoordinate() {
		assertEquals(4, f.getYCoordinate());
		
	}
	
	@Test
	public void changeStateTest() {
		assertTrue(f.changeState(KnownFieldState.WATER, 123));
		assertFalse(f.changeState(KnownFieldState.HIT, 123));
		assertFalse(f.changeState(KnownFieldState.SUNK, 123));
		assertFalse(f.changeState(KnownFieldState.UNKNOWN, 123));
	}

}
