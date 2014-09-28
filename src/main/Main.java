package main;

public class Main {
	private final static Integer personCount = 3;
	private final static Integer[] scoreSequence = {
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

	public static void main(String[] args) {
		new GameRunner(personCount, scoreSequence);
	}
}