package com.mindata.superheroe.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindata.superheroe.entity.SuperHero;
import com.mindata.superheroe.exception.SuperHeroNotFoundException;
import com.mindata.superheroe.repository.SuperHeroRepository;
import com.mindata.superheroe.service.SuperHeroService;

@Service
public class SuperHeroServiceImpl implements SuperHeroService {
	@Autowired
	private SuperHeroRepository superHeroRepository;

	public List<SuperHero> getAllSuperheroes() {
		List<SuperHero> superHeroes = superHeroRepository.findAll();
		superHeroes.stream().findFirst().orElseThrow(() -> new SuperHeroNotFoundException());
		return superHeroes;
	}

	public SuperHero getSuperheroById(Long id) {
		Optional<SuperHero> superHero = superHeroRepository.findById(id);
		return superHero.orElseThrow(() -> new SuperHeroNotFoundException(id));
	}

	public List<SuperHero> searchSuperheroes(String name) {
		List<SuperHero> superHeroes = superHeroRepository.findByNameContaining(name);
		superHeroes.stream().findFirst().orElseThrow(() -> new SuperHeroNotFoundException(name));
		return superHeroes;
	}

	public SuperHero createSuperhero(SuperHero superHero) {
		return superHeroRepository.save(superHero);
	}

	public SuperHero updateSuperhero(Long id, SuperHero superHero) {
		if (superHeroRepository.existsById(id)) {
			superHero.setId(id);
			return superHeroRepository.save(superHero);
		} else {
			return null;
		}
	}

	public boolean deleteSuperhero(Long id) {
		if (superHeroRepository.existsById(id)) {
			superHeroRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

}
