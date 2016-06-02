package bowling.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import bowling.frame.Frame;

public interface FrameRepository extends CrudRepository<Frame, Long>  {
	
	  @Override
	  @RestResource(exported = false)
	  void delete(Long id);

	  @Override
	  @RestResource(exported = false)
	  void delete(Frame entity);
	
}
