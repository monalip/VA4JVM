package se.kth.tracedata.jvm;


import se.kth.tracedata.Instruction;


public class Step implements se.kth.tracedata.Step{
	Instruction insn;
	Step next;
	String lineString;
	String locationString;
	
	 public Step(Instruction insn, String lineString, String locationString) {
		 if (insn == null)
		      throw new IllegalArgumentException("insn == null");
		 this.insn = insn;
		 this.lineString = lineString;
		 this.locationString = locationString;
		 
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
	

}
