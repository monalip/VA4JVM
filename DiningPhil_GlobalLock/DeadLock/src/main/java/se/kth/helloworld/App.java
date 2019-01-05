package se.kth.helloworld;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import se.kth.helloworld.aspect.RuntimeData;

/**
 * DiningPhil deadlock
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

	

	class Philosopher extends Thread{
		public static  Set<Thread> threadList = new HashSet<Thread>();
		static Object globalLock = new Object();//in order to make the whole thing atomic we use the global lock
		RuntimeData global= RuntimeData.getInstance();
		boolean isdeadlock = false;
		static long maxThreadId;
		
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
					 System.out.println("Thread Id "+Thread.currentThread().getId() + " Got right fork  "+"\n" );
	        	} 
	        	synchronized (globalLock) 
	        	{
	        		while (leftFork.isLocked()) {
	        			System.out.println("Thread Id "+Thread.currentThread().getId() + " Left fork is locked \n" );
	        			{
		        			threadList=(global.getThread());
		    		    	maxThreadId = global.maxThreadId;
								int countval=0;
								System.out.println("Thread List  \n");
								
								for(Thread t:global.getThread())
								   {
										System.out.println("Thread Id "+t.getId()+"State "+t.getState()+"\n");
									//condition to check the last thread is the only thread that is in the state RUNNABLE
									//&&(Thread.currentThread().getId() ==maxThreadId)
									if((Thread.currentThread().getState().toString()=="RUNNABLE")&&(t.getState().toString() =="RUNNABLE"))
									 {
										 countval++;
										 
									 }
								   }
								/* if(countval == 1)
								 {
									 System.out.println("Deadlock...");
									 isdeadlock = true;
									   //Start GUI
									 	global.displayErrorTrace();
									 // clear the threadList
									  global.threads.clear();
									  //System.exit(0); //it is not working as the System.exit(0) immediately terminating visualization window also
									   
								 }*/
	        			
	        			 try {
							globalLock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
	        		}
					leftFork.lock();
					System.out.println("Thread Id "+Thread.currentThread().getId() + " Got left fork \n" );
					
				
	        	}
	        	synchronized (globalLock) 
				{
					leftFork.unlock();
					System.out.println("Thread Id "+Thread.currentThread().getId() + " Unlock left fork \n" );
					globalLock.notifyAll();
				}
	        	
	        	synchronized (globalLock) 
	        	{
	        	
					rightFork.unlock();
					System.out.println("Thread Id "+Thread.currentThread().getId() + " Unlock right fork \n" );
					globalLock.notifyAll();
				}
			
	        }
	    }


		
	}
	/*
	 * while (leftFork.isLocked()) 
	        		{
	        			threadList=(global.getThread());
	    		    	maxThreadId = global.maxThreadId;
							int countval=0;
							System.out.println("Thread List  \n");
							
							for(Thread t:global.getThread())
							   {
									System.out.println("Thread Id "+t.getId()+"State "+t.getState()+"\n");
								//condition to check the last thread is the only thread that is in the state RUNNABLE
								//&&(Thread.currentThread().getId() ==maxThreadId)
								if((Thread.currentThread().getState().toString()=="RUNNABLE")&&(t.getState().toString() =="RUNNABLE"))
								 {
									 countval++;
									 
								 }
							   }
							 if(countval == 1)
							 {
								 System.out.println("Deadlock...");
								 isdeadlock = true;
								   //Start GUI
								 	global.displayErrorTrace();
								 // clear the threadList
								  global.threads.clear();
								  //System.exit(0); //it is not working as the System.exit(0) immediately terminating visualization window also
								   
							 }
						
								try {
								globalLock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					}
	        		
	 */
