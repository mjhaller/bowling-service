package bowling.game;

import bowling.ScoringStrategy;
import bowling.frame.Frame;

public enum GameType implements ScoringStrategy {
	TENPIN {

		@Override
		public int maxRolls(Frame frame) {
			if (frame.getNumber() == 10)
			{
				return 3;
			}
			return 2;
		}
		
	},
	FIVEPIN {

		@Override
		public int maxRolls(Frame frame) {
			throw new IllegalArgumentException("Unimplemented");
		}
		
	};
	
	
	
	
}
