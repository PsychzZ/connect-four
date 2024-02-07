package com.encoway.viergewinnt.game;

import com.encoway.viergewinnt.exceptions.FullColumnException;
import com.encoway.viergewinnt.printer.Printer;
import com.encoway.viergewinnt.service.GameLogicService;
import com.encoway.viergewinnt.utils.Utils;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * The type Console view.
 */
@Controller
public class ConsoleView {
    private GameState gameState;

    private final GameLogicService gameLogicService;

    private final Printer printer;

    /**
     * Instantiates a new Console view.
     */
    public ConsoleView() {
        this.gameState = new GameState();
        this.gameLogicService = new GameLogicService();
        this.printer = new Printer();
    }

    /**
     * Run game.
     */
    public void runGame() {
        printer.greetingMessage();
        printer.displayField(gameState);
        while (!gameTick());
        gameFinished();
    }

    private boolean gameTick() {
        printer.printColumnSelectMessage();
        try {
            gameState = gameLogicService.doTurn(gameState, Utils.getColumnFromPlayer());
            printer.displayField(gameState);
        } catch (ArrayIndexOutOfBoundsException | InputMismatchException | FullColumnException e) {
            printer.printInputCorrectColumnNumber();
        }
        return gameState.getWinner() != null || gameState.isDraw();
    }

    private void gameFinished() {
        if (gameState.isDraw()) {
            printer.printDraw();
        } else {
            printer.printWinner(gameState.getCurrentPlayer());
        }
        anotherGame();
    }

    private void anotherGame() {
        printer.printAnotherGame();
        try {
            if (Utils.getInputForPlayingAnotherGame()) {
                runGame();
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            printer.printInputCorrectBoolean();
            anotherGame();
        }
    }
}
