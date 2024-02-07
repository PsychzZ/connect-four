package com.encoway.viergewinnt;

import com.encoway.viergewinnt.utils.GameIdUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;


@ExtendWith(MockitoExtension.class)
public class GameIdUtilsTest {


    @InjectMocks
    private GameIdUtils gameIdUtils;

    Set<String> keys = new HashSet<>();

    @Test
    public void testGenerateUniqueID() {
        try (MockedStatic<RandomStringUtils> utilsMockedStatic = mockStatic(RandomStringUtils.class)) {
            utilsMockedStatic.when(() -> RandomStringUtils.randomAlphanumeric(5)).thenReturn("aaaaa");

            assertEquals(gameIdUtils.generateUniqueId(keys), "aaaaa");
        }

    }

    @Test
    public void testGenerateUniqueIdWhenSetIsNotEmpty() {
        keys.add("abcde");
        try (MockedStatic<RandomStringUtils> utilsMockedStatic = mockStatic(RandomStringUtils.class)) {
            utilsMockedStatic.when(() -> RandomStringUtils.randomAlphanumeric(5)).thenReturn("aaaaa");

            assertEquals(gameIdUtils.generateUniqueId(keys), "aaaaa");
        }

    }

    @Test
    public void testIfKeyIsAlreadyThere(){
        keys.add("aaaaa");
        try (MockedStatic<RandomStringUtils> utilsMockedStatic = mockStatic(RandomStringUtils.class)) {
            utilsMockedStatic.when(() -> RandomStringUtils.randomAlphanumeric(5)).thenReturn("aaaaa","bbbbb");

            assertEquals("bbbbb", gameIdUtils.generateUniqueId(keys));
        }

    }
}
