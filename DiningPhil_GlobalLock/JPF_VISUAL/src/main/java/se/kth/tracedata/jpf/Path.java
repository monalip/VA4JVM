package se.kth.tracedata.jpf;

import java.io.PrintWriter;

import java.util.Iterator;
import java.util.LinkedList;


import se.kth.tracedata.Transition;


public class Path implements se.kth.tracedata.Path,Iterable<Transition> {
	
	 gov.nasa.jpf.vm.Path jpfpath;
	 String             application;  
	  private LinkedList<Transition> stack;
	  
	  private Path() {} // for cloning
	  
	  public Path(Path path2) {
		  
	  } // for cloning
	  
	 
	  public Path (gov.nasa.jpf.vm.Path jpfpath) {
		  	
		    this.jpfpath = jpfpath; 
		  }
	  public Path (String app) {
		    jpfpath = new gov.nasa.jpf.vm.Path(app);
		  }
	  
	public Path clone() {
		  	    
		    return new Path(jpfpath.clone());
	  }
	  public String getApplication () {
	    return(jpfpath.getApplication());
	  }

	  public Transition getLast () {
		  return new se.kth.tracedata.jpf.Transition((jpfpath.getLast()));
	  }

	  public void add (Transition t) {
	    stack.add(t);
	  }

	  
	 

	  public boolean isEmpty() {
	   return jpfpath.isEmpty();
	  }
	  
	  public int size () {
	    return jpfpath.size();
	  }

	  public boolean hasOutput () {
	    return jpfpath.hasOutput();
	  }
	  
	  public void printOutputOn (PrintWriter pw) {
	    jpfpath.printOn(pw);
	  }
	  
	 
	  public void printOn (PrintWriter pw) {
	/**** <2do> this is going away
	    int    length = size;
	    Transition entry;

	    for (int index = 0; index < length; index++) {
	      pw.print("Transition #");
	      pw.print(index);
	      
	      if ((entry = get(index)) != null) {
	        pw.print(' ');

	        entry.printOn(pw);
	      }
	    }
	***/
	  }

	  public void removeLast () {
		  jpfpath.removeLast();
	  }
	  
	  
	  

	@Override
	public Iterator<Transition> iterator() {
		Iterator<gov.nasa.jpf.vm.Transition> jpftraniter = jpfpath.iterator();
		Iterator<Transition> transiter = new Iterator<Transition>() {

			@Override
			public boolean hasNext() {
				
				return(jpftraniter.hasNext());
			}

			@Override
			public Transition next() {
				return new se.kth.tracedata.jpf.Transition(jpftraniter.next());
			}
		};
		
		return transiter;
	}

	@Override
	public Transition get(int pos) {
		return new se.kth.tracedata.jpf.Transition(jpfpath.get(pos));
	}

	
	

	
	


	
	}