package bowling.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bowling.frame.Roll;
import bowling.game.Game;
import bowling.game.Player;

/**
 * DEMO QUALITY ONLY - Game is scoped as a singleton 
 * 
 * this is just to demonstrate game play via REST
 * 
 * TODO: use actual persistence
 * @author mhaller
 *
 */
@RestController
public class GameController {


	Game game;

	/**
	 * Take a game with a player set in the body
	 * 
	 * TODO: currently have to fake out ids
	 * @return
	 */
	@RequestMapping(value = "/game", method = RequestMethod.POST)
	public ResponseEntity<Game> game( @RequestBody Player player) {
		game = new Game();
		player.setId(1L);
		game.setPlayer(player);
		game.getFrames().forEach(f -> f.setId(f.getNumber().longValue()));//fake out ids 
		game.setId(1L);
		return ResponseEntity.ok(game);
	}
	
	@RequestMapping(value = "/game/{id}/roll", method = RequestMethod.POST)
	public ResponseEntity<Game> index(@PathVariable Long id, @RequestBody Roll roll ) {
		game.roll(roll.getPins()); 
		game.score();
		return ResponseEntity.ok(game);
	}
	
	@RequestMapping(value = "/frame/{id}/roll", method = RequestMethod.POST)
	public String index(@RequestBody Roll roll ) {
		return "Unimplemented";
	}

}