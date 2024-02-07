package com.encoway.viergewinnt.dto;

import com.encoway.viergewinnt.game.GameState;

/**
 * The type Game object.
 */
public class GameObject {

    private final String id;

    private GameState gameState;

    /**
     * Instantiates a new Game object.
     *
     * @param id        the id
     * @param gameState the game state
     */
    public GameObject(String id, GameState gameState) {
        this.id = id;
        this.gameState = gameState;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets game state.
     *
     * @return the game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets game state.
     *
     * @param gameState the game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
