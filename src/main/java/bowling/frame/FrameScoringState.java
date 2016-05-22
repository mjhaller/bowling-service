package bowling.frame;

import bowling.Loggable;

public enum FrameScoringState implements FrameScoring, Loggable {
	INITIAL(false) {
		@Override
		public boolean calculate(FrameScoringContext context) {
			Ball nextBall = context.nextBall();
			if (nextBall == null) {
				log().info("null ball"); 
				return false;
			}
			log().info(nextBall.toString());
			context.addScore(nextBall.score());
			Mark mark = nextBall.getMark();
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
			Ball nextBall = context.nextBall();
			if (nextBall == null) {
				return false;
			}
			log().info(nextBall.toString());
			Mark mark = nextBall.getMark();
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
			Ball nextBall = context.nextBall();
			if (nextBall == null) {
				return false;
			}
			log().info(nextBall.toString());
			context.addScore(nextBall.score());
			context.setState(RESOLVED);
			return true;
		}

	},
	STRIKE_BALL1(true) {

		@Override
		public boolean calculate(FrameScoringContext context) {
			Ball nextBall = context.nextBall();
			if (nextBall == null) {
				return false;
			}
			log().info(nextBall.toString());
			context.addScore(nextBall.score());
			context.setState(STRIKE_BALL2);
			return true;
		}
	},
	STRIKE_BALL2(true) {

		@Override
		public boolean calculate(FrameScoringContext context) {
			Ball nextBall = context.nextBall();
			if (nextBall == null) {
				return false;
			}
			log().info(nextBall.toString());
			context.addScore(nextBall.score());
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
