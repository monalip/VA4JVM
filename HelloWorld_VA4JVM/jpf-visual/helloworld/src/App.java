import java.util.concurrent.locks.ReentrantLock;


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
		int r = app.add(2,3);
		MyThread t = new MyThread(name);
		app.m();
		
		new Thread(t).start();
		try {
			new Thread(t).join();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("Finish");
		
       
    }
	public synchronized void m() {
		
		int count = 0;

	}

	 int add(int a2, int b2) {
	System.err.println("non-static method running");		
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
		int k = 4/0;
		
		
	}
	 private synchronized int threadadd(int m, int n) {
			return m + n;
			
			
		}
	 private int threadsub(int m, int n) {
			return m - n;
			
			
		}
}
