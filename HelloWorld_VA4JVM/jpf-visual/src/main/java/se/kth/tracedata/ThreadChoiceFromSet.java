package se.kth.tracedata;

import se.kth.tracedata.ThreadInfo;

public interface ThreadChoiceFromSet extends ChoiceGenerator<ThreadInfo>{
	 public ThreadInfo getChoice (int idx);

}
