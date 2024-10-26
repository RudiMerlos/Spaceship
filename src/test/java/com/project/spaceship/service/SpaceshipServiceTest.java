package com.project.spaceship.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.project.spaceship.model.dto.SpaceshipDto;
import com.project.spaceship.model.entity.Spaceship;
import com.project.spaceship.model.mapper.SpaceshipMapper;
import com.project.spaceship.model.repository.SpaceshipRepository;

@SpringBootTest
public class SpaceshipServiceTest {

	@Mock
	private SpaceshipRepository spaceshipRepository;

	@Mock
	private SpaceshipMapper spaceshipMapper;

	@InjectMocks
	private SpaceshipService spaceshipService;

	private Spaceship spaceship1;
	private SpaceshipDto spaceshipDto1;
	private Spaceship spaceship2;
	private SpaceshipDto spaceshipDto2;

	@BeforeEach
	public void setUp() {
		spaceship1 = new Spaceship(1L, "USS Enterprise Test", "Star Trek Test");
		spaceshipDto1 = new SpaceshipDto(1L, "USS Enterprise Test", "Star Trek Test");
		spaceship2 = new Spaceship(2L, "Halcón Milenario Test", "Star Wars Test");
		spaceshipDto2 = new SpaceshipDto(2L, "Halcón Milenario Test", "Star Wars Test");
	}

	@Test
	public void testFindAll() {
		Page<Spaceship> spaceshipPage = new PageImpl<>(List.of(spaceship1, spaceship2));
		when(this.spaceshipRepository.findAll(PageRequest.of(0, 10))).thenReturn(spaceshipPage);
		when(this.spaceshipMapper.entityToDto(spaceshipPage)).thenReturn(List.of(spaceshipDto1, spaceshipDto2));

		List<SpaceshipDto> result = this.spaceshipService.findAll(0, 10);

		assertFalse(result.isEmpty());
		assertEquals(2, result.size());
	}

	@Test
	public void testFindByIdSpaceshipExists() {
		when(this.spaceshipRepository.findById(1L)).thenReturn(Optional.of(this.spaceship1));
		when(this.spaceshipMapper.entityToDto(this.spaceship1)).thenReturn(spaceshipDto1);

		Optional<SpaceshipDto> result = this.spaceshipService.findById(1L);

		assertTrue(result.isPresent());
		assertEquals(this.spaceshipDto1, result.get());
	}

	@Test
	public void testFindByIdSpaceshipDoesNotExists() {
		when(this.spaceshipRepository.findById(2L)).thenReturn(Optional.empty());

		Optional<SpaceshipDto> result = this.spaceshipService.findById(2L);

		assertFalse(result.isPresent());
	}

	@Test
	public void testFindBySpaceshipName() {
		when(this.spaceshipRepository.findBySpaceshipNameContainingIgnoreCase("Enterprise"))
				.thenReturn(List.of(spaceship1));
		when(this.spaceshipMapper.entityToDto(List.of(spaceship1))).thenReturn(List.of(spaceshipDto1));

		List<SpaceshipDto> result = this.spaceshipService.findBySpaceshipName("Enterprise");

		assertEquals(1, result.size());
	}

	@Test
	public void testCreateSpaceship() {
		when(this.spaceshipRepository.saveAndFlush(spaceship1)).thenReturn(spaceship1);
		when(this.spaceshipRepository.saveAndFlush(spaceship2)).thenReturn(spaceship2);

		when(this.spaceshipMapper.entityToDto(spaceship1)).thenReturn(spaceshipDto1);
		when(this.spaceshipMapper.entityToDto(spaceship2)).thenReturn(spaceshipDto2);

		when(this.spaceshipMapper.dtoToEntity(spaceshipDto1)).thenReturn(spaceship1);
		when(this.spaceshipMapper.dtoToEntity(spaceshipDto2)).thenReturn(spaceship2);

		SpaceshipDto result1 = this.spaceshipService.save(spaceshipDto1);
		SpaceshipDto result2 = this.spaceshipService.save(spaceshipDto2);

		assertEquals(spaceshipDto1, result1);
		assertEquals(spaceshipDto2, result2);
	}

	@Test
	public void testDeleteById() {
		this.spaceshipService.deleteById(1L);
		this.spaceshipService.deleteById(2L);

		verify(this.spaceshipRepository, times(1)).deleteById(1L);
		verify(this.spaceshipRepository, times(1)).deleteById(2L);
	}

	@Test
	public void testDeleteAll() {
		this.spaceshipService.deleteAll();

		verify(this.spaceshipRepository, times(1)).deleteAll();
	}

}
