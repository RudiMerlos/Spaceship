package com.project.spaceship.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.project.spaceship.model.entity.Spaceship;

@SpringBootTest
public class SpaceshipServiceTest {

	@Autowired
	private SpaceshipService spaceshipService;

	@BeforeEach
	public void setUp() {
		this.spaceshipService.save(new Spaceship(1L, "Halcón Milenario", "Star Wars"));
		this.spaceshipService.save(new Spaceship(2L, "USS Enterprise", "Star Trek"));
		this.spaceshipService.save(new Spaceship(3L, "Nave Nodriza", "District 0"));
	}

	@AfterEach
	public void cleanUp() {
		this.spaceshipService.deleteAll();
	}

	@Test
	public void testFindAll() {
		Page<Spaceship> spaceships = this.spaceshipService.findAll(0, 10);
		assertEquals(spaceships.getContent().size(), 3);
	}

	@Test
	public void testFindById() {
		Page<Spaceship> spaceships = this.spaceshipService.findAll(0, 10);
		Long id = spaceships.getContent().get(1).getId();

		Spaceship spaceship = this.spaceshipService.findById(id).orElse(null);
		assertNotNull(spaceship);

		spaceship = this.spaceshipService.findById(14L).orElse(null);
		assertNull(spaceship);
	}

	@Test
	public void testFindBySpaceshipName() {
		String strToSearch = "nodri";
		String expectedName = "Nave Nodriza";
		List<Spaceship> spaceships = this.spaceshipService.findBySpaceshipName(strToSearch);

		assertEquals(spaceships.size(), 1);
		assertEquals(spaceships.get(0).getSpaceshipName(), expectedName);
	}

	@Test
	public void testDeleteById() {
		Page<Spaceship> spaceships = this.spaceshipService.findAll(0, 10);
		assertEquals(spaceships.getContent().size(), 3);

		Long id = spaceships.getContent().get(1).getId();
		this.spaceshipService.deleteById(id);

		spaceships = this.spaceshipService.findAll(0, 10);
		assertEquals(spaceships.getContent().size(), 2);
	}

	@Test
	public void testCreateSpaceship() {
		Spaceship newSpaceship = new Spaceship("New spaceship name", "New movie name");
		this.spaceshipService.save(newSpaceship);

		Page<Spaceship> spaceships = this.spaceshipService.findAll(0, 10);
		assertEquals(spaceships.getContent().size(), 4);
	}

}
