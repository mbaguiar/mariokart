package com.lpoo1718_t1g3.mariokart.model;

import java.io.Serializable;

/**
 * Represents a Character in the game
 */
public class Character implements Serializable {

    private String name;
    private boolean available = true;

    /**
     * Constructs a character with the given name
     * @param name character name
     */
    public Character(String name) {
        this.name = name;
    }

    /**
     * Gets character name
     * @return Returns the character name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if the character is available or not
     * @return Returns true if the character is available and false otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets character availability
     * @param available true to make the character available and false to make the character unavailable
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

}