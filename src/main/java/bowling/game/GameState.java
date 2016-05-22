package bowling.game;

import bowling.Loggable;
import bowling.frame.Frame;
import bowling.frame.FrameScoringState;
import bowling.game.Game.GameContext;

public enum GameState implements GameStateResolver, Loggable {
	INITIAL {
		@Override
		public void resolve(GameContext context) {
			context.setNextFrame(1);
			context.setGameState(GameState.IN_PROGRESS);
		}
		
	},
	IN_PROGRESS {
		@Override
		public void resolve(GameContext context) {
			Frame currentFrame = context.getFrames().peekLast();
			
			if (currentFrame.getFrameScoringState().isPlayNextFrame())
			{
				int nextFrameNumber = currentFrame.getNumber() + 1;
				context.setNextFrame(nextFrameNumber);
				if (nextFrameNumber == GameType.TENPIN.maxFrames())
				{
					context.setGameState(GameState.LAST_FRAME);
					return;
				}
				return;
			}
			context.setNextFrame(currentFrame.getNumber());
			context.setGameState(GameState.IN_PROGRESS);
		}
	},
	LAST_FRAME {

		@Override
		public void resolve(GameContext context) {
			Frame currentFrame = context.getFrames().peekLast();
			FrameScoringState frameScoringState = currentFrame.getFrameScoringState();
			if (frameScoringState == FrameScoringState.STRIKE_BALL1 || frameScoringState == FrameScoringState.STRIKE_BALL2 || 
					frameScoringState == FrameScoringState.SPARE )
			{
				context.setNextFrame(currentFrame.getNumber());
				context.setGameState(GameState.LAST_FRAME);
				return;
			}
			context.setNextFrame(null);
			context.setGameState(GameState.FINISHED);
		}
		
	},
	FINISHED {

		@Override
		public void resolve(GameContext context) {
			return;
		}
		
	}
}
