package bowling;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import bowling.game.Game;

public class PlayingTest implements Loggable, GameTester {

	Game game;
	
	@Before
	public void setup()
	{
		this.game = new Game();
	}
	
	public Game game(){ return this.game; }
	
	@Test
	public void perfect()
	{
		addRangeOfRolls(12,10);
		
		assertThat(game.score(), equalTo(300));
		
	}
	
	@Test
	public void allOnes()
	{
		addRangeOfRolls(20,1);
		assertThat(game.score(), equalTo(20));
	}
	
	@Test
	public void allFives() //spares
	{
		addRangeOfRolls(21,5);
		
		assertThat(game.score(), equalTo(150));
	}
	
	@Test
	public void palindromeAlmost()
	{
		roll(9,1,8,2,7,3,6,4,5,5,4,6,3,7,2,8,1,9,0,0);
		assertThat(game.score(), equalTo(126));
	}
	
	
	@Test
	public void running() 
	{
		roll(10,5,5,10);
		
		assertThat(game.getFrames().get(0).getRolls(), hasSize(1));
		assertThat(game.getFrames().get(1).getRolls(), hasSize(2));
		assertThat(game.getFrames().get(2).getRolls(), hasSize(1));
		assertThat(game.getFrames().get(3).getRolls(), hasSize(0));
		
		assertThat(game.score(), equalTo(40));
		roll(3,3);
		assertThat(game.score(), equalTo(62));
		roll(9);
		assertThat(game.score(), equalTo(62));
		roll(1);
		assertThat(game.score(), equalTo(62));
		roll(8);
		assertThat(game.score(), equalTo(80));
		roll(2);
		assertThat(game.score(), equalTo(80));
		roll(2);
		assertThat(game.score(), equalTo(92));
		roll(5);
		assertThat(game.score(), equalTo(99));
		roll(10);
		assertThat(game.score(), equalTo(99));
		roll(9);
		assertThat(game.score(), equalTo(99));
		roll(1);
		assertThat(game.score(), equalTo(119));
		roll(9);
		assertThat(game.score(), equalTo(138));
		roll(1);
		assertThat(game.score(), equalTo(138));
		roll(10);
		assertThat(game.score(), equalTo(158));
	}
	
	
}
