package bowling;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import bowling.TestBuilders.GameBuilder;
import bowling.game.Game;

public class ScoringTest {

	@Test
	public void perfectGameSave()
	{
		Game game = GameBuilder.perfectGame();
		
		game.resolveStates();
		
		assertThat(game.getTotalScore(), equalTo(300));
	}

}
