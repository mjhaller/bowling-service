package bowling;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

@Entity
public class Game extends AbstractEntity {

	@Enumerated(EnumType.STRING)
	private GameType gameType = GameType.TENPIN;
	
	@OneToMany(mappedBy = "game")
	private List<GamePlayer> gamePlayers;

	public Game() {
	}
	
	public GameType getScoringStrategyType() {
		return gameType;
	}

	public void setScoringStrategyType(GameType gameType) {
		this.gameType = gameType;
	}


}
