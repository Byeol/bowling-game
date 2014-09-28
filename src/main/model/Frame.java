package main.model;

import main.exception.AlreadyFallTwiceException;
import main.exception.PinCountRangeException;
import main.util.IConstants;

public class Frame {
	public enum FrameStatus {
		ROLL_NEVER, ROLL_ONCE, ROLL_TWICE;
	};

	public enum ScoreStatus {
		IN_GAME, FINAL, NEXT_TWO_BALL, NEXT_BALL;		
	}

	public enum ScoreType {
		FIRST, SECOND, TOTAL;
	}

	private Integer ball[];
	private Integer score;
	private FrameStatus status;
	private ScoreStatus scoreStatus;

	Frame() {
		ball = new Integer[IConstants.BALL_PER_FRAME];
		score = 0;
		status = FrameStatus.ROLL_NEVER;
		scoreStatus = ScoreStatus.IN_GAME;
	}

	public void roll(Integer knockOverPinCount) throws AlreadyFallTwiceException,
			PinCountRangeException {

		// if already roll twice, make exception
		if (status == FrameStatus.ROLL_TWICE) {
			System.out.println("fall twice");
			throw new AlreadyFallTwiceException();
		}

		// if knocked over pin count is negative or more than available, make exception
		if ((knockOverPinCount < 0) || (score + knockOverPinCount > IConstants.PIN_PER_FRAME)) {
			throw new PinCountRangeException();
		}

		score += knockOverPinCount;

		if (status == FrameStatus.ROLL_ONCE) {
			ball[1] = knockOverPinCount;
			status = FrameStatus.ROLL_TWICE;
		} else {
			ball[0] = knockOverPinCount;
			status = FrameStatus.ROLL_ONCE;
		}

		// System.out.println("status: " + status);
		checkScore();
		//System.out.println("roll: " + knockOverPinCount);
	}

	private void checkScore() {
		if(status == FrameStatus.ROLL_TWICE) {
			scoreStatus = ScoreStatus.FINAL;
		}

		if(score == IConstants.PIN_PER_FRAME) {
			if(status == FrameStatus.ROLL_ONCE) {
				// STRIKE
				scoreStatus = ScoreStatus.NEXT_TWO_BALL;
			} else {
				// SPARE
				scoreStatus = ScoreStatus.NEXT_BALL;
			}
		}
	}

	public Integer getScore(ScoreType scoreType) {
		if (scoreType == ScoreType.FIRST) {
			return ball[0];
		}

		if (scoreType == ScoreType.SECOND) {
			return ball[1];
		}

		return score;
	}

	public void plusBonusScore(Integer knockOverPinCount) {
		if(getScoreStatus() == ScoreStatus.NEXT_BALL) {
			score += knockOverPinCount;
		}

		if(scoreStatus == ScoreStatus.NEXT_TWO_BALL) {
			scoreStatus = ScoreStatus.NEXT_BALL;
		} else if(scoreStatus == ScoreStatus.NEXT_BALL) {
			scoreStatus = ScoreStatus.FINAL;
		}
	}

	public FrameStatus getStatus() {
		return status;
	}

	public ScoreStatus getScoreStatus() {
		if(scoreStatus == ScoreStatus.NEXT_TWO_BALL) {
			return ScoreStatus.NEXT_BALL;
		}

		return scoreStatus;
	}
}
