package model;

import java.util.ArrayList;

import fieldState.Response;
import fieldState.SetState;

public class Ship {
	
	public final int length, id;
	private final MyGameField gameField;
	private int remainingParts;
	private final ArrayList<OwnField> fields;
	
	public Ship(int length, int id, MyGameField gameField, ArrayList<OwnField> fields){
		this.length = length;
		remainingParts = length;
		this.gameField = gameField;
		this.id = id;
		this.fields = fields;
		for (OwnField field : this.fields ) {
			field.changeState(SetState.SHIP, id);
			blockNeighbours(field);
		}
	}
	
	private void blockNeighbours(OwnField field) {
		for (Field<SetState> neighbourField : gameField.getNeighbours(field)) {
			if (neighbourField.getState() == SetState.WATER)
				neighbourField.changeState(SetState.BLOCKED);
		}
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
			if (field.getXCoordinate() == shot.getX() && field.getYCoordinate() == shot.getY()) {
				field.shotOn();
				if (field.getState() == SetState.SHIP) {
					--remainingParts;
					field.changeState(SetState.WATER);
					reponse = Response.HIT;
				}
			}
		}
		if (remainingParts == 0)
			reponse = Response.HIT_AND_SUNK;
		return reponse;
	}
	
	
	/**
	 * sets state of all fields from the ship and of all neighbourfields (which don't touch other ships) to SetState.WATER
	 */
	public void destroy() {
		boolean noNeighbourShip;
		for (OwnField field : fields) {
			field.changeState(SetState.WATER);
			for (Field<SetState> neighbourField : gameField.getNeighbours(field)) {
				noNeighbourShip = true;
				for (Field<SetState> neighbourFromNeighbour : gameField.getNeighbours(neighbourField)) {
					if (neighbourFromNeighbour.getState() == SetState.SHIP)
						noNeighbourShip = false;
				}
				if (noNeighbourShip)
					neighbourField.changeState(SetState.WATER);
			}
				
		}
	}

}
