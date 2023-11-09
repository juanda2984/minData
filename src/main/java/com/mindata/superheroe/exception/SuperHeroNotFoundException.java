package com.mindata.superheroe.exception;

public class SuperHeroNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SuperHeroNotFoundException(Long id) {
        super("Superhéroe no encontrado con ID: " + id);
    }
	
	public SuperHeroNotFoundException() {
        super("Superhéroes no encontrados");
    }

	public SuperHeroNotFoundException(String name) {
		 super("Superhéroes no encontrados con el prefijo "+name);
	}
}
