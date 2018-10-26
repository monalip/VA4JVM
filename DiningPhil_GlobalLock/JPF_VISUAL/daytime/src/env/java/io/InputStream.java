/* $Id: InputStream.java 834 2010-11-24 07:28:51Z cartho $ */

package env.java.io;

/* Stub class for InputStream (hard-coded one-line string). */

import java.io.IOException;

public class InputStream extends java.io.InputStream {
  int pos;
  static final String hardCodedString = "Tue Jan 17 11:01:01 JST 2011\n";

  public InputStream() {
    pos = 0;
  }

  public int read() throws IOException {
    if (pos == hardCodedString.length()) {
      return -1;
    }
    return hardCodedString.charAt(pos++);
  }
}
