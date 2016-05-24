package bowling.frame;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bowling.AbstractEntity;

@Entity
public class Roll extends AbstractEntity {
	
	private Integer pins;
	
	private Mark mark = Mark.OPEN;
	
	@ManyToOne
	@JsonIgnore
	private Frame frame;
	
	
	public Mark getMark() {
		return mark;
	}
	
	public void setMark(Mark mark) {
		this.mark = mark;
	}

	public Frame getFrame() {
		return frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}


	@Override
	public String toString() {
		return "Roll [pins=" + pins + ", mark=" + mark + "]";
	}


	public Integer getPins() {
		return pins;
	}


	public void setPins(Integer pins) {
		this.pins = pins;
	}



	
}
