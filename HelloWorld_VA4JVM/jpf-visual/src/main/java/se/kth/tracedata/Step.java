package se.kth.tracedata;

import se.kth.tracedata.Instruction;

<<<<<<< HEAD
public interface Step {
	public String getLineString () ;
	 public String getLocationString();
	 public Instruction getInstruction();
=======

public interface Step {
	

	public String getLineString () ;
	 public String getLocationString();
	 public Instruction getInstruction();
	 public ChoiceGenerator<ThreadInfo> getCg();
	 
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d

}
