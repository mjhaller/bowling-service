package bowling.frame;

import java.util.LinkedList;

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
	private FrameScoringState frameScoringState = FrameScoringState.INITIAL;
	
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
	
	public boolean finished()
	{
		return maxBalls() == getBalls().size();
	}
	
	public boolean addBall(Ball ball)
	{
		int newSize = getBalls().size() + 1;
		if (newSize > maxBalls())
		{
			throw new IllegalArgumentException("Maximum balls for this frame reached: " + maxBalls());			
		}
		ball.setNumber(newSize);
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
	
	public void resolveState(FrameScoringContext context)
	{
		while (context.getState().calculate(context));
		this.frameScore = context.getScore();
		this.frameScoringState = context.getState();
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
	public FrameScoringState getFrameScoringState() {
		return frameScoringState;
	}
	public void setFrameScoringState(FrameScoringState frameScoringState) {
		this.frameScoringState = frameScoringState;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public LinkedList<Ball> getBalls() {
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
		return "Frame [number=" + number + ", frameScoringState=" + frameScoringState + "]";
	}

}
