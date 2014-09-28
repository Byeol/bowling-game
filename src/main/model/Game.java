package main.model;

import main.exception.AlreadyLastFallException;
import main.exception.PinCountRangeException;

public class Game {
	private final ScoreBoard scoreBoard;

	public Game(Integer personCount, Integer[] scoreSequence) {
		scoreBoard = new ScoreBoard(personCount);

		for(Integer score : scoreSequence) {
			try {
				scoreBoard.roll(score);
			} catch (AlreadyLastFallException e) {
				e.printStackTrace();
			} catch (PinCountRangeException e) {
				e.printStackTrace();
			}
		}
	}
}
