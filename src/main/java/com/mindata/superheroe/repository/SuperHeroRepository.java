package com.mindata.superheroe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindata.superheroe.entity.SuperHero;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHero, Long> {
    List<SuperHero> findByNameContaining(String name);
}
