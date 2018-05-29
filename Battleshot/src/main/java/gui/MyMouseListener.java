package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import control.GameControl;

public class MyMouseListener implements MouseListener {
	
	private final GameControl game;
	private static boolean mousePressed = false;
	private static int startX, startY, lastX, lastY;
	
	public MyMouseListener(GameControl game) {
		this.game = game;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			System.out.println("handle nur bei doppelklick");
		}
		int x = ((MyPanel) e.getComponent()).x;
		int y = ((MyPanel) e.getComponent()).y;
		if (game.isThereAShip(x, y)){
			game.removeShip(x,y);
			// repaint: WATER
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
	 * Schiff erzeugen vom Startfeld bis hin zum letzten gültigen pressed Field
	 */
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
		int length = getLength();
		if (game.canThereBeAShip(startX, startY, lastX, lastY, length)) {
			game.createShip(startX, startY, lastX, lastY, length);
		}
	}

	private int getLength() {
		int l=0;
		if ( Math.abs(lastY-startY) > Math.abs(lastX-startX) )
			l = Math.abs(lastY-startY);
		if ( Math.abs(lastY-startY) < Math.abs(lastX-startX) )
			l = Math.abs(lastX-startX);
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
