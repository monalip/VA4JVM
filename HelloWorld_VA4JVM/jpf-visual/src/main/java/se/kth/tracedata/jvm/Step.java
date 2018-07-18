package se.kth.tracedata.jvm;

import se.kth.tracedata.Instruction;

public class Step implements se.kth.tracedata.Step{
	Instruction insn = null;
	 public Step(Instruction insn) {
		 this.insn = insn;
		 
		  }
	@Override
	public String getLineString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getLocationString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Instruction getInstruction() {
		return insn;
	}

}
