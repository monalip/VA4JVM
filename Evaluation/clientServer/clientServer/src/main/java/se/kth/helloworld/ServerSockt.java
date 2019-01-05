package se.kth.helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerSockt {

	  public final static int PORT = 1024;  //13;

	  public static void main(String[]args) throws IOException {
		  System.out.println("Server is started.");
	    Socket connection = null;
	    ServerSocket server = null;
	    server = new ServerSocket(PORT);
		  System.out.println("Server is waiting for client request.");
	    connection = server.accept();
		  System.out.println("Client Connected.");
		  BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); //in order to read data from the socket 
		  String str =br.readLine();
		  System.out.println("Client Data: "+str);
		  server.close();
	    /*try {
	      server = new ServerSocket(PORT);

	      while (true) {
	        connection = server.accept();
	        OutputStreamWriter out
	          = new OutputStreamWriter(connection.getOutputStream());
	        Date now = new Date();
	        out.write(now.toString() + "\n");
	        out.flush();
	      }
	    }
	    catch(IOException e) {
	      System.err.println(e);
	    }
	    finally {
	      try {
	        connection.close();
		server.close();
	      }
	      catch(IOException e) {
	        System.err.println(e);
	      }
	      System.out.println("Connection closed.");
	    }*/
	  }
	}

