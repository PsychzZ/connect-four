package com.encoway.viergewinnt.service;

import com.encoway.viergewinnt.dto.Player;
import com.encoway.viergewinnt.exceptions.FullColumnException;
import com.encoway.viergewinnt.game.GameState;
import com.encoway.viergewinnt.repository.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Game logic service.
 */
@Service
public class GameLogicService {

    private int fullColumnsCounter = 0;

    private final static int TOP_ROW = 0;
    private final static int COLUMNS = 7;

    @Autowired
    private GameRepository gameRepository;

    /**
     * Instantiates a new Game logic service.
     */
    public GameLogicService() {

    }

    /**
     * Do turn game state.
     *
     * @param gameState the game state
     * @param column    the column
     * @return the game state
     * @throws FullColumnException the full column exception
     */
    public GameState doTurn(GameState gameState, int column) throws FullColumnException {
        Player[][] gameField = gameState.getField();
        for (int i = 6; i > -1; i--) {
            if (gameField[i][column] == null) {
                gameState.setField(i, column, gameState.getCurrentPlayer());
                break;
            }
            if (gameField[0][column] != null) {
                throw new FullColumnException("Column is Full " + column);
            }
        }
        if (checkWinCondition(gameState)) {
            gameState.setWinner(gameState.getCurrentPlayer());
            gameState.setCurrentPlayer(null);
            return gameState;
        }
        for (int x = 0; x < COLUMNS; x++) {
            if (gameField[TOP_ROW][x] != null) {
                fullColumnsCounter++;
            }
        }
        if (fullColumnsCounter == COLUMNS) {
            gameState.setDraw(true);
        }
        fullColumnsCounter = 0;
        gameState.switchPlayer();
        return gameState;
    }

    private boolean checkWinCondition(GameState gameState) {
        return checkRows(gameState) || checkColumns(gameState) || checkDiagonals(gameState);
    }

    private boolean checkRows(GameState gamestate) {
        Player currentPlayer = gamestate.getCurrentPlayer();
        Player[][] field = gamestate.getField();
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 4; col++) {
                if (field[row][col] == currentPlayer &&
                        field[row][col + 1] == currentPlayer &&
                        field[row][col + 2] == currentPlayer &&
                        field[row][col + 3] == currentPlayer) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkColumns(GameState gamestate) {
        Player currentPlayer = gamestate.getCurrentPlayer();
        Player[][] field = gamestate.getField();
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 4; row++) {
                if (field[row][col] == currentPlayer &&
                        field[row + 1][col] == currentPlayer &&
                        field[row + 2][col] == currentPlayer &&
                        field[row + 3][col] == currentPlayer) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonals(GameState gamestate) {
        Player currentPlayer = gamestate.getCurrentPlayer();
        Player[][] field = gamestate.getField();
        int numRows = gamestate.getField().length;
        int numCols = gamestate.getField()[0].length;

        // Überprüfen der Diagonalen von links oben nach rechts unten
        for (int row = 0; row < numRows - 3; row++) {
            for (int col = 0; col < numCols - 3; col++) {
                if (field[row][col] == currentPlayer &&
                        field[row + 1][col + 1] == currentPlayer &&
                        field[row + 2][col + 2] == currentPlayer &&
                        field[row + 3][col + 3] == currentPlayer) {
                    return true;
                }
            }
        }

        // Überprüfen der Diagonalen von rechts oben nach links unten
        for (int row = 0; row < numRows - 3; row++) {
            for (int col = 3; col < numCols; col++) {
                if (field[row][col] == currentPlayer &&
                        field[row + 1][col - 1] == currentPlayer &&
                        field[row + 2][col - 2] == currentPlayer &&
                        field[row + 3][col - 3] == currentPlayer) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Initialize new game.
     *
     * @return the game state
     */
    public GameState initializeNewGameState() {
        return new GameState();
    }

}
