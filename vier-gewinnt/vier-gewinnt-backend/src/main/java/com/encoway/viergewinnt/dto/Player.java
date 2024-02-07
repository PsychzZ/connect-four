package com.encoway.viergewinnt.dto;

public enum Player {
    X("Ben"),
    O("Bob");

    private final String name;

    Player(String name) {
        this.name = name;
    }

    public Player other() {
        return this == X ? O : X;
    }
}
