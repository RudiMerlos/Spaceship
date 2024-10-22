package com.project.spaceship.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.spaceship.model.entity.Spaceship;
import com.project.spaceship.model.repository.SpaceshipRepository;

@Service
public class SpaceshipService {

	private final SpaceshipRepository spaceshipRepository;

	public SpaceshipService(SpaceshipRepository spaceshipRepository) {
		this.spaceshipRepository = spaceshipRepository;
	}

	public Page<Spaceship> findAll(Integer pageNumber, Integer pageSize) {
		return this.spaceshipRepository.findAll(PageRequest.of(pageNumber, pageSize));
	}

	public Optional<Spaceship> findById(Long id) {
		return this.spaceshipRepository.findById(id);
	}

	public List<Spaceship> findBySpaceshipName(String name) {
//		return this.spaceshipRepository.findBySpaceshipName(name);
		return this.spaceshipRepository.findBySpaceshipNameContainingIgnoreCase(name);
	}

	public Spaceship save(Spaceship newSpaceship) {
		return this.spaceshipRepository.saveAndFlush(newSpaceship);
	}

	public void deleteById(Long id) {
		this.spaceshipRepository.deleteById(id);
	}

}
