package com.encoway.viergewinnt;

import com.encoway.viergewinnt.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class UtilsTest {

    @Test
    public void testGetColumnFromPlayer() {
        try (MockedStatic<Utils> utilsMockedStatic = mockStatic(Utils.class)) {
            utilsMockedStatic.when(Utils::getColumnFromPlayer).thenReturn(4);

            assertEquals(Utils.getColumnFromPlayer(), 4);
        }
    }

    @Test
    public void testGetInputForPlayingAnotherGame() {
        try (MockedStatic<Utils> utilsMockedStatic = mockStatic(Utils.class)) {
            utilsMockedStatic.when(Utils::getInputForPlayingAnotherGame).thenReturn(true);

            assertTrue(Utils.getInputForPlayingAnotherGame());
        }
    }
}
