package se.kth.tracedata.jvm;


import se.kth.tracedata.Instruction;
import se.kth.tracedata.MethodInfo;

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

	    return "result = division(a,b);";
	}
	@Override
	public String getLocationString() {
		// TODO Auto-generated method stub
		return "App.java : 9";
	}
	@Override
	public Instruction getInstruction() {
		return insn;
	}
	

}
