package se.kth.helloworld;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;


/**

* For a set of points in a coordinates system (10000 maximum), 
* ClosestPair class calculates the two closest points.

* @author: anonymous 
* @author: Marisa Afuera
*/

 public final class Fibonacci
 {
	
	
	 public static void main(String[] args) throws Exception {
	
	     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	     int n = Integer.parseInt(br.readLine());
	     MyThread t = new MyThread(n);
	     new Thread(t).start();
			try {
				new Thread(t).join();
			} catch (Exception e) {
				// TODO: handle exception
			}
	     
	 }
 }
 class MyThread implements Runnable
 {	
	private static Map<Integer,Integer> map = new HashMap<Integer,Integer>();
 	public int n;
 	
 	public MyThread(int n) {
 		this.n = n;
 	}
 	public void run()
 	{
 		System.out.println(fibMemo(n)); // Returns 8 for n = 6
 	     System.out.println(fibBotUp(n)); // Returns 8 for n = 6
 		
 	}
 	 private static int fibMemo(int n) {
 	     if (map.containsKey(n)) {
 	         return map.get(n);
 	     }

 	     int f;

 	     if (n <= 2) {
 	         f = 1;
 	     }
 	     else {
 	         f = fibMemo(n-1) + fibMemo(n-2);
 	         map.put(n,f);
 	     }

 	     return f;
 	 }

 	 /**
 	  * This method finds the nth fibonacci number using bottom up
 	  *
 	  * @param n The input n for which we have to determine the fibonacci number
 	  * Outputs the nth fibonacci number
 	  **/

 	 private static int fibBotUp(int n) {

 	     Map<Integer,Integer> fib = new HashMap<Integer,Integer>();

 	     for (int i=1;i<n+1;i++) {
 	         int f = 1;
 	         if (i<=2) {
 	             f = 1;
 	         }
 	         else {
 	             f = fib.get(i-1) + fib.get(i-2);
 	         }
 	         fib.put(i, f);
 	     }

 	     return fib.get(n);
 	 }
 

 /**
  * This method finds the nth fibonacci number using memoization technique
  *
  * @param n The input n for which we have to determine the fibonacci number
  * Outputs the nth fibonacci number
  **/


}
