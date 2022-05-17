package com.nsu.anya.game;

public interface GameWatcher {
    void updateField(Field field, Cell.Type move, int row, int col, String status);
    void win(Cell.Type player, Field field);
    void draw();
}
