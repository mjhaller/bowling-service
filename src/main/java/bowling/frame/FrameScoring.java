package bowling.frame;

import bowling.Loggable;
import bowling.frame.Frame.FrameContext;

public interface FrameScoring extends Loggable {

	boolean calculate(FrameContext context);
	
	default boolean checkNextAndTransition(FrameContext context, FrameState newState) {
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
