package se.kth.jpf_visual;

import java.util.HashMap;
import java.util.Map;

public class PaneConstants {
	public static final int START_SIZE = 50;
	// public static final int TOP_SPACE = 10;
	public static final int BAR_SIZE = 18;
	public static final int TOP_SPACE = 5;
	public static final int LEFT_SPACE = 5;
	public static final int ALTER_SIZE = 50;
	public static final int FONT_SIZE = 11;
	public static final int CONTENT_FONT = 12;
	public static final int OUTLINE_SIZE = 150;
	public static final int CELL_HEIGHT = 45;
	public static final int SIGN_SIZE = 15;
	public static final int RANGE_SIZE = 60;
	public static final int DEFAULT_CELL_WIDTH = 201;
	public static final int THREAD_STATE_WIDTH = 10;
	public static final int ARROW_SIZE = 20;
	public static final String[] COLOR_TABLE = { "#e3a5bc", "#75d4b7", "#ec8dad", "#66d0d6", "#e69e93", "#81bfe7",
			"#d3c478", "#c3afe6", "#cdd295", "#d1b6ca", "#ddad79", "#adc6d5", "#b9cda1", "#dfb5a6", "#b6cfbe" };

	public static final double PlainLineScale = 0.5;

	public static final Map<String, String> threadState = new HashMap<>();
	static {
		threadState.put("ROOT", "green");
		threadState.put("LOCK", "#ffbf00");// amber
		threadState.put("WAIT", "red");
		threadState.put("RELEASE", "green");
		threadState.put("TERMINATE", "none");
		threadState.put("START", "green");
	}

}
