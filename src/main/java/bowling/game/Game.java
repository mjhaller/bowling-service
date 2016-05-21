package bowling.game;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import bowling.AbstractEntity;
import bowling.frame.Frame;

@Entity
public class Game extends AbstractEntity {

	@Enumerated(EnumType.STRING)
	private GameType gameType = GameType.TENPIN;
	
	@OneToMany(cascade = CascadeType.ALL)
	public List<Frame> frames = new ArrayList<>();
	
	@ManyToOne
	public Player player;
	
	public boolean addToFrames(Frame frame)
	{
		if ( !getFrames().contains(frame) )
			return getFrames().add(frame);
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
}
