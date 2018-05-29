package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;


import control.GameControl;
import fieldState.SetState;
import gamefield.GameField;

public class SetShipScreen extends JFrame {

	private static final long serialVersionUID = -1304378069951890720L;
	private final GameControl game;
	private MyPanel[][] fields;
	private final JSplitPane splitPane; 
	public static final Color WATER_COLOR = Color.blue, SHIP_COLOR = Color.ORANGE;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SetShipScreen frame = new SetShipScreen(new GameControl());
					frame.setVisible(true);
					frame.setTitle("Set Ships");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SetShipScreen(GameControl game) {
		this.game = game;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 763, 456);
		
		splitPane = new JSplitPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
		);
		
		initializeField();
		initializeLeftSide();		
		
		getContentPane().setLayout(groupLayout);
	}
	
	
	private void initializeLeftSide() {
		JPanel leftPanel = new JPanel();
		// to do
		splitPane.setLeftComponent(leftPanel);
	}

	private void initializeField() {
	
		JPanel rightPanel = new JPanel();
		MyPanel panel;
		MyMouseListener listener;
		rightPanel.setLayout(new GridLayout(GameField.FIELD_SIZE[0],GameField.FIELD_SIZE[1]));
		fields = new MyPanel[GameField.FIELD_SIZE[0]][GameField.FIELD_SIZE[1]];
		
		for (int i=0; i<GameField.FIELD_SIZE[0]; i++) {
			for (int j=0; j<GameField.FIELD_SIZE[1]; j++) {
				panel = new MyPanel(i,j);
				panel.setSize(getContentPane().getWidth()/10, getContentPane().getWidth()/10);
				panel.setBorder(BorderFactory.createLineBorder(Color.black));
				panel.setBackground(WATER_COLOR);
				fields[i][j] = panel;
				listener = new MyMouseListener(game);
				panel.addMouseListener(listener );
				rightPanel.add(panel);
			}
		}

		splitPane.setRightComponent(rightPanel);
		splitPane.setVisible(true);
	}
	
	
	public void refreshField(ArrayList<ArrayList<SetState>> states) {
		for (int i=0; i<GameField.FIELD_SIZE[0]; i++) {
			for (int j=0; j<GameField.FIELD_SIZE[1]; j++) {		// OutOfBounds abfangen??
				if (states.get(i).get(j) == SetState.WATER || states.get(i).get(j) == SetState.BLOCKED) {
					fields[i][j].setBackground(WATER_COLOR);
				}
				else if (states.get(i).get(j) == SetState.SHIP ) {
					fields[i][j].setBackground(SHIP_COLOR);
				}
				else {
					fields[i][j].setBackground(Color.black);	// Fehler anzeigen
				}
			}		
		}
	}
}
