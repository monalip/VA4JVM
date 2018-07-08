package se.kth.tracedata.jpf;

import se.kth.tracedata.Instruction;

public abstract class FieldInstruction extends  se.kth.tracedata.FieldInstruction {
	gov.nasa.jpf.vm.bytecode.FieldInstruction jpfFieldinstruction;
	
	
	public FieldInstruction(gov.nasa.jpf.vm.bytecode.FieldInstruction jFieldInstruction)
	{
			
		this.jpfFieldinstruction = jFieldInstruction;
	}
	
	public String getVariableId() {
		return jpfFieldinstruction.getVariableId();
	}

	
	

}