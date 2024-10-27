package com.project.spaceship.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Spaceship extends BaseEntity {

	private static final long serialVersionUID = 2949732088094493592L;

	private String spaceshipName;

	private String movieName;

	public Spaceship(Long id, String spaceshipName, String movieName) {
		this.id = id;
		this.spaceshipName = spaceshipName;
		this.movieName = movieName;
	}

}
