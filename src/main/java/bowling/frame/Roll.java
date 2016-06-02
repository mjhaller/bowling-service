package bowling.frame;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bowling.AbstractEntity;

@Entity
public class Roll extends AbstractEntity {
	
	private Integer pins;
	
	@JsonIgnore
	//unimplemented
	private Mark mark = Mark.OPEN;
	
	
	public Mark getMark() {
		return mark;
	}
	
	public void setMark(Mark mark) {
		this.mark = mark;
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
