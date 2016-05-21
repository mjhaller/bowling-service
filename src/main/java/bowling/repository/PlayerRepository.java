package bowling.repository;

import org.springframework.data.repository.CrudRepository;

import bowling.game.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {

}
