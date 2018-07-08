package se.kth.tracedata;

public abstract class LockInstruction extends Instruction
{
	/**
	    * only useful post-execution (in an instructionExecuted() notification)
	    */
	  public abstract int getLastLockRef () ;

}
