package com.klymenko.chess;

/**
 * Created by klymenko.ruslan on 20.07.2017.
 */
public class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
