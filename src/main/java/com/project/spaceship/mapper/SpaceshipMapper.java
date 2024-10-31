package com.project.spaceship.mapper;

import org.mapstruct.Mapper;

import com.project.spaceship.dto.SpaceshipDto;
import com.project.spaceship.model.entity.Spaceship;

@Mapper(componentModel = "spring")
public interface SpaceshipMapper extends BaseMapper<Spaceship, SpaceshipDto> {
	// Empty interface
}
