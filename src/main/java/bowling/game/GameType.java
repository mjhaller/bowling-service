package bowling.game;

import bowling.ScoringStrategy;
import bowling.frame.Frame;

public enum GameType implements ScoringStrategy {
	TENPIN {

		@Override
		public int maxFrames() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int maxBalls(Frame frame) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	},
	FIVEPIN {

		@Override
		public int maxFrames() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int maxBalls(Frame frame) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	};
	
	
	
	
}
