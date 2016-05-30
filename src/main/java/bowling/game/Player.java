package bowling.game;

import javax.persistence.Entity;

import org.springframework.data.rest.core.annotation.RestResource;

import bowling.AbstractEntity;

@Entity
@RestResource
public class Player extends AbstractEntity {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
