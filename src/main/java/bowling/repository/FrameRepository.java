package bowling.repository;

import org.springframework.data.repository.CrudRepository;

import bowling.frame.Frame;

public interface FrameRepository extends CrudRepository<Frame, Long>  {

	
}
