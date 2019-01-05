package se.kth.helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class ClientSocket {
	  static final int PORT = 1024; //13

	  public final static void main(String args[]) throws UnknownHostException, IOException {
	    
	      Socket socket = new Socket("localhost", PORT);
	      String str = "Client connection";
	      OutputStreamWriter os = new OutputStreamWriter(socket.getOutputStream());
	      PrintWriter out = new PrintWriter(os);
	      os.write(str);
	      os.flush();
	      /*InetSocketAddress addr = new InetSocketAddress("localhost", PORT);
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
	    }*/
	  }
	}
