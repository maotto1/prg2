package de.htw.ai.prg2.Battleshot;

import java.awt.EventQueue;
import control.GameControl;
import gui.SetShipScreen;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {	    	
    	GameControl game = new GameControl();
    	game.initialize();
    	
    	
    	
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
