package com.nsu.anya;

import com.nsu.anya.game.Cell;
import com.nsu.anya.game.Field;
import com.nsu.anya.game.GameWatcher;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class View extends JFrame implements GameWatcher {
    private static Controller controller;
    private final JLabel m_message;
    public static JPanel gameFieldPanel;
    public static JPanel gridPanel;
    private final String disactiveCommand = "disactive";

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

        JButton m_newGame = new JButton("Новая игра");
        m_newGame.setSize(new Dimension(80, 40));
        m_newGame.addActionListener(controller.gameStartActionListener());

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
    public void win(Cell.Type player, Field field) {
        updateField(field, Cell.Type.X, field.getRowCount(), field.getColCount(), disactiveCommand);
        if (player == Cell.Type.X) {
            m_message.setText("Победил: Х");
        }
        if (player == Cell.Type.O) {
            m_message.setText("Победил: O");
        }
    }

    @Override
    public void draw() {
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
                if (Objects.equals(status, "disactive")){
                    newButton.setEnabled(false);
                }

                newButton.setVisible(true);
                newButton.addActionListener(controller.moveActionListener(i, j, move));

                gridPanel.add(newButton);
            }
        }

        gridPanel.setEnabled(true);
        gridPanel.setVisible(true);
        gameFieldPanel.add(gridPanel);
    }
}
