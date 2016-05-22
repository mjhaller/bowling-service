package bowling.frame;

import java.util.ArrayList;
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
	private List<Roll> rolls = new LinkedList<>();
	
	@Enumerated(EnumType.STRING)
	private FrameScoringState frameScoringState = FrameScoringState.INITIAL;
	
	@ManyToOne 
	@RestResource(exported = false)
	private Game game;

	@Transient
	private Integer nextRoll;
	
	
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
		return maxRolls() == getRolls().size();
	}
	
	public boolean addRoll(Roll roll)
	{
		int newSize = getRolls().size() + 1;
		if (newSize > maxRolls())
		{
			throw new IllegalArgumentException("Maximum rolls for this frame reached: " + maxRolls());			
		}
		roll.setNumber(newSize);
		roll.resolveMark();
		return getRolls().add(roll);
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
	
	public int maxRolls()
	{
		return GameType.TENPIN.maxRolls(this);
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
	public List<Roll> getRolls() {
		return rolls;
	}
	
	@Override
	public String toString() {
		return "Frame [number=" + number + ", frameScoringState=" + frameScoringState + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
