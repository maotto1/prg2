package gamefield;

import java.util.ArrayList;
import java.util.HashMap;

import fieldState.Response;
import fieldState.SetState;
import fieldState.State;
import game.Shot;

public class MyGameField extends GameField<SetState> {
	
	private HashMap<Integer,Ship> ships = new HashMap<Integer,Ship>();
	
	public MyGameField(){
		remainingShips = 0;
		fields = new OwnField[FIELD_SIZE[0]][FIELD_SIZE[1]];
		for (int i=0; i<FIELD_SIZE[0]; i++) {
			for (int j=0; j<FIELD_SIZE[1]; j++) {
				fields[i][j] = new OwnField(i,j);
			}
		}
	}
	
	@Override
	public Response treatShot(Shot shot) {
		OwnField field = (OwnField) fields[shot.getX()][shot.getY()];
		if (field.getState() == SetState.WATER || field.getState() == SetState.BLOCKED) {
			return Response.WATER;
		}
		else if (field.getState() == SetState.SHIP) {
			Ship ship = findShip(field);
			Response reponse = ship.treatShot(shot);
			if (reponse == Response.HIT_AND_SUNK) {
				// 2. Varariante: Schiff existieren lassen für Ausgabe   removeShip(ship.id);			
				ships.remove(ship.id);
				--remainingShips;
				if (remainingShips == 0)
					reponse = Response.HIT_AND_SUNK_LOOSED;
			}
			return reponse; 
		}
		return null;
	}
	
	
	private Ship findShip(OwnField field) {
		int id = field.getId();
		if (id == -1)  // -1 signifies: there isn't any  ship
			return null;
		return ships.get(id);
	}
	
	/**
	 * looking in neighborhood after other ships
	 * @param x0, y0 
	 * @param x1, y1
	 * @return
	 */
	public boolean fitShip(int x0, int y0, int x1, int y1) {
		ArrayList<OwnField> fields = getFieldsFromCoordinates(x0, y0, x1, y1);
		for (Field<SetState> field : fields) {
			if (field.getState() == SetState.BLOCKED || field.getState() == SetState.SHIP)
				return false;
			for (Field<SetState> neighbourField :  this.getNeighbours(field)) {		// redundant
				if (neighbourField.getState() == SetState.SHIP)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * delivers Fields from intern field variable, orders 
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @return
	 */
	private ArrayList<OwnField> getFieldsFromCoordinates(int x0, int y0, int x1, int y1) {
		ArrayList<OwnField> wantedFields = new ArrayList<OwnField>();
		if (x0 > x1) {
			int x = x1; 
			x1 = x0;
			x0 = x;
		}
		else if (y0 > y1) {
			int y = y1; 
			y1 = y0;
			y0 = y;
		}
		if (x0 == x1) {
			for (int i=0; i< y1-y0; i++) {
				wantedFields.add((OwnField) fields[x0][y0+i]);
			}
		}
		else if (y0 == y1) {
			for (int i=0; i< x1-x0; i++) {
				wantedFields.add((OwnField) fields[x0+i][y0]);
			}
		}
		return wantedFields;
	}

	/**
	 * unfertig : Status sezten
	 * @param ship
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @param id
	 * @return
	 */
	public boolean setShip(Ship ship,  int x0, int y0, int x1, int y1, int id) {
		if (!fitShip(x0, y0, x1, y1) || ships.containsKey(id) || id == -1)
			return false;
		ships.put(id, ship);
		++remainingShips;
		return true;
		
	}
	
	/**
	 * removes and destroys a ship
	 * @param shipId
	 * @return
	 */
	public boolean removeShip(int shipId) {
		if (!ships.containsKey(shipId))
			return false;
		ships.get(shipId).destroy();
		ships.remove(shipId);	
		--remainingShips;
		return true;
	}

	@Override
	public ArrayList<? extends State> getFieldStates() {
		ArrayList<SetState> states = new ArrayList<SetState>();
		for (Field<SetState>[] fieldRow : this.fields) {
			for (Field<SetState> fieldEntry : fieldRow) {
				states.add( fieldEntry.getState());
			}
		}
		return states;
	}

}
