package com.klymenko.chess;

import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by klymenko.ruslan on 20.07.2017.
 */
public class Game {

    private static Board board = new Board();
    private static Player whitePlayer;
    private static Player blackPlayer;
    private static Scanner scanner = new Scanner(System.in);

    private static final String CELL_REGEX = "[a-h][1-8]";

    public void startGame(Player firstPlayer, Player secondPlayer) {
        whitePlayer = firstPlayer;
        blackPlayer = secondPlayer;
        boolean isWhiteTurn = true;
        while(true) {
            Status status = executeTurn(isWhiteTurn);
            System.out.println("Status: " + status);
            if(status == Status.whiteCheckMate || status == Status.blackCheckMate) break;
            isWhiteTurn = !isWhiteTurn;
        }
    }
    private Status executeTurn(boolean isWhiteTurn) {
        board.printBoard();
        Player currentplayer = isWhiteTurn ? whitePlayer : blackPlayer;
        Set<Cell> accessibileCells = null;
        Cell movedCharacter = null;
        while(accessibileCells == null || accessibileCells.isEmpty()) {
            System.out.println(currentplayer.getName() + ", it's your turn! Select the character using column-row format. Example: a1");
            movedCharacter = parseUserInput();
            accessibileCells = board.getAvailableTurns(movedCharacter, isWhiteTurn);
            if(accessibileCells.isEmpty()) System.out.println("There's no turns for this character. Select another one.");
        }
        Cell destinationPoint = null;
        while(!accessibileCells.contains(destinationPoint)) {
            System.out.println(currentplayer.getName() + ", choose destination point from given cells:" + accessibileCells);
            destinationPoint = parseUserInput();
        }
        return board.executeTurn(movedCharacter, destinationPoint, isWhiteTurn);
    }

    private Cell parseUserInput() {
        String userInput = scanner.next();
        Pattern pattern = Pattern.compile(CELL_REGEX);
        while(!pattern.matcher(userInput).matches()) {
            System.out.println("Wrong input. Use column-row format. Example: a1");
            userInput = scanner.next();
        }
        return new Cell(userInput);
    }
}
