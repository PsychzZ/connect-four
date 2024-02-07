package com.encoway.viergewinnt.utils;

import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;


@Component
public class GameIdUtils {

    final int SHORT_ID_LENGTH = 5;

    /**
     * Generate unique id string.
     *
     * @param uniqueIds the unique ids
     * @return the string
     */
    public String generateUniqueId(Set<String> uniqueIds) {
        boolean isIdOccupied = true;
        String id = "";
        if (uniqueIds.isEmpty()) {
            id = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
            return id;
        }
        while (isIdOccupied) {
            id = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
            isIdOccupied = checkIfUniqueIdIsOccupied(id, uniqueIds);
        }
        return id;
    }

    private boolean checkIfUniqueIdIsOccupied(String id, Set<String> allUniqueIds) {
        for (String key : allUniqueIds) {
            if (key.equals(id)) {
                return true;
            }
        }
        return false;
    }
}
