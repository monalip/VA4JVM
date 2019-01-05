package se.kth.tracedata.jvm;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import se.kth.tracedata.Transition;

public class Path implements se.kth.tracedata.Path{
	String             application;
	 LinkedList<Transition> stack=new LinkedList<Transition>();
	
	 public Path (String app,  LinkedList<Transition> stack) {
		    this.application = app;
		    this.stack= stack;
		  } 
	  public void add (Transition t) {
		    stack.add(t);
		  }
	@Override
	public Iterator<Transition> iterator() {
		return stack.iterator();
	}
	@Override
	public se.kth.tracedata.Path clone() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getApplication() {
		// TODO Auto-generated method stub
		return application;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return stack.size();
	}
	@Override
	public boolean hasOutput() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void printOutputOn(PrintWriter pw) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printOn(PrintWriter pw) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeLast() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Transition get(int pos) {
		 return stack.get(pos);
	}

}
