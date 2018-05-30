package control;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.MyPanel;
import gui.SetShipScreen;

public class MyMouseListener implements MouseListener {
	
	private final GameControl game;
	private final SetShipScreen screen;
	private static boolean mousePressed = false;
	private static int startX, startY, lastX, lastY;
	
	public MyMouseListener(GameControl game, SetShipScreen screen) {
		this.game = game;
		this.screen = screen;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			System.out.println("handle nur bei doppelklick");
		}
		int x = ((MyPanel) e.getComponent()).x;
		int y = ((MyPanel) e.getComponent()).y;
		if (game.isThereAShip(x, y)){
			game.removeShip(x,y);
			// repaint: 
			screen.refresh();
		}
	}

	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		startX = ((MyPanel) e.getComponent()).x;
		startY = ((MyPanel) e.getComponent()).y;
		lastX = startX;
		lastY = startY;
		
		// Startfeld checken
		if (game.canThereBeAShip(startX, startY, lastX, lastY, getLength())) {
			((MyPanel) e.getComponent()).setBackground(Color.yellow);
			System.out.println("MousePressed at Field " +startX + " " +startY );
		}
		else { 
			System.out.println("not possible");
		}

	}
	
	/**
	 * Schiff erzeugen beim Loslassen vom Startfeld bis hin zum letzten gültigen pressed Field
	 */
	public void mouseReleased(MouseEvent e) {
		System.out.println(lastX +" "+ lastY);
		mousePressed = false;
		int length = getLength();
		if (game.canThereBeAShipOfLength(startX, startY, lastX, lastY, length)) {
			game.createShip(startX, startY, lastX, lastY, length);
		}
		screen.refresh();
	}

	private int getLength() {
		int l=0;
		if ( Math.abs(lastY-startY) > Math.abs(lastX-startX) )
			l = Math.abs(lastY-startY)+1;
		if ( Math.abs(lastY-startY) < Math.abs(lastX-startX) )
			l = Math.abs(lastX-startX)+1;
		else if (Math.abs(lastY-startY) == Math.abs(lastX-startX))
			l = 1;
		return l;
	}

	public void mouseEntered(MouseEvent e) {
		if (mousePressed) {
			int actX = ((MyPanel) e.getComponent()).x;
			int actY = ((MyPanel) e.getComponent()).y;
			// ist es in der selben Reihe wie Startfeld und letztesFeld?
			if (actX == startX && actX == lastX && actY != startY) {
				((MyPanel) e.getComponent()).setBackground(Color.yellow);
				lastX = actX;
				lastY = actY;
			}	
			else if (actX != startX && actY == startY && actY == lastY ) {
				((MyPanel) e.getComponent()).setBackground(Color.yellow);
				lastX = actX;
				lastY = actY;
			}
			else if (actX != startX && actY != startY) {
				//Problem
			}
		
		}
	}

	public void mouseExited(MouseEvent e) {
	}
	
	//private 

}
