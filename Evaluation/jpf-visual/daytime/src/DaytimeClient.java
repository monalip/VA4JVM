/* $Id: DaytimeClient.java 264 2006-01-16 09:46:15Z cartho $ */

/* Daytime client.
 * Connects to port 1024, reads and prints one line. */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class DaytimeClient {
  static final int PORT = 1024; //13

  public final static void main(String args[]) {
    try {
      Socket socket = new Socket();
      InetSocketAddress addr = new InetSocketAddress("localhost", PORT);
      socket.connect(addr);
      InputStreamReader istr =
        new InputStreamReader(socket.getInputStream());
      BufferedReader in = new BufferedReader(istr);
      String line;
      while ((line = in.readLine()) != null) {
        System.out.println("Received " + line);
      }
    }
    catch(IOException e) {
      System.err.println(e);
    }
  }
}
