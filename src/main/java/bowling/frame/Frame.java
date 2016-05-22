package bowling.frame;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.data.rest.core.annotation.RestResource;

import bowling.AbstractEntity;
import bowling.game.Game;
import bowling.game.GameType;

@Entity
public class Frame extends AbstractEntity {
	
	private Integer number;

	@Transient
	private Integer frameScore;
	
	@Transient
	private Integer runningScore;

	
	@OneToMany(mappedBy = "frame", cascade = CascadeType.ALL)
	private LinkedList<Ball> balls = new LinkedList<>();
	
	@Enumerated(EnumType.STRING)
	private FrameState frameState = FrameState.INITIAL;
	
	@ManyToOne 
	@RestResource(exported = false)
	private Game game;

	@Transient
	private Integer nextBall;
	
	
	public boolean isLastFrame()
	{
		if (GameType.TENPIN.maxFrames() == getNumber())
		{
			return true;
		}
		return false;
	}
	
	public boolean addBall(Ball ball)
	{
		if (getBalls().size() > maxBalls())
		{
			throw new IllegalArgumentException("Maximum balls for this frame reached: " + maxBalls());
		}
		ball.setNumber(getBalls().size() + 1);
		ball.resolveMark();
		return getBalls().add(ball);
	}
	
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getFrameScore() {
		return frameScore;
	}
	
	public static class FrameContext
	{
		private FrameState state;
		private Iterator<Ball> balls;
		private Integer score = null;
		private Integer nextBallToPlay;
		
		public FrameContext(FrameState state, Iterator<Ball> balls) {
			super();
			this.state = state;
			this.balls = balls;
		}
		
		public void setNextBallToPlay(Integer nextBallToPlay)
		{
			this.nextBallToPlay = nextBallToPlay;
		}
		
		public Ball nextBall()
		{
			if (balls.hasNext())
			{
				return balls.next();
			}
			return null;
		}
		
		public FrameState getState() {
			return state;
		}

		public void setState(FrameState state) {
			this.state = state;
		}

		public void addScore(Integer score) {
			if (this.score == null)
			{
				this.score = 0;
			}
			this.score += score;
		}				
		
		public Integer getScore() {
			return score;
		}
	}
	
	public void resolveState(FrameContext context)
	{
		while (context.getState().calculate(context));
		this.frameScore = context.getScore();
		this.frameState = context.getState();
	}
	
	public int maxBalls()
	{
		return GameType.TENPIN.maxBalls(this);
	}
	
	public int maxPins()
	{
		return GameType.TENPIN.maxPins();
	}
	
	
	//////////////////YAY JAVA!////////////////////
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


	@Override
	public String toString() {
		return "Frame [number=" + number + ", frameState=" + frameState + "]";
	}

}
