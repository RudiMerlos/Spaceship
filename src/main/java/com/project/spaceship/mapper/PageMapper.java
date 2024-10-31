package com.project.spaceship.mapper;

import java.util.List;
import java.util.function.Function;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.project.spaceship.dto.PageDto;

@Mapper(componentModel = "spring")
public interface PageMapper {

	default <E, D> PageDto<D> toPageDto(Page<E> page, Function<E, D> mapperFunction) {
		List<D> content = page.getContent().stream().map(mapperFunction).toList();
		return new PageDto<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
	}

}
