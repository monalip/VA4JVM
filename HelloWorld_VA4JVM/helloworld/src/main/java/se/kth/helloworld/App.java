package se.kth.helloworld;


/**
 * Hello world!
 *
 */
public class App extends Thread
{
	static int a;
	static int b;
	int result;
	int add;
	public void run()
	{
		int m,n,r;
		m = 1;
		n = 2;
		r = threadadd(m,n);
		System.out.println("ChildThread"+r);
			
		
		
		
	}
    private int threadadd(int m, int n) {
		return m + n;
		
		
	}
	
	public static void main( String[] args )
    {
		int a1 ,b1 ,result,add;
        a1 = 4;
        b1 =0 ;
        
        Thread t = new Thread();
        Thread t1 = new Thread();
        t.start();
        t1.start();
       
        add = addition(a,b);
        result = division(a,b);
        displayResult(result);
        //System.out.println("Value = "+result);
    }
	private static int addition(int a, int b) {
		return a +b;
	}
	private static void displayResult(int result) {
		
		System.out.println("Value = "+result);
	}
	private static int division(int a, int b) {
		synchronized (App.class) {
			return a /b;
			
		}
		
	
	}
}

