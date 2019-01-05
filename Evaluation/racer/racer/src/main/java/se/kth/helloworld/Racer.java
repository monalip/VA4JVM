package se.kth.helloworld;

import java.util.concurrent.TimeUnit;

public class Racer implements Runnable {
	

    int d = 42;
    /*public static long startMainTime = 0;
    public static long endMainTime = 0;
    public static long endTraceTime = 0;
    public static long startVisTime = 0;*/

    public void run () {
         doSomething(1001);                   // (1)
         d = 0;                               // (2)
    }

    public static void main (String[] args){
    	//startMainTime = System.nanoTime();
         Racer racer = new Racer();
         Thread t = new Thread(racer);
         t.start();

         doSomething(1000);                   // (3)
         int c = 420 / racer.d; 
         try {
 			t.join();
 		} catch (InterruptedException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}				// (4)
         System.out.println(c);
         /*endMainTime = System.nanoTime();
		    long totaltime =endMainTime-startMainTime;
		    System.out.println("Total program execution time:"+TimeUnit.NANOSECONDS.toMillis(totaltime)+"\n");*/

         
    }
    
    static void doSomething (int n) {
         // not very interesting..
         try { Thread.sleep(n); } catch (InterruptedException ix) {}
    }
}