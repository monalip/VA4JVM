package se.kth.tracedata.jpf;

import se.kth.tracedata.jpf.Instruction;


public class Step implements se.kth.tracedata.Step{
	Step next;
	gov.nasa.jpf.vm.Step  jpfStep;
	  public Step(gov.nasa.jpf.vm.Step  jpfStep) {
		 this.jpfStep = jpfStep;
		 
		  }
	@Override
	public String getLineString() {
		return jpfStep.getLineString();
	}
	@Override
	public String getLocationString() {
		
		return jpfStep.getLocationString();
	}
	@Override
	public se.kth.tracedata.Instruction getInstruction() {
		return new Instruction(jpfStep.getInstruction());
		///return jpfStep.getInstruction();
	}
	

}
