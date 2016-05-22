package bowling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import bowling.frame.Frame;
import bowling.frame.Roll;
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
		Frame frame = new Frame();
		frame.setNumber(1);
		Roll roll = new Roll();
		roll.setPins(5);
		frame.addRoll(roll);
		game.addFrame(frame);
		this.repository.save(game);
	}
}