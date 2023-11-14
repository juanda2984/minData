package com.mindata.superheroe;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mindata.superheroe.entity.SuperHero;
import com.mindata.superheroe.exception.SuperHeroNotFoundException;
import com.mindata.superheroe.service.SuperHeroService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class SuperHeroIntegrationTest {

	@Autowired
	private SuperHeroService superHeroService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testGetAllSuperheroes() throws Exception {
		SuperHero superHero = SuperHero.builder().id(100L).name("Capitana Marvel").superPower("Super fuerza").build();

		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/superHeroes")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andReturn();
		ObjectMapper mapper = new ObjectMapper();

		List<SuperHero> founds = mapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<SuperHero>>() {
				});
		assertThat(founds, contains(hasProperty("name", is(superHero.getName()))));
	}

	@Test
	public void testGetSuperheroById() throws Exception {
		SuperHero superHero = SuperHero.builder().id(100L).name("Capitana Marvel").superPower("Super fuerza").build();

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/superHeroes/" + superHero.getId())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		ObjectMapper mapper = new ObjectMapper();

		SuperHero found = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<SuperHero>() {
		});
		assertEquals(superHero.getName(), found.getName());
	}

	@Test
	void testSearchSuperheroes() throws Exception {
		SuperHero superHero = SuperHero.builder().name("Superman").superPower("Vision Rayos X").build();
		SuperHero superHero2 = SuperHero.builder().name("Spiderman").superPower("Trepar por las paredes").build();
		SuperHero superHero3 = SuperHero.builder().name("Chapulín Colorado").superPower("Noble como una lechuga")
				.build();

		superHeroService.createSuperhero(superHero);
		superHeroService.createSuperhero(superHero2);
		superHeroService.createSuperhero(superHero3);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/superHeroes/search").param("name", "man")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		ObjectMapper mapper = new ObjectMapper();

		List<SuperHero> founds = mapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<SuperHero>>() {
				});

		assertEquals(2, founds.size());
	}

	@Test
	void testCreateSuperhero() throws Exception {
		SuperHero superhero = SuperHero.builder().name("Batman").superPower("Super Inteligente").build();

		ObjectMapper mapperPost = new ObjectMapper();
		ObjectWriter ow = mapperPost.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(superhero);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/superHeroes").content(requestJson)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		ObjectMapper mapperReturn = new ObjectMapper();

		SuperHero found = mapperReturn.readValue(result.getResponse().getContentAsString(),
				new TypeReference<SuperHero>() {
				});
		assertNotNull(found.getId());
		assertEquals("Batman", found.getName());
	}

	@Test
	public void testUpdateSuperhero() throws Exception {
		SuperHero superhero = SuperHero.builder().name("Superman").superPower("Volar").build();
		ObjectMapper mapperPost = new ObjectMapper();
		ObjectWriter ow = mapperPost.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(superhero);

		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.put("/superHeroes/100").content(requestJson)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		ObjectMapper mapperReturn = new ObjectMapper();

		SuperHero found = mapperReturn.readValue(result.getResponse().getContentAsString(),
				new TypeReference<SuperHero>() {
				});

		assertEquals(found.getName(), superhero.getName());
	}

	@Test
	void testUpdateSuperheroNoExist() {
		SuperHero superhero = SuperHero.builder().name("Wonder Woman").superPower("Super Fuerza").build();
		assertEquals(superHeroService.updateSuperhero(1L, superhero), null);
	}

	@Test
	void testDeleteSuperhero() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/superHeroes/100")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	void testDeleteSuperheroNoExist() {
		SuperHero superhero = SuperHero.builder().name("Wonder Woman").superPower("Super Fuerza").build();
		superhero = superHeroService.createSuperhero(superhero);
		Long superheroId = superhero.getId();
		assertFalse(superHeroService.deleteSuperhero(++superheroId));
	}

	@Test
	public void testGetAllSuperheroesNoExist() {
		SuperHero superHero = SuperHero.builder().id(100L).name("Capitana Marvel").superPower("Super fuerza").build();
		superHeroService.deleteSuperhero(superHero.getId());
		SuperHeroNotFoundException thrown = assertThrows(SuperHeroNotFoundException.class,
				() -> superHeroService.getAllSuperheroes());
		assertTrue(thrown.getMessage().contains("Superhéroes no encontrados"));
	}

	@Test
	public void testGetSuperheroByIdNoExist() {
		SuperHero superHero = SuperHero.builder().id(100L).name("Capitana Marvel").superPower("Super fuerza").build();
		superHeroService.deleteSuperhero(superHero.getId());
		SuperHeroNotFoundException thrown = assertThrows(SuperHeroNotFoundException.class,
				() -> superHeroService.getSuperheroById(superHero.getId()));
		assertTrue(thrown.getMessage().contains("Superhéroe no encontrado con ID"));
	}

	@Test
	void testSearchSuperheroesNoExist() {
		SuperHeroNotFoundException thrown = assertThrows(SuperHeroNotFoundException.class,
				() -> superHeroService.searchSuperheroes("man"));
		assertTrue(thrown.getMessage().contains("Superhéroes no encontrados con el prefijo"));
	}
}
