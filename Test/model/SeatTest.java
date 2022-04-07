package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import exception.OccupiedSeatException;

public class SeatTest {
	
	Seat test;
	Spectator sp;
	
	public void setupStage1() throws OccupiedSeatException {
		test = new Seat();
		sp = new Spectator("Juan", "1234");
		test.setSpectator(sp);
	}
	
	public void setupStage2() {
		test = new Seat();
	}
	
	@Test
	void test1() {
		try {
			setupStage1();
			sp = new Spectator("Pedro","1524");
			test.setSpectator(sp);
			fail();
		} catch (OccupiedSeatException e) {
			assertEquals("La silla esta ocupada",e.getMessage());
			assertNotEquals(sp, test.getSpectator());
		}
	}
	
	@Test
	void test2() {
		
		setupStage2();
		sp= new Spectator("Pepito","456");
		try {
			test.setSpectator(sp);
		} catch (OccupiedSeatException e) {
			fail();
		}
		
	}

}
