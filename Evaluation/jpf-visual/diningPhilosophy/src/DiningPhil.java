import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;



//
// Copyright (C) 2006 United States Government as represented by the
// Administrator of the National Aeronautics and Space Administration
// (NASA).  All Rights Reserved.
//
// This software is distributed under the NASA Open Source Agreement
// (NOSA), version 1.3.  The NOSA has been approved by the Open Source
// Initiative.  See the file NOSA-1.3-JPF at the top of the distribution
// directory tree for the complete NOSA document.
//
// THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY WARRANTY OF ANY
// KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT
// LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL CONFORM TO
// SPECIFICATIONS, ANY IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR
// A PARTICULAR PURPOSE, OR FREEDOM FROM INFRINGEMENT, ANY WARRANTY THAT
// THE SUBJECT SOFTWARE WILL BE ERROR FREE, OR ANY WARRANTY THAT
// DOCUMENTATION, IF PROVIDED, WILL CONFORM TO THE SUBJECT SOFTWARE.
//

public class DiningPhil {

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

	

	class Philosopher extends Thread{
		public static  Set<Thread> threadList = new HashSet<Thread>();
		static Object globalLock = new Object();//in order to make the whole thing atomic we use the global lock
		//static RuntimeData global= RuntimeData.getInstance();
		boolean isdeadlock = false;
		static long maxThreadId;
		static int countval=0;
		static int count =0;
		
		
	    public ReentrantLock rightFork;
	    public ReentrantLock leftFork;

	    public Philosopher(ReentrantLock forks, ReentrantLock forks2) {
	        this.rightFork = forks;
	        this.leftFork = forks2;
	    }
	    

	    public void run() {
	    	count++;
	        while (true) {
	        	synchronized (globalLock) 
	        	{
	        		 
					 rightFork.lock();
					//System.out.println("Thread Id "+Thread.currentThread().getId() + " Got right fork  "+"\n" );
					
					
	        	}
	        	synchronized (globalLock) 
	        	{
	        		 while (leftFork.isLocked()) {
	        			 
	        			 if(count >= 4)
	        			 {
	        				// detectDeadLock();
	        			 }
						 
		        			
		        			 try {
		        		
		        				 globalLock.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		        		}
					 
					leftFork.lock();
					//System.out.println("Thread Id "+Thread.currentThread().getId() + " Got left fork \n" );
					//System.out.println("Thread Id "+Thread.currentThread().getId() + " Got both forks for eating \n" );
					if(count == 5)
    			 {
					 //detectDeadLock();
    			 }
					
	        	}
	        	synchronized (globalLock) 
	        	{
					leftFork.unlock();
					//System.out.println("Thread Id "+Thread.currentThread().getId() + " Unlock left fork \n" );
					globalLock.notifyAll();
				
				
	        	}
	        	synchronized (globalLock) 
	        	{
					rightFork.unlock();
					//System.out.println("Thread Id "+Thread.currentThread().getId() + " Unlock right fork \n" );
					globalLock.notifyAll();
				}
			
	        }
	    
 	

}


		
		
	}
	