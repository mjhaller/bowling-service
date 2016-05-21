package bowling;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import bowling.frame.Frame;
import bowling.game.Game;
import bowling.game.Player;
import bowling.repository.GameRepository;
import bowling.repository.PlayerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BowlingServiceApplication.class)
@WebAppConfiguration
public class GameTest {

	@Resource private GameRepository gameRepository;
	@Resource private PlayerRepository playerRepository;
	
	@Test
	public void gameSave() {
		Game game = new Game();
		Player player = new Player();
		playerRepository.save(player);
		game.setPlayer(player);
		
		Frame frame1 = new Frame();
		frame1.setNumber(1);
		
		Frame frame2 = new Frame();
		frame2.setNumber(1); 

		game.addToFrames(frame1);
		game.addToFrames(frame2);
		
		assertThat(game.getFrames(),hasSize(1));
		
		frame2  = new Frame();
		frame2.setNumber(2);
		game.addToFrames(frame2);
		
		assertThat(game.getFrames(),hasSize(2));
		
		game = gameRepository.save(game);
		
		assertThat(gameRepository.findOne(game.getId()),notNullValue());
		
	}

}
