package se.kth.tracedata.jpf;

public abstract class LockInstruction  extends se.kth.tracedata.LockInstruction{
	
	gov.nasa.jpf.jvm.bytecode.LockInstruction jpfLockinstruction;
	static gov.nasa.jpf.vm.Instruction jpfInstruction;
	

	 public LockInstruction(gov.nasa.jpf.jvm.bytecode.LockInstruction jpfLockinstruction) {
		 
		 this.jpfLockinstruction = jpfLockinstruction;
	 }
	
	public int getLastLockRef() {
		return jpfLockinstruction.getLastLockRef();
	}

}
