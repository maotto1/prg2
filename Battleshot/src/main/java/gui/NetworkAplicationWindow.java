package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import control.GameControl;
import control.NetworkControl;

public class NetworkAplicationWindow {

	private JFrame frame;
	private final JTextField textFieldHost;
	private final JTextField textFieldPort, textFieldMyPort;
	protected final GameControl game;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NetworkAplicationWindow window = new NetworkAplicationWindow(new GameControl());
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NetworkAplicationWindow(GameControl game) {
		this.game = game;
		textFieldHost = new JTextField();
		textFieldPort = new JTextField();
		textFieldMyPort = new JTextField();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("Network Configuration");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 2, 0, 0));
		
		
		JLabel lblMyPort = new JLabel("use port:");
		panel.add(lblMyPort);
		
		textFieldMyPort.setText(String.valueOf(NetworkControl.myPort));
		panel.add(textFieldMyPort);
		
		JLabel lblAdversary = new JLabel("adress form the other player:");
		panel.add(lblAdversary);
		panel.add(new JLabel());
		
		JLabel lblUrl = new JLabel("URL");
		panel.add(lblUrl);
		
		
		textFieldHost.setText(NetworkControl.host);
		panel.add(textFieldHost);
		//textFieldHost.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		panel.add(lblPort);
		
		
		textFieldPort.setText(String.valueOf(NetworkControl.port));
		panel.add(textFieldPort);
		//textFieldPort.setColumns(10);
		
		final JPanel panel_1 = new JPanel();
		final JButton btnAbort = new JButton("Abort connection");
		btnAbort.setVisible(false);
		final JButton btnConnect = new JButton("Connect");
		btnAbort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnect.setEnabled(true);
				btnAbort.setVisible(false);
				game.abortConnect();
			}
		});
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);

		btnConnect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				NetworkControl.host = textFieldHost.getText().trim();
				NetworkControl.port = Integer.parseInt(textFieldPort.getText().trim());
				NetworkControl.myPort = Integer.parseInt(textFieldMyPort.getText().trim());
				((JButton) e.getSource()).setEnabled(false);
				btnAbort.setVisible(true);
				game.connect();
			}
		});
		
		panel_1.add(btnConnect);
		panel_1.add(btnAbort);
		
	}

}
