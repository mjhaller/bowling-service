package bowling.game;

import bowling.game.Game.GameContext;

public interface GameStateResolver {

	void resolve(GameContext context);
}
