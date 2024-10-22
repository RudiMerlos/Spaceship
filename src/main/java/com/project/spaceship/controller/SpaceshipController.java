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

import com.project.spaceship.model.entity.Spaceship;
import com.project.spaceship.service.SpaceshipService;

@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {

	private final SpaceshipService spaceshipService;

	public SpaceshipController(SpaceshipService spaceshipService) {
		this.spaceshipService = spaceshipService;
	}

	@GetMapping({"", "/"})
	public ResponseEntity<Page<Spaceship>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(this.spaceshipService.findAll(page, size));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Spaceship> getById(@PathVariable Long id) {
		return this.spaceshipService.findById(id).map(spaceship -> ResponseEntity.ok(spaceship))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/search")
	public ResponseEntity<List<Spaceship>> searchByName(@RequestParam String name) {
		return ResponseEntity.ok(this.spaceshipService.findBySpaceshipName(name));
	}

	@PostMapping("/")
	public ResponseEntity<Spaceship> create(@RequestBody Spaceship spaceship) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.spaceshipService.save(spaceship));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Spaceship> edit(@PathVariable Long id, @RequestBody Spaceship spaceship) {
		return this.spaceshipService.findById(id).map(spaceshipDB -> {
			spaceshipDB.setSpaceshipName(spaceship.getSpaceshipName());
			spaceshipDB.setMovieName(spaceship.getMovieName());
			return ResponseEntity.status(HttpStatus.CREATED).body(this.spaceshipService.save(spaceshipDB));
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.spaceshipService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
