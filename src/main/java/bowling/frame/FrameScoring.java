package bowling.frame;

import bowling.Loggable;

public interface FrameScoring extends Loggable {

	boolean calculate(FrameScoringContext context);
	
	default boolean checkNextAndTransition(FrameScoringContext context, FrameScoringState newState) {
		Ball nextBall = context.nextBall();
		if (nextBall == null) {
			return false;
		}
		log().info(nextBall.toString());
		context.addScore(nextBall.score());
		context.setState(newState);
		return true;
	}
}
