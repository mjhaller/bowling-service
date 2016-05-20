package bowling;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class GamePlayer extends AbstractEntity {

	@ManyToOne
	private Game game;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
