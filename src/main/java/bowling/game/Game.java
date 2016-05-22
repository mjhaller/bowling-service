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
import bowling.frame.Frame.FrameContext;
import bowling.frame.FrameState;

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
	private Integer nextFrame;
	

	public Integer getNextFrame()
	{
		return this.nextFrame;
	}
	
	public void setNextFrame()
	{
		Frame last = getFrames().getLast();
		this.nextFrame = last.getNumber(); 
		if (last.getFrameState() == FrameState.RESOLVED)
		{
			this.nextFrame = last.getNumber() + 1;
		}
		if (last.isLastFrame())
		{
			this.nextFrame = last.getNumber() + 1;
		}
	}
	
	
	public void resolveStates()
	{
		LinkedList<Ball> allBalls = new LinkedList<>(); 
		for (Frame frame : getFrames())
		{
			allBalls.addAll(frame.getBalls());
		}
		
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
			frame.resolveState(new FrameContext(FrameState.INITIAL,remainingBalls.iterator()));
			if (frame.getFrameState() == FrameState.RESOLVED) 
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
