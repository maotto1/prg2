package network;


import control.GameControl;
import fieldState.Response;
import model.Shot;

public class RemoteObject implements RemoteInterface{
	
	private GameControl game;
	
	public RemoteObject(GameControl game){
		this.game = game;
	}

	public Response treatShot(Shot shot) {
		return game.treatShot(shot);
	}

}
