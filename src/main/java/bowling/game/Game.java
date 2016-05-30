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
@RestResource
public class Game extends AbstractEntity implements Loggable {

	@Enumerated(EnumType.STRING)
	private GameType gameType = GameType.TENPIN;
	
	//TODO: move eager to be configured per query (maybe)
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@RestResource(exported = true)
	private List<Frame> frames = new ArrayList<>();
	
	@ManyToOne
	private Player player;
	
	private Integer totalScore = null;
	
	/**
	* This is here as a convenience so we don't have to worry about creating
	* frames on the fly.
	* 
	* It also reflects the game view for a scoring card
	*/
	private void initializeFrames() {
		if (getFrames().isEmpty()) {
			frames = new ArrayList<>();
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
	
	/**
	 * Calculates the score of each frame (null score indicates the frame is not scorable) 
	 * as well as the total score of the game
	 * 
	 * TODO: currently score iterates through all frames - add frame state (lastScorableFrame?) to reduce that 
	 * @return
	 */
	public Integer score()
	{
		this.totalScore = 0;
		for (Frame frame : getFrames())
		{
			frame.setFrameScore(0);
			LinkedList<Roll> futureRolls = getNextRolls(frame.getNumber());
			if (frame.isSpare())
			{
				frame.score(futureRolls.pollFirst());
			}
			if (frame.isStrike())
			{
				frame.score(futureRolls.pollFirst(),futureRolls.pollFirst());
			}
			if (frame.isOpen())
			{
				frame.setFrameScore(frame.naturalFrameScore());
			}
			log().debug("Scoring : " + frame.toString() );
			if (frame.getFrameScore() != null)
			{
				this.totalScore += frame.getFrameScore();
			}
		}
		return getTotalScore();
	}
	
	/**
	 * Finds the correct frame to hold the ball, and adds the ball to it  
	 * 
	 * TODO: currently roll iterates through all frames - take and last frame
	 * TODO: could consider closing the game when you can't place any more balls
	 * @param score - this is the number of pins knocked down
	 */
	public void roll(Integer score) {

		Roll roll = new Roll();
		roll.setPins(score);
		Frame nextFrame = nextFrame();
		if (nextFrame != null)
			nextFrame.addRoll(roll);
	}
	
	/**
	 * Finds the next frame of the game  
	 * 
	 */
	public Frame nextFrame() {

		Iterator<Frame> framesIterator = frames.iterator();
		Frame currentFrame = framesIterator.next();
		while (currentFrame.canAdd() || framesIterator.hasNext()) {
			if (currentFrame.canAdd())
			{
				return currentFrame;
			}
			if (currentFrame.isOpen() || currentFrame.isStrike() || currentFrame.isSpare()) {
				if (framesIterator.hasNext())
					currentFrame = framesIterator.next();
			}
		}
		return null;
	}

	/**
	 * In order to score a frame with a mark (spare/strike) you need to look ahead to the next rolls
	 * and this method returns those next rolls.
	 * 
	 * @param frameNumber - this is the frame you are scoring
	 * @return
	 */
	private LinkedList<Roll> getNextRolls(int frameNumber) {
		LinkedList<Roll> rolls = new LinkedList<>();
		frameNumber++; // get the frames after the scoring frame
		while (rolls.size() < 2)
		{
			if (frameNumber > getFrames().size()) {
				return rolls;
			}
			List<Roll> frameRolls = getFrames().get(frameNumber-1).getRolls();
			//TODO: rethink the 
			if (frameRolls.isEmpty())
				return rolls;
			rolls.addAll(frameRolls);
			frameNumber++;
		}
		return rolls;
	}


	// getters, setters, convenience methods below
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
