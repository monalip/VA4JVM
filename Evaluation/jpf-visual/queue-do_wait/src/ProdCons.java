/** Class to test queue. */

public class ProdCons {
    static final int DELTA = 16;
    static final int N = 2;
    static final int Q_SIZE = 2;

    static Queue q;

    static class Producer extends Thread {
	byte[] data;

	Producer(int i) {
	    data = new byte[]{(byte)i, (byte)(i + DELTA)};
	}

	public void run() {
	    q.put(data);
	}
    }

    static class Consumer extends Thread {
	public void run() {
	    byte[] result = new byte[2];
	    q.remove(result);
	    System.out.println(result[0] + ", " + result[1]);
	    assert (result[1] - result[0] == DELTA);
	}
    }

    /** Test. */
    public static void main(String[] args) {
	q = new Queue(Q_SIZE);
	for (int i = 0; i < N; i++) {
	    new Producer(i).start();
	    new Consumer().start();
	}
    }
}
