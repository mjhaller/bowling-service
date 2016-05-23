package bowling.frame;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import bowling.AbstractEntity;

@Entity
public class Roll extends AbstractEntity {
	
	private Integer number;
	
	private Integer pins;
	
	private Mark mark = Mark.OPEN;
	
	@ManyToOne
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Roll [number=" + number + ", pins=" + pins + ", mark=" + mark + ", frame=" + frame.getNumber()
				+ "]";
	}


	public Integer getPins() {
		return pins;
	}


	public void setPins(Integer pins) {
		this.pins = pins;
	}



	
}
