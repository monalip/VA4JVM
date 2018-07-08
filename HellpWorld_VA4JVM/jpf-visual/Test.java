public class Test extends Thread {
  static int x;
  public void run() {
    x++;
  }

  public static void main(String[] args) {
    new Test().start();
    x++;
    System.out.println("Hello, World!");
    assert(false);
  }
}
