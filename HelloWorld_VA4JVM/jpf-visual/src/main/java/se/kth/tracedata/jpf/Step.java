package se.kth.tracedata.jpf;

<<<<<<< HEAD
=======
import se.kth.tracedata.ChoiceGenerator;
import se.kth.tracedata.ThreadInfo;
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d
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
<<<<<<< HEAD
=======
	@Override
	public ChoiceGenerator<ThreadInfo> getCg()
	{
		return null;
	}
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d
	

}
