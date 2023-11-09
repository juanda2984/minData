package com.mindata.superheroe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mindata.superheroe.entity.SuperHero;
import com.mindata.superheroe.service.SuperHeroService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class SuperHeroIntegrationTest {

	@Autowired
	private SuperHeroService superHeroService;

	@Test
	public void testGetSuperheroById() {
		SuperHero superHero = SuperHero.builder().id(100L).name("Capitana Marvel").superPower("Super fuerza").build();

		SuperHero found = superHeroService.getSuperheroById(superHero.getId());
		assertEquals(superHero.getName(), found.getName());
	}
	
	@Test
	void testSearchSuperheroes() {
		SuperHero superHero = SuperHero.builder().name("Superman").superPower("Vision Rayos X").build();
		SuperHero superHero2 = SuperHero.builder().name("Spiderman").superPower("Trepar por las paredes").build();
		SuperHero superHero3 = SuperHero.builder().name("Chapulín Colorado").superPower("Noble como una lechuga")
				.build();

		superHeroService.createSuperhero(superHero);
		superHeroService.createSuperhero(superHero2);
		superHeroService.createSuperhero(superHero3);

		List<SuperHero> searchResults = superHeroService.searchSuperheroes("man");

		assertEquals(2, searchResults.size());
	}

	@Test
	void testCreateSuperhero() {
		SuperHero superHero = SuperHero.builder().name("Batman").superPower("Super Inteligente").build();
		SuperHero createdSuperhero = superHeroService.createSuperhero(superHero);
		assertNotNull(createdSuperhero.getId());
		assertEquals("Batman", createdSuperhero.getName());
	}
	
	@Test
	public void testUpdateSuperhero() {
		SuperHero superHero = SuperHero.builder().name("Superman").superPower("Volar").build();
		assertEquals(superHeroService.updateSuperhero(100L,superHero).getName(), superHero.getName());
	}

	@Test
	void testDeleteSuperhero() {
		SuperHero superhero = SuperHero.builder().name("Wonder Woman").superPower("Super Fuerza").build();
		superhero = superHeroService.createSuperhero(superhero);
		Long superheroId = superhero.getId();
		assertTrue(superHeroService.deleteSuperhero(superheroId));
	}
}
