package bowling.game;

import javax.persistence.Entity;

import bowling.AbstractEntity;

@Entity
public class Player extends AbstractEntity {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
