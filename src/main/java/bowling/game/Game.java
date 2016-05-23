package bowling.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.data.rest.core.annotation.RestResource;

import bowling.AbstractEntity;
import bowling.Loggable;
import bowling.frame.Frame;
import bowling.frame.Roll;

@Entity
public class Game extends AbstractEntity implements Loggable {

	@Enumerated(EnumType.STRING)
	private GameType gameType = GameType.TENPIN;
	
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Frame> frames = new ArrayList<>();
	
	@Transient
	private List<Roll> rolls = new ArrayList<>();

	
	@ManyToOne
	@RestResource(exported = false)
	private Player player;
	
	@Transient
	private Integer totalScore = 0;
	
	@Transient
	private Integer nextFrame = 1;
	
	private void initializeFrames() {
		if (frames.isEmpty()) {
			for (int i = 0; i < gameType.maxFrames(); i++) {
				Frame frame = new Frame(i + 1);
				frame.setGame(this);
				frames.add(frame);
			}
		}
	}
	
	
	public Game(){
		initializeFrames();
	}
	
	public Integer score()
	{
		LinkedList<Roll> allRolls = allRolls();
		this.totalScore = 0;
		for (Frame frame : getFrames())
		{
			frame.setFrameScore(0);
			LinkedList<Roll> remainingRolls = rollsRemaining(allRolls, frame);
			if (frame.isSpare())
			{
				frame.setFrameScore(remainingRolls.pollFirst());
			}
			if (frame.isStrike())
			{
				frame.setFrameScore(remainingRolls.pollFirst(),remainingRolls.pollFirst());
			}
			if (frame.isOpen())
			{
				frame.setFrameScore(frame.naturalFrameScore());
			}
			log().debug("Scoring : " + frame.toString());
			if (frame.getFrameScore() != null)
			{
				this.totalScore += frame.getFrameScore();
			}

		}
		return getTotalScore();
	}
	
	public void roll(Integer score) {

		Roll roll = new Roll();
		roll.setPins(score);

		Iterator<Frame> framesIterator = frames.iterator();
		Frame currentFrame = framesIterator.next();
		while (currentFrame.canAdd() || framesIterator.hasNext()) {
			if (currentFrame.canAdd())
			{
				currentFrame.addRoll(roll);
				log().info("Added " + roll + " to " + currentFrame.toString());
				return;
			}
			if (currentFrame.isOpen() || currentFrame.isStrike() || currentFrame.isSpare()) {
				if (framesIterator.hasNext())
					currentFrame = framesIterator.next();
			}
		}
	}

	private LinkedList<Roll> allRolls() {
		LinkedList<Roll> allRolls = new LinkedList<>(); 
		for (Frame frame : getFrames())
		{
			allRolls.addAll(frame.getRolls());
		}
		return allRolls;
	}


	private LinkedList<Roll> rollsRemaining(LinkedList<Roll> allRolls, Frame frame) {
		LinkedList<Roll> remainingRolls = new LinkedList<>();
		for (Roll roll : allRolls)
		{
			if (roll.getFrame().getNumber() > frame.getNumber())
			{
				remainingRolls.add(roll);
			}
		}
		return remainingRolls;
	}


	public boolean addFrame(Frame frame)
	{
		if ( !getFrames().contains(frame) )
		{
			frame.setNumber(getFrames().size() + 1);
			return getFrames().add(frame);
		}
		return false;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public List<Frame> getFrames() {
		return frames;
	}

	public Integer getTotalScore() {
		return totalScore;
	}


	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}


	@Override
	public String toString() {
		return "Game [totalScore=" + totalScore + "]";
	}


	
	
}
