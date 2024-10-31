package com.project.spaceship.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.spaceship.dto.PageDto;
import com.project.spaceship.dto.SpaceshipDto;
import com.project.spaceship.service.SpaceshipQueryService;
import com.project.spaceship.service.SpaceshipService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Spaceships management", description = "Operations related to spaceship management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {

	private final SpaceshipService spaceshipService;

	private final SpaceshipQueryService spaceshipQueryService;

	@Operation(summary = "Get all spaceships", description = "Returns a list of all spaceships registered in the system")
	@GetMapping({ "", "/" })
	public ResponseEntity<PageDto<SpaceshipDto>> getAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(this.spaceshipQueryService.findAll(page, size));
	}

	@Operation(summary = "Get a spaceship by ID", description = "Returns a spaceship whose ID matches the provided ID")
	@GetMapping("/{id}")
	public ResponseEntity<SpaceshipDto> getById(@PathVariable Long id) {
		return ResponseEntity.ok(this.spaceshipQueryService.findById(id));
	}

	@Operation(summary = "Get a spaceship by name", description = "Returns a spaceship whose name matches the provided name")
	@GetMapping("/search")
	public ResponseEntity<List<SpaceshipDto>> searchByName(@RequestParam String name) {
		return ResponseEntity.ok(this.spaceshipQueryService.findBySpaceshipName(name));
	}

	@Operation(summary = "Create a new spaceship", description = "Create a new spaceship with the information provided")
	@PostMapping("/")
	public ResponseEntity<SpaceshipDto> create(@RequestBody SpaceshipDto spaceship) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.spaceshipService.save(spaceship));
	}

	@Operation(summary = "Modify a spaceship", description = "Modify an existing spaceship with the information provided")
	@PutMapping("/{id}")
	public ResponseEntity<SpaceshipDto> modify(@PathVariable Long id, @RequestBody SpaceshipDto spaceship) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.spaceshipService.modify(id, spaceship));
	}

	@Operation(summary = "Delete a spaceship", description = "Deletes a spaceship whose ID matches the provided ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		this.spaceshipService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
