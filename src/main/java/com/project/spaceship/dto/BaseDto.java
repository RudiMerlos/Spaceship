package com.project.spaceship.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseDto implements Serializable {

	private static final long serialVersionUID = 7868352572787578686L;

	protected Long id;

}
