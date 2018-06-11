package de.htw.ai.prg2.Battleshot;

import java.awt.EventQueue;
import control.GameControl;
import gui.NetworkAplicationWindow;
import gui.SetShipScreen;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {	    	
    	final GameControl game = new GameControl();
    	game.initialize();
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NetworkAplicationWindow frame = new NetworkAplicationWindow(game);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    	//
    	
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SetShipScreen frame = new SetShipScreen(game);
					frame.setVisible(true);
					frame.setTitle("Set Ships");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
    	/*
    	
    	Thread t1 = new Thread();
    	t1.start();
    	try {
			t1.join();
			System.out.println();
		} catch (InterruptedException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
      
    	*/
    			
    }

        
        
  
}
