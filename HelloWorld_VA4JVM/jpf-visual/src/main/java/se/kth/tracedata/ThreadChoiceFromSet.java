package se.kth.tracedata;

import se.kth.tracedata.ThreadInfo;

<<<<<<< HEAD
public interface ThreadChoiceFromSet {
	 public ThreadInfo getChoice (int idx);
=======
public interface ThreadChoiceFromSet extends ChoiceGenerator<ThreadInfo>{
	 public ThreadInfo getChoice (int idx);
	 public int getThreadId();
	
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d

}
