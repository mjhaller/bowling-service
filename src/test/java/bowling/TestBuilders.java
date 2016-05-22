package bowling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import bowling.frame.Roll;
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

		private List<Roll> rolls = new LinkedList<>();
		private Integer number;

		private FrameBuilder() {
		}

		public static FrameBuilder frame() {
			return new FrameBuilder();
		}

		public FrameBuilder withRolls(Roll ... rolls) {
			this.rolls.addAll(Arrays.asList(rolls));
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
			this.rolls.forEach(b -> { frame.addRoll(b); b.setFrame(frame); } );
			return frame;
		}
		
		public static Frame aFrame() {
			return frame().build();
		}
		
		public static Frame strike(Integer frameNumber) {
			return frame().withNumber(frameNumber).withRolls(RollBuilder.allDown()).build();
		}
		
		
	}
	
	public static class RollBuilder {
		private static List<Integer> allPins = GameType.TENPIN.allPins();

		private Integer pins;

		private RollBuilder() {
		}

		public static RollBuilder roll() {
			return new RollBuilder();
		}

		public RollBuilder withPins(Integer pins) {
			this.pins = pins;
			return this;
		}

		public Roll build() {
			Roll roll = new Roll();
			roll.setPins(pins);
			return roll;
		}
		
		public static Roll allDown() 
		{
			return roll().withPins(10).build();
		}
		
		public static Roll someRoll(Integer pins) 
		{
			return roll().withPins(pins).build();
		}

		
		
	}
}