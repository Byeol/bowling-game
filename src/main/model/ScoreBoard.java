package main.model;

import main.exception.AlreadyLastFallException;
import main.exception.PinCountRangeException;
import main.model.Person.PersonStatus;

public class ScoreBoard {

	private final Person[] personList;
	private final Integer personCount;
	private Integer currentPersonIdx;

	ScoreBoard(Integer count) {
		personCount = count;
		personList = new Person[count];

		for (int i = 0; i < personList.length; i++) {
			personList[i] = new Person();
		}

		currentPersonIdx = 0;
	}

	public void roll(Integer knockOverPinCount) throws AlreadyLastFallException, PinCountRangeException {
		// System.out.println("roll: " + knockOverPinCount);
		// System.out.println("person: " + currentPersonIdx);
		Person currentPerson = personList[currentPersonIdx];
		if(currentPerson.roll(knockOverPinCount) == PersonStatus.NEXT_TURN) {
			moveNextPerson();
		}
		printAllScore();
	}

	public void printAllScore() {
		System.out.println();
		for(Person person : personList) {
			person.printScore();
		}
	}

	private void moveNextPerson() {
		if(currentPersonIdx == personCount - 1) {
			currentPersonIdx = -1;
		}

		currentPersonIdx++;
	}
}
