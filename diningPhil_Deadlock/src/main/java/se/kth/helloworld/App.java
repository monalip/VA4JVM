package se.kth.helloworld;

import java.lang.Thread.State;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;




/**
 * Hello world!
 *
 */

public class App 
{ 
	
	
	  public static void main(String[] args) {
		  
	        int num = 5;
	        if((args != null) && (args.length > 0)) {
	        	num = Integer.parseInt(args[0]);
	        }
			
			ReentrantLock forks []=new ReentrantLock[num];
	    	for (int i=0;i<num;i++) {
	    		forks[i]=new ReentrantLock();
	    	}

			Philosopher phils[] = new Philosopher[num];
			for (int i=0; i<num; i++){
				if (i==0)
					phils[i] = new Philosopher(forks[num-1],forks[i]);
				else
					phils[i] = new Philosopher(forks[i-1],forks[i]);
				
			}

			for (int i=0; i<num; i++){
				phils[i].start();
			}
			
	    }

	}

	

	class Philosopher extends Thread {
		
	    public ReentrantLock rightFork;
	    public ReentrantLock leftFork;

	    public Philosopher(ReentrantLock forks, ReentrantLock forks2) {
	        this.rightFork = forks;
	        this.leftFork = forks2;
	    }
	    

	    public void run() {
	    	
	        while (true) {
	          rightFork.lock();
	          if(leftFork.isLocked())
	          {
	        	
	   			  
	          }
	          try {
				leftFork.lock();
				try {
					
				} finally {
					leftFork.unlock();
				}
			} finally {
				
				rightFork.unlock();
			}
	        }
	    }


		
	}