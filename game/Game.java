package com.nsu.anya.game;


public class Game {
    public final int DEFAULT_FIELD_SIZE = 10;
    public final int WIN_ROW_SIZE = 5;
    private final GameWatcher watcher;
    private final Field field;
    private final String command = "nothing";

    public Game(GameWatcher watcher) {
        this.watcher = watcher;
        this.field = new Field(DEFAULT_FIELD_SIZE, DEFAULT_FIELD_SIZE);
        watcher.updateField(field, Cell.Type.X, DEFAULT_FIELD_SIZE, DEFAULT_FIELD_SIZE, command);
    }

    public void move(int row, int col, Cell.Type move) {
        field.exchangeCell(row, col, move);
        watcher.updateField(field, move.opposite(), DEFAULT_FIELD_SIZE, DEFAULT_FIELD_SIZE, command);
        checkGameEnd(row, col, move);
    }

    private void checkGameEnd(int row, int col, Cell.Type move) {
        for (int i = 0; i < WIN_ROW_SIZE; i++) {
            boolean xWin = true, yWin = true, xyWin = true, yxWin = true;
            for (int j = 0; j < WIN_ROW_SIZE; j++) {
                if (row - i + j < 0 || row - i + j >= field.getRowCount()) {
                    xWin = false;
                } else {
                    if (field.getCell(row - i + j, col).getType() != move)
                        xWin = false;
                }

                if (col - i + j < 0 || col - i + j >= field.getColCount()) {
                    yWin = false;
                } else {
                    if (field.getCell(row, col - i + j).getType() != move)
                        yWin = false;
                }

                if (col - i + j < 0 || col - i + j >= field.getColCount() || row - i + j < 0 || row - i + j >= field.getRowCount()) {
                    xyWin = false;
                } else {
                    if (field.getCell(row - i + j, col - i + j).getType() != move)
                        xyWin = false;
                }

                if (col - i + j < 0 || col - i + j >= field.getColCount() || row + i - j < 0 || row + i - j >= field.getRowCount()) {
                    yxWin = false;
                } else {
                    if (field.getCell(row + i - j, col - i + j).getType() != move)
                        yxWin = false;
                }
            }

            if (xyWin || xWin || yWin || yxWin) {
                watcher.win(move, field);
            }
        }

        if (field.getFreeCells() == 0) {
            watcher.draw();
        }
    }
}
