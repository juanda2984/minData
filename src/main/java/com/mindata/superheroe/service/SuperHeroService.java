package com.mindata.superheroe.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.mindata.superheroe.entity.SuperHero;

public interface SuperHeroService {
	@Cacheable("superHeroes")
	public List<SuperHero> getAllSuperheroes();
	public SuperHero getSuperheroById(Long id);
	@Cacheable("superHeroesForName")
	public List<SuperHero> searchSuperheroes(String name);
	public SuperHero createSuperhero(SuperHero superHero);
	public SuperHero updateSuperhero(Long id, SuperHero superHero);
	public boolean deleteSuperhero(Long id);
}
