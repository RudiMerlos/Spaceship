package com.project.spaceship.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.spaceship.model.entity.Spaceship;

public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {

	List<Spaceship> findBySpaceshipNameContainingIgnoreCase(String name);

}
