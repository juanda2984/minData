package com.mindata.superheroe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.mindata.superheroe.entity.SuperHero;
import com.mindata.superheroe.service.SuperHeroService;
import com.mindata.superheroe.util.ExecutionTimeLog;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/superHeroes")
@Tag(name = "SuperHeroes", description = "API de Super Héroes")
public class SuperHeroController {
	@Autowired
	private SuperHeroService superHeroService;

	@GetMapping
	@ExecutionTimeLog("getAllSuperHeroes")
	@Operation(summary = "Obtener todos los superhéroes", description = "Obtiene una lista de todos los superhéroes")
	@ApiResponse(responseCode = "200", description = "Lista de superhéroes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SuperHero.class))))
	public ResponseEntity<List<SuperHero>> getAllSuperheroes() {
		return ResponseEntity.ok(superHeroService.getAllSuperheroes());
	}

	@GetMapping("/{id}")
	@ExecutionTimeLog("getSuperheroById")
	@Operation(summary = "Obtener un superhéroe por id", description = "Obtener un superhéroe por Id")
	@ApiResponse(responseCode = "200", description = "Superhéroe consultado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SuperHero.class)) })
	public ResponseEntity<SuperHero> getSuperheroById(@PathVariable Long id) {
		SuperHero superHero = superHeroService.getSuperheroById(id);
		return ResponseEntity.ok(superHero);
	}

	@GetMapping("/search")
	@ExecutionTimeLog("searchSuperheroes")
	@Operation(summary = "Obtener los superhéroes por un name", description = "Obtener los superhéroes por sección de un name")
	@ApiResponse(responseCode = "200", description = "Superhéroe consultado", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SuperHero.class)) })
	public ResponseEntity<List<SuperHero>> searchSuperheroes(@RequestParam String name) {
		return ResponseEntity.ok(superHeroService.searchSuperheroes(name));
	}

	@PostMapping
	@ExecutionTimeLog("createSuperhero")
	@Operation(summary = "Crear superhéroes", description = "Crear superhéroe")
	@ApiResponse(responseCode = "200", description = "Crear superhéroe", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SuperHero.class)) })
	public ResponseEntity<SuperHero> createSuperhero(@RequestBody @Valid SuperHero superHero) {
		return ResponseEntity.ok(superHeroService.createSuperhero(superHero));
	}

	@PutMapping("/{id}")
	@ExecutionTimeLog("updateSuperhero")
	@Operation(summary = "Actualizar superhéroes", description = "Actualizar superhéroe")
	@ApiResponse(responseCode = "200", description = "Actualizar superhéroe", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SuperHero.class)) })
	public ResponseEntity<SuperHero> updateSuperhero(@PathVariable Long id, @RequestBody @Valid SuperHero superHero) {
		SuperHero superHeroLocal = superHeroService.updateSuperhero(id, superHero);
		if (superHeroLocal != null) {
			return ResponseEntity.ok(superHero);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	@ExecutionTimeLog("deleteSuperhero")
	@Operation(summary = "Elimnar superhéroes", description = "Elimnar superhéroe")
	@ApiResponse(responseCode = "200", description = "Elimnar superhéroe")
	public ResponseEntity<Void> deleteSuperhero(@PathVariable Long id) {
		if (superHeroService.deleteSuperhero(id)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
