package se.kth.tracedata.shell;


import se.kth.tracedata.shell.ShellCommandListener;
import se.kth.tracedata.shell.ShellCommand;

import java.util.ArrayList;

//import gov.nasa.jpf.shell.ShellCommand;
//import gov.nasa.jpf.shell.ShellCommandListener;
//import gov.nasa.jpf.Config;
import se.kth.tracedata.jpf.Config;
//import gov.nasa.jpf.shell.ShellCommand;
//import gov.nasa.jpf.shell.listeners.VerifyCommandListener;



public class ShellManager {
	
	//static members belong to the class instead of a specific instance.
	//It means that only one instance of a static field exists[1] even if you create a million instances of the class or you don't create any. It will be shared by all instances.

	//static gov.nasa.jpf.shell.ShellCommand cmd;
	//static gov.nasa.jpf.shell.ShellCommandListener list;
	
	
			static gov.nasa.jpf.shell.ShellManager jpfshellmnger;
		public ShellManager(gov.nasa.jpf.shell.ShellManager jpfshellmnger) {
			  	
			    this.jpfshellmnger = jpfshellmnger; 
			  }
		
		public static ShellManager getManager(){
		    
			ShellManager jpfmanager = new ShellManager(jpfshellmnger.getManager());
			return jpfmanager;
		  }
		/**
		   * @return this manager's configuration
		   */
		  public Config getConfig(){
		    return new Config(jpfshellmnger.getConfig());
		  }
		  /**
		   * Adds a listener for the command
		   * @param class1 the command to listen for
		   * @param listener the listener to register for the command
		   */
		 /* public void addCommandListener(ShellCommand class1,
                  ShellCommandListener listener){
			  addCommandListener(class1.getClass(),listener);
			 
			jpfshellmnger.addCommandListener(class1, listener);
			  
		  }*/
		  /*public void addCommandListener(Class<VerifyCommand> class1,
                  ShellCommandListener listener){
			  jpfshellmnger.addCommandListener(class1, listener);
			  
			  
		  }*/

		  /**
		   * Adds the listener to all commands that are a subclass of the <code>commandClass</code>
		   * @param commandClass - the ShellCommand class to listen for
		   * @param listener - The listener to be executed when the ShellCommand executes
		   */
		 /* public void addCommandListener(Class<VerifyCommand> commandClass,
		                                 ShellCommandListener listener ){
			 
			  //Class<? extends gov.nasa.jpf.shell.ShellCommand> cmd = (Class<? extends gov.nasa.jpf.shell.ShellCommand>) commandClass;
			  gov.nasa.jpf.shell.commands.VerifyCommand cmd = new VerifyCommand(commandClass);
			  Class<gov.nasa.jpf.shell.commands.VerifyCommand> command =  ( Class<gov.nasa.jpf.shell.commands.VerifyCommand>)commandClass.getClass();
			 
			  gov.nasa.jpf.shell.ShellCommandListener list = (gov.nasa.jpf.shell.ShellCommandListener) listener; 
			jpfshellmnger.addCommandListener(cmd, list);
			
			
		}*/

		
		  

		  /**
		   * Adds a listener for the command
		   * @param command the command to listen for
		   * @param listener the listener to register for the command
		   */
		  /*public void addCommandListener(ShellCommand command,
		                                 ShellCommandListener listener){
			  jpfshellmnger.addCommandListener(command,listener );
			
			}*/

		

		

}
