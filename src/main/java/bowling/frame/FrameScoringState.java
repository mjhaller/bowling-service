package bowling.frame;

public enum FrameScoringState implements FrameScoring {
	INITIAL(false) {
		@Override
		public boolean calculate(FrameScoringContext context) {
			Roll nextRoll = context.nextRoll();
			if (nextRoll == null) {
				log().trace("null roll"); 
				return false;
			}
			log().trace(nextRoll.toString());
			context.addScore(nextRoll.getPins());
			Mark mark = nextRoll.getMark();
			if (mark == Mark.STRIKE) {
				context.setState(STRIKE_BALL1);
				return true;
			}
			if (mark == Mark.STRIKE_LAST) {
				context.setState(STRIKE_BALL2);
				return true;
			}
			if (mark == Mark.SPARE) {
				context.setState(SPARE);
				return true;
			}
			if (mark == Mark.OPEN) {
				context.setState(OPEN);
				return true;
			}
			return false;

		}
	},
	OPEN(false) {
		@Override
		public boolean calculate(FrameScoringContext context) {
			Roll nextRoll = context.nextRoll();
			if (nextRoll == null) {
				return false;
			}
			log().trace(nextRoll.toString());
			Mark mark = nextRoll.getMark();
			if (mark == Mark.SPARE) {
				context.setState(SPARE);
				return true;
			}

			if (mark == Mark.OPEN) {
				context.setState(RESOLVED);
				return true;
			}
			return false;
		}
	},
	SPARE(true) {

		@Override
		public boolean calculate(FrameScoringContext context) {
			Roll nextRoll = context.nextRoll();
			if (nextRoll == null) {
				return false;
			}
			log().trace(nextRoll.toString());
			context.addScore(nextRoll.getPins());
			context.setState(RESOLVED);
			return true;
		}

	},
	STRIKE_BALL1(true) {

		@Override
		public boolean calculate(FrameScoringContext context) {
			Roll nextRoll = context.nextRoll();
			if (nextRoll == null) {
				return false;
			}
			log().trace(nextRoll.toString());
			context.addScore(nextRoll.getPins());
			context.setState(STRIKE_BALL2);
			return true;
		}
	},
	STRIKE_BALL2(true) {

		@Override
		public boolean calculate(FrameScoringContext context) {
			Roll nextRoll = context.nextRoll();
			if (nextRoll == null) {
				return false;
			}
			log().trace(nextRoll.toString());
			context.addScore(nextRoll.getPins());
			context.setState(RESOLVED);
			return true;
		}
	},
	RESOLVED(true) {

		@Override
		public boolean calculate(FrameScoringContext context) {
			return false;
		}
	};
	
	private boolean playNextFrame;
	
	FrameScoringState(boolean playNextFrame)
	{
		this.playNextFrame = playNextFrame;
	}
	
	public boolean isPlayNextFrame()
	{
		return this.playNextFrame;
	}
}
