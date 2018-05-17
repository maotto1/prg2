package gamefield;

import fieldState.KnownFieldState;
import fieldState.State;

public class AdversaryField extends Field{
	
	private KnownFieldState state;
	
	AdversaryField(int x, int y) {
		super(x, y);
		state = KnownFieldState.UNKNOWN;
	}
	
	
	/**
	 * rules for change of States are: 
	 * UNKNOWN -> * 
	 * WATER -> WATER
	 * HIT -> SUNK
	 * @return whether change was possible
	 */
	@Override
	public boolean changeState(State state, int... shipId) {
		boolean succes = false;
		if (this.state == KnownFieldState.UNKNOWN) {
			this.state  = (KnownFieldState) state;
			succes = true;
		}
		else if (this.state == KnownFieldState.WATER && state == KnownFieldState.WATER) {
			succes = true;
		}
		else if (this.state == KnownFieldState.HIT && state == KnownFieldState.SUNK) {
			this.state  = (KnownFieldState) state;
			succes = true;
		}
		else if (this.state == KnownFieldState.HIT && state == KnownFieldState.HIT) {
			succes = true;
		}
		return succes;
	}

}
