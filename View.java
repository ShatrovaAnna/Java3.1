package com.nsu.anya;

import com.nsu.anya.game.Cell;
import com.nsu.anya.game.Field;
import com.nsu.anya.game.GameWatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements GameWatcher {
    private Controller controller;
    private JButton m_newGame;
    private JLabel m_message;
    private JPanel gameFieldPanel;
    private JPanel gridPanel;
    private final String disactiveCommand = "disactive";
    private  JButton  m_buttons[][];

    public View() throws HeadlessException {
        super("Game Window");
        controller = new Controller(this);
        this.pack();
        this.setVisible(true);
        this.setEnabled(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel toolbarPanel = new JPanel(new GridLayout(1, 2, 2, 2));

        m_message = new JLabel("Игра не начата");
        m_message.setSize(new Dimension(80, 40));

        m_newGame = new JButton("Новая игра");
        m_newGame.setSize(new Dimension(80, 40));
        m_newGame.addActionListener(gameStartActionListener());

        toolbarPanel.add(m_message);
        toolbarPanel.add(m_newGame);

        gameFieldPanel = new JPanel();
        gameFieldPanel.setVisible(true);
        gameFieldPanel.setEnabled(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        mainPanel.add(toolbarPanel, BorderLayout.NORTH);
        mainPanel.add(gameFieldPanel, BorderLayout.SOUTH);
        setSize(500, 580);

    }

    @Override
    public void win(Cell.Type player, int row, int col, Field field) {
        updateField(field, Cell.Type.X, row, col, disactiveCommand);
        if (player == Cell.Type.X) {
            m_message.setText("Победил: Х");
        }
        if (player == Cell.Type.O) {
            m_message.setText("Победил: O");
        }
    }

    @Override
    public void draw(int row, int col, Field field) {

        m_message.setText("Ничья");
    }

    @Override
    public void updateField(Field field, Cell.Type move, int row, int col, String status) {
        m_message.setText("Ходит: " + move.toString());

        if (gridPanel != null) {
            gameFieldPanel.remove(gridPanel);
        }
        gridPanel = new JPanel(new GridLayout(field.getRowCount(), field.getColCount(), 2, 2));
        gridPanel.setBackground(Color.darkGray);
        gridPanel.setPreferredSize(new Dimension(500, 500));

        for (int i = 0; i < field.getColCount(); i++) {
            for (int j = 0; j < field.getRowCount(); j++) {
                Cell cell = field.getCell(i, j);

                JButton newButton = new JButton(cell.toString());
                newButton.setMargin(new Insets(0, 0, 0, 0));
                if (cell.getType() != Cell.Type.EMPTY) {
                     newButton.setEnabled(false);
                }
                if (status == "disactive"){
                    newButton.setEnabled(false);
                }

                newButton.setVisible(true);
                newButton.addActionListener(moveActionListener(i, j, move));

                gridPanel.add(newButton);
            }
        }

        gridPanel.setEnabled(true);
        gridPanel.setVisible(true);
        gameFieldPanel.add(gridPanel);
    }

    public class GameStartActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gridPanel != null) {
                gameFieldPanel.remove(gridPanel);
            }
            controller.startNewGame();
        }
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
            controller.move(row, col, move);
        }
    }

    public ActionListener gameStartActionListener() {
        return new GameStartActionListener();
    }

    public ActionListener moveActionListener(int row, int col, Cell.Type move) {
        return new MoveActionListener(row, col, move);
    }
}
