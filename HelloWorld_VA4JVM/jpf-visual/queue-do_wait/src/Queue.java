/** Fixed-size, blocking queue with atomic multi-element put/remove. */
public class Queue {
    byte data[];
    int p, c; // indices in queue at which to insert/remove elements
    int size;

    /** Constructor for a queue with <tt>size</tt> bytes. */
    public Queue(int size) {
	assert (size <= Integer.MAX_VALUE / 2);
	data = new byte[size];
	this.size = size;
	p = 0;
	c = 0;
    }

    /** Atomic put for multiple elements. Blocks until space available. */
    public synchronized void put(byte[] items) {
	int len = items.length;
	assert (len <= size);
	// wait for space to become available
	while (p + len > c + size) {
	    try {
		wait();
	    } catch (InterruptedException e) {
	    }
	}
	assert (p + len <= c + size) :
	  "p + len <= c + size failed: p = " + p + ", c = " + ", len = " + len;
	for (int i = 0; i < len; i++) {
	    data[p++ % size] = items[i];
	}
	assert (p <= c + size) :
	    "p <= c + size failed: p = " + p + ", c = " + c;
	notifyAll(); // wake up waiting consumers
    }

    /* must hold lock on <tt>this</tt> before calling this method */
    private void waitForData(int len) {
	assert (len <= size);
	do {
	    try {
	        wait();
	    } catch (InterruptedException e) {
	    }
	} while (c + len > p);
	assert (p >= c + len) :
	    "p >= c + len failed: p = " + p + ", c = " + c + ", len = " + len;
	assert (p - size <= c) :
	    "p - size <= c failed: p = " + p + ", c = " + c;
    }

    /** Atomic remove for multiple elements. Blocks until data available. */
    public synchronized void remove(byte[] storage) {
	int len = storage.length;
	waitForData(len);
	for (int i = 0; i < len; i++) {
	    storage[i] = data[c++ % size];
	}
	/* index reduction to prevent index overflow */
	if (c >= size) {
	    assert (p >=c);
	    p -= size;
	    c -= size;
	}
	notifyAll(); // wake up waiting producers
    }
}
