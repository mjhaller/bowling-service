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
import bowling.frame.Ball;
import bowling.frame.Frame;
import bowling.frame.FrameScoringContext;
import bowling.frame.FrameScoringState;

@Entity
public class Game extends AbstractEntity implements Loggable {

	@Enumerated(EnumType.STRING)
	private GameType gameType = GameType.TENPIN;
	
	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
	private LinkedList<Frame> frames = new LinkedList<>();
	
	@ManyToOne
	@RestResource(exported = false)
	private Player player;
	
	@Transient
	private Integer totalScore = 0;
	
	@Transient
	private Frame nextFrame;
	

	public Frame getNextFrame()
	{
		return this.nextFrame;
	}
	
	public void setNextFrame()
	{
		Frame last = getFrames().peekLast();
		this.nextFrame = last; 
		if (last == null || (last.getFrameScoringState().isPlayNextFrame() && !last.isLastFrame()))
		{
			Frame frame = new Frame();
			frame.setGame(this);
			frame.setNumber(last == null ? 1 : last.getNumber() + 1);
			this.nextFrame = frame;
			return;
		}
		if (last.isLastFrame())
		{
			if (last.finished())
			{
				this.nextFrame = null;
			}
		}
	}
	
	
	public void resolveStates()
	{
		LinkedList<Ball> allBalls = new LinkedList<>(); 
		for (Frame frame : getFrames())
		{
			allBalls.addAll(frame.getBalls());
		}
		
		this.totalScore = 0;
		for (Frame frame : getFrames())
		{
			log().info(frame.toString());
			List<Ball> remainingBalls = new ArrayList<>();
			for (Ball ball : allBalls)
			{
				if (ball.getFrame().getNumber() >= frame.getNumber())
				{
					remainingBalls.add(ball);
				}
			}
			frame.resolveState(new FrameScoringContext(FrameScoringState.INITIAL,remainingBalls.iterator(), frame.isLastFrame()));
			if (frame.getFrameScoringState() == FrameScoringState.RESOLVED) 
			{
				this.totalScore += frame.getFrameScore(); 
			}
		}
		setNextFrame();
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

	public LinkedList<Frame> getFrames() {
		return frames;
	}

	public Integer getTotalScore() {
		return totalScore;
	}
}
