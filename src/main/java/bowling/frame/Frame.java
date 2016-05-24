package bowling.frame;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	//TODO: move eager fetchtype out of here and configure that at the repository level
	@OneToMany(mappedBy = "frame", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	private List<Roll> rolls = new LinkedList<>();
	
	@ManyToOne 
	@RestResource(exported = false)
	private Game game;

	public Frame(Integer number) {
		super();
		this.number = number;
	}
	
	public Frame() {
		super();
	}
	
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

	public boolean isLastFrame()
	{
		if (GameType.TENPIN.maxFrames() == getNumber())
		{
			return true;
		}
		return false;
	}
	
	/**
	 *  Bonus setter!
	 *  
	 *  Calculates and set the frame score based on the status of future rolls
	 *  a null score indicates the frame is not yet scorable
	 */
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

	

	
	// getters, setters, convenience methods below
	
	
	public boolean addRoll(Roll roll)
	{
		//roll.setFrame(this);
		return getRolls().add(roll);
	}
	
	public void setRolls(List<Roll> rolls) {
		this.rolls = rolls;
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
	

	@Override
	public String toString() {
		return "Frame [number=" + number + ", frameScore=" + frameScore + ", rolls.size()=" + rolls.size() + "]";
	}



}
