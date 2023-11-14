package com.mindata.superheroe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mindata.superheroe.entity.SuperHero;
import com.mindata.superheroe.repository.SuperHeroRepository;
import com.mindata.superheroe.service.SuperHeroService;

@SpringBootTest
class SuperHeroeApplicationTests {

	@Autowired
	private SuperHeroService superHeroService;

	@MockBean
	private SuperHeroRepository superHeroRepository;

	@Test
	public void testGetSuperheroById() {
		SuperHero superHero = SuperHero.builder().id(1L).name("Superman").superPower("Vision Rayos X").build();

		when(superHeroRepository.findById(1L)).thenReturn(Optional.of(superHero));
		SuperHero found = superHeroService.getSuperheroById(superHero.getId());
		assertEquals(superHero.getName(), found.getName());
	}
	
	@Test
	public void testSearchSuperheroes() {
		SuperHero superHero = SuperHero.builder().id(1L).name("Superman").superPower("Vision Rayos X").build();
		SuperHero superHero2 = SuperHero.builder().id(2L).name("Spiderman").superPower("Trepar por las paredes").build();
		
		List<SuperHero> expectedResults = List.of(superHero, superHero2);
		
		when(superHeroRepository.findByNameContaining("man")).thenReturn(expectedResults);
		List<SuperHero> founds = superHeroService.searchSuperheroes("man");
		assertEquals(founds, expectedResults);
	}

	@Test
	public void testCreateSuperhero() {
		SuperHero superHero = SuperHero.builder().id(2L).name("Spiderman").superPower("Trepar por las paredes").build();

		when(superHeroRepository.save(superHero)).thenReturn(superHero);
		SuperHero createdSuperHero = superHeroService.createSuperhero(superHero);
		assertNotNull(createdSuperHero);
		assertEquals("Spiderman", createdSuperHero.getName());
		assertEquals("Trepar por las paredes", createdSuperHero.getSuperPower());
	}
	
	@Test
	public void testUpdateSuperhero() {
		SuperHero superHero = SuperHero.builder().name("Superman").superPower("Volar").build();

		when(superHeroRepository.existsById(1L)).thenReturn(true);
		superHeroService.updateSuperhero(1L,superHero);
		verify(superHeroRepository, times(1)).save(superHero);
	}

	@Test
	public void testDeleteSuperhero() {
		SuperHero superHero = SuperHero.builder().id(1L).name("Superman").superPower("Vision Rayos X").build();

		when(superHeroRepository.existsById(superHero.getId())).thenReturn(true);
		superHeroService.deleteSuperhero(superHero.getId());
		verify(superHeroRepository, times(1)).deleteById(1L);
	}

}
