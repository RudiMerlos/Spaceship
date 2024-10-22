package com.project.spaceship.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "spaceship")
public class Spaceship implements Serializable {

	private static final long serialVersionUID = 2949732088094493592L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "spaceship_name")
	private String spaceshipName;

	@Column(name = "movie_name")
	private String movieName;

}
