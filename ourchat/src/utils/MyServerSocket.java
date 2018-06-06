package utils;

import java.net.*;
import java.io.*;


public class MyServerSocket{

	ServerSocket ss;
	
	public MyServerSocket(int port) throws Exception{
		ss = new ServerSocket(port);
	}
	
	public MySocket accept(){
		MySocket socket = null;
		try {		
			Socket tmp = ss.accept();
			socket = new MySocket(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return socket;
	}

}
