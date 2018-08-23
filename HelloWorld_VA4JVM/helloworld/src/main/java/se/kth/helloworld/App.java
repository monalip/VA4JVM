package se.kth.helloworld;

/**
 * Hello world!
 *
 */
public class App 
{
	static int a = 3;
	static int b = 5;

	
   public App()
   {
	 this.a =1;
	 this.b= 2;
   }
	
	public static void main( String[] args )
    {
       
        
		
		App app = new App();
		System.out.println("App values: "+app.a);
		int r = add(2,3);
		MyThread t = new MyThread();
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Finish");
       
    }

	private static int add(int a2, int b2) {
		
		return a2+b2;
	}
	

}
class MyThread extends Thread
{	
	public String name;
	
	public MyThread() {
		this.name = "Monali";
	}
	public void run()
	{
		int m,n,r;
		m = 4;
		n = 2;
		r = threadadd(m,n);
		r = threadsub(m,n);
		System.out.println("ChildThread "+r);
		
		
	}
	 private int threadadd(int m, int n) {
			return m + n;
			
			
		}
	 private int threadsub(int m, int n) {
			return m - n;
			
			
		}
}
