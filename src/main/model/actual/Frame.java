package main.model.actual;

import main.exception.AlreadyFrameFinishedException;
import main.exception.PinCountRangeException;
import main.exception.UnexpectedTypeException;
import main.model.PinCount;
import main.model.Rollable;
import main.util.IConstants;

public class Frame implements Rollable {

	public enum RollStatus {
		ROLL_NEVER, ROLL_ONCE, ROLL_TWICE, ROLL_THIRD;
	};

	public enum FrameStatus {
		CAN_ROLL, FINISHED, STRIKE, SPARE;
	}

	public enum ScoreStatus {
		IN_GAME, FINAL, NEXT_TWO_BALL, NEXT_BALL;		
	}

	public enum ScoreType {
		FIRST, SECOND, THIRD, TOTAL;
	}

	protected Integer ball[];
	protected Integer score;
	protected FrameStatus status;
	protected RollStatus rollStatus;
	protected ScoreStatus scoreStatus;

	Frame() {
		ball = new Integer[IConstants.BALL_PER_FRAME];
		score = 0;
		status = FrameStatus.CAN_ROLL;
		rollStatus = RollStatus.ROLL_NEVER;
		scoreStatus = ScoreStatus.IN_GAME;
	}

	public void roll(PinCount knockOverCount) throws Exception {
		if (status == FrameStatus.FINISHED) {
			throw new AlreadyFrameFinishedException();
		}

		Integer knockOverPinCount = knockOverCount.intValue();
		if (score + knockOverPinCount > IConstants.PIN_PER_FRAME) {
			if (!isFinalFrame()) {
				throw new PinCountRangeException();
			}
		}

		switch (rollStatus) {
		case ROLL_NEVER:
			ball[0] = knockOverPinCount;
			rollStatus = RollStatus.ROLL_ONCE;
			break;
		case ROLL_ONCE:
			ball[1] = knockOverPinCount;
			rollStatus = RollStatus.ROLL_TWICE;
			break;
		case ROLL_TWICE:
			ball[2] = knockOverPinCount;
			rollStatus = RollStatus.ROLL_THIRD;
			break;
		case ROLL_THIRD:
			throw new Exception();
		}

		score += knockOverPinCount;
		refreshStatus();
	}

	private boolean isFinalFrame() {
		return this instanceof FinalFrame;
	}

	protected void refreshStatus() {
		if (rollStatus == RollStatus.ROLL_TWICE) {
			status = FrameStatus.FINISHED;
			scoreStatus = ScoreStatus.FINAL;
		}

		if (score != IConstants.PIN_PER_FRAME) {
			return;
		}

		if (rollStatus == RollStatus.ROLL_ONCE) {
			status = FrameStatus.STRIKE;
			scoreStatus = ScoreStatus.NEXT_TWO_BALL;
		}

		if (rollStatus == RollStatus.ROLL_TWICE) {
			status = FrameStatus.SPARE;
			scoreStatus = ScoreStatus.NEXT_BALL;
		}
	}

	public Integer getScore(ScoreType scoreType) throws UnexpectedTypeException {
		if (scoreType == ScoreType.FIRST) {
			return ball[0];
		}

		if (scoreType == ScoreType.SECOND) {
			return ball[1];
		}

		if (scoreType == ScoreType.THIRD) {
			if (!isFinalFrame()) {
				throw new UnexpectedTypeException();
			}
			return ball[2];
		}

		return score;
	}

	public void plusBonusScore(PinCount knockOverCount) {
		Integer knockOverPinCount = knockOverCount.intValue();

		if (getScoreStatus() == ScoreStatus.NEXT_BALL) {
			score += knockOverPinCount;
		}

		if (scoreStatus == ScoreStatus.NEXT_TWO_BALL) {
			scoreStatus = ScoreStatus.NEXT_BALL;
		} else if (scoreStatus == ScoreStatus.NEXT_BALL) {
			scoreStatus = ScoreStatus.FINAL;
		}
	}

	public boolean canRoll() {
		if (status == FrameStatus.CAN_ROLL) {
			return true;
		}

		return false;
	}

	public FrameStatus getStatus() {
		return status;
	}

	public ScoreStatus getScoreStatus() {
		if (scoreStatus == ScoreStatus.NEXT_TWO_BALL) {
			return ScoreStatus.NEXT_BALL;
		}

		return scoreStatus;
	}
}
