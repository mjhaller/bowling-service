package bowling.frame;

import bowling.Loggable;

public interface FrameScoring extends Loggable {

	boolean calculate(FrameScoringContext context);
	
}
