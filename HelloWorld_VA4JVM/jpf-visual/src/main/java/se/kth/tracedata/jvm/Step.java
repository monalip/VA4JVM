package se.kth.tracedata.jvm;


import se.kth.tracedata.ChoiceGenerator;
import se.kth.tracedata.Instruction;
import se.kth.tracedata.ThreadInfo;


public class Step implements se.kth.tracedata.Step{
	Instruction insn;
	Step next;
	String lineString;
	String locationString;
	private ChoiceGenerator<ThreadInfo> cg;
	
	 public Step(Instruction insn, String lineString, String locationString, ChoiceGenerator<ThreadInfo> cg) {
		 if (insn == null)
		      throw new IllegalArgumentException("insn == null");
		 this.insn = insn;
		 this.lineString = lineString;
		 this.locationString = locationString;
		 this.cg =cg;
		 
		  }
	 public Step() {
		
	}
	@Override
	public String getLineString() {
		/*MethodInfo mi = insn.getMethodInfo();
	    if (mi != null) {
	      Source source = Source.getSource(mi.getSourceFileName());
	      if (source != null) {
	        int line = mi.getLineNumber(insn);
	        if (line > 0) {
	          return source.getLine(line);
	        }
	      }
	    }*/

	    return lineString;
	}
	@Override
	public String getLocationString() {
		return locationString;
	}
	@Override
	public Instruction getInstruction() {
		return insn;
	}
	@Override
	public ChoiceGenerator<ThreadInfo> getCg()
	{
		return cg;
	}
	

}
