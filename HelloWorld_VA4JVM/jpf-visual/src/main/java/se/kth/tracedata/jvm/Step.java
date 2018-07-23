package se.kth.tracedata.jvm;


import se.kth.tracedata.Instruction;

public class Step implements se.kth.tracedata.Step{
	Instruction insn;
	Step next;
	
	 public Step(Instruction insn) {
		 if (insn == null)
		      throw new IllegalArgumentException("insn == null");
		 this.insn = insn;
		 
		  }
	 public Step() {
		
	}
	@Override
	public String getLineString() {
		// TODO Auto-generated method stub
		return "LineString";
	}
	@Override
	public String getLocationString() {
		// TODO Auto-generated method stub
		return "LocationString";
	}
	@Override
	public Instruction getInstruction() {
		return insn;
	}
	

}
