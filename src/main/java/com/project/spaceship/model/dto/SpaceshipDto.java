package com.project.spaceship.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceshipDto extends BaseDto {

	private static final long serialVersionUID = -8083152094570879983L;

	private String spaceshipName;

	private String movieName;

	public SpaceshipDto(Long id, String spaceshipName, String movieName) {
		this.id = id;
		this.spaceshipName = spaceshipName;
		this.movieName = movieName;
	}

}
