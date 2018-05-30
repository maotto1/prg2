package model;

import fieldState.KnownFieldState;


public class AdversaryField extends Field<KnownFieldState>{
	
	//private KnownFieldState state;
	
	public AdversaryField(int x, int y) {
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
	public boolean changeState(KnownFieldState state, int... shipId) {
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
