package com.project.spaceship.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.spaceship.model.entity.Spaceship;

public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {

	@Override
	@Cacheable(value = "spaceships", key = "#id", unless = "#result == null")
	Optional<Spaceship> findById(Long id);

	@Override
	@Caching(evict = {
		@CacheEvict(value = "spacechips", key = "#id"),
		@CacheEvict(value = "spaceships", allEntries = true),
		@CacheEvict(value = "spaceshipsByName", allEntries = true)
	})
	void deleteById(Long id);

	@Override
	@Caching(evict = {
		@CacheEvict(value = "spaceships", allEntries = true),
		@CacheEvict(value = "spaceshipsByName", allEntries = true)
	})
	void deleteAll();

	@Override
	@Cacheable(value = "spaceships", key = "'findAll_' + #pageNumber + '_' + #pageSize")
	Page<Spaceship> findAll(Pageable pageable);

	@Override
	@CachePut(value = "spaceships", key = "#result.id")
	@Caching(evict = {
		@CacheEvict(value = "spaceships", allEntries = true),
		@CacheEvict(value = "spaceshipsByName", allEntries = true)
	})
	<S extends Spaceship> S saveAndFlush(S entity);

	@Cacheable(value = "spaceshipsByName", key = "#name")
	List<Spaceship> findBySpaceshipNameContainingIgnoreCase(String name);

}
