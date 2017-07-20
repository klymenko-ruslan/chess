package com.klymenko.chess;

/**
 * Created by klymenko.ruslan on 20.07.2017.
 */
class Cell {
    private char row;
    private char column;

    public Cell(int row, int column) {
        this.row = getRowFromInt(row);
        this.column = getColumnFromInt(column);
    }

    public Cell(String cell) {
        column = cell.charAt(0);
        row = cell.charAt(1);
    }

    public char getColumn() {
        return column;
    }

    public void setColumn(final char column) {
        this.column = column;
    }

    public char getRow() {
        return row;
    }

    public void setRow(final char row) {
        this.row = row;
    }

    public int getColumnAsInt() {
        switch (column) {
            case 'a':
                return 1;
            case 'b':
                return 2;
            case 'c':
                return 3;
            case 'd':
                return 4;
            case 'e':
                return 5;
            case 'f':
                return 6;
            case 'g':
                return 7;
            case 'h':
                return 8;
            default:
                return -1;

        }
    }

    public static char getColumnFromInt(int column) {
        switch (column) {
            case 1:
                return 'a';
            case 2:
                return 'b';
            case 3:
                return 'c';
            case 4:
                return 'd';
            case 5:
                return 'e';
            case 6:
                return 'f';
            case 7:
                return 'g';
            case 8:
                return 'h';
            default:
                throw new RuntimeException();

        }
    }

    public int getRowAsInt() {
        switch (row) {
            case '1':
                return 7;
            case '2':
                return 6;
            case '3':
                return 5;
            case '4':
                return 4;
            case '5':
                return 3;
            case '6':
                return 2;
            case '7':
                return 1;
            case '8':
                return 0;
            default:
                return -1;

        }
    }

    public static char getRowFromInt(int row) {
        switch (row) {
            case 7:
                return '1';
            case 6:
                return '2';
            case 5:
                return '3';
            case 4:
                return '4';
            case 3:
                return '5';
            case 2:
                return '6';
            case 1:
                return '7';
            case 0:
                return '8';
            default:
                throw new RuntimeException();

        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Cell cell = (Cell) o;

        if (column != cell.column) return false;
        return row == cell.row;
    }

    @Override
    public int hashCode() {
        int result = (int) column;
        result = 31 * result + (int) row;
        return result;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "column=" + column +
                ", row=" + row +
                '}';
    }
}
