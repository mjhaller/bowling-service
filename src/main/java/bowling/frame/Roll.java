package bowling.frame;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import bowling.AbstractEntity;
import bowling.game.GameType;

@Entity
public class Roll extends AbstractEntity {
	
	private Integer number;
	
	private Integer pins;
	
	private Mark mark;
	
	@ManyToOne
	private Frame frame;
	
	
	public Mark getMark() {
		return mark;
	}
	
	public void resolveMark() {
		if (getNumber() == 1 && this.getPins() == GameType.TENPIN.maxPins() ) {
			setMark(Mark.STRIKE);
			return;
		}
		Integer combinedRolls = frame.getRolls().get(0).getPins() + getPins();
		if (getNumber() == 2 && combinedRolls == GameType.TENPIN.maxPins())
		{
			setMark(Mark.SPARE);
			return;
		}
		setMark(Mark.OPEN);
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
