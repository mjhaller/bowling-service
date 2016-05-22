package bowling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import bowling.frame.Ball;
import bowling.frame.Frame;
import bowling.game.Game;
import bowling.game.GameType;
import bowling.game.Player;

public class TestBuilders {

	public static class GameBuilder {

		private static final LinkedList<Integer> allFrames = new LinkedList<>(GameType.TENPIN.allPins());
		
		private List<Frame> frames = new LinkedList<>();;
		private Player player;
		

		private GameBuilder() {
		}

		public static Game perfectGame() {
			List<Frame> frames = allFrames.stream().map(i -> FrameBuilder.strike(i)).collect(Collectors.toList());
			return game().withFrames(frames).build();
		}
		
		public static GameBuilder game() {
			return new GameBuilder();
		}

		public GameBuilder withFrames(List<Frame> frames) {
			this.frames.addAll(frames);
			return this;
		}
		
		public GameBuilder withPlayer(Player player)
		{
			this.player = player;
			return this;
		}

		public Game build() {
			Game game = new Game();
			game.setPlayer(player);
			this.frames.forEach(f -> { game.addFrame(f); f.setGame(game); });
			return game;
		}
	}

	public static class FrameBuilder {

		private List<Ball> balls = new LinkedList<>();
		private Integer number;

		private FrameBuilder() {
		}

		public static FrameBuilder frame() {
			return new FrameBuilder();
		}

		public FrameBuilder withBalls(Ball ... balls) {
			this.balls.addAll(Arrays.asList(balls));
			return this;
		}
		
		public FrameBuilder withNumber(Integer number)
		{
			this.number = number;
			return this;
		}

		public Frame build() {
			Frame frame = new Frame();
			frame.setNumber(number);
			this.balls.forEach(b -> { frame.addBall(b); b.setFrame(frame); } );
			return frame;
		}
		
		public static Frame aFrame() {
			return frame().build();
		}
		
		public static Frame strike(Integer frameNumber) {
			return frame().withNumber(frameNumber).withBalls(BallBuilder.tenDown()).build();
		}
		
		public static Frame spare(Integer pinsDownFirstBall) {
			return frame().withBalls(
					BallBuilder.rangeRemaining(1,pinsDownFirstBall),
					BallBuilder.rangeRemaining(pinsDownFirstBall+1,10)
					).build();
					
		}
		
	}
	
	public static class BallBuilder {
		private static List<Integer> allPins = GameType.TENPIN.allPins();

		private List<Integer> pins = new ArrayList<>();

		private BallBuilder() {
		}

		public static BallBuilder ball() {
			return new BallBuilder();
		}

		public BallBuilder withPinsRemaining(List<Integer> pins) {
			this.pins.addAll(pins);
			return this;
		}

		public Ball build() {
			Ball ball = new Ball();
			ball.setPinsRemaining(pins);
			return ball;
		}
		
		public static Ball tenDown() 
		{
			return ball().withPinsRemaining(new ArrayList<>()).build();
		}
		
		public static Ball someRemaining(Integer ...integers ) 
		{
			return ball().withPinsRemaining(Arrays.asList(integers)).build();
		}
		
		public static Ball rangeRemaining(Integer start, Integer end ) 
		{
			return ball().withPinsRemaining(allPins.subList(start - 1, end - 1)).build();
		}
		
	}
}