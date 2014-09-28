package main.util;

import main.exception.ScoreNotDigitException;
import main.exception.UnexpectedTypeException;
import main.model.actual.Frame;
import main.model.actual.Frame.ScoreType;

public class ScoreUtil {
	public static StringBuffer[] createScoreTemplate() {
		StringBuffer[] scoreLine = new StringBuffer[2];
		for (int i = 0; i < scoreLine.length; i++) {
			scoreLine[i] = new StringBuffer();
		}

		scoreLine[0].append(IConstants.SCORE_TABLE_TEMPLATE);
		scoreLine[1].append(IConstants.SCORE_TABLE_TEMPLATE);

		for (int i = 0; i < IConstants.FRAME_PER_PERSON - 1; i++) {
			scoreLine[0].append(IConstants.SCORE_TABLE_DIVIDED_TEMPLATE);
			scoreLine[1].append(IConstants.SCORE_TABLE_TEMPLATE);
		}

		scoreLine[0].append(IConstants.SCORE_TABLE_FINAL_DIVIDED_TEMPLATE);
		scoreLine[1].append(IConstants.SCORE_TABLE_FINAL_TEMPLATE);

		scoreLine[0].append(IConstants.SCORE_TABLE_DIVIDED_TEMPLATE);
		scoreLine[1].append(IConstants.SCORE_TABLE_TEMPLATE);

		return scoreLine;
	}

	public static char convertScoreToChar(Integer score) throws ScoreNotDigitException {
		if (score == 0) {
			return IConstants.ZERO_LABEL;
		}

		if (score == 10) {
			return IConstants.STRIKE_LABEL;
		}

		String scoreString = score.toString();
		if(scoreString.length() != 1) {
			throw new ScoreNotDigitException();
		}
		return scoreString.charAt(0);
	}

	public static Integer getLinePosition(Integer frameIdx, ScoreType scoreType) {
		Integer targetPos = IConstants.SCORE_TABLE_SPACE + 2;
		targetPos += frameIdx * (IConstants.SCORE_TABLE_SPACE + 2);

		switch (scoreType) {
		case FIRST:
			targetPos += 1;
			break;
		case SECOND:
			targetPos += 4;
			break;
		case THIRD:
			targetPos += 7;
			break;
		case TOTAL:
			targetPos += 5;
			break;
		}

		return targetPos;
	}

	public static boolean checkSpare(Frame frame) throws UnexpectedTypeException {
		Integer firstScore = frame.getScore(ScoreType.FIRST);
		Integer secondScore = frame.getScore(ScoreType.SECOND);

		if(firstScore == null || secondScore == null) {
			return false;
		}

		if((firstScore + secondScore) == IConstants.FRAME_PER_PERSON) {
			return true;
		}

		return false;
	}
}
