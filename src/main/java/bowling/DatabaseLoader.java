package bowling;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import bowling.frame.Roll;
import bowling.game.Game;
import bowling.game.Player;
import bowling.repository.FrameRepository;
import bowling.repository.GameRepository;
import bowling.repository.PlayerRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	@Resource private GameRepository gameRepository;
	@Resource private PlayerRepository playerRepository;
	@Resource private FrameRepository frameRepository;


	@Override
	public void run(String... strings) throws Exception {

		Game game = new Game();
		Player player = new Player();
		playerRepository.save(player);

		game.setPlayer(player);

		game = gameRepository.save(game);
		
		game.getFrames().forEach(f -> {
			Roll roll = new Roll();
			f.addRoll(roll);
			roll = new Roll();
			f.addRoll(roll);
			frameRepository.save(f);
		});
	}
}