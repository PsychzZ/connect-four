package com.encoway.viergewinnt;

import com.encoway.viergewinnt.dto.Player;
import com.encoway.viergewinnt.exceptions.FullColumnException;
import com.encoway.viergewinnt.service.GameLogicService;
import com.encoway.viergewinnt.game.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.encoway.viergewinnt.dto.Player.O;
import static com.encoway.viergewinnt.dto.Player.X;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicServiceTest {

    private GameState gameState;

    private GameLogicService classUnderTest;


    @BeforeEach
    public void setUp() {
        classUnderTest = new GameLogicService();
        this.gameState = new GameState();
    }

    @Test
    public void placeOneChipTest() throws FullColumnException {
        Player[][] expectedGameField = new Player[7][7];
        expectedGameField[6][0] = X;

        gameState = classUnderTest.doTurn(gameState, 0);

        assertEquals(expectedGameField[6][0], gameState.getField()[6][0]);
    }

    @Test
    public void placeTwoChipsOnAnotherTest() throws FullColumnException {
        Player[][] expectedGameField = new Player[7][7];
        expectedGameField[6][0] = X;
        expectedGameField[5][0] = O;

        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 0);


        assertEquals(expectedGameField[6][0], gameState.getField()[6][0]);
        assertEquals(expectedGameField[5][0], gameState.getField()[5][0]);
    }

    @Test
    public void placeTwoChipsNextToEachOtherTest() throws FullColumnException {
        Player[][] expectedGameField = new Player[7][7];
        expectedGameField[6][0] = X;
        expectedGameField[6][1] = O;

        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 1);


        assertEquals(expectedGameField[6][0], gameState.getField()[6][0]);
        assertEquals(expectedGameField[6][1], gameState.getField()[6][1]);
    }

    @Test
    public void testPlayerChange() throws FullColumnException {
        Player player1 = X;
        Player player2 = O;

        assertEquals(player1, gameState.getCurrentPlayer());

        gameState = classUnderTest.doTurn(gameState, 0);
        assertEquals(player2, gameState.getCurrentPlayer());

        gameState = classUnderTest.doTurn(gameState, 0);
        assertEquals(player1, gameState.getCurrentPlayer());

        gameState = classUnderTest.doTurn(gameState, 0);
        assertEquals(player2, gameState.getCurrentPlayer());
    }


    @Test
    public void testFullColumnException() throws FullColumnException {
        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 0);

        assertThrows(FullColumnException.class, () -> classUnderTest.doTurn(gameState, 0));
    }

    @Test
    public void placeChipBeyondColumnRange_OutOfBoundsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> classUnderTest.doTurn(gameState, 7));
    }

    @Test
    public void testColumnWin() throws FullColumnException {
        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 1);
        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 1);
        gameState = classUnderTest.doTurn(gameState, 0);
        gameState = classUnderTest.doTurn(gameState, 1);
        gameState = classUnderTest.doTurn(gameState, 0);

        assertSame(gameState.getWinner(), X);
    }

    @Test
    public void testRowWin() throws FullColumnException {
        gameState.setGameField(new Player[][]{
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {O, O, O, null, null, null, null},
                {X, X, X, null, null, null, null}});

        gameState = classUnderTest.doTurn(gameState, 3);

        assertSame(gameState.getWinner(), X);
    }

    @Test
    public void testLeftDiagonalWin() throws FullColumnException {
        gameState.setGameField(new Player[][]{
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {X, null, null, null, null, null, null},
                {O, X, null, null, null, null, null},
                {X, O, X, null, null, null, null},
                {X, O, O, X, O, null, null}});

        gameState = classUnderTest.doTurn(gameState, 3);

        assertSame(gameState.getWinner(), X);
    }

    @Test
    public void testRightDiagonalWin() throws FullColumnException {
        gameState.setGameField(new Player[][]{
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, X},
                {null, null, null, null, null, X, O},
                {null, null, null, null, X, O, X},
                {null, null, O, X, O, O, X}});

        gameState = classUnderTest.doTurn(gameState, 3);

        assertSame(gameState.getWinner(), X);
    }

    @Test
    public void testDraw() throws FullColumnException {
        gameState.setGameField(new Player[][]{
                {O, X, X, O, X, X, null},
                {X, O, O, X, O, O, O},
                {O, X, X, O, X, X, X},
                {X, O, O, X, O, O, O},
                {O, X, X, O, X, X, X},
                {X, O, O, X, O, O, O},
                {O, X, X, O, X, X, O}});

        gameState = classUnderTest.doTurn(gameState, 6);

        assertTrue(gameState.isDraw());
    }

}