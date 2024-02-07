package com.encoway.viergewinnt;

import com.encoway.viergewinnt.dto.Player;
import com.encoway.viergewinnt.exceptions.FullColumnException;
import com.encoway.viergewinnt.game.ConsoleView;
import com.encoway.viergewinnt.service.GameLogicService;
import com.encoway.viergewinnt.game.GameState;
import com.encoway.viergewinnt.printer.Printer;
import com.encoway.viergewinnt.utils.Utils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@Disabled
@ExtendWith(MockitoExtension.class)
public class ConsoleViewTest {

    @Mock
    private GameLogicService gameLogicService;

    @Mock
    private GameState gameState;

    @Mock
    private Printer printer;

    @InjectMocks
    private ConsoleView classUnderTest = new ConsoleView();



    @Test
    public void testSuccessfulGameStart() throws FullColumnException {
        when(gameState.getWinner()).thenReturn(Player.X);
        when(gameLogicService.doTurn(gameState, 3)).thenReturn(gameState);

        try (MockedStatic<Utils> utilsMockedStatic = mockStatic(Utils.class)) {
            utilsMockedStatic.when(Utils::getColumnFromPlayer).thenReturn(3);

            classUnderTest.runGame();
        }

        verify(printer).greetingMessage();
        verify(printer, times(2)).displayField(gameState);
        verify(printer, times(1)).printColumnSelectMessage();
        verify(printer).printWinner(null);
    }

    @Test
    public void testGameDraw() throws FullColumnException {
        when(gameState.isDraw()).thenReturn(true);
        when(gameLogicService.doTurn(gameState, 3)).thenReturn(gameState);

        try (MockedStatic<Utils> utilsMockedStatic = mockStatic(Utils.class)) {
            utilsMockedStatic.when(Utils::getColumnFromPlayer).thenReturn(3);

            classUnderTest.runGame();
        }

        verify(printer).greetingMessage();
        verify(printer, times(2)).displayField(gameState);
        verify(printer, times(1)).printColumnSelectMessage();
        verify(printer).printDraw();
    }

    @Test
    public void testIncorrectColumnInsertion() throws FullColumnException {
        when(gameState.getWinner()).thenReturn(Player.X);
        when(gameLogicService.doTurn(gameState, 7)).thenThrow(ArrayIndexOutOfBoundsException.class);

        try (MockedStatic<Utils> utilsMockedStatic = mockStatic(Utils.class)) {
            utilsMockedStatic.when(Utils::getColumnFromPlayer).thenReturn(7);

            classUnderTest.runGame();
        }

        verify(printer).greetingMessage();
        verify(printer).displayField(gameState);
        verify(printer).printColumnSelectMessage();
        verify(printer).printWinner(null);
    }
}
