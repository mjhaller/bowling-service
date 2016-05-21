package bowling.repository;

import org.springframework.data.repository.CrudRepository;

import bowling.game.Game;

public interface GameRepository extends CrudRepository<Game, Long>  {

	
}
