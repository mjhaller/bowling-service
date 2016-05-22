package bowling.frame;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import bowling.AbstractEntity;
import bowling.game.GameType;

@Entity
public class Ball extends AbstractEntity {
	
	private Integer number;
	
	@ElementCollection
	private List<Integer> pinsRemaining = new ArrayList<>(GameType.TENPIN.allPins());
	
	private Mark mark;
	
	@ManyToOne
	private Frame frame;
	
	public int score(){
		return GameType.TENPIN.allPins().size() - pinsRemaining.size();
	}
	
	public Mark getMark() {
		return mark;
	}
	
	public void resolveMark()
	{
		if (pinsRemaining.size() == 0) {
			if (getNumber() == 1) {
				setMark(Mark.STRIKE);
				return;
			}

			if (!frame.isLastFrame())
			{
				if (getNumber() == 2) {
					setMark(Mark.SPARE);
					return;
				}
			}
			else
			{
				if (getNumber() >= 2) {
					if (score() == GameType.TENPIN.maxPins())
					{
						setMark(Mark.STRIKE_LAST);
					}
					if (frame.getBalls().peekFirst().score() < GameType.TENPIN.maxPins())
					{
						setMark(Mark.SPARE);						
					}
					return;
				}
			}
		}
		setMark(Mark.OPEN);
	}
	
	public void setMark(Mark mark) {
		this.mark = mark;
	}
	
	public List<Integer> getPinsRemaining() {
		return pinsRemaining;
	}
	
	public void setPinsRemaining(List<Integer> pinsRemaining) {
		this.pinsRemaining = pinsRemaining;
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
		return "Ball [number=" + number + ", pinsRemaining=" + pinsRemaining + ", mark=" + mark + ", frame=" + frame.getNumber()
				+ "]";
	}



	
}
