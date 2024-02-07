package com.encoway.viergewinnt.service;


import com.encoway.viergewinnt.dto.GameObject;
import com.encoway.viergewinnt.dto.Player;
import com.encoway.viergewinnt.exceptions.FullColumnException;
import com.encoway.viergewinnt.exceptions.NoGameFoundException;
import com.encoway.viergewinnt.game.GameState;
import com.encoway.viergewinnt.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameSessionServiceTest {


    @Mock
    private GameLogicService gameLogicService;

    @Mock
    private GameIdService gameIdService;

    @Mock
    private GameRepository gameRepository;


    @InjectMocks
    private GameSessionService classUnderTest;
    private final String GAME_ID = "aaaaa";

    @Mock
    private GameState gameState;

    @Test
    public void testCreateNewGame(){
        when(gameLogicService.initializeNewGameState()).thenReturn(gameState);
        when(gameIdService.getUniqueID()).thenReturn(GAME_ID);

        assertEquals(classUnderTest.createNewGame().getId(), new GameObject(GAME_ID, gameState).getId());
    }

    @Test
    public void testJoinGame() throws NoGameFoundException {
        when(gameIdService.getGameWithID(GAME_ID)).thenReturn(gameState);

        assertEquals(gameState, classUnderTest.joinGame(GAME_ID));
    }

    @Test
    public void testJoinGameWithGameNull(){
        when(gameIdService.getGameWithID(GAME_ID)).thenReturn(null);

        assertThrows(NoGameFoundException.class, () -> classUnderTest.joinGame(GAME_ID));
    }

    @Test
    public void testGetCurrentPlayer(){
        when(gameIdService.getGameWithID(GAME_ID)).thenReturn(gameState);
        when(gameState.getCurrentPlayer()).thenReturn(Player.X);

        assertEquals(classUnderTest.getCurrentPlayer(GAME_ID), Player.X);
    }

    @Test
    public void testGetCurrentGameState(){
        when(gameIdService.getGameWithID(GAME_ID)).thenReturn(gameState);

        assertEquals(classUnderTest.getCurrentGameState(GAME_ID), gameState);
    }

    @Test
    public void testSaveGame(){
       assertEquals(classUnderTest.saveGame(GAME_ID,gameState),gameState);
    }

    @Test
    public void testDoTurn() throws FullColumnException{
        when(gameIdService.getGameWithID(GAME_ID)).thenReturn(gameState);
        when(gameLogicService.doTurn(gameState, 3)).thenReturn(gameState);

        assertEquals(classUnderTest.doTurn(GAME_ID, 3), gameState);
    }
}
