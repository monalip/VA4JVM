package se.kth.tracedata.shell;

//import gov.nasa.jpf.shell.ShellCommand;
import se.kth.tracedata.shell.ShellCommand;
//import gov.nasa.jpf.JPF;
import se.kth.tracedata.jpf.JPF;

public class VerifyCommand extends ShellCommand{ 
	gov.nasa.jpf.shell.commands.VerifyCommand jpfverifycmd;
	static gov.nasa.jpf.shell.ShellCommand jpfshellcmd;
	
	VerifyCommand vercmd ;
	public VerifyCommand(gov.nasa.jpf.shell.commands.VerifyCommand jpfverifycmd)
	{
		super(jpfshellcmd);
		this.jpfverifycmd = jpfverifycmd;
	}
	public VerifyCommand(VerifyCommand vercmd)
	{
		super(jpfshellcmd);
		this.vercmd = vercmd;
	}
	private JPF jpf;
	
	/**
	   * only works after {@link #prepare()} is called.
	   * @return the instance of JPF that is being used to run the verify. 
	   *	     Mostly meant to be used by listeners.
	   */
	  public JPF getJPF(){
	    return new JPF(jpfverifycmd.getJPF());
	  }
	  public boolean errorOccured() {
			return jpfverifycmd.errorOccured();
		}


}
