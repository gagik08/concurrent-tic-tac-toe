package com.epam.rd.autocode.concurrenttictactoe;

public class PlayerImpl implements Player {
    private static final char WHITESPACE = ' ';
    private final TicTacToe ticTacToe;
    private final char mark;
    private final PlayerStrategy strategy;

    public PlayerImpl(TicTacToe ticTacToe, char mark, PlayerStrategy strategy) {
        this.ticTacToe = ticTacToe;
        this.mark = mark;
        this.strategy = strategy;
    }

    @Override
    public void run() {
        while (isNotFilledTable() && isNotGameEnded()) {
            synchronized (ticTacToe) {
                if (isPossibleToMove()) {
                    Move move = strategy.computeMove(mark, ticTacToe);
                    ticTacToe.setMark(move.row, move.column, mark);
                }
            }
        }
    }

    private boolean isPossibleToMove() {
        return isNotFilledTable() && isNotGameEnded() && isMarkedCell(ticTacToe.lastMark(), mark);
    }

    private boolean isNotFilledTable() {
        for (int i = 0; i < ticTacToe.table().length; i++) {
            for (int j = 0; j < ticTacToe.table()[i].length; j++) {
                if (ticTacToe.table()[i][j] == WHITESPACE) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean gameIsEnded() {
        if (isFirstDiagonalFull() || isSecondDiagonalFull()) {
            return true;
        }
        for (int i = 0; i < ticTacToe.table().length; i++) {
            if (isRowFull(i) || isColumnFull(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFirstDiagonalFull() {
        char cellForChecking = ticTacToe.table()[0][0];

        for (int i = 0; i < ticTacToe.table().length; i++) {
            if (ticTacToe.table()[i][i] != cellForChecking) {
                return false;
            }
            cellForChecking = ticTacToe.table()[i][i];
        }

        return isMarkedCell(cellForChecking, WHITESPACE);
    }


    private boolean isSecondDiagonalFull() {
        int lastRow = ticTacToe.table().length - 1;
        char cellForChecking = ticTacToe.table()[lastRow][0];

        for (int i = 0; i < ticTacToe.table().length; i++) {
            if (ticTacToe.table()[lastRow - i][i] != cellForChecking) {
                return false;
            }
            cellForChecking = ticTacToe.table()[lastRow - i][i];
        }

        return isMarkedCell(cellForChecking, WHITESPACE);
    }

    private boolean isRowFull(int row) {
        char cellForChecking = ticTacToe.table()[row][0];

        for (int i = 0; i < ticTacToe.table()[row].length; i++) {
            if (ticTacToe.table()[row][i] != cellForChecking) {
                return false;
            }
            cellForChecking = ticTacToe.table()[row][i];
        }

        return isMarkedCell(cellForChecking, WHITESPACE);
    }

    private boolean isColumnFull(int column) {
        char cellForChecking = ticTacToe.table()[0][column];

        for (int i = 0; i < ticTacToe.table().length; i++) {
            if (isMarkedCell(ticTacToe.table()[i][column], cellForChecking)) {
                return false;
            }
            cellForChecking = ticTacToe.table()[i][column];
        }

        return isMarkedCell(cellForChecking, WHITESPACE);
    }

    private boolean isMarkedCell(char markedCell, char whitespace) {
        return markedCell != whitespace;
    }

    private boolean isNotGameEnded() {
        return !gameIsEnded();
    }
}