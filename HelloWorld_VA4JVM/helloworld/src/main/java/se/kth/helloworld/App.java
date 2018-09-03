package se.kth.helloworld;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Hello world!
 *
 */
public class App 
{
	static int a = 3;
	static int b = 5;

	private final ReentrantLock lock = new ReentrantLock();

	
   public App()
   {
	 this.a =1;
	 this.b= 2;
   }
	
	public static void main( String[] args )
    {
       String name = "Monali Pande";
		App app = new App();
		System.out.println("App values: "+app.a);
		int r = add(2,3);
		MyThread t = new MyThread(name);
		app.m();
		new Thread(t).start();
		try {
			new Thread(t).join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Finish");
       
    }
	public void m() {
		
		int count = 0;

	    lock.lock();
	    try {
	        count++;
	    } finally {
	        lock.unlock();
	    }
	}

	private static synchronized int add(int a2, int b2) {

		synchronized (App.class) {

			System.err.println("non-static method running");

		}
		
		return a2+b2;
	}
	

}
class MyThread implements Runnable
{	
	public String name;
	
	public MyThread(String name) {
		this.name = name;
	}
	public void run()
	{
		int m,n,r;
		m = 4;
		n = 2;
		r = threadadd(m,n);
		r = threadsub(m,n);
		System.out.println("ChildThread "+r);
		synchronized (name) 
		{
            try{
                System.out.println(name+" waiting to get notified at time:"+System.currentTimeMillis());
                name.wait(2000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
		}
		
		
	}
	 private int threadadd(int m, int n) {
			return m + n;
			
			
		}
	 private int threadsub(int m, int n) {
			return m - n;
			
			
		}
}
