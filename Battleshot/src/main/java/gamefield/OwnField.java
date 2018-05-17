package gamefield;

import fieldState.SetState;
import fieldState.State;

public class OwnField extends Field{
	
	private int shipId = -1;
	private SetState state;
	
	OwnField(int x, int y) {
		super(x, y);
	}
	
	public int getId() {
		return this.shipId;
	}

	@Override
	public boolean changeState(State state, int... shipId) {
		if ((shipId.length == 0 || shipId[0] == -1) && state == SetState.SHIP) {
			System.out.println("wrong usage of changeState");
			return false;
		}
		if (this.state == SetState.SHIP) {
			this.shipId = -1;
		}
		if (state == SetState.SHIP) {
			this.shipId = shipId[0];
		}
		this.state = (SetState) state;
		return true;
	}

}
