package test;

import main.GameRunner;

import org.junit.Test;

public class ExampleTest {

	@Test
	public void testStrike() {
		Integer personCount = 1;
		Integer[] scoreSequence = {
			10, 1, 2
		};
		new GameRunner(personCount, scoreSequence);
		
	}

	@Test
	public void testSpare() {
		Integer personCount = 1;
		Integer[] scoreSequence = {
			10, 9, 1, 2, 3
		};
		new GameRunner(personCount, scoreSequence);
		
	}

	@Test
	public void testDoubleStrike() {
		Integer personCount = 1;
		Integer[] scoreSequence = {
			10, 10, 1, 2, 3
		};
		new GameRunner(personCount, scoreSequence);
		
	}

	@Test
	public void testPerfectGame() {
		Integer personCount = 3;
		Integer[] scoreSequence = {
			10, 10, 10,	
			10, 10, 10,	
			10, 10, 10,	
			10, 10, 10,	
			10, 10, 10,	
			10, 10, 10,	
			10, 10, 10,	
			10, 10, 10,	
			10, 10, 10,	
			10, 10, 10, 10, 10, 10, 10, 10, 10};
		new GameRunner(personCount, scoreSequence);
	}

	@Test
	public void testTenthExtraBall() {
		Integer personCount = 3;
		Integer[] scoreSequence = {
				10, 10, 10,	
				10, 10, 10,	
				10, 10, 10,	
				10, 10, 10,	
				10, 10, 10,	
				10, 10, 10,	
				10, 10, 10,	
				10, 10, 10,	
				10, 10, 10,	
				9, 1, 9, 3, 7, 6, 10, 10, 10};
		new GameRunner(personCount, scoreSequence);
	}
	
	@Test
	public void testGeneralCase() {
		Integer personCount = 3;
		Integer[] scoreSequence = {
			9, 1, 8, 2, 6, 1,	// FRAME 1
			0, 10, 10, 5, 1,	// FRAME 2
			10, 3, 7, 9, 0,		// FRAME 3
			10, 7, 3, 3, 6,		// FRAME 4
			0, 5, 8, 2, 3, 3,	// FRAME 5
			6, 0, 10, 10,		// FRAME 6
			0, 8, 7, 2, 7, 0,	// FRAME 7
			8, 0, 10, 0, 9,		// FRAME 8
			10, 3, 0, 10,		// FRAME 9
			3, 6, 6, 0, 1, 6};	// FRAME 10*/
		new GameRunner(personCount, scoreSequence);
	}
}
