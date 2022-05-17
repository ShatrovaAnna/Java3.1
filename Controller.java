package com.nsu.anya;

import com.nsu.anya.game.Cell;
import com.nsu.anya.game.Game;
import com.nsu.anya.game.GameWatcher;

public class Controller {
    private GameWatcher watcher;
    private Game game;

    Controller(GameWatcher watcher) {
        this.watcher = watcher;
    }

    public void startNewGame() {
        game = new Game(watcher);
    }

    public void move(int row, int col, Cell.Type move) {
        game.move(row, col, move);
    }
}
