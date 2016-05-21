package bowling.frame;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import bowling.AbstractEntity;

@Entity
public class Ball extends AbstractEntity {
	
	@ElementCollection
	private List<Integer> pins = new ArrayList<>();
	
	@Transient
	private Mark mark;
	
	@ManyToOne
	private Frame frame;

	public int score(){
		return pins.size();
	}
	
	public Mark getMark() {
		int maxBalls = frame.getGame().getGameType().maxBalls(frame);
		
		//frame.getBalls().stream().findFirst().orElse();
		return mark;
	}
	
	public List<Integer> getPins() {
		return pins;
	}

	public Frame getFrame() {
		return frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}
	
}
