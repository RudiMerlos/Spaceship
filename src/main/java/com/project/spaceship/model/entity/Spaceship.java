package com.project.spaceship.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Spaceship extends BaseEntity {

	private static final long serialVersionUID = 2949732088094493592L;

	@Column(name = "spaceship_name")
	private String spaceshipName;

	@Column(name = "movie_name")
	private String movieName;

	public Spaceship(String spaceshipName, String movieName) {
		this.spaceshipName = spaceshipName;
		this.movieName = movieName;
	}

}
