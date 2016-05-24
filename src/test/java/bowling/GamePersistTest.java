package bowling;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import bowling.frame.Roll;
import bowling.game.Game;
import bowling.game.Player;
import bowling.repository.GameRepository;
import bowling.repository.PlayerRepository;

/**
 * Placeholder for testing persistence - as of this writing it 
 * does not work with one-to-many cascades - it creates extra frames
 * 
 * @author mhaller
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BowlingServiceApplication.class)
@WebAppConfiguration
@Ignore
public class GamePersistTest implements GameTester {

	@Resource private GameRepository gameRepository;
	@Resource private PlayerRepository playerRepository;

	Game game;
	
	@Before
	public void setup()
	{
		this.game = new Game();
	}
	
	public Game game(){ return this.game; }
	
	
	@Test
	public void gameSave() {
		Game game = new Game();
		Player player = new Player();
		playerRepository.save(player);

		game.setPlayer(player);
		assertThat(game.getFrames(), hasSize(10));

		game.getFrames().forEach(f -> {
			Roll roll = new Roll();
			f.addRoll(roll);
			roll = new Roll();
			f.addRoll(roll);
		});
		game = gameRepository.save(game);

		Game persistedGame = gameRepository.findOne(game.getId());
		assertThat(persistedGame, notNullValue());
		assertThat(persistedGame.getFrames(), hasSize(10));
	}
	
	@Test
	public void perfectGameSave()
	{
		assertThat(game.getFrames(),hasSize(10));
		gameRepository.save(game);

		game = gameRepository.findOne(game.getId());
		assertThat(game,notNullValue());
		assertThat(game.getFrames(),hasSize(10));
		addRangeOfRolls(21,10);
		assertThat(game.score(), equalTo(300));
		
		game = gameRepository.save(game);
		
		game = gameRepository.findOne(game.getId());
		assertThat(game.score(), equalTo(300));
	}
	

}
