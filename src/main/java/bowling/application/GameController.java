package bowling.application;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
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
import bowling.repository.FrameRepository;
import bowling.repository.GameRepository;

@RepositoryRestController
public class GameController {

	@Autowired FrameRepository frameRepository;
	@Autowired GameRepository gameRepository;
	
	@Autowired RepositoryEntityLinks entityLinks;
	
	@RequestMapping(value = "/frames/{id}/rolls", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody ResponseEntity<?> addRoll(@PathVariable Long id, @RequestBody Roll roll ) {
		
		Frame persistedFrame =frameRepository.findOne(id);
		persistedFrame.addRoll(roll);
		persistedFrame = frameRepository.save(persistedFrame);

		Game game = gameRepository.findOne(persistedFrame.getGame().getId());
		game.score();
		gameRepository.save(game);
		
		Resource<Frame> resource = new Resource<Frame>(persistedFrame);
		resource.add(entityLinks.linkToSingleResource(Game.class, game.getId()));
		resource.add(entityLinks.linkToSingleResource(game.nextFrame()).withRel("nextFrame"));

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
		resource.add(entityLinks.linkToSingleResource(game.nextFrame()).withRel("nextFrame"));

		return ResponseEntity.ok(resource);
	}
	
	
	@RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody ResponseEntity<?> game(@PathVariable Long id) {
		Game game = gameRepository.findOne(id);
		if (game == null)
		{
			return ResponseEntity.notFound().build();
		}
		Resource<Game> resource = new Resource<Game>(game);
		resource.add(entityLinks.linkToCollectionResource(Frame.class));
		resource.add(entityLinks.linkToSingleResource(game.nextFrame()).withRel("nextFrame"));
		return ResponseEntity.ok(resource);
	}

}
