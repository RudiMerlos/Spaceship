package com.project.spaceship.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.spaceship.model.entity.Spaceship;
import com.project.spaceship.model.repository.SpaceshipRepository;

@Service
public class SpaceshipService {

	private final SpaceshipRepository spaceshipRepository;

	public SpaceshipService(SpaceshipRepository spaceshipRepository) {
		this.spaceshipRepository = spaceshipRepository;
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "spaceships", key = "'findAll_' + #pageNumber + '_' + #pageSize")
	public Page<Spaceship> findAll(Integer pageNumber, Integer pageSize) {
		return this.spaceshipRepository.findAll(PageRequest.of(pageNumber, pageSize));
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "spaceships", key = "#id", unless = "#result == null")
	public Optional<Spaceship> findById(Long id) {
		return this.spaceshipRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "spaceshipsByName", key = "#name")
	public List<Spaceship> findBySpaceshipName(String name) {
		return this.spaceshipRepository.findBySpaceshipNameContainingIgnoreCase(name);
	}

	@Transactional
	@CachePut(value = "spaceships", key = "#result.id")
	@Caching(evict = {
		@CacheEvict(value = "spaceships", allEntries = true),
		@CacheEvict(value = "spaceshipsByName", allEntries = true)
	})
	public Spaceship save(Spaceship newSpaceship) {
		return this.spaceshipRepository.saveAndFlush(newSpaceship);
	}

	@Transactional
	@Caching(evict = {
		@CacheEvict(value = "spacechips", key = "#id"),
		@CacheEvict(value = "spaceships", allEntries = true),
		@CacheEvict(value = "spaceshipsByName", allEntries = true)
	})
	public void deleteById(Long id) {
		this.spaceshipRepository.deleteById(id);
	}
	
	@Transactional
	@Caching(evict = {
		@CacheEvict(value = "spaceships", allEntries = true),
		@CacheEvict(value = "spaceshipsByName", allEntries = true)
	})
	public void deleteAll() {
		this.spaceshipRepository.deleteAll();
	}

}
