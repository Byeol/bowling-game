package main;

import main.model.Game;

public class Main {
	private final static Integer personCount = 3;
	private final static Integer[] scoreSequence = {
		1, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2};

	public static void main(String[] args) {
		new Game(personCount, scoreSequence);
	}

}