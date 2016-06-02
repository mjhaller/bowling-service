package bowling.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bowling.frame.Frame;
import bowling.frame.Roll;
import bowling.game.Game;
import bowling.game.Player;
import bowling.repository.FrameRepository;
import bowling.repository.GameRepository;
import bowling.repository.PlayerRepository;

@RepositoryRestController
public class GameController {

	@Autowired PlayerRepository playerRepository;
	@Autowired FrameRepository frameRepository;
	@Autowired GameRepository gameRepository;
	
	@Autowired RepositoryEntityLinks entityLinks;

	//TODO: it is not a great practice to make mvc controller methods transactional,
	//      so move the model choreography to the service layer

	
	@RequestMapping(value = "/frames/{id}/rolls", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody ResponseEntity<?> createRoll(@PathVariable Long id, @RequestBody Roll roll ) {
		
		Frame persistedFrame =frameRepository.findOne(id);
		persistedFrame.addRoll(roll);
		persistedFrame = frameRepository.save(persistedFrame);

		Game game = gameRepository.findOne(persistedFrame.getGame().getId());
		game.score();
		gameRepository.save(game);
		
		Resource<Frame> resource = new Resource<Frame>(persistedFrame);
		resource.add(entityLinks.linkToSingleResource(Game.class, game.getId()));
		addNextFrame(game, resource);

		return ResponseEntity.ok(resource);
	}
	
	@RequestMapping(value = "/frames/{id}", method = RequestMethod.PUT)
	@Transactional
	public @ResponseBody ResponseEntity<?> updateFrame(@PathVariable Long id, @RequestBody Frame frame ) {
		
		Frame persistedFrame =frameRepository.findOne(id);
		persistedFrame.setRolls(frame.getRolls());
		persistedFrame = frameRepository.save(persistedFrame);

		Game game = gameRepository.findOne(persistedFrame.getGame().getId());
		game.score();
		gameRepository.save(game);
		
		Resource<Frame> resource = new Resource<Frame>(persistedFrame);
		resource.add(entityLinks.linkToSingleResource(Game.class, game.getId()));
		addNextFrame(game, resource);

		return ResponseEntity.ok(resource);
	}

	private void addNextFrame(Game game, Resource<?> resource) {
		Frame nextFrame = game.nextFrame();
		if (nextFrame != null)
			resource.add(entityLinks.linkToSingleResource(nextFrame).withRel("nextFrame"));
	}
	
	
	@RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody ResponseEntity<?> retrieveGame(@PathVariable Long id) {
		Game game = gameRepository.findOne(id);
		if (game == null)
		{
			return ResponseEntity.notFound().build();
		}
		Resource<Game> resource = new Resource<Game>(game);
		if (game.getPlayer() != null)
			resource.add(entityLinks.linkForSingleResource(game.getPlayer()).withRel("player"));
		resource.add(entityLinks.linkToCollectionResource(Frame.class));
		addNextFrame(game, resource);
		return ResponseEntity.ok(resource);
	}

}
