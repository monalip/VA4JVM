package se.kth.tracedata;

import se.kth.tracedata.Instruction;

public interface Step {
	public String getLineString () ;
	 public String getLocationString();
	 public Instruction getInstruction();

}
