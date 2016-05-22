package bowling;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import bowling.frame.Frame;

public interface ScoringStrategy {
	
	default List<Integer> allPins()
	{
		return Collections.unmodifiableList(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
	}
	
	default int maxFrames(){
		return 10;
	};
	
	default int maxPins(){
		return 10;
	};

	
	public int maxRolls(Frame frame);

}
