package se.kth.tracedata;

import java.io.PrintWriter;

import se.kth.tracedata.Transition;


public interface Path extends Iterable<Transition> {
	
			
			//clone check
			public  Path clone();
			
			  
			  public String getApplication () ;
			 

			  public boolean isEmpty() ;
			  
			  public int size () ;

			  public boolean hasOutput ();
			  
			  public void printOutputOn (PrintWriter pw);
			
			  public void printOn (PrintWriter pw);

			  public void removeLast () ;
			 
			 
			
			  public Transition get (int pos);

	}