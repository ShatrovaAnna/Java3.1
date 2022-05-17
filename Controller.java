package com.nsu.anya;

import com.nsu.anya.game.Cell;
import com.nsu.anya.game.Game;
import com.nsu.anya.game.GameWatcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.nsu.anya.View.gameFieldPanel;
import static com.nsu.anya.View.gridPanel;

public class Controller {
    private static GameWatcher watcher = null;
    private static Game game;

    Controller(GameWatcher watcher) {
        Controller.watcher = watcher;
    }

    public static void startNewGame() {
        game = new Game(watcher);
    }

    public void move(int row, int col, Cell.Type move) {
        game.move(row, col, move);
    }

    public class MoveActionListener implements ActionListener {
        private final Cell.Type move;
        private final int row;
        private final int col;

        public MoveActionListener(int row, int col, Cell.Type move) {
            this.row = row;
            this.col = col;
            this.move = move;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            move(row, col, move);
        }
    }

    public ActionListener gameStartActionListener() {
        return new GameStartActionListener();
    }

    public ActionListener moveActionListener(int row, int col, Cell.Type move) {
        return new MoveActionListener(row, col, move);
    }

    public static class GameStartActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gridPanel != null) {
                gameFieldPanel.remove(gridPanel);
            }
            startNewGame();
        }
    }
}
