package com.klymenko;

import com.klymenko.chess.Game;
import com.klymenko.chess.Player;

public class Client {

    public static void main(String[] args) {
        if(args.length != 2) {
            throw new IllegalArgumentException("Provide names for both players!");
        }
        String firstPlayerName = args[0];
        String secondPlayerName = args[1];
	    new Game().startGame(new Player(firstPlayerName), new Player(secondPlayerName));
    }
}
