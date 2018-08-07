
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
		int result;
        result = add(a, b);
        
        MyThread t = new MyThread();
 
        t.start();
       
       

    }

	private static int add(int a2, int b2) {
		
		return a2+b2;
	}
	

}
class MyThread extends Thread
{	public String name;
	
	public MyThread() {
		this.name = "Monali";
	}
	public void run()
	{
		int m,n,r;
		m = 1;
		n = 0;
		r = threadadd(m,n);
		System.out.println("ChildThread"+r);
		r = m/n;
		System.out.println("ChildThread"+r);
		
		
	}
	 private int threadadd(int m, int n) {
			return m + n;
			
			
		}
}