package main.model.actual;

import main.exception.UnexpectedTypeException;
import main.model.PinCount;
import main.model.actual.Frame.ScoreStatus;
import main.model.actual.Frame.ScoreType;
import main.util.IConstants;
import main.util.ScoreUtil;

public class Score {

	private final StringBuffer scoreLine[];
	private Integer[] scoreSum;
	private Integer totalScore;

	Score() {
		scoreLine = ScoreUtil.createScoreTemplate();

		scoreSum = new Integer[IConstants.FRAME_PER_PERSON];
		for (int i = 0; i < scoreSum.length; i++) {
			scoreSum[i] = 0;
		}
		
		totalScore = 0;
	}

	void setName(String name) {
		scoreLine[0].replace(1, 1 + name.length(), name);
	}

	private void setScore(Integer frameIdx, ScoreType scoreType, PinCount score) throws Exception {
		Integer targetPos = ScoreUtil.getLinePosition(frameIdx, scoreType);

		char scoreChar = ScoreUtil.convertScoreToChar(score.intValue());
		scoreLine[0].setCharAt(targetPos, scoreChar);
	}

	private void setScoreSum(Integer frameIdx, Integer score) {
		Integer targetPos = ScoreUtil.getLinePosition(frameIdx, ScoreType.TOTAL);
		String scoreString = score.toString();
		targetPos -= scoreString.length();

		if(frameIdx >= IConstants.FRAME_PER_PERSON - 1) {
			targetPos += 3;
		}

		scoreLine[1].replace(targetPos, targetPos + scoreString.length(), scoreString);
	}

	private void setSpare(Integer frameIdx) {
		Integer targetPos = ScoreUtil.getLinePosition(frameIdx, ScoreType.SECOND);
		scoreLine[0].setCharAt(targetPos, IConstants.SPARE_LABEL);
	}

	void setScore(Integer frameIdx, ScoreType scoreType, Frame frame) throws Exception {
		Integer score = frame.getScore(scoreType);
		if(score != null) {
			PinCount pinCount = new PinCount(score);
			setScore(frameIdx, scoreType, pinCount);
		}
	}

	void refreshScore(Frame frame, Integer frameIdx) throws Exception {
		setScore(frameIdx, ScoreType.FIRST, frame);
		setScore(frameIdx, ScoreType.SECOND, frame);

		if (frame instanceof FinalFrame) {
			setScore(frameIdx, ScoreType.THIRD, frame);
		}

		if(ScoreUtil.checkSpare(frame)) {
			setSpare(frameIdx);
		}

		refreshScoreSum(frame, frameIdx);
	}

	void refreshScoreSum(Frame frame, Integer frameIdx) throws UnexpectedTypeException {
		if(frame.getScoreStatus() != ScoreStatus.FINAL) {
			return;
		}

		Integer previousScore = 0;
		if(frameIdx > 0) {
			previousScore = scoreSum[frameIdx - 1];
		}

		scoreSum[frameIdx] = previousScore + frame.getScore(ScoreType.TOTAL);
		if(totalScore < scoreSum[frameIdx]) {
			totalScore = scoreSum[frameIdx];
		}
		setScoreSum(frameIdx, scoreSum[frameIdx]);
	}

	void refreshTotalScore(Integer frameIdx) {
		if(scoreSum[IConstants.FRAME_PER_PERSON - 1] != 0) {
			setScoreSum(IConstants.FRAME_PER_PERSON, scoreSum[IConstants.FRAME_PER_PERSON - 1]);
		}
	}

	void printScore() {
		System.out.println(scoreLine[0].toString());
		System.out.println(scoreLine[1].toString());
	}

	Integer getTotalScore() {
		return totalScore;
	}
}
