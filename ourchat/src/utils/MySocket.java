package utils;

import java.net.*;
import java.io.*;

public class MySocket{

	private BufferedReader buffRead;
	private PrintWriter printWr;
	private Socket sock;
	
	public MySocket(String addr,int port) throws Exception{
		sock = new Socket(addr, port);
		setReader();
		setWriter();	
	}


	public MySocket(Socket sock) throws Exception{
		this.sock = sock;
		setReader();
		setWriter();
	}

	public void setReader() throws IOException{
		printWr = new PrintWriter(sock.getOutputStream());
	}

	public void setWriter() throws IOException{
		buffRead = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	}

	public BufferedReader getReader() throws IOException{
		return buffRead;
	}

	public PrintWriter getWriter() throws IOException{
		return printWr;
	}

	public void send(String message) {
		printWr.println(message);
		printWr.flush();
	}

	public String receive() throws IOException{
		return buffRead.readLine();
	}

	public void close() throws Exception{
		sock.close();
	}

}
