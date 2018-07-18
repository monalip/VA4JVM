package se.kth.tracedata.shell;

import javax.swing.Box;

//import gov.nasa.jpf.JPF;
import se.kth.tracedata.jpf.JPF;

public class ProgressTrackerUI extends Box{
	private static final int INTERVAL = 0;

	gov.nasa.jpf.JPF jpfnasa;
	 
	gov.nasa.jpf.shell.util.ProgressTrackerUI jpfprogresstrcker;
	public ProgressTrackerUI(){
		super(INTERVAL);
		
	}
public ProgressTrackerUI (gov.nasa.jpf.shell.util.ProgressTrackerUI jpfprogresstrcker) {
	super(INTERVAL);
	  	
	    this.jpfprogresstrcker = jpfprogresstrcker; 
	  }
public void resetFields(){
    jpfprogresstrcker.resetFields();
	}
// note this is called from the thread that runs JPF (not the EventDispatcher)
public void attachJPF(JPF jpf){
	   jpfprogresstrcker.attachJPF(jpfnasa);
}

}
