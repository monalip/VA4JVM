package se.kth.jpf_visual;


import java.util.HashSet;
import java.util.Set;

import se.kth.tracedata.Left;
import se.kth.tracedata.Step;
import se.kth.tracedata.Transition;

public class TextLine {
	private String text;
	private boolean isCG;
	private boolean isSrc;
	private Transition tran;
	private int lineNum;
	private int groupNum;
	private boolean isFirst = false;
	private boolean isLast = false;
	private int stepStart;
	private int stepEnd;
	private Set<String> highlight = new HashSet<>();

	public TextLine(String text, boolean isCG, boolean isSrc, Transition tran, int groupNum, int lineNum) {
		this.text = text;
		this.isCG = isCG;
		this.isSrc = isSrc;
		this.tran = tran;
		this.lineNum = lineNum;
		this.groupNum = groupNum;
	}

	public String getText() {
		return text;
	}

	public boolean isCG() {
		return isCG;
	}

	public boolean isSrc() {
		return isSrc;
	}

	public Transition getTransition() {
		return tran;
	}

	public int getLineNum() {
		return lineNum;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setFirst() {
		isFirst = true;
	}

	public void setLast() {
		isLast = true;
	}

	public int getStartStep() {
		return stepStart;
	}

	public int getEndStep() {
		return stepEnd;
	}

	public void setStartStep(int ss) {
		stepStart = ss;
	}

	public void setEndStep(int se) {
		stepEnd = se;
	}

	// public void setHighlight(boolean b) {
	// isHighlighted = b;
	// }

	public boolean isHighlighted() {
		return highlight.size() > 0;
	}

	public boolean isHighlightedColor(String color) {
		return highlight.contains(color);
	}

	public void setHighlight(String color) {
		highlight.add(color);
	}

	public void resetHighlight(String color) {
		highlight.remove(color);
	}

	public Set<String> getAllHighlight() {
		return new HashSet<>(highlight);
	}

	public String getOneColor() {

		for (String s : highlight) {
			return s;
		}

		return null;
	}
	
	public String getLocationString(){
		Step s = tran.getStep(stepStart);
		return Left.format(s.getLocationString(), 20);
	}

}
