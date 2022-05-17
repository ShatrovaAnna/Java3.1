package com.nsu.anya.game;

public interface GameWatcher {
    void updateField(Field field, Cell.Type move, int row, int col, String status);
    void win(Cell.Type player, int row, int col, Field field);
    void draw(int row, int col, Field field);
}
