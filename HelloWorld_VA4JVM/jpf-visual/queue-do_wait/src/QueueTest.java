/** Class to test queue. */

public class QueueTest {
    static final int DELTA = 16;
    static final int N = 2;
    static final int Q_SIZE = 2;

    static Queue q;

    static class Worker extends Thread {
	byte[] data;

	Worker(byte[] data) {
	    this.data = data;
	}

	public void run() {
	    byte[] result = new byte[2];
	    q.put(data);
	    q.remove(result);
	    // both operations should be atomic
	    System.out.println(result[0] + ", " + result[1]);
	    assert (result[1] - result[0] == DELTA);
	}
    }

    /** Test. */
    public static void main(String[] args) {
	q = new Queue(Q_SIZE);
	for (int i = 0; i < N; i++) {
	    (new Worker(new byte[]{(byte)i, (byte)(i + DELTA)})).start();
	}
    }
}
