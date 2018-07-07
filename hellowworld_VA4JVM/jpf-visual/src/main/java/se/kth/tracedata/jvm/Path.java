package se.kth.tracedata.jvm;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import se.kth.tracedata.Transition;

public class Path implements se.kth.tracedata.Path{
	String             application;
	 private LinkedList<Transition> stack;
	
	  public Path (String app) {
		    application = app;
		    stack= new LinkedList<Transition>();
		  }
	@Override
	public Iterator<Transition> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public se.kth.tracedata.Path clone() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getApplication() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return null;
	}

}
