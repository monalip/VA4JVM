package se.kth.helloworld;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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

public class Fibonacci {
	/* public static long startMainTime = 0;
	    public static long endMainTime = 0;
	    public static long endTraceTime = 0;
	    public static long startVisTime = 0;*/

    private static Map<Integer,Integer> map = new HashMap<Integer,Integer>();

    public static void main(String[] args) throws Exception {
    	//startMainTime = System.nanoTime();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        System.out.println(fibMemo(n)); // Returns 8 for n = 6
        System.out.println(fibBotUp(n)); // Returns 8 for n = 6
        /*endMainTime = System.nanoTime();
	    long totaltime =endMainTime-startMainTime;
	    System.out.println("Total program execution time:"+TimeUnit.NANOSECONDS.toMillis(totaltime)+"\n");*/

    }

    /**
     * This method finds the nth fibonacci number using memoization technique
     *
     * @param n The input n for which we have to determine the fibonacci number
     * Outputs the nth fibonacci number
     **/

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
}