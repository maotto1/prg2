package model;

import java.util.ArrayList;

import fieldState.KnownFieldState;
import fieldState.Response;

public class AdversaryGameField extends GameField<KnownFieldState> {
	
	private int sunkedShips = 0;
	private Shot lastShot = null;
	
	public AdversaryGameField(){
		fields = new AdversaryField[FIELD_SIZE[0]][FIELD_SIZE[1]];
		for (int i=0; i<FIELD_SIZE[0]; i++) {
			for (int j=0; j<FIELD_SIZE[1]; j++) {
				fields[i][j] = new AdversaryField(i,j);
			}
		}
	}
	
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
				//treatNeighbourFields(field);
			}
		}
		else if (response == Response.HIT_AND_SUNK || response == Response.HIT_AND_SUNK_LOOSED) {
			++sunkedShips;
			if (field.changeState(KnownFieldState.SUNK)) {
				treatNeighbourFieldsOfSunkedShip(field);
			}
		}

	}
	
	/**
	 * marks !!!after a ship was sunk the water fields
	 * @param field
	 */
	private void treatNeighbourFieldsOfSunkedShip(Field<KnownFieldState> field) {
		ArrayList<Field<KnownFieldState>> neighbours = this.getNeighbours(field);
		for (Field<KnownFieldState> neighbour : neighbours) {
			if (neighbour.getState() == KnownFieldState.HIT) {
				neighbour.changeState(KnownFieldState.SUNK);
				treatNeighbourFieldsOfSunkedShip(neighbour);
				
			}
			if (neighbour.getState() == KnownFieldState.UNKNOWN)
				neighbour.changeState(KnownFieldState.WATER);
		}
	}

	@Override
	public ArrayList<ArrayList<KnownFieldState>> getFieldStates() {
		ArrayList<ArrayList<KnownFieldState>> states = new ArrayList<ArrayList<KnownFieldState>>();
		ArrayList<KnownFieldState> statesRow;
		for (Field<KnownFieldState>[] fieldRow : this.fields) {
			statesRow = new ArrayList<KnownFieldState>();
			for (Field<KnownFieldState> fieldEntry : fieldRow) {
				statesRow.add((KnownFieldState) fieldEntry.getState());
			}
			states.add(statesRow);
		}
		return states;
	}

	public int getSunkedShips() {
		return sunkedShips;
	}


}
