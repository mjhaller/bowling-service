package bowling;

import java.util.stream.IntStream;

import bowling.game.Game;

interface GameTester {


	Game game();
	
	default void roll(Integer ... rolls) {
		for (Integer pins : rolls)
		{
			game().roll(pins);
		}
	}

	default void addRangeOfRolls(Integer numberOfRolls, Integer pinsPerRoll) {
		IntStream.range(1, numberOfRolls + 1).forEach(i -> game().roll(pinsPerRoll));		
	}

}