package bowling.frame;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import bowling.AbstractEntity;
import bowling.game.Game;

@Entity
public class Frame extends AbstractEntity {
	
	private Integer number;
	
	private Integer score;
	
	@OneToMany(mappedBy = "frame")
	private List<Ball> balls = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private FrameState frameState;
	
	@ManyToOne 
	private Game game;
	
	
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public FrameState getFrameState() {
		return frameState;
	}
	public void setFrameState(FrameState frameState) {
		this.frameState = frameState;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public List<Ball> getBalls() {
		return balls;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((balls == null) ? 0 : balls.hashCode());
		result = prime * result + ((game == null) ? 0 : game.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Frame other = (Frame) obj;
		if (balls == null) {
			if (other.balls != null)
				return false;
		} else if (!balls.equals(other.balls))
			return false;
		if (game == null) {
			if (other.game != null)
				return false;
		} else if (!game.equals(other.game))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

}
