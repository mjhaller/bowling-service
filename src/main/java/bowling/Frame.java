package bowling;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;

@Entity
public class Frame extends AbstractEntity {
	
	private Integer number;
	private Integer score;
	
	@OneToMany(mappedBy = "frame")
	private List<Ball> balls;
	
	@Enumerated(EnumType.STRING)
	private FrameState frameState;
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public FrameState getFrameState() {
		return frameState;
	}
	public void setFrameState(FrameState frameState) {
		this.frameState = frameState;
	}

}
