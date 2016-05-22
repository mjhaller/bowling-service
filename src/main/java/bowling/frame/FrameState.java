package bowling.frame;

import bowling.Loggable;
import bowling.frame.Frame.FrameContext;

public enum FrameState implements FrameScoring, Loggable {
	INITIAL {
		@Override
		public boolean calculate(FrameContext context) {
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
	OPEN {
		@Override
		public boolean calculate(FrameContext context) {
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
	SPARE {

		@Override
		public boolean calculate(FrameContext context) {
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
	STRIKE_BALL1 {

		@Override
		public boolean calculate(FrameContext context) {
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
	STRIKE_BALL2 {

		@Override
		public boolean calculate(FrameContext context) {
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
	RESOLVED {

		@Override
		public boolean calculate(FrameContext context) {
			context.setNextBallToPlay(1);
			return false;
		}
	}
}
