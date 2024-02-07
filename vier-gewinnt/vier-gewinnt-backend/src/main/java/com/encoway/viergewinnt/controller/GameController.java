package com.encoway.viergewinnt.controller;

import com.encoway.viergewinnt.dto.GameObject;
import com.encoway.viergewinnt.dto.Player;
import com.encoway.viergewinnt.exceptions.FullColumnException;
import com.encoway.viergewinnt.exceptions.NoGameFoundException;
import com.encoway.viergewinnt.game.GameState;
import com.encoway.viergewinnt.service.GameSessionService;

import java.util.InputMismatchException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/viergewinnt")
public class GameController {

    @Autowired
    private GameSessionService gameSessionService;

    /**
     * Initialize game.
     *
     * @return the response entity
     */
    @GetMapping("/newGame")
    public ResponseEntity<GameObject> initializeGame() {
        GameObject gameObject = gameSessionService.createNewGame();
        return ResponseEntity.ok(gameObject);
    }

    /**
     * Check current player.
     *
     * @param gameid the game id
     * @return the response entity
     */
    @GetMapping("/{gameid}/currentPlayer")
    public ResponseEntity<Player> checkCurrentPlayer(@PathVariable String gameid) {
        return ResponseEntity.ok(gameSessionService.getCurrentPlayer(gameid));
    }

    /**
     * Get current game state.
     *
     * @param gameid the game id
     * @return the response entity
     */
    @GetMapping("/{gameid}")
    public ResponseEntity<GameState> getCurrentGameState(@PathVariable String gameid) {
        GameState gameState = gameSessionService.getCurrentGameState(gameid);
        if (gameState == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(gameState);
    }

    /**
     * Do Turn.
     *
     * @param gameid the game id
     * @param column the column
     * @return the response entity
     */
    @PostMapping("/{gameid}/doTurn/")
    public ResponseEntity<GameState> placeChip(@PathVariable String gameid, @RequestParam int column) {
        try {
            GameState gameState = gameSessionService.doTurn(gameid, column);
            return ResponseEntity.ok(gameState);
        } catch (FullColumnException | ArrayIndexOutOfBoundsException | InputMismatchException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Join game.
     *
     * @param gameid the game id
     * @return the response entity
     */
    @PostMapping("/join/")
    public ResponseEntity<GameState> joinGame(@RequestParam String gameid) {
        try {
            return ResponseEntity.ok(gameSessionService.joinGame(gameid));
        } catch (NoGameFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{gameid}/restart")
    public ResponseEntity<GameState> restartGame(@PathVariable String gameid) {
        GameState currentGame = gameSessionService.getCurrentGameState(gameid);
        if (currentGame.getWinner() == null) {
            return ResponseEntity.badRequest().build();
        }
        GameState newGame = new GameState();
        newGame.setCurrentPlayer(currentGame.getWinner().other());
        return ResponseEntity.ok().body(gameSessionService.saveGame(gameid, newGame));
    }

}
