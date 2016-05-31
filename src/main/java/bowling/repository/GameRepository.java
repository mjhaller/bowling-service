package bowling.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import bowling.game.Game;
import bowling.game.Game.Inline;

@RepositoryRestResource(excerptProjection = Inline.class)
public interface GameRepository extends CrudRepository<Game, Long>  {

	
}
