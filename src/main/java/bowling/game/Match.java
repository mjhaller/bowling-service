package bowling.game;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import bowling.AbstractEntity;

@Entity
public class Match extends AbstractEntity {
	
	@OneToMany
	public List<Game> games;

	public List<Game> getGames() {
		return games;
	}
}
