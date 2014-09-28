package main.model.actual;

import main.model.PinCount;
import main.model.Rollable;
import main.util.IConstants;

public class Game implements Rollable {

	private final Person[] personList;
	private final Integer personCount;
	private Integer currentPersonIdx;
	private final String scoreTitle;

	public Game(Integer playerCount) {
		personCount = playerCount;
		personList = new Person[personCount];

		for (int i = 0; i < personList.length; i++) {
			String personName = Integer.toString(i + 1);
			personList[i] = new Person(personName);
		}

		currentPersonIdx = 0;
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < IConstants.FRAME_PER_PERSON; i++) {
			sb.append(IConstants.SCORE_TABLE_TEMPLATE);
		}
		sb.append(IConstants.SCORE_TABLE_FINAL_TEMPLATE);
		sb.append(IConstants.SCORE_TABLE_TEMPLATE);
		
		for (int i = 1; i <= IConstants.FRAME_PER_PERSON; i++) {
			String title = Integer.toString(i);
			Integer curPosition = i * (IConstants.SCORE_TABLE_SPACE + 2) + title.length() + 2;
			sb.replace(curPosition, curPosition + title.length(), title);
		}
		scoreTitle = sb.toString();
	}

	public void roll(PinCount knockOverCount) throws Exception {
		Person currentPerson = personList[currentPersonIdx];
		currentPerson.roll(knockOverCount);
		if(currentPerson.isFrameFinished()) {
			moveNextPerson();
		}
		printAllScore();
	}

	public boolean canRoll() {
		return personList[personCount - 1].canRoll();
	}

	public Integer getTotalScore() {
		Integer totalScore = 0;

		for(Person person : personList) {
			totalScore += person.getTotalScore();
		}

		return totalScore;
	}

	public void printAllScore() {
		System.out.println();

		System.out.println(scoreTitle);
		for(Person person : personList) {
			person.printScore();
		}
		System.out.println("TOTAL: " + getTotalScore());
	}

	private void moveNextPerson() {
		if(currentPersonIdx == personCount - 1) {
			currentPersonIdx = -1;
		}

		currentPersonIdx++;
	}
}
