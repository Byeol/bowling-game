package main.model.actual;

import main.exception.AlreadyLastFrameException;
import main.exception.UnexpectedTypeException;
import main.model.PinCount;
import main.model.Rollable;
import main.util.IConstants;

public class Person implements Rollable {

	private final Frame[] frameList;
	private Integer currentFrameIdx;
	private Score scoreStub;

	Person(String personName) {
		frameList = new Frame[IConstants.FRAME_PER_PERSON];
		for (int i = 0; i < frameList.length; i++) {
			frameList[i] = new Frame();
		}

		frameList[IConstants.FRAME_PER_PERSON - 1] = new FinalFrame();

		currentFrameIdx = 0;
		scoreStub = new Score();
		scoreStub.setName(personName);
	}

	public void roll(PinCount knockOverCount) throws Exception {
		if(isFrameFinished()) {
			moveNextFrame();
		}

		Frame currentFrame = frameList[currentFrameIdx];

		currentFrame.roll(knockOverCount);
		checkPreviousFrame(knockOverCount);
		scoreStub.refreshScore(currentFrame, currentFrameIdx);
		scoreStub.refreshTotalScore(currentFrameIdx);
	}

	private void checkPreviousFrame(PinCount knockOverCount) throws UnexpectedTypeException {
		checkPreviousFrame(currentFrameIdx - 1, knockOverCount);
		checkPreviousFrame(currentFrameIdx - 2, knockOverCount);
	}

	private void checkPreviousFrame(Integer previousFrameIdx, PinCount knockOverCount) throws UnexpectedTypeException {
		if(previousFrameIdx < 0) {
			return;
		}
		Frame previousFrame = frameList[previousFrameIdx];

		previousFrame.plusBonusScore(knockOverCount);
		scoreStub.refreshScoreSum(previousFrame, previousFrameIdx);
	}

	private void moveNextFrame() throws AlreadyLastFrameException {
		if(currentFrameIdx >= IConstants.FRAME_PER_PERSON - 1) {
			throw new AlreadyLastFrameException();
		}

		currentFrameIdx++;
	}

	public boolean canRoll() {
		if(!isFinalFrame()) {
			return true;
		}

		Frame currentFrame = frameList[currentFrameIdx];		
		return currentFrame.canRoll();
	}

	public boolean isFrameFinished() {
		Frame currentFrame = frameList[currentFrameIdx];
		return !currentFrame.canRoll();
	}

	public boolean isFinalFrame() {
		return currentFrameIdx == IConstants.FRAME_PER_PERSON;
	}

	public void printScore() {
		scoreStub.printScore();
	}
	
	public Integer getTotalScore() {
		return scoreStub.getTotalScore();
	}
}
