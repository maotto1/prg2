package gamefield;

import java.util.ArrayList;

import fieldState.Response;
import fieldState.State;
import game.Shot;

public abstract class GameField {
	protected final Field[][] fields = new Field[FIELD_SIZE[0]][FIELD_SIZE[1]];
	public static final int[] FIELD_SIZE = {10,10};
	protected int remainingShips;
	
	public abstract Response treatShot(Shot shot);
	
	public ArrayList<Field> getNeighbours(Field field) {
		int x0 = field.getXCoordinate();
		int y0 = field.getYCoordinate();
		/*
		int neighbours = 4;
		if (field.getXCoordinate() == 0 || field.getXCoordinate() == FIELD_SIZE[0]-1)
			neighbours -= 1;
		if (field.getYCoordinate() == 0 || field.getYCoordinate() == FIELD_SIZE[1]-1)
			neighbours -= 1; */
		ArrayList<Field> response = new ArrayList<Field>();
		for (int x = -1;  x <= 1; x += 2 ) {
			if (x0 + x >= 0 && x0 +x < FIELD_SIZE[0]) {
				for (int y = -1;  y <= 1; y += 2 ) {
					if (y0 + y >= 0 && y0 +y < FIELD_SIZE[1]) {
						response.add(fields[x0+x][y0+y]);
					}
				}
			}
		}
		return response;
	}
	
	public abstract ArrayList<? extends State> getFieldStates();

}
