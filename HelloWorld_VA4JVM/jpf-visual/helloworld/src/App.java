
public class App extends Thread
{
    public static void main( String[] args )
    {
        int a ,b ,result;
        a = 4;
        b =0 ;
        result = division(a,b);
        System.out.println("Value = "+result);

    }
	private static int division(int a, int b) {
	synchronized (App.class) {
		return a /b;
		
	}
	
	}
}

