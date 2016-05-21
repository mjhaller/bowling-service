package bowling;

import bowling.frame.Frame;

public interface ScoringStrategy {
	
	public int maxFrames();
	
	public int maxBalls(Frame frame);

}
