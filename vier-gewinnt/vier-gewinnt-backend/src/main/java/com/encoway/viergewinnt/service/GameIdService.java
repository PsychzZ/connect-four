package com.encoway.viergewinnt.service;

import com.encoway.viergewinnt.game.GameState;
import com.encoway.viergewinnt.repository.GameRepository;
import com.encoway.viergewinnt.utils.GameIdUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameIdService {

    @Autowired
    private GameIdUtils gameIdUtils;

    @Autowired
    private GameRepository gameRepository;

    public String getUniqueID() {
        return gameIdUtils.generateUniqueId(gameRepository.getAllKeys());
    }

    public GameState getGameWithID(String id) {
        return gameRepository.getGame(id);
    }

}
