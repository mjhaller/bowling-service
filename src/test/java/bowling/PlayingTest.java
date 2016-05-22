package bowling;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import bowling.TestBuilders.RollBuilder;
import bowling.frame.Frame;
import bowling.frame.Roll;
import bowling.game.Game;
import bowling.game.GameState;

public class PlayingTest implements Loggable {

	private Game game;
	
	@Before
	public void setup()
	{
		this.game = new Game();
		this.game.resolveStates();
	}
	
	@Test
	public void perfectGamePlay()
	{
		Game game = new Game();
		game.resolveStates();
		while (game.getGameState() != GameState.FINISHED)
		{
			roll(RollBuilder.allDown());
		}
		assertThat(game.getTotalScore(), equalTo(300));
	}
	
	private void roll(Roll roll) {
		Frame frame = new Frame();
		frame.setNumber(game.getNextFrame());
		log().info(frame.toString());
		if (!game.addFrame(frame))
		{
			frame = game.getFrames().get(game.getFrames().size()-1);
		}
		roll.setFrame(frame);
		frame.addRoll(roll);
		game.resolveStates();
	}

	
}
