package com.epam.rd.autocode.concurrenttictactoe;

import java.util.concurrent.locks.ReentrantLock;

public class TicTacToeImpl implements TicTacToe {
    private final char[][] table = new char[3][3];
    private char lastMark = 'O';
    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public void setMark(int x, int y, char mark) {
        try {
            reentrantLock.lock();
            if (table[x][y] != 0) {
                throw new IllegalArgumentException(String.format("This cell %d:%d is not empty!", x, y));
            }
            lastMark = mark;
            table[x][y] = mark;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public char[][] table() {
        char[][] newTable = new char[3][3];

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == 0) {
                    newTable[i][j] = ' ';
                } else {
                    newTable[i][j] = table[i][j];
                }
            }
        }

        return newTable;
    }

    @Override
    public char lastMark() {
        return lastMark;
    }
}