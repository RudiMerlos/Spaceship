package com.project.spaceship.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.spaceship.model.dto.SpaceshipDto;
import com.project.spaceship.model.mapper.SpaceshipMapper;
import com.project.spaceship.model.repository.SpaceshipRepository;

@Service
public class SpaceshipService {

	private final SpaceshipRepository spaceshipRepository;
	
	private final SpaceshipMapper spaceshipMapper;

	public SpaceshipService(SpaceshipRepository spaceshipRepository, SpaceshipMapper spaceshipMapper) {
		this.spaceshipRepository = spaceshipRepository;
		this.spaceshipMapper = spaceshipMapper;
	}

	@Transactional(readOnly = true)
	public List<SpaceshipDto> findAll(Integer pageNumber, Integer pageSize) {
		return this.spaceshipMapper.entityToDto(this.spaceshipRepository.findAll(PageRequest.of(pageNumber, pageSize)));
	}

	@Transactional(readOnly = true)
	public Optional<SpaceshipDto> findById(Long id) {
		return this.spaceshipRepository.findById(id).map(spaceship -> this.spaceshipMapper.entityToDto(spaceship));
	}

	@Transactional(readOnly = true)
	public List<SpaceshipDto> findBySpaceshipName(String name) {
		return this.spaceshipMapper.entityToDto(this.spaceshipRepository.findBySpaceshipNameContainingIgnoreCase(name));
	}

	@Transactional
	public SpaceshipDto save(SpaceshipDto newSpaceship) {
		return this.spaceshipMapper.entityToDto(this.spaceshipRepository.saveAndFlush(this.spaceshipMapper.dtoToEntity(newSpaceship)));
	}

	@Transactional
	public void deleteById(Long id) {
		this.spaceshipRepository.deleteById(id);
	}

	@Transactional
	public void deleteAll() {
		this.spaceshipRepository.deleteAll();
	}

}
