package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import control.AdversaryFieldMouseListener;
import control.GameControl;
import fieldState.KnownFieldState;
import fieldState.SetState;
import model.GameField;

import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

public class GameScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2933198952816291714L;
	public static final Color SUNK_COLOR = Color.CYAN;
	private final GameControl game;
	private JPanel contentPane;
	private int lastMarkedY=-1, lastMarkedX=-1;
	/**
	 * Left own field, right adversary field
	 */
	private MyPanel[][] fieldsLeft; 
	private MyPanel[][] fieldsRight;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameScreen frame = new GameScreen(new GameControl());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameScreen(GameControl game) {
		this.game = game;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 900, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 0, 30));
		initializeField(); 

	}
	
	private void initializeField() {
		
		JPanel rightPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		MyPanel panelRight, panelLeft;
		AdversaryFieldMouseListener listener;
		rightPanel.setLayout(new GridLayout(GameField.FIELD_SIZE[0],GameField.FIELD_SIZE[1]));
		leftPanel.setLayout(new GridLayout(GameField.FIELD_SIZE[0],GameField.FIELD_SIZE[1]));
		fieldsRight = new MyPanel[GameField.FIELD_SIZE[0]][GameField.FIELD_SIZE[1]];
		fieldsLeft = new MyPanel[GameField.FIELD_SIZE[0]][GameField.FIELD_SIZE[1]];
		
		for (int i=0; i<GameField.FIELD_SIZE[0]; i++) {
			for (int j=0; j<GameField.FIELD_SIZE[1]; j++) {
				panelRight = new MyPanel(i,j);
				panelLeft = new MyPanel(i,j);
				listener = new AdversaryFieldMouseListener(game);
				panelRight.addMouseListener(listener);
				panelRight.setSize(getContentPane().getWidth()/10, getContentPane().getWidth()/10);
				panelRight.setBorder(BorderFactory.createLineBorder(Color.black));
				panelLeft.setBorder(BorderFactory.createLineBorder(Color.black));
				panelRight.setBackground(Color.white);
				panelLeft.setBackground(SetShipScreen.WATER_COLOR);
				fieldsRight[i][j] = panelRight;
				fieldsLeft[i][j] = panelLeft;
				rightPanel.add(panelRight);
				leftPanel.add(panelLeft);
				//   listener = new MyMouseListener(game);
				//  panelRight.addMouseListener(listener );

			}
		}
		
		this.getContentPane().add(leftPanel);
		this.getContentPane().add(rightPanel);

	}
	
	public void markShootedField(int i, int j) {
		if ( (lastMarkedX != -1) && (lastMarkedY != -1) ) {
			fieldsLeft[lastMarkedX][lastMarkedY].setBorder(BorderFactory.createLineBorder(Color.black));
		}
		fieldsLeft[i][j].setBorder(new BevelBorder(BevelBorder.LOWERED));
		JLabel label = new JLabel("X");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setSize(fieldsLeft[i][j].getWidth(), fieldsLeft[i][j].getHeight());
		fieldsLeft[i][j].add(label);
		fieldsLeft[i][j].repaint();
		lastMarkedX = i;
		lastMarkedY = j;
	}
	
	
	public void refreshMyField(ArrayList<ArrayList<SetState>> states) {
		for (int i=0; i<GameField.FIELD_SIZE[0]; i++) {
			for (int j=0; j<GameField.FIELD_SIZE[1]; j++) {		// OutOfBounds abfangen??
				if (states.get(i).get(j) == SetState.WATER || states.get(i).get(j) == SetState.BLOCKED) {
					fieldsLeft[i][j].setBackground(SetShipScreen.WATER_COLOR);
				}
				else if (states.get(i).get(j) == SetState.SHIP ) {
					fieldsLeft[i][j].setBackground(SetShipScreen.SHIP_COLOR);
				}
				else {
					fieldsLeft[i][j].setBackground(Color.black);	// Fehler anzeigen
				}
			}		
		}
	}
	
	public void refreshAdversaryField(ArrayList<ArrayList<KnownFieldState>> states) {
		for (int i=0; i<GameField.FIELD_SIZE[0]; i++) {
			for (int j=0; j<GameField.FIELD_SIZE[1]; j++) {		// OutOfBounds abfangen??
				if (states.get(i).get(j) == KnownFieldState.WATER ) {
					fieldsRight[i][j].setBackground(SetShipScreen.WATER_COLOR);
				}
				else if (states.get(i).get(j) == KnownFieldState.HIT ) {
					fieldsRight[i][j].setBackground(SetShipScreen.SHIP_COLOR);
				}
				else if (states.get(i).get(j) == KnownFieldState.HIT ) {
					fieldsRight[i][j].setBackground(SUNK_COLOR);
				}
				else if (states.get(i).get(j) == KnownFieldState.UNKNOWN ) {
					fieldsRight[i][j].setBackground(Color.white);
				}
				else {
					fieldsRight[i][j].setBackground(Color.black);	// Fehler anzeigen
				}
			}		
		}
	}
}
