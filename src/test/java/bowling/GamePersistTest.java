package bowling;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import bowling.TestBuilders.GameBuilder;
import bowling.game.Game;
import bowling.game.Player;
import bowling.repository.GameRepository;
import bowling.repository.PlayerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BowlingServiceApplication.class)
@WebAppConfiguration
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
		assertThat(game.getFrames(),hasSize(10));
		game = gameRepository.save(game);
		assertThat(gameRepository.findOne(game.getId()),notNullValue());
	}
	
	@Test
	public void perfectGameSave()
	{
		Game game = gameRepository.save(GameBuilder.perfectGame());
		
		Game actualGame = gameRepository.findOne(game.getId());
		assertThat(actualGame,notNullValue());
		assertThat(actualGame.getFrames(),hasSize(10));
	}
	
	@Test
	public void perfectGameSave1()
	{
		addRangeOfRolls(12,10);
		assertThat(game.score(), equalTo(300));
		gameRepository.save(game);

		Game actualGame = gameRepository.findOne(game.getId());
		assertThat(actualGame,notNullValue());
		assertThat(actualGame.getFrames(),hasSize(10));
		assertThat(actualGame.score(), equalTo(300));
	}

}
