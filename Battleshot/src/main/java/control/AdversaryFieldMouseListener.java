package control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.MyPanel;
import model.Shot;

public class AdversaryFieldMouseListener implements MouseListener{
	
	private final GameControl game;
	/**
	 * zur Vermeidung von Doppelklicks
	 */
	private int lastClickedX = -1, lastClickedY = -1;
	
	public AdversaryFieldMouseListener(GameControl game){
		this.game = game;
	}
	

	public void mouseClicked(MouseEvent e) {
		int x = ((MyPanel) e.getComponent()).x;
		int y = ((MyPanel) e.getComponent()).y;
		if (lastClickedX != x && lastClickedY != y ) {
			Shot shot = new Shot(x, y);
			game.fire(shot);
		}
		lastClickedX = x; 
		lastClickedY = y;
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {	
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
	
	

}
