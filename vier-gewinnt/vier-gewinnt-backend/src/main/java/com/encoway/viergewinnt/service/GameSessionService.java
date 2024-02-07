package com.encoway.viergewinnt.service;

import com.encoway.viergewinnt.dto.GameObject;
import com.encoway.viergewinnt.dto.Player;
import com.encoway.viergewinnt.exceptions.FullColumnException;
import com.encoway.viergewinnt.exceptions.NoGameFoundException;
import com.encoway.viergewinnt.game.GameState;
import com.encoway.viergewinnt.repository.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameSessionService {

    @Autowired
    private GameLogicService gameLogicService;

    @Autowired
    private GameIdService gameIdService;

    @Autowired
    private GameRepository gameRepository;

    /**
     * Join game.
     *
     * @param id the id
     * @return the game state
     * @throws NoGameFoundException the no game found exception
     */
    public GameState joinGame(String id) throws NoGameFoundException {
        GameState game = gameIdService.getGameWithID(id);
        if (game == null) {
            throw new NoGameFoundException("No Game Found with " + id);
        }
        return game;
    }

    /**
     * Create new game object.
     *
     * @return the game object
     */
    public GameObject createNewGame() {
        GameState gameState = gameLogicService.initializeNewGameState();
        String id = gameIdService.getUniqueID();
        saveGame(id, gameState);
        return new GameObject(id, gameState);
    }

    /**
     * Get current player.
     *
     * @param id the id
     * @return the player
     */
    public Player getCurrentPlayer(String id) {
        return gameIdService.getGameWithID(id).getCurrentPlayer();
    }

    /**
     * Get current game.
     *
     * @param id the id
     * @return the game state
     */
    public GameState getCurrentGameState(String id) {
        return gameIdService.getGameWithID(id);
    }

    /**
     * Save game.
     *
     * @param id        the id
     * @param gameState the game state
     * @return the game state
     */
    public GameState saveGame(String id, GameState gameState) {
        gameRepository.saveGames(id, gameState);
        return gameState;
    }

    /**
     * Do turn game state.
     *
     * @param id     the id
     * @param column the column
     * @return the game state
     * @throws FullColumnException the full column exception
     */
    public GameState doTurn(String id, int column) throws FullColumnException {
        return saveGame(id, gameLogicService.doTurn(getCurrentGameState(id), column));
    }
}
