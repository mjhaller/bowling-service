package bowling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import bowling.game.Game;
import bowling.repository.GameRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final GameRepository repository;

	@Autowired
	public DatabaseLoader(GameRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(String... strings) throws Exception {
		Game game = new Game();
		this.repository.save(game);
	}
}