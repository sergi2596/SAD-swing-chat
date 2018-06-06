package Client;

import java.io.IOException;
import utils.MySocket;

public class Client {
	private String nomusuari;
	private static MySocket mysocket;
	private static ClientGUI gui;
	
	
	public Client(String host, int port, String nomusuari, ClientGUI clientgui) {
		try{
			this.nomusuari = nomusuari;
			mysocket = new MySocket(host,port);
			gui = clientgui;
			mysocket.send(nomusuari);
			new Thread(new Receiver(mysocket)).start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public static void sendText(String message){
		mysocket.send(message);
	}
	
	public static void closeSocket() {
		try {
			mysocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static class Receiver implements Runnable{
		private static String entrar = "$$";
		private static String sortir = "%%";
		private static String afegir = "##";
		private static String denegat = "//";
		MySocket sock;

		public Receiver(MySocket sock) {
			this.sock = sock;		
		}
		public void run(){
			try{	
				String line;
				while((line = sock.receive())!= null) {
					String tmp = line.substring(2);
					
					if(line.equals(denegat)){
						gui.usuariDuplicat();
					}else if(line.startsWith(entrar)){
						gui.receiveText(tmp+" s'ha unit al chat");
						gui.addUser(tmp);
					}else if(line.startsWith(afegir)){
						gui.addUser(tmp);
					}else if(line.startsWith(sortir)){
						gui.receiveText(tmp+" ha marxat del chat");
						gui.removeUser(tmp);
					}
					else{
						gui.receiveText(line);
					}

				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
}
