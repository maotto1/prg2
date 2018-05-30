package control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.MyPanel;
import model.Shot;

public class AdversaryFieldMouseListener implements MouseListener{
	
	private final GameControl game;
	
	public AdversaryFieldMouseListener(GameControl game){
		this.game = game;
	}
	

	public void mouseClicked(MouseEvent e) {
		Shot shot = new Shot(((MyPanel) e.getComponent()).x, ((MyPanel) e.getComponent()).y);
		game.fire(shot);
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
