package se.kth.tracedata;

public abstract class LockInstruction extends Instruction
{
	/**
	    * only useful post-execution (in an instructionExecuted() notification)
	    */
	  public abstract int getLastLockRef () ;

<<<<<<< HEAD
=======
	

>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d
}
