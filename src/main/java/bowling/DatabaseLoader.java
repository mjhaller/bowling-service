package bowling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
		game.setScoringStrategyType(GameType.TENPIN);
		this.repository.save(game);
	}
}