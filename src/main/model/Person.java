package main.model;

import main.exception.AlreadyFallTwiceException;
import main.exception.AlreadyLastFallException;
import main.exception.AlreadyLastFrameException;
import main.exception.PinCountRangeException;
import main.model.Frame.FrameStatus;
import main.model.Frame.ScoreStatus;
import main.model.Frame.ScoreType;
import main.util.IConstants;

public class Person {

	public enum PersonStatus {
		CAN_FALL, NEXT_TURN, FIN_GAME;
	};

	private final Frame[] frameList;
	private Integer currentFrameIdx;
	private StringBuffer scoreLine[];
	private Integer[] totalScore;

	Person() {
		frameList = new Frame[IConstants.FRAME_PER_PERSON];
		for (int i = 0; i < frameList.length; i++) {
			frameList[i] = new Frame();
		}

		currentFrameIdx = 0;

		scoreLine = new StringBuffer[2];
		for (int i = 0; i < scoreLine.length; i++) {
			scoreLine[i] = new StringBuffer();
		}
		
		for (int i = 0; i <= IConstants.FRAME_PER_PERSON; i++) {
			scoreLine[0].append("[ ][ ]");
			scoreLine[1].append("[    ]");
		}
		
		totalScore = new Integer[IConstants.FRAME_PER_PERSON];
		for (int i = 0; i < totalScore.length; i++) {
			totalScore[i] = 0;
		}
	}

	public void setScore(Integer frameIdx, ScoreType scoreType, Integer score) throws Exception {
		Integer targetPos = 0;
		targetPos += frameIdx * 6;
		
		if(scoreType == ScoreType.FIRST) {
			targetPos += 1;
		} else if(scoreType == ScoreType.SECOND) {
			targetPos += 4;
		} else {
			throw new Exception();
		}

		char scoreChar;
		scoreChar = Character.forDigit(score, 10);
		if(score == 0) {
			scoreChar = IConstants.ZERO_LABEL;
		} else if(score == 10) {
			scoreChar = IConstants.STRIKE_LABEL;
		}

		scoreLine[0].setCharAt(targetPos, scoreChar);
	}

	public void setTotalScore(Integer frameIdx, Integer score) {
		Integer targetPos = 0;
		targetPos += frameIdx * 6;

		targetPos += 3;

		char scoreChar;
		scoreChar = Character.forDigit(score, 10);

		scoreLine[1].setCharAt(targetPos, scoreChar);
	}

	public void printScore() {
		System.out.println(scoreLine[0].toString());
		System.out.println(scoreLine[1].toString());
	}

	public PersonStatus roll(Integer knockOverPinCount) throws AlreadyLastFallException, PinCountRangeException {
		Frame currentFrame = frameList[currentFrameIdx];

		if(currentFrame.getStatus() == FrameStatus.ROLL_TWICE) {
			try {
				moveNextFrame();
				currentFrame = frameList[currentFrameIdx];
			} catch (AlreadyLastFrameException e) {
				throw new AlreadyLastFallException();
			}
		}

		// System.out.println("frame: " + currentFrameIdx);
		try {
			currentFrame.roll(knockOverPinCount);
			if(currentFrame.getStatus() == FrameStatus.ROLL_ONCE) {
				setScore(currentFrameIdx, ScoreType.FIRST, currentFrame.getScore(ScoreType.FIRST));
			} else {
				setScore(currentFrameIdx, ScoreType.SECOND, currentFrame.getScore(ScoreType.SECOND));
				refreshTotalScore(currentFrameIdx);
			}
			checkPreviousFrame(knockOverPinCount);
		} catch (AlreadyFallTwiceException e) {
			throw new AlreadyLastFallException();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(currentFrame.getStatus() == FrameStatus.ROLL_TWICE) {
			return PersonStatus.NEXT_TURN;
		}

		return PersonStatus.CAN_FALL;
	}

	private void refreshTotalScore(Integer frameIdx) {
		Frame frame = frameList[frameIdx];
		if(frame.getScoreStatus() == ScoreStatus.FINAL) {
			totalScore[frameIdx] = totalScore[frameIdx - 1] + frame.getScore(ScoreType.TOTAL);
			setTotalScore(frameIdx, totalScore[frameIdx]);
		}
	}

	private void checkPreviousFrame(Integer knockOverPinCount) {
		if(currentFrameIdx - 1 < 0) {
			return;
		}
		Integer previousFrameIdx = currentFrameIdx - 1;
		Frame previousFrame = frameList[previousFrameIdx];

		previousFrame.plusBonusScore(knockOverPinCount);
		setTotalScore(previousFrameIdx, previousFrame.getScore(ScoreType.TOTAL));
		refreshTotalScore(previousFrameIdx);

		if(currentFrameIdx - 2 < 0) {
			return;
		}
		previousFrameIdx = currentFrameIdx - 2;
		previousFrame = frameList[previousFrameIdx];

		previousFrame.plusBonusScore(knockOverPinCount);
		setTotalScore(previousFrameIdx, previousFrame.getScore(ScoreType.TOTAL));
		refreshTotalScore(previousFrameIdx);
	}

	private void moveNextFrame() throws AlreadyLastFrameException {
		if(currentFrameIdx >= IConstants.FRAME_PER_PERSON - 1) {
			throw new AlreadyLastFrameException();
		}

		currentFrameIdx++;
	}
	
	public PersonStatus getStatus() {
		if(currentFrameIdx == IConstants.FRAME_PER_PERSON) {
			Frame currentFrame = frameList[currentFrameIdx];

			if(currentFrame.getStatus() == FrameStatus.ROLL_TWICE) {
				return PersonStatus.FIN_GAME;
			}
		}
		
		return PersonStatus.CAN_FALL;
	}
}
