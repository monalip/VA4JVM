package se.kth.jpf_visual;


public class SummaryCell {
	private Object summary;
	private Object nextSrc = null;
	private Object prevSrc = null;
	private Object nextDots = null;
	private Object prevDots = null;
	private SummaryCell prevSummary = null;
	private boolean isFirst = false;
	private boolean isLast = false;
	private int threadIdx = -1;

	public SummaryCell(Object sum, int threadIdx) {
		this.summary = sum;
		this.threadIdx = threadIdx;
	}

	public void setPreviousSummary(SummaryCell prevSummary) {
		this.prevSummary = prevSummary;
	}

	public int getThreadIdx() {
		return threadIdx;
	}

	public SummaryCell getPreviousSummary() {
		return this.prevSummary;
	}

	public void setFirst(boolean b) {
		this.isFirst = b;
	}

	public void setLast(boolean b) {
		this.isLast = b;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setNextSrc(Object nextSrc) {
		this.nextSrc = nextSrc;
	}

	public void setPrevSrc(Object prevSrc) {
		this.prevSrc = prevSrc;
	}

	public void setNextDots(Object nextDots) {
		this.nextDots = nextDots;
	}

	public void setPrevDots(Object prevDots) {
		this.prevDots = prevDots;
	}

	public Object getNextSrc() {
		return nextSrc;
	}

	public Object getPrevSrc() {
		return prevSrc;
	}

	public Object getNextDots() {
		return nextDots;
	}

	public Object getPrevDots() {
		return prevDots;
	}

	public Object getSummary() {
		return summary;
	}

}
