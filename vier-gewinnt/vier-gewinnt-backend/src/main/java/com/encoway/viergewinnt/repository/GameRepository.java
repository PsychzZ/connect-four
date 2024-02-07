package com.encoway.viergewinnt.repository;

import com.encoway.viergewinnt.exceptions.NoGameFoundException;
import com.encoway.viergewinnt.game.GameState;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class GameRepository {

    private final Map<String, GameState> games = new HashMap<>();

    public void saveGames(String ID, GameState gameState) {
        games.put(ID, gameState);
    }

    public GameState getGame(String gameID){
        return games.get(gameID);
    }

    public Set<String> getAllKeys() {
        return games.keySet();
    }

}
