package com.klymenko.chess;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by klymenko.ruslan on 20.07.2017.
 */
class Board {

    private static final char WHITE_SQUARE = '\u25A1';
    private static final char BLACK_SQUARE = '\u25A0';

    private static final char WHITE_KING_CHAR = '\u2654';
    private static final char WHITE_QUEEN_CHAR = '\u2655';
    private static final char WHITE_ROOK_CHAR = '\u2656';
    private static final char WHITE_BISHOP_CHAR = '\u2657';
    private static final char WHITE_KNIGHT_CHAR = '\u2658';
    private static final char WHITE_PAWN_CHAR = '\u2659';
    private static Set<Character> whiteChars = Collections.unmodifiableSet(new HashSet<Character>() {
        {
            add(WHITE_KING_CHAR);
            add(WHITE_QUEEN_CHAR);
            add(WHITE_ROOK_CHAR);
            add(WHITE_BISHOP_CHAR);
            add(WHITE_KNIGHT_CHAR);
            add(WHITE_PAWN_CHAR);
        }
    });

    private static final char BLACK_KING_CHAR = '\u265A';
    private static final char BLACK_QUEEN_CHAR = '\u265B';
    private static final char BLACK_ROOK_CHAR = '\u265C';
    private static final char BLACK_BISHOP_CHAR = '\u265D';
    private static final char BLACK_KNIGHT_CHAR = '\u265E';
    private static final char BLACK_PAWN_CHAR = '\u265F';
    private static Set<Character> blackChars = Collections.unmodifiableSet(new HashSet<Character>() {
        {
            add(BLACK_KING_CHAR);
            add(BLACK_QUEEN_CHAR);
            add(BLACK_ROOK_CHAR);
            add(BLACK_BISHOP_CHAR);
            add(BLACK_KNIGHT_CHAR);
            add(BLACK_PAWN_CHAR);
        }
    });

    private static Set<Character> allChars = Collections.unmodifiableSet(new HashSet<Character>() {
        {
            addAll(whiteChars);
            addAll(blackChars);
        }
    });

    private char board[][] = {
            {'8', BLACK_ROOK_CHAR,BLACK_KNIGHT_CHAR,BLACK_BISHOP_CHAR,BLACK_KING_CHAR,BLACK_QUEEN_CHAR,BLACK_BISHOP_CHAR,BLACK_KNIGHT_CHAR,BLACK_ROOK_CHAR},
            {'7', BLACK_PAWN_CHAR,BLACK_PAWN_CHAR,BLACK_PAWN_CHAR,BLACK_PAWN_CHAR,BLACK_PAWN_CHAR,BLACK_PAWN_CHAR,BLACK_PAWN_CHAR,BLACK_PAWN_CHAR},
            {'6', WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE},
            {'5', BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE},
            {'4', WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE},
            {'3', BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE,BLACK_SQUARE,WHITE_SQUARE},
            {'2', WHITE_PAWN_CHAR,WHITE_PAWN_CHAR,WHITE_PAWN_CHAR,WHITE_PAWN_CHAR,WHITE_PAWN_CHAR,WHITE_PAWN_CHAR,WHITE_PAWN_CHAR,WHITE_PAWN_CHAR},
            {'1', WHITE_ROOK_CHAR,WHITE_KNIGHT_CHAR,WHITE_BISHOP_CHAR,WHITE_QUEEN_CHAR,WHITE_KING_CHAR,WHITE_BISHOP_CHAR,WHITE_KNIGHT_CHAR,WHITE_ROOK_CHAR},
            {' ', 'a',' ','b',' ','c',' ','d',' ','e',' ','f',' ','g',' ','h'}
    };

    public Set<Cell> getAvailableTurns(Cell cell, boolean isWhiteTurn) {
        int column = cell.getColumnAsInt();
        int row = cell.getRowAsInt();
        char currentChar = board[row][column];
        Set<Character> selfChars = isWhiteTurn ? whiteChars : blackChars;
        if(selfChars.contains(currentChar)) {
            if (currentChar == WHITE_KING_CHAR || currentChar == BLACK_KING_CHAR) {
                return getKingTurns(row, column, isWhiteTurn);
            } else if (currentChar == WHITE_QUEEN_CHAR || currentChar == BLACK_QUEEN_CHAR) {
                return getQueenTurns(row, column, isWhiteTurn);
            } else if (currentChar == WHITE_ROOK_CHAR || currentChar == BLACK_ROOK_CHAR) {
                return getRookTurns(row, column, isWhiteTurn);
            } else if (currentChar == WHITE_BISHOP_CHAR || currentChar == BLACK_BISHOP_CHAR) {
                return getBishopTurns(row, column, isWhiteTurn);
            } else if (currentChar == WHITE_KNIGHT_CHAR || currentChar == BLACK_KNIGHT_CHAR) {
                return getKnightTurns(row, column, isWhiteTurn);
            } else if (currentChar == WHITE_PAWN_CHAR || currentChar == BLACK_PAWN_CHAR) {
                return getPawnTurns(row, column, isWhiteTurn);
            }
        }
        return new HashSet<>();
    }

    public Status executeTurn(Cell movedCharacter, Cell destinationCharacter, boolean isWhiteTurn) {
        char movedChar = board[movedCharacter.getRowAsInt()][movedCharacter.getColumnAsInt()];
        board[destinationCharacter.getRowAsInt()][destinationCharacter.getColumnAsInt()] = movedChar;
        cleanCell(movedCharacter);
        return getStatus(isWhiteTurn);
    }

    private Set<Cell> getKingTurns(int row, int column, boolean isWhiteTurn) {
        return Collections.unmodifiableSet(new HashSet<Cell>() {
            Set<Character> selfChars = isWhiteTurn ? whiteChars : blackChars;
            {
                if(column - 1 > 0) {
                    if(!selfChars.contains(board[row][column - 1])) add(new Cell(row, column - 1));
                    if(row - 1 >= 0 && !selfChars.contains(board[row - 1][column - 1])) add(new Cell(row - 1, column - 1));
                }
                if(column + 1 < board[0].length) {
                    if(!selfChars.contains(board[row][column + 1])) add(new Cell(row, column + 1));
                    if(row + 1 < board.length - 1 && !selfChars.contains(board[row + 1][column + 1])) add(new Cell(row + 1, column + 1));
                }
                if(row - 1 >= 0) {
                    if(!selfChars.contains(board[row - 1][column])) add(new Cell(row - 1, column));
                    if(column + 1 < board[0].length && !selfChars.contains(board[row - 1][column + 1])) add(new Cell(row - 1, column + 1));
                }
                if(row + 1 < board.length - 1) {
                    if(!selfChars.contains(board[row + 1][column])) add(new Cell(row + 1, column));
                    if(column - 1 > 0 && !selfChars.contains(board[row + 1][column - 1])) add(new Cell(row + 1, column - 1));
                }
            }
        });
    }

    private Set<Cell> getQueenTurns(int row, int column, boolean isWhiteTurn) {
        return Collections.unmodifiableSet(new HashSet<Cell>() {
            {
                addAll(getBishopTurns(row, column, isWhiteTurn));
                addAll(getRookTurns(row, column, isWhiteTurn));
            }
        });
    }

    private Set<Cell> getRookTurns(int row, int column, boolean isWhiteTurn) {
        return Collections.unmodifiableSet(new HashSet<Cell>() {
            {
                Set<Character> enemyChars = isWhiteTurn ? blackChars : whiteChars;

                int cnt = row - 1;
                while(cnt >= 0 && !allChars.contains(board[cnt][column])) add(new Cell(cnt--, column));
                if(cnt >= 0 && enemyChars.contains(board[cnt][column])) add(new Cell(cnt, column));
                cnt = row + 1;
                while(cnt < board.length - 1 && !allChars.contains(board[cnt][column])) add(new Cell(cnt++, column));
                if(cnt < board.length - 1 && enemyChars.contains(board[cnt][column])) add(new Cell(cnt, column));

                cnt = column - 1;
                while(cnt > 0 && !allChars.contains(board[row][cnt])) add(new Cell(row, cnt--));
                if(cnt > 0 && enemyChars.contains(board[row][cnt])) add(new Cell(row, cnt));
                cnt = column + 1;
                while(cnt < board[0].length && !allChars.contains(board[row][cnt])) add(new Cell(row, cnt++));
                if(cnt < board[0].length && enemyChars.contains(board[row][cnt])) add(new Cell(row, cnt));
            }
        });
    }

    private Set<Cell> getBishopTurns(int row, int column, boolean isWhiteTurn) {
        return Collections.unmodifiableSet(new HashSet<Cell>() {
            {
                Set<Character> enemyChars = isWhiteTurn ? blackChars : whiteChars;
                int rowCnt = row - 1;
                int columnCnt = column - 1;
                while(rowCnt >= 0 && columnCnt > 0 && !allChars.contains(board[rowCnt][columnCnt])) add(new Cell(rowCnt--, columnCnt--));
                if(rowCnt >= 0 && columnCnt > 0 && enemyChars.contains(board[rowCnt][columnCnt])) add(new Cell(rowCnt, columnCnt));

                rowCnt = row + 1;
                columnCnt = column - 1;
                while(rowCnt < board.length - 1 && columnCnt > 0 && !allChars.contains(board[rowCnt][columnCnt])) add(new Cell(rowCnt++, columnCnt--));
                if(rowCnt < board.length - 1 && columnCnt > 0 && enemyChars.contains(board[rowCnt][columnCnt])) add(new Cell(rowCnt, columnCnt));

                rowCnt = row - 1;
                columnCnt = column + 1;
                while(rowCnt >= 0 && columnCnt < board[0].length && !allChars.contains(board[rowCnt][columnCnt])) add(new Cell(rowCnt--, columnCnt++));
                if(rowCnt >= 0 && columnCnt < board[0].length && enemyChars.contains(board[rowCnt][columnCnt])) add(new Cell(rowCnt, columnCnt));

                rowCnt = row + 1;
                columnCnt = column + 1;
                while(rowCnt < board.length - 1 && columnCnt < board[0].length && !allChars.contains(board[rowCnt][columnCnt])) add(new Cell(rowCnt++, columnCnt++));
                if(rowCnt < board.length - 1 && columnCnt < board[0].length && enemyChars.contains(board[rowCnt][columnCnt])) add(new Cell(rowCnt, columnCnt));

            }
        });
    }

    private Set<Cell> getKnightTurns(int row, int column, boolean isWhiteTurn) {
        return Collections.unmodifiableSet(new HashSet<Cell>() {
            {
                Set<Character> selfChars = isWhiteTurn ? whiteChars : blackChars;
                if(column - 2 > 0) {
                    if(row + 1 < board.length - 1 && !selfChars.contains(board[row + 1][column - 2])) add(new Cell(row + 1, column - 2));
                    if(row - 1 >= 0 && !selfChars.contains(board[row - 1][column - 2])) add(new Cell(row - 1, column - 2));
                }
                if(column + 2 < board[0].length) {
                    if(row + 1 < board.length - 1 && !selfChars.contains(board[row + 1][column + 2])) add(new Cell(row + 1, column + 2));
                    if(row - 1 >= 0 && !selfChars.contains(board[row - 1][column + 2])) add(new Cell(row - 1, column + 2));
                }
                if(row - 2 >= 0) {
                    if(column - 1 > 0 && !selfChars.contains(board[row - 2][column - 1])) add(new Cell(row - 2, column - 1));
                    if(column + 1 < board[0].length && !selfChars.contains(board[row - 2][column + 1])) add(new Cell(row - 2, column + 1));
                }
                if(row + 2 < board.length - 1) {
                    if(column - 1 > 0 && !selfChars.contains(board[row + 2][column - 1])) add(new Cell(row + 2, column - 1));
                    if(column + 1 < board[0].length && !selfChars.contains(board[row + 2][column + 1])) add(new Cell(row + 2, column + 1));
                }
            }
        });
    }

    private Set<Cell> getPawnTurns(int row, int column, boolean isWhiteTurn) {
        return Collections.unmodifiableSet(new HashSet<Cell>() {
            {
                Set<Character> selfChars = isWhiteTurn ? whiteChars : blackChars;
                Set<Character> enemyChars = isWhiteTurn ? blackChars : whiteChars;
                if(isWhiteTurn) {
                    if(row == 6 && !allChars.contains(board[row - 2][column])) add(new Cell(row - 2, column));
                    if(row - 1 >= 0 && !allChars.contains(board[row - 1][column])) add(new Cell(row - 1, column));
                    if(row - 1 >= 0 && column - 1 > 0 && enemyChars.contains(board[row - 1][column - 1])) add(new Cell(row - 1, column - 1));
                    if(row - 1 >= 0 && column + 1 < board[0].length && enemyChars.contains(board[row - 1][column + 1])) add(new Cell(row - 1, column + 1));
                } else {
                    if(row == 1 && !allChars.contains(board[row + 2][column])) add(new Cell(row + 2, column));
                    if(row + 1 < board.length - 1 && !allChars.contains(board[row + 1][column])) add(new Cell(row + 1, column));
                    if(row + 1 < board.length - 1 && column - 1 > 0 && enemyChars.contains(board[row + 1][column - 1])) add(new Cell(row + 1, column - 1));
                    if(row + 1 < board.length - 1 && column + 1 < board[0].length && enemyChars.contains(board[row + 1][column + 1])) add(new Cell(row + 1, column + 1));
                }
            }
        });
    }

    private void cleanCell(Cell cell) {
        int row = cell.getRowAsInt();
        boolean evenRow = row % 2 == 0;

        int column = cell.getColumnAsInt();
        boolean evenColumn = column % 2 == 0;

        boolean isWhite = evenRow && evenColumn || !evenRow && !evenColumn ? false : true;
        board[row][column] = isWhite ? WHITE_SQUARE : BLACK_SQUARE;
    }

    public Status getStatus(boolean isWhiteTurn) {
        if(checkMate(true)) return Status.blackCheckMate;
        if(checkMate(false)) return Status.whiteCheckMate;
        if(check(true)) {
            if(!isWhiteTurn) return Status.blackCheckMate;
            return Status.blackCheck;
        }
        if(check(false)) {
            if(isWhiteTurn) return Status.whiteCheckMate;
            return Status.whiteCheck;
        }
        return Status.ok;
    }

    private boolean check(boolean isWhiteAttacker) {
        Cell kingPosition = getCharPositions(isWhiteAttacker ? BLACK_KING_CHAR : WHITE_KING_CHAR).iterator().next();
        return check(kingPosition, isWhiteAttacker);
    }
    private boolean check(Cell defendingKingPosition, boolean isWhiteAttacker) {
        Set<Cell> attackingCharacters = getCharPositions(isWhiteAttacker ? whiteChars : blackChars);
        return attackingCharacters.stream()
                                  .map(character -> getAvailableTurns(character, isWhiteAttacker))
                                  .flatMap(Collection::stream)
                                  .filter(cell -> cell.equals(defendingKingPosition))
                                  .findAny()
                                  .isPresent();
    }
    private boolean checkMate(boolean isWhiteAttacker) {
        Cell defendingKing = getCharPositions(isWhiteAttacker ? BLACK_KING_CHAR : WHITE_KING_CHAR).iterator().next();
        Set<Cell> availableTurns = getAvailableTurns(defendingKing, isWhiteAttacker);

        return !availableTurns.isEmpty() && availableTurns.stream()
                                                          .allMatch(kingPosition -> check(kingPosition, isWhiteAttacker));
    }

    public void printBoard() {
        for(char [] row : board) {
            for(char ch : row) {
                System.out.print(ch);
            }
            System.out.println();
        }
    }

    private Set<Cell> getCharPositions(Set<Character> characters) {
        return characters.stream()
                         .map(this::getCharPositions)
                         .flatMap(Collection::stream)
                         .collect(Collectors.toSet());
    }

    private Set<Cell> getCharPositions(char givenChar) {
        Set<Cell> characters = new HashSet<>();
        for(int i = 0; i < board.length - 1; i++) {
            for(int j = 1; j < board[0].length; j++) {
                if(board[i][j] == givenChar) characters.add(new Cell(i, j));
            }
        }
        return characters;
    }
}
