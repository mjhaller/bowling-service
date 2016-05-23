package bowling.frame;

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
	private Integer frameScore = null;
	
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
	
	
	public Integer naturalFrameScore()
	{
		return this.rollOne().getPins() + (this.rollTwo() == null ? 0 : this.rollTwo().getPins());
	}
	
	public Roll rollOne()
	{
		return getRoll(0);
	}
	
	public Roll  rollTwo()
	{
		return getRoll(1);
	}
	
	public Roll rollBonus()
	{
		return getRoll(2);
	}

	public Roll getRoll(int index)
	{
		if (this.getRolls().size() > index)
		{
			return this.getRolls().get(index);
		}
		return null;
	}

	
	public Frame(Integer number) {
		super();
		this.number = number;
	}
	
	public Frame() {
		super();
	}

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
		roll.setFrame(this);
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
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public List<Roll> getRolls() {
		return rolls;
	}
	
	public void setFrameScore(Integer frameScore) {
		this.frameScore = frameScore;
	}
	
	public void setFrameScore(Roll... futureRolls) {
		// just add them up if on the last frame
		if (isLastFrame()) {
			this.frameScore = null;
			if (rollBonus() != null)
			{
				this.frameScore = naturalFrameScore() + rollBonus().getPins();	
			}
			return;
		}
		this.frameScore = naturalFrameScore();
		for (Roll roll : futureRolls) {
			if (roll == null) {
				this.frameScore = null;
				return;
			} else {
				this.frameScore += roll.getPins();
			}
		}
	}
	
	public boolean canAdd()
	{
		if ( lastFrameAddable())
		{
			return true;
		}
		return !(isOpen() || isStrike() || isSpare()); 
	}

	public boolean lastFrameAddable() {
		return isLastFrame() && this.getRolls().size() < 3;
	}

	public boolean isOpen() {
		return getRolls().size() == 2 && naturalFrameScore() < 10;
	}

	public boolean isStrike() {
		return  getRolls().size() > 0 && rollOne().getPins() == 10 ;
	}

	public boolean isSpare() {
		return !(getRolls().size() < 2) && naturalFrameScore() == 10;
	}

	@Override
	public String toString() {
		return "Frame [number=" + number + ", frameScore=" + frameScore + ", rolls.size()=" + rolls.size() + "]";
	}



}
