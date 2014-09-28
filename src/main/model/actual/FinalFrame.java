package main.model.actual;

import main.util.IConstants;

public class FinalFrame extends Frame {

	FinalFrame() {
		ball = new Integer[IConstants.BALL_PER_FRAME + 1];
	}

	@Override
	protected void refreshStatus() {
		if (rollStatus == RollStatus.ROLL_THIRD) {
			status = FrameStatus.FINISHED;
			scoreStatus = ScoreStatus.FINAL;
		}

		if (rollStatus == RollStatus.ROLL_TWICE &&
			status != FrameStatus.STRIKE) {
			status = FrameStatus.FINISHED;
			scoreStatus = ScoreStatus.FINAL;
		}

		if (score != IConstants.PIN_PER_FRAME) {
			return;
		}

		if (rollStatus == RollStatus.ROLL_ONCE) {
			status = FrameStatus.STRIKE;
		}

		if (rollStatus == RollStatus.ROLL_TWICE) {
			status = FrameStatus.SPARE;
		}
	}

	@Override
	public boolean canRoll() {
		if (status != FrameStatus.FINISHED) {
			return true;
		}

		return false;
	}
}
