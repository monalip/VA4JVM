package se.kth.helloworld;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import se.kth.helloworld.aspect.RuntimeData;

/**
 * DiningPhil deadlock
 *
 */


public class MemoryConsistencyErrorExample {
    private static boolean sayHello = false;

    public static void main(String[] args) throws InterruptedException {
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        Thread first = new Thread(() -> {
          for (int i = 0; i < 5; i++) { 
            unsafeCounter.inc();
          }
        });
        Thread second = new Thread(() -> {
          for (int i = 0; i < 5; i++) {
            unsafeCounter.dec();
          }
        });
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println("Current counter value: " + unsafeCounter.get());
      }
}
class UnsafeCounter {
	  private volatile int counter;
	  public void inc() {
	    counter++;
	  }
	  public void dec() {
	    counter--;
	  }
	  public int get() {
	    return counter;
	  }
	}