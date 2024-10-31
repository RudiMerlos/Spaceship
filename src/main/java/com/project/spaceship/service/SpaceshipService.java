package com.project.spaceship.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.spaceship.dto.SpaceshipDto;
import com.project.spaceship.exception.ExceptionMessage;
import com.project.spaceship.exception.ItemNotFoundException;
import com.project.spaceship.mapper.SpaceshipMapper;
import com.project.spaceship.model.entity.Spaceship;
import com.project.spaceship.model.repository.SpaceshipRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class SpaceshipService {

	private final SpaceshipRepository spaceshipRepository;

	private final SpaceshipMapper spaceshipMapper;

	private final ExceptionMessage exceptionMessage;
	
	@Caching(evict = {
		@CacheEvict(value = "spaceships", allEntries = true),
		@CacheEvict(value = "spaceshipsByName", allEntries = true)
	})
	public SpaceshipDto modify(Long id, SpaceshipDto spaceship) throws ItemNotFoundException {
		return this.spaceshipRepository.findById(id).map(spaceshipDB -> {
			spaceshipDB.setSpaceshipName(spaceship.getSpaceshipName());
			spaceshipDB.setMovieName(spaceship.getMovieName());
			return this.spaceshipMapper.entityToDto(this.spaceshipRepository.save(spaceshipDB));
		}).orElseThrow(() -> new ItemNotFoundException(this.exceptionMessage.getMessageItemNotFound("Spaceship", id)));
	}

	@CachePut(value = "spaceships", key = "#result.id")
	@Caching(evict = {
		@CacheEvict(value = "spaceships", allEntries = true),
		@CacheEvict(value = "spaceshipsByName", allEntries = true)
	})
	public SpaceshipDto save(SpaceshipDto newSpaceship) {
		return this.spaceshipMapper.entityToDto(this.spaceshipRepository.save(this.spaceshipMapper.dtoToEntity(newSpaceship)));
	}

	@Caching(evict = {
		@CacheEvict(value = "spacechips", key = "#id"),
		@CacheEvict(value = "spaceships", allEntries = true),
		@CacheEvict(value = "spaceshipsByName", allEntries = true)
	})
	public void deleteById(Long id) throws ItemNotFoundException {
		Optional<Spaceship> spaceship = this.spaceshipRepository.findById(id);
		if (spaceship.isEmpty()) {
			throw new ItemNotFoundException(this.exceptionMessage.getMessageItemNotFound("Spaceship", id));
		}
		this.spaceshipRepository.deleteById(id);
	}

	@Caching(evict = {
		@CacheEvict(value = "spaceships", allEntries = true),
		@CacheEvict(value = "spaceshipsByName", allEntries = true)
	})
	public void deleteAll() {
		this.spaceshipRepository.deleteAll();
	}

}
