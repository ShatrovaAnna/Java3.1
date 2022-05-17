package com.nsu.anya.game;

public class Field {
    private final int rowCount;
    private final int colCount;
    private final Cell[][] cells;
    private int freeCells;

    Field(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.freeCells = rowCount * colCount;
        cells = new Cell[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void exchangeCell(int row, int col, Cell.Type move) {
        if (cells[row][col].getType() == Cell.Type.EMPTY && move != Cell.Type.EMPTY) {
            freeCells--;
        }
        cells[row][col] = new Cell(row, col, move);
    }

    public int getFreeCells(){
        return freeCells;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }
}
