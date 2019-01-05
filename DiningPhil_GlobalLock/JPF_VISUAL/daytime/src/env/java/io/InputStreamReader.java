/* (C) JNuke project */

/* $Id: InputStreamReader.java 663 2006-07-07 03:52:16Z cartho $ */

package env.java.io;

import java.io.IOException;

public class InputStreamReader extends java.io.Reader {

	private InputStream in;

	public InputStreamReader(InputStream in) {
		super(in);
		this.in = in;
	}

	public InputStreamReader(InputStream in, String charsetName) {
		this(in);
	}

	private int convertBufs (byte[] from, char[] to, int startTo, int n) {
		/* TODO: cover case where sizeof (char) > sizeof (byte) */
		int i;
		for (i = 0; i < n; i++) {
			to[startTo + i] = (char)from[i];
		}
		return n;
	}

	public int read(char buf[], int off, int len) throws IOException {
		byte lbuf[] = new byte[8192];
		int lblen;
		int nRead;
		int numChars = 0;
		lblen = len - off;
		if (lblen > 8192)
			lblen = 8192;
		synchronized (lock) {
			while ((numChars == 0) && (lblen > 0)) {
				nRead = in.read(lbuf, 0, lblen);
				numChars += convertBufs(lbuf, buf,
							off + numChars, nRead);
				lblen = len - numChars - off;
				if (lblen > 8192)
					lblen = 8192;
			}
		}
		return numChars;
	}

	public void close() throws IOException {
		synchronized (lock) {
			if (in == null)
				return;
			in.close();
			in = null;
		}
	}
}
