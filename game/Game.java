package com.nsu.anya.game;


public class Game {
    public final int DEFAULT_FIELD_SIZE = 10;
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
        boolean xWin = true, yWin = true, xyWin = true, yxWin = true;
        for (int i = 0; i < 5; ++i) {
            if (i + col >= DEFAULT_FIELD_SIZE || field.getCell(row, i + col).getType()  != move)
                xWin = false;
            System.out.println("1 check: " + field.getCell(row, i + col).getType());
            if (i + row >= DEFAULT_FIELD_SIZE || field.getCell(i + row, col).getType()  != move)
                yWin = false;
            System.out.println("2 check: " + field.getCell(i + row, col).getType());
            if (i + col >= DEFAULT_FIELD_SIZE || i + row >= DEFAULT_FIELD_SIZE || field.getCell(i + row, i + col).getType()  != move)
                xyWin = false;
            System.out.println("3 check: " + field.getCell(i + row, i + col).getType());
            if (row - i < 0 || col + i >= DEFAULT_FIELD_SIZE || field.getCell(row - i, i + col).getType()  != move)
                yxWin = false;
            System.out.println("4 check: " + field.getCell(row - i, col + i).getType());

        }

        if (xyWin || xWin || yWin || yxWin) {
            watcher.win(move, DEFAULT_FIELD_SIZE, DEFAULT_FIELD_SIZE, field);
        }

        if (field.getFreeCells() == 0) {
            watcher.draw(DEFAULT_FIELD_SIZE, DEFAULT_FIELD_SIZE, field);
        }
    }
}
