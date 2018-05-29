package gamefield;

import fieldState.SetState;

public class OwnField extends Field<SetState>{
	
	private int shipId = -1;
	private boolean hit;
	
	public OwnField(int x, int y) {
		super(x, y);
		state = SetState.WATER;
		hit = false;
	}
	
	public int getId() {
		return this.shipId;
	}

	@Override
	public boolean changeState(SetState state, int... shipId) {
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

	public boolean isHit() {
		return hit;
	}

	public void shotOn() {
		this.hit = true;
	}

}
