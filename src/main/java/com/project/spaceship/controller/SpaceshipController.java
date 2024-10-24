package com.project.spaceship.controller;

import java.util.List;

import org.springframework.data.domain.Page;
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

import com.project.spaceship.exception.ItemNotFoundException;
import com.project.spaceship.model.entity.Spaceship;
import com.project.spaceship.service.SpaceshipService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Spaceships management", description = "Operations related to spaceship management")
@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {

	private final SpaceshipService spaceshipService;

	public SpaceshipController(SpaceshipService spaceshipService) {
		this.spaceshipService = spaceshipService;
	}

	@Operation(summary = "Get all spaceships", description = "Returns a list of all spaceships registered in the system")
	@GetMapping({"", "/"})
	public ResponseEntity<Page<Spaceship>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(this.spaceshipService.findAll(page, size));
	}

	@Operation(summary = "Get a spaceship by ID", description = "Returns a spaceship whose ID matches the provided ID")
	@GetMapping("/{id}")
	public ResponseEntity<Spaceship> getById(@PathVariable Long id) {
		return this.spaceshipService.findById(id).map(spaceship -> ResponseEntity.ok(spaceship))
				.orElseThrow(() -> new ItemNotFoundException("Spaceship with ID " + id + " is not found in the database."));
	}

	@Operation(summary = "Get a spaceship by name", description = "Returns a spaceship whose name matches the provided name")
	@GetMapping("/search")
	public ResponseEntity<List<Spaceship>> searchByName(@RequestParam String name) {
		return ResponseEntity.ok(this.spaceshipService.findBySpaceshipName(name));
	}

	@Operation(summary = "Create a new spaceship", description = "Create a new spaceship with the information provided")
	@PostMapping("/")
	public ResponseEntity<Spaceship> create(@RequestBody Spaceship spaceship) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.spaceshipService.save(spaceship));
	}

	@Operation(summary = "Modify a spaceship", description = "Modify an existing spaceship with the information provided")
	@PutMapping("/{id}")
	public ResponseEntity<Spaceship> edit(@PathVariable Long id, @RequestBody Spaceship spaceship) {
		return this.spaceshipService.findById(id).map(spaceshipDB -> {
			spaceshipDB.setSpaceshipName(spaceship.getSpaceshipName());
			spaceshipDB.setMovieName(spaceship.getMovieName());
			return ResponseEntity.status(HttpStatus.CREATED).body(this.spaceshipService.save(spaceshipDB));
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Operation(summary = "Delete a spaceship", description = "Deletes a spaceship whose ID matches the provided ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		return this.spaceshipService.findById(id).map(spaceship -> {
			this.spaceshipService.deleteById(id);
			return ResponseEntity.noContent().build();
		}).orElseThrow(() -> new ItemNotFoundException("Spaceship with ID " + id + " is not found in the database."));
	}

}
