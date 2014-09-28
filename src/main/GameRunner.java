package main;

import main.model.PinCount;
import main.model.actual.Game;

public class GameRunner {
	private final Game bowlingGame;

	public GameRunner(Integer personCount, Integer[] scoreSequence) {
		bowlingGame = new Game(personCount);

		PinCount pinCount;
		for(Integer score : scoreSequence) {
			try {
				pinCount = new PinCount(score);
				bowlingGame.roll(pinCount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
