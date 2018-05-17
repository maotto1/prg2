package gamefield;

import java.util.ArrayList;

import fieldState.KnownFieldState;
import fieldState.Response;
import fieldState.SetState;
import fieldState.State;
import game.Shot;

public class AdversaryGameField extends GameField {
	
	private int sunkedShips = 0;
	private Shot lastShot = null;
	
	@Override
	public Response treatShot(Shot shot) {
		lastShot = shot;
		return null;
	}
	
	public void treatReponse(Response response) {
		AdversaryField field = (AdversaryField) fields[lastShot.getX()][lastShot.getY()];
		if (response == Response.WATER)
				field.changeState(KnownFieldState.WATER);
		else if (response == Response.HIT) {
			if (field.changeState(KnownFieldState.HIT)) {
				treatNeighbourFields(field);
			}
		}
		else if (response == Response.HIT) {
			if (field.changeState(KnownFieldState.HIT)) {
				//treatNeighbourFields(field);
			}
		}
		else if (response == Response.HIT_AND_SUNK || response == Response.HIT_AND_SUNK_LOOSED) {
			++sunkedShips;
			if (field.changeState(KnownFieldState.SUNK)) {
				treatNeighbourFields(field);
			}
		}

	}
	
	/**
	 * marks !!!after a ship was sunk the water fields
	 * @param field
	 */
	private void treatNeighbourFields(Field field) {
		ArrayList<Field> neighbours = this.getNeighbours(field);
		for (Field neighbour : neighbours) {
			if (neighbour.getState() == KnownFieldState.HIT) {
				neighbour.changeState(KnownFieldState.SUNK);
				treatNeighbourFields(neighbour);
				
			}
			if (neighbour.getState() == KnownFieldState.UNKNOWN)
				neighbour.changeState(KnownFieldState.WATER);
		}
	}

	@Override
	public ArrayList<? extends State> getFieldStates() {
		ArrayList<KnownFieldState> states = new ArrayList<KnownFieldState>();
		for (Field[] fieldRow : this.fields) {
			for (Field fieldEntry : fieldRow) {
				states.add((KnownFieldState) fieldEntry.getState());
			}
		}
		return states;
	}
}
