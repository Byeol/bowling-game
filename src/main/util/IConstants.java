package main.util;

public interface IConstants {

	public static final Integer FRAME_PER_PERSON = 10;
	public static final Integer PIN_PER_FRAME = 10;
	public static final Integer BALL_PER_FRAME = 2;

	public static final Integer PIN_COUNT_MIN = 0;
	public static final Integer PIN_COUNT_MAX = 10;

	public static final Integer SCORE_TABLE_SPACE = 4;
	public static final String SCORE_TABLE_TEMPLATE = "[    ]";
	public static final String SCORE_TABLE_DIVIDED_TEMPLATE = "[ ][ ]";
	public static final String SCORE_TABLE_FINAL_TEMPLATE = "[       ]";
	public static final String SCORE_TABLE_FINAL_DIVIDED_TEMPLATE = "[ ][ ][ ]";

	public static final char STRIKE_LABEL = 'X';
	public static final char SPARE_LABEL = '/';
	public static final char ZERO_LABEL = '-';

}
