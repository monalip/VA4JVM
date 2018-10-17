package se.kth.helloworld;
import java.util.concurrent.locks.ReentrantLock;

import se.kth.helloworld.aspect.RuntimeData;

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
		static Object globalLock = new Object();
		RuntimeData global= RuntimeData.getInstance();
		
	    public ReentrantLock rightFork;
	    public ReentrantLock leftFork;

	    public Philosopher(ReentrantLock forks, ReentrantLock forks2) {
	        this.rightFork = forks;
	        this.leftFork = forks2;
	    }
	    

	    public void run() {
	    	
	        while (true) {
	        	synchronized (globalLock) 
	        	{
					 rightFork.lock();
				}
	        	synchronized (globalLock) 
	        	{
	        		while (leftFork.isLocked()) 
	        		{
						if(global.threads.size()==5)
						{
							int countval=0;
							for(Thread t:global.threads)
							   {
									
								//condition to check the last thread is the only thread that is in the state RUNNABLE
								if((Thread.currentThread().getState().toString()=="RUNNABLE")&&(t.getState().toString() =="RUNNABLE"))
								 {
									 countval++;
									 
								 }
							   }
							 if(countval == 1)
							 {
								 System.out.println("Deadlock...");
								   //Start GUI
								 	global.displayErrorTrace();
								 // clear the threadList
								  global.threads.clear();
								  //System.exit(0); //it is not working as the System.exit(0) immediately terminating visualization window also
								   
							 }
						}
								try {
								globalLock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					}
	        		
					leftFork.lock();
				}
	        	synchronized (globalLock) 
	        	{
					leftFork.unlock();
					globalLock.notifyAll();
				}
	        	synchronized (globalLock)
	        	{
					rightFork.unlock();
					globalLock.notifyAll();
				}
			
	        }
	    }


		
	}