package com.encoway.viergewinnt.controller;

import com.encoway.viergewinnt.dto.Player;
import com.encoway.viergewinnt.dto.GameObject;
import com.encoway.viergewinnt.exceptions.FullColumnException;
import com.encoway.viergewinnt.exceptions.NoGameFoundException;
import com.encoway.viergewinnt.game.GameState;
import com.encoway.viergewinnt.service.GameSessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    private final String TEST_GAME_ID = "aaaaa";


    @Mock
    private GameSessionService gameSessionService;

    @InjectMocks
    private GameController classUnderTest;

    @Mock
    private GameState gameState;


    @Test
    public void testInitializeNewGame() {
        when(gameSessionService.createNewGame()).thenReturn(new GameObject(TEST_GAME_ID, gameState));

        assertEquals(classUnderTest.initializeGame().getStatusCode(), HttpStatus.OK);
    }


    @Test
    public void testGetCurrentGameState() throws NoGameFoundException {
        when(gameSessionService.getCurrentGameState(TEST_GAME_ID)).thenReturn(gameState);

        assertEquals(classUnderTest.getCurrentGameState(TEST_GAME_ID).getBody(), gameState);
    }

    @Test
    public void testJoinGame() throws NoGameFoundException {
        when(gameSessionService.joinGame(TEST_GAME_ID)).thenReturn(gameState);

        assertEquals(HttpStatus.OK, classUnderTest.joinGame(TEST_GAME_ID).getStatusCode());
    }

    @Test
    public void testJoinNonExistingGame() throws NoGameFoundException {
        when(gameSessionService.joinGame(TEST_GAME_ID)).thenThrow(NoGameFoundException.class);

        assertEquals(HttpStatus.BAD_REQUEST, classUnderTest.joinGame(TEST_GAME_ID).getStatusCode());
    }

    @Test
    public void testDoTurn() throws FullColumnException {
        when(gameSessionService.doTurn(TEST_GAME_ID, 3)).thenReturn(gameState);

        assertEquals(classUnderTest.placeChip(TEST_GAME_ID, 3).getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testPlayerMakesCorrectTurn() throws NoGameFoundException {
        when(gameSessionService.getCurrentPlayer(TEST_GAME_ID)).thenReturn(Player.X);

        assertEquals(classUnderTest.checkCurrentPlayer(TEST_GAME_ID).getBody(), Player.X);
    }

    @Test
    public void testPlayerMakesIncorrectTurn() throws FullColumnException {
        when(gameSessionService.doTurn(TEST_GAME_ID, 7)).thenThrow(ArrayIndexOutOfBoundsException.class);

        assertEquals(classUnderTest.placeChip(TEST_GAME_ID, 7).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testRestartGame(){
        when(gameSessionService.getCurrentGameState(TEST_GAME_ID)).thenReturn(gameState);
        when(gameState.getWinner()).thenReturn(Player.X);

        assertEquals(classUnderTest.restartGame(TEST_GAME_ID).getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testFailedRestartGame(){
        when(gameSessionService.getCurrentGameState(TEST_GAME_ID)).thenReturn(gameState);
        when(gameState.getWinner()).thenReturn(null);

        assertEquals(classUnderTest.restartGame(TEST_GAME_ID).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}