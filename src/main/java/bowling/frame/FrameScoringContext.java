package bowling.frame;

import java.util.Iterator;

public class FrameScoringContext {
	private FrameScoringState state;
	private Iterator<Ball> balls;
	private Integer score = null;
	private boolean lastFrame;

	public FrameScoringContext(FrameScoringState state, Iterator<Ball> balls, boolean lastFrame) {
		super();
		this.state = state;
		this.balls = balls;
		this.lastFrame = lastFrame;
	}

	public boolean isLastFrame() {
		return lastFrame;
	}

	public Ball nextBall() {
		if (balls.hasNext()) {
			return balls.next();
		}
		return null;
	}

	public FrameScoringState getState() {
		return state;
	}

	public void setState(FrameScoringState state) {
		this.state = state;
	}

	public void addScore(Integer score) {
		if (this.score == null) {
			this.score = 0;
		}
		this.score += score;
	}

	public Integer getScore() {
		return score;
	}
}