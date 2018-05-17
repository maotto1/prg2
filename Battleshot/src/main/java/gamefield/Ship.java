package gamefield;

import fieldState.Response;
import fieldState.SetState;
import game.Shot;

public class Ship {
	
	public final int length, id;
	private final MyGameField gameField;
	private int remainingParts;
	private final OwnField[] fields;
	
	Ship(int length, int id, MyGameField gameField, OwnField[] fields){
		this.length = length;
		remainingParts = length;
		this.gameField = gameField;
		this.id = id;
		this.fields = fields;
	}
	
	/**
	 * 
	 * @param shot
	 * @return <ul> <li>HIT   if the shot field has state SHIP </li>
	 *				<li>HIT_AND_SUNK   if it was the last not hidden field</li>
	 * 				<li>WATER   else</li>  </ul>
	 */
	public Response treatShot(Shot shot){
		Response reponse = Response.WATER;
		for (OwnField field : fields) {
			if (field.getXCoordinate() == shot.getX() && field.getXCoordinate() == shot.getY()
					&& field.getState() == SetState.SHIP) {
				--remainingParts;
				field.changeState(SetState.WATER);
				reponse = Response.HIT;
			}
		}
		if (remainingParts == 0)
			reponse = Response.HIT_AND_SUNK;
		return reponse;
	}
	
	
	/**
	 * sets state of all fields from the ship and of all neighbourfields to SetState.WATER
	 */
	public void destroy() {
		for (OwnField field : fields) {
			field.changeState(SetState.WATER);
			for (Field neighbourField : gameField.getNeighbours(field))
				neighbourField.changeState(SetState.WATER);
		}
	}

}
