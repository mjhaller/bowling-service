package bowling.game;

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
import bowling.Loggable;
import bowling.frame.Roll;
import bowling.frame.Frame;
import bowling.frame.FrameScoringContext;
import bowling.frame.FrameScoringState;

@Entity
public class Game extends AbstractEntity implements Loggable {

	@Enumerated(EnumType.STRING)
	private GameType gameType = GameType.TENPIN;
	
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	private List<Frame> frames = new ArrayList<>();
	
	@ManyToOne
	@RestResource(exported = false)
	private Player player;
	
	@Transient
	private Integer totalScore = 0;
	
	@Transient
	private Integer nextFrame;
	
	@Enumerated(EnumType.STRING)
	private GameState gameState = GameState.INITIAL;

	
	
	private void initializeFrames() {
		if (frames.isEmpty()) {
			for (int i = 0; i < gameType.maxFrames(); i++) {
				frames.add(new Frame());
			}
		}
	}
	
	
	public static class GameContext
	{
		GameState gameState;
		List<Frame> frames;
		Integer nextFrame;
		
		public GameContext(GameState gameState, List<Frame> list) {
			super();
			this.gameState = gameState;
			this.frames = list;
		}

		public Integer getNextFrame() {
			return nextFrame;
		}

		public void setNextFrame(Integer nextFrame) {
			this.nextFrame = nextFrame;
		}

		public GameState getGameState() {
			return gameState;
		}

		public void setGameState(GameState gameState) {
			this.gameState = gameState;
		}

		public LinkedList<Frame> getFrames() {
			return new LinkedList<Frame>(frames);
		}
	}
	
	public void setNextFrame(Integer nextFrame)
	{
		this.nextFrame = nextFrame;
	}
	
	public Integer getNextFrame() {
		return this.nextFrame;
	}
	
	
	public void score()
	{
		LinkedList<Roll> allRolls = allRolls();
		
		
		this.totalScore = 0;
		for (Frame frame : getFrames())
		{
			List<Roll> remainingRolls = rollsRemaining(allRolls, frame);
			frame.resolveState(new FrameScoringContext(FrameScoringState.INITIAL,remainingRolls.iterator()));
			if (frame.getFrameScoringState() == FrameScoringState.RESOLVED) 
			{
				this.totalScore += frame.getFrameScore(); 
			}
		}
		resolveState();
	}


	private LinkedList<Roll> allRolls() {
		LinkedList<Roll> allRolls = new LinkedList<>(); 
		for (Frame frame : getFrames())
		{
			allRolls.addAll(frame.getRolls());
		}
		return allRolls;
	}


	private List<Roll> rollsRemaining(LinkedList<Roll> allRolls, Frame frame) {
		List<Roll> remainingRolls = new ArrayList<>();
		for (Roll roll : allRolls)
		{
			if (roll.getFrame().getNumber() >= frame.getNumber())
			{
				remainingRolls.add(roll);
			}
		}
		return remainingRolls;
	}


	private void resolveState() {
		GameContext context = new GameContext(getGameState(),getFrames());
		getGameState().resolve(context);
		setGameState(context.getGameState());
		setNextFrame(context.getNextFrame());
		log().info(this.toString());
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


	public GameState getGameState() {
		return gameState;
	}


	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}


	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}


	@Override
	public String toString() {
		return "Game [totalScore=" + totalScore + ", nextFrame=" + nextFrame + ", gameState=" + gameState + "]";
	}


	
	
}
