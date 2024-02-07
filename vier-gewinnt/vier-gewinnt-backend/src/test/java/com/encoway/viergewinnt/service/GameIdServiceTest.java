package com.encoway.viergewinnt.service;


import com.encoway.viergewinnt.exceptions.NoGameFoundException;
import com.encoway.viergewinnt.game.GameState;
import com.encoway.viergewinnt.repository.GameRepository;
import com.encoway.viergewinnt.utils.GameIdUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameIdServiceTest {

    @Mock
    private GameIdUtils gameIdUtils;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameIdService classUnderTest;


    private final String TEST_GAME_ID = "aaaaa";
    Set<String> keys;

    private GameState gameState = new GameState();

    @Test
    public void testGetUniqueID(){
        when(gameIdUtils.generateUniqueId(keys)).thenReturn(TEST_GAME_ID);
        when(gameRepository.getAllKeys()).thenReturn(keys);

        assertEquals(classUnderTest.getUniqueID(), TEST_GAME_ID);
    }

    @Test
    public void testGetGameWithUniqueID() throws NoGameFoundException {
        when(gameRepository.getGame(TEST_GAME_ID)).thenReturn(gameState);

        assertEquals(classUnderTest.getGameWithID(TEST_GAME_ID), gameState);
    }
}
