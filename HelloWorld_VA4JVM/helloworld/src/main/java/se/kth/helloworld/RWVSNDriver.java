package se.kth.helloworld;

public class RWVSNDriver {
	static RWPrinter rwp;
	static IntWrapper iw;
        static int bound; // if set to -1 then infinite loop
        static int reading;

	public static void main(String argv[]) {
		rwp = new RWPrinter();
		iw = new IntWrapper();

		int readers = 1;
		int writers = 1;
		bound = 2;
                reading = 0;

		if((argv != null) && (argv.length == 3)) {
		   readers = Integer.parseInt(argv[0]);
		   writers = Integer.parseInt(argv[1]);
		   bound = Integer.parseInt(argv[2]);
		}

		for (int i = 0; i < readers; i++) {
			new Reader(rwp, i).start();
		}

		for (int i = 0; i < writers; i++) {
			new Writer(rwp, i).start();
		}
		
	}
	
}

class IntWrapper {
	public int x = 0;
}

final class Reader extends Thread {
	protected RWPrinter rwp;
	protected int id;
	public Reader(RWPrinter r, int id) {
		rwp = r;
		this.id = id;
	}
	public void run() {
		for (int i = 0; i < RWVSNDriver.bound; i++) {
		   rwp.read();
		}
	}
}

final class Writer extends Thread {
	protected RWPrinter rwp;
	protected int id;
	public Writer(RWPrinter r, int id) {
		rwp = r;
		this.id = id;
	}
	public void run() {
		for (int i = 0; i < RWVSNDriver.bound; i++) {
		   rwp.write();
		}
	}
}

class RWPrinter extends RWVSN {
	protected int reading = 0;
	protected void doRead() {
                //System.out.println("  enter doRead()");
		synchronized (this) { reading++; }
                //System.out.println("  reading = "+reading);
		if (RWVSNDriver.iw.x < 10) {
			RWVSNDriver.iw.x++;
		}
                //System.out.println("  exit doRead()");
		synchronized (this) { reading--; }
	}
	protected void doWrite() {
                if (reading > 0) {
		  throw new RuntimeException("bug found");
		}
		if (RWVSNDriver.iw.x > 0) {
			RWVSNDriver.iw.x--;
		}
	}
}

abstract class RWVSN {
	
	//@ invariant !(activeReaders > 0 && activeWriters > 0);
	//@ invariant !(activeWriters > 1);
	
	protected int activeReaders = 0;  // threads executing read
	protected int activeWriters = 0;  // always zero or one

	protected int waitingReaders = 0; // threads not yet in read
	protected int waitingWriters = 0; // same for write

	protected abstract void doRead(); // implement in subclasses
	protected abstract void doWrite(); 

	public void read() /*throws InterruptedException*/ {
	  beforeRead(); 
	  try     { doRead(); }
	  finally { afterRead(); }
	}

	public void write() /*throws InterruptedException*/ { 
	  beforeWrite(); 
	  try     { doWrite(); }
	  finally { afterWrite(); }
	}
	
	/*@ behavior
	   @    ensures \result <==> (waitingWriters == 0 && activeWriters == 0); 
	   @*/
	protected /*@ pure @*/ boolean allowReader() {
	  return waitingWriters == 0 && activeWriters == 0;
	}

	/*@ behavior
	   @    ensures \result <==> (activeWriters == 0 && activeReaders == 0); 
	   @*/
	protected boolean allowWriter() {
	  return activeReaders == 0 && activeWriters == 0;
	}

	/*@ behavior
	   @    assignable waitingReaders, activeReaders;
	   @    ensures activeReaders >= 1 && \old(allowReader()) ==> \old(activeReaders) + 1 == activeReaders;
	   @*/
	protected void beforeRead() 
	 /*throws InterruptedException*/ {

	 	synchronized(this) {
		  ++waitingReaders;
		  while (!allowReader()) {
			try { wait(); } 
			catch (InterruptedException ie) {
			  --waitingReaders; // roll back state 
	//		  throw ie;
			}
		  }
		  --waitingReaders;
	 	}
                // BUG: shrinking synch region exposes update bug
		  //++activeReaders;
 		  //JPF POR workaround
		  int tmp = activeReaders;
		  tmp++;
		  activeReaders = tmp;
	}

	/*@ behavior
	   @    assignable activeReaders;
	   @    ensures \old(activeReaders) - 1 == activeReaders;
	   @*/
	protected void afterRead()  {
		synchronized(this) { 
		  --activeReaders;
		  notifyAll();
		}
	}

	/*@ behavior
	   @    assignable waitingWriters, activeWriters;
	   @    ensures activeWriters == 1 && \old(allowWriter()) ==> \old(activeWriters) + 1 == activeWriters;
	   @*/
	protected void beforeWrite() 
	 /*throws InterruptedException*/ {
		synchronized(this) {
		  ++waitingWriters;
		  while (!allowWriter()) {
			try { wait(); } 
			catch (InterruptedException ie) {
			  --waitingWriters;
	//		  throw ie;
			}
		  }
		  --waitingWriters;
		  ++activeWriters;
		}
	}

	/*@ behavior
	   @    assignable activeWriters;
	   @    ensures \old(activeWriters) - 1 == activeWriters;
	   @*/
	protected void afterWrite() {
		synchronized(this) { 
		  --activeWriters;
		  notifyAll();
		}
	}
}
