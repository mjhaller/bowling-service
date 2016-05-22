package bowling.frame;

import java.util.Iterator;

public class FrameScoringContext {
	private FrameScoringState scoringState;
	private Iterator<Roll> rolls;
	private Integer score = null;

	public FrameScoringContext(FrameScoringState state, Iterator<Roll> rolls) {
		super();
		this.scoringState = state;
		this.rolls = rolls;
	}

	public Roll nextRoll() {
		if (rolls.hasNext()) {
			return rolls.next();
		}
		return null;
	}

	public FrameScoringState getState() {
		return scoringState;
	}

	public void setState(FrameScoringState state) {
		this.scoringState = state;
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