package Server;

import utils.MySocket;
import utils.MyServerSocket;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
	
	private static MyServerSocket serverSocket;
	public static volatile Map<String, MySocket> map;
	//public ReentrantLock lock;

	public static void main(String[] args) {
		try {
			map = new HashMap<String, MySocket>();
			serverSocket = new MyServerSocket(2500);
			System.out.println("Server started");
			while(true) {
				MySocket tmp = serverSocket.accept();
				System.out.println("Connection Accepted");
				new Thread(new Worker(tmp)).start();
			}
		} catch (Exception e) {
			
		}
	}

	private static class Worker implements Runnable{
		
		private static final String entrar = "$$";
		private static final String sortir = "%%";
		private static final String afegir = "##";
		private static final String denegat = "//";
		
		private MySocket sock;
		private String key;
		private boolean permis;
		

		public Worker (MySocket sock) {
			this.sock = sock;
			permis = true;
		}
	
		public void run() {
			try {
				key = sock.receive();
				System.out.println("Worker started: "+key);
				for(String keyTmp: map.keySet()) {
					if(keyTmp.equals(key)){
						permis = false;
						sock.send(denegat);
					}
				}
				if(permis){
					for(String keyTmp: map.keySet()) {
						(map.get(keyTmp)).send(entrar+key);
						sock.send(afegir + keyTmp);
					}
					map.put(key, sock);
					String tmp;
					while((tmp = sock.receive())!= null) {
						System.out.println(tmp);
						for(String keyTmp: map.keySet()) {
							if (keyTmp != key) (map.get(keyTmp)).send(key + ": " + tmp);
						}
					}
					for(String keyTmp: map.keySet()) {
						if (keyTmp != key) (map.get(keyTmp)).send(sortir+key);
					}
					map.remove(key);
				}
				System.out.println("Worker finished: "+key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}