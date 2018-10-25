/* $Id: OutputStreamWriter.java 834 2010-11-24 07:28:51Z cartho $ */

package env.java.io;

/* Stub class for OutputStreamWriter (essentially >/dev/null). */

import gov.nasa.jpf.vm.Verify;
import java.io.IOException;

public class OutputStreamWriter {
  public OutputStreamWriter(OutputStream target) {
  }

  public void write(String str) throws IOException {
    if (Verify.getBoolean()) {
      throw new IOException("Simulated exception when writing a line.");
    }
    System.out.print(str);
  }

  public void flush() throws IOException {
//    if (Verify.getBoolean()) {
//      throw new IOException("Simulated failure when flushing output.");
//    }
  }
}
