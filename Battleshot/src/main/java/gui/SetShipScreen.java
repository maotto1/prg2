package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import control.GameControl;
import control.MyMouseListener;
import fieldState.SetState;
import model.GameField;

public class SetShipScreen extends JFrame {

	private static final long serialVersionUID = -1304378069951890720L;
	private final GameControl game;
	private JTextField[] textFields = new JTextField[GameControl.ALLOWED_SHIPS.length];
	private MyPanel[][] fields;
	private final JSplitPane splitPane; 
	private JButton button;
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
		setBounds(150, 150, 763, 456);
		
		splitPane = new JSplitPane();
		splitPane.setDividerLocation(0.7);
		
		
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
		GroupLayout layout = new GroupLayout(leftPanel);
		leftPanel.setLayout(layout);
		ParallelGroup horizontalGroup =layout.createParallelGroup();
		SequentialGroup verticalGroup=layout.createSequentialGroup();
		
		for (int i=1; i < GameControl.ALLOWED_SHIPS.length; i++ ) {
			JLabel label = new JLabel("# ships of length "+i+ " :");
			JTextField textField1 = new JTextField();
			JTextField textField2 = new JTextField();
			textFields[i] = textField1;
			textField1.setText("0");
			textField2.setText(Integer.toString(GameControl.ALLOWED_SHIPS[i]));
			textField1.setHorizontalAlignment(JTextField.CENTER);
			textField2.setHorizontalAlignment(JTextField.CENTER);
			textField1.setEditable(false);
			textField2.setEditable(false);
		
			verticalGroup.addGroup(layout.createParallelGroup()
					.addComponent(label).addComponent(textField1)
					.addComponent(textField2));
			horizontalGroup.addGroup(layout.createSequentialGroup()
					.addComponent(label).addComponent(textField1)
					.addComponent(textField2));
			
		}
		button = new JButton("Continue");
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
				System.out.println("Los geht's");
				game.startMatch();
			}
			
		});
		verticalGroup.addComponent(button);
		horizontalGroup.addComponent(button);
		
		layout.setVerticalGroup(verticalGroup);
		layout.setHorizontalGroup(horizontalGroup);
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
				//panel.setSize(getContentPane().getWidth()/10, getContentPane().getWidth()/10);
				panel.setBorder(BorderFactory.createLineBorder(Color.black));
				panel.setBackground(WATER_COLOR);
				fields[i][j] = panel;
				listener = new MyMouseListener(game, this);
				panel.addMouseListener(listener );
				rightPanel.add(panel);
			}
		}

		splitPane.setRightComponent(rightPanel);
		splitPane.setVisible(true);
	}
	
	
	private void refreshField(ArrayList<ArrayList<SetState>> states) {
		for (int i=0; i<GameField.FIELD_SIZE[0]; i++) {
			for (int j=0; j<GameField.FIELD_SIZE[1]; j++) {		// OutOfBounds abfangen??
				if (states.get(i).get(j) == SetState.WATER) {
					fields[i][j].setBackground(WATER_COLOR);
				}
				else if (states.get(i).get(j) == SetState.BLOCKED) {
					fields[i][j].setBackground(Color.GREEN);
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

	public void refresh() {
		boolean enoughShipSetted = true;
		int setted;
		// die Anzahl gesetzter Schiffe aktualisieren
		for (int i=1; i< textFields.length; i++) {
			setted = game.getNumberOfSettedShipsOfSize(i);
			textFields[i].setText( Integer.toString(setted) );
			if (setted < game.ALLOWED_SHIPS[i])
				enoughShipSetted = false;
		}
		button.setEnabled(enoughShipSetted);
		refreshField(game.getSetStates());		
	}
}
