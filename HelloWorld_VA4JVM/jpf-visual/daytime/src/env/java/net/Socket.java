/* $Id: Socket.java 840 2010-11-25 01:10:42Z cartho $ */

package env.java.net;

/* Stub class for java.net.Socket. */

import env.java.io.InputStream;
import env.java.io.OutputStream;
import gov.nasa.jpf.vm.Verify;
import java.io.IOException;

public class Socket {
  InputStream hardCodedInput;

  public Socket() {
     hardCodedInput = new InputStream();
  }

  public void connect(InetSocketAddress addr)
    throws java.net.ConnectException {
      if (Verify.getBoolean()) { // possible failure
        throw new java.net.ConnectException("Connection refused");
      }
  }

  public void close() throws IOException {
    if (Verify.getBoolean()) {
      throw new IOException("Simulated exception when closing connection.");
    }
  }

  public OutputStream getOutputStream() throws IOException {
    return new OutputStream();
  }

  public InputStream getInputStream() throws IOException {
    return hardCodedInput;
  }
}
