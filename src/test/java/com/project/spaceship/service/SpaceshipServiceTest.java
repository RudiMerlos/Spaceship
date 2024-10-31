package com.project.spaceship.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
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

import com.project.spaceship.dto.PageDto;
import com.project.spaceship.dto.SpaceshipDto;
import com.project.spaceship.exception.ExceptionMessage;
import com.project.spaceship.exception.ItemNotFoundException;
import com.project.spaceship.mapper.PageMapper;
import com.project.spaceship.mapper.SpaceshipMapper;
import com.project.spaceship.model.entity.Spaceship;
import com.project.spaceship.model.repository.SpaceshipRepository;

@SpringBootTest
public class SpaceshipServiceTest {

	private static final String EXCEPTION_MESSAGE = "Item not found in database: ";

	@Mock
	private SpaceshipRepository spaceshipRepository;

	@Mock
	private SpaceshipMapper spaceshipMapper;

	@Mock
	private PageMapper pageMapper;

	@Mock
	private ExceptionMessage exceptionMessage;

	@InjectMocks
	private SpaceshipService spaceshipService;
	@InjectMocks
	private SpaceshipQueryService spaceshipQueryService;

	private Spaceship spaceship1;
	private SpaceshipDto spaceshipDto1;
	private Spaceship spaceship2;
	private SpaceshipDto spaceshipDto2;

	@BeforeEach
	public void setUp() {
		this.spaceship1 = new Spaceship(1L, "USS Enterprise Test", "Star Trek Test");
		this.spaceshipDto1 = new SpaceshipDto(1L, "USS Enterprise Test", "Star Trek Test");
		this.spaceship2 = new Spaceship(2L, "Halcón Milenario Test", "Star Wars Test");
		this.spaceshipDto2 = new SpaceshipDto(2L, "Halcón Milenario Test", "Star Wars Test");
	}

	@Test
	public void testFindAll() {
		Page<Spaceship> spaceshipPage = new PageImpl<>(List.of(this.spaceship1, this.spaceship2));
		when(this.spaceshipRepository.findAll(PageRequest.of(0, 10))).thenReturn(spaceshipPage);
		when(this.spaceshipMapper.entityToDto(spaceshipPage)).thenReturn(List.of(this.spaceshipDto1, this.spaceshipDto2));
		when(this.pageMapper.toPageDto(any(), any()))
				.thenReturn(new PageDto<>(List.of(this.spaceshipDto1, this.spaceshipDto2), 0, 2, 2, 1));

		PageDto<SpaceshipDto> result = this.spaceshipQueryService.findAll(0, 10);

		assertFalse(result.getContent().isEmpty());
		assertEquals(2, result.getContent().size());
		verify(this.spaceshipRepository).findAll(PageRequest.of(0, 10));
	}

	@Test
	public void testFindByIdSpaceshipExists() {
		when(this.spaceshipRepository.findById(1L)).thenReturn(Optional.of(this.spaceship1));
		when(this.spaceshipMapper.entityToDto(this.spaceship1)).thenReturn(this.spaceshipDto1);

		SpaceshipDto result = this.spaceshipQueryService.findById(1L);

		assertNotNull(result);
		assertEquals(this.spaceshipDto1, result);
		verify(this.spaceshipRepository).findById(1L);
	}

	@Test
	public void testFindByIdSpaceshipDoesNotExists() {
		when(this.spaceshipRepository.findById(2L)).thenReturn(Optional.empty());
		when(this.exceptionMessage.getMessageItemNotFound(anyString(), anyLong())).thenReturn("Spaceship not found");

		ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> this.spaceshipQueryService.findById(1L));

		assertEquals(EXCEPTION_MESSAGE + "Spaceship not found", exception.getMessage());
		verify(this.spaceshipRepository).findById(1L);
	}

	@Test
	public void testFindBySpaceshipName() {
		when(this.spaceshipRepository.findBySpaceshipNameContainingIgnoreCase("Enterprise")).thenReturn(List.of(this.spaceship1));
		when(this.spaceshipMapper.entityToDto(List.of(this.spaceship1))).thenReturn(List.of(this.spaceshipDto1));

		List<SpaceshipDto> result = this.spaceshipQueryService.findBySpaceshipName("Enterprise");

		assertEquals(1, result.size());
		verify(this.spaceshipRepository).findBySpaceshipNameContainingIgnoreCase("Enterprise");
	}

	@Test
	public void testCreateSpaceship() {
		when(this.spaceshipMapper.dtoToEntity(this.spaceshipDto1)).thenReturn(this.spaceship1);
		when(this.spaceshipRepository.save(any(Spaceship.class))).thenReturn(this.spaceship1);
		when(this.spaceshipMapper.entityToDto(this.spaceship1)).thenReturn(this.spaceshipDto1);

		SpaceshipDto result = this.spaceshipService.save(this.spaceshipDto1);

		assertNotNull(result);
		assertEquals(this.spaceshipDto1, result);
		verify(this.spaceshipMapper).dtoToEntity(this.spaceshipDto1);
		verify(this.spaceshipRepository).save(this.spaceship1);
		verify(this.spaceshipMapper).entityToDto(this.spaceship1);
	}

	@Test
	public void testModify() {
		when(this.spaceshipRepository.findById(1L)).thenReturn(Optional.of(this.spaceship1));
		when(this.spaceshipRepository.save(any(Spaceship.class))).thenReturn(this.spaceship1);
		when(this.spaceshipMapper.entityToDto(this.spaceship1)).thenReturn(this.spaceshipDto1);

		this.spaceshipDto1.setSpaceshipName("New spaceship name");
		SpaceshipDto result = this.spaceshipService.modify(1L, this.spaceshipDto1);

		assertNotNull(result);
		assertEquals("New spaceship name", result.getSpaceshipName());
		verify(this.spaceshipRepository).findById(1L);
		verify(this.spaceshipRepository).save(this.spaceship1);
		verify(this.spaceshipMapper).entityToDto(this.spaceship1);
	}

	@Test
	public void testDeleteByIdExists() {
		when(this.spaceshipRepository.findById(1L)).thenReturn(Optional.of(this.spaceship1));

		assertDoesNotThrow(() -> this.spaceshipService.deleteById(1L));

		verify(this.spaceshipRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testDeleteByIdDoesNotExists() {
		when(this.spaceshipRepository.findById(1L)).thenReturn(Optional.empty());
		when(this.exceptionMessage.getMessageItemNotFound(anyString(), anyLong())).thenReturn("Spaceship not found");

		ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> this.spaceshipService.deleteById(1L));

		assertEquals(EXCEPTION_MESSAGE + "Spaceship not found", exception.getMessage());

		verify(this.spaceshipRepository).findById(1L);
		verify(this.spaceshipRepository, never()).deleteById(1L);
	}

	@Test
	public void testDeleteAll() {
		this.spaceshipService.deleteAll();

		verify(this.spaceshipRepository, times(1)).deleteAll();
	}

}
