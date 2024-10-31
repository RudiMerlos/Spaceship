package com.project.spaceship.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.spaceship.dto.PageDto;
import com.project.spaceship.dto.SpaceshipDto;
import com.project.spaceship.exception.ExceptionMessage;
import com.project.spaceship.exception.ItemNotFoundException;
import com.project.spaceship.mapper.PageMapper;
import com.project.spaceship.mapper.SpaceshipMapper;
import com.project.spaceship.model.entity.Spaceship;
import com.project.spaceship.model.repository.SpaceshipRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SpaceshipQueryService {

	private final SpaceshipRepository spaceshipRepository;

	private final SpaceshipMapper spaceshipMapper;

	private final PageMapper pageMapper;

	private final ExceptionMessage exceptionMessage;

	@Cacheable(value = "spaceships", key = "'findAll_' + #pageNumber + '_' + #pageSize")
	public PageDto<SpaceshipDto> findAll(Integer pageNumber, Integer pageSize) {
		Page<Spaceship> page = this.spaceshipRepository.findAll(PageRequest.of(pageNumber, pageSize));
		return this.pageMapper.toPageDto(page, this.spaceshipMapper::entityToDto);
	}

	@Cacheable(value = "spaceships", key = "#id", unless = "#result == null")
	public SpaceshipDto findById(Long id) throws ItemNotFoundException {
		return this.spaceshipRepository.findById(id).map(spaceship -> this.spaceshipMapper.entityToDto(spaceship))
				.orElseThrow(() -> new ItemNotFoundException(this.exceptionMessage.getMessageItemNotFound("Spaceship", id)));
	}

	@Cacheable(value = "spaceshipsByName", key = "#name")
	public List<SpaceshipDto> findBySpaceshipName(String name) {
		return this.spaceshipMapper.entityToDto(this.spaceshipRepository.findBySpaceshipNameContainingIgnoreCase(name));
	}

}
