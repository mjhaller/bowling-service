package bowling.frame;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.data.rest.core.annotation.RestResource;

import bowling.AbstractEntity;
import bowling.game.GameType;

@Entity
public class Ball extends AbstractEntity {
	
	private Integer number;
	
	@ElementCollection
	private List<Integer> pinsRemaining = new ArrayList<>(GameType.TENPIN.allPins());
	
	private Mark mark;
	
	@ManyToOne
	@RestResource(exported = false)
	private Frame frame;
	
	public int score(){
		return GameType.TENPIN.allPins().size() - pinsRemaining.size();
	}
	
	public Mark getMark() {
		return mark;
	}
	
	public void resolveMark()
	{
		if (getNumber() ==1 && pinsRemaining.size() == 0)
		{
			setMark(Mark.STRIKE);
			return;
		}
		
		if (getNumber() ==2 && pinsRemaining.size() == 0)
		{
			setMark(Mark.SPARE);
			return;
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
