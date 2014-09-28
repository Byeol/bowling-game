package main.model;

import main.exception.PinCountRangeException;
import main.util.IConstants;

public class PinCount extends Number {

	private int value;

	public PinCount(Integer count) throws PinCountRangeException {
		if(count < IConstants.PIN_COUNT_MIN || count > IConstants.PIN_COUNT_MAX) {
			throw new PinCountRangeException();
		}

		value = count;
	}

	@Override
	public int intValue() {
		return value;
	}

	@Override
	public long longValue() {
		return value;
	}

	@Override
	public float floatValue() {
		return value;
	}

	@Override
	public double doubleValue() {
		return value;
	}
}
