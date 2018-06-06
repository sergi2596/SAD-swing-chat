package Client;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class ClientGUI {

	private JFrame frame;
	private JTextField inputChat;
	private JTextField txtusername;
    private DefaultListModel model;
    private JList<String> userList;
	private static boolean conectat;
	private Client client;
	private JButton btnConnect;
	private JTextArea txtChatArea;
	private static ClientGUI window;
	private static String name;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ClientGUI();
					window.frame.setVisible(true);
					conectat = false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientGUI() {
		initialize();
        model = new DefaultListModel<String>();
        userList.setModel(model);
	}

	/*Inicialitzem la part gràfica*/
	private void initialize() {
		frame = new JFrame("Xat");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		txtChatArea = new JTextArea();
		txtChatArea.setBounds(10, 11, 297, 194);
		panel.add(txtChatArea);
		
		userList = new JList<>();
		userList.setBounds(317, 99, 107, 151);
		panel.add(userList);
		
		JButton btnSend = new JButton("Enviar");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String missatge = inputChat.getText();
				client.sendText(missatge);
				receiveText("Jo: " + missatge);
				inputChat.setText("");
			}
		});
		btnSend.setBackground(SystemColor.activeCaption);
		btnSend.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		btnSend.setBounds(218, 216, 89, 34);
		btnSend.setMnemonic(KeyEvent.VK_ENTER);
		panel.add(btnSend);
		
		inputChat = new JTextField();
		inputChat.setBounds(10, 216, 196, 34);
		panel.add(inputChat);
		inputChat.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Usuari:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(317, 11, 107, 14);
		panel.add(lblNewLabel);
		
		txtusername = new JTextField();
		txtusername.setBounds(317, 36, 107, 20);
		panel.add(txtusername);
		txtusername.setColumns(10);
		
		btnConnect = new JButton("Conectar");
		btnConnect.setBackground(new Color(0, 255, 0));
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(conectat){
					//tancar socket
					btnDisconnect();
					desconectar();
				}else{
					name = txtusername.getText(); 
					if(name.equals("")){
						JOptionPane.showMessageDialog(null, "Introdueix el nom d'usuari!");
					}else {
						btnConnect();
						conectar();
					}
				}
				
				
			}
		});
		btnConnect.setBounds(317, 67, 107, 23);
		panel.add(btnConnect);
	}
	public void btnConnect(){
		btnConnect.setText("Desconectar");
		btnConnect.setBackground(Color.red);
	}
	public void btnDisconnect(){
		btnConnect.setText("Conectar");
		btnConnect.setBackground(Color.green);
	}
	public void conectar(){
		txtusername.setText("");
		conectat = true;
		client = new Client("localhost", 2500, name, window);
	}
	
	public void desconectar(){
		conectat = false;
		client.closeSocket();
		model.clear();
		txtChatArea.setText(null);
	}
	public void receiveText(String str) {
        txtChatArea.append(str + "\n");
    }

    public void addUser(String user) {
        model.addElement(user);
    }

    public void removeUser(String user) {
        model.removeElement(user);
    }
    public void usuariDuplicat(){
    	conectat = false;
    	btnDisconnect();
    	JOptionPane.showMessageDialog(null, "El nom d'usuari ja existeix, escolleix un altre.");
    	
    }
}
