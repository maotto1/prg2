package game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShotTest {

	@Test
	public void getXTest() {
		Shot s = new Shot(5, 5);
		assertEquals(5, s.getX());
	}
	
	@Test
	
	public void getYTest() {
		Shot s = new Shot(5, 15);
		assertEquals(5, s.getY());
	}
}
