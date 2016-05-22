package bowling;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers.*;
import org.hamcrest.Matchers.*;

import bowling.TestBuilders.BallBuilder;
import bowling.frame.Ball;
import bowling.frame.Frame;
import bowling.game.Game;

public class PlayingTest implements Loggable {

	@Test
	public void perfectGamePlay()
	{
		Game game = new Game();
		game.resolveStates();
		while (game.getNextFrame() !=null)
		{
			roll(game);
			game.resolveStates();
		}
		assertThat(game.getTotalScore(), equalTo(300));
	}

	private void roll(Game game) {
		Frame frame = game.getNextFrame();
		log().info(frame.toString());
		game.addFrame(frame);
		Ball ball = BallBuilder.tenDown();
		ball.setFrame(frame);
		frame.addBall(ball);
	}

}
