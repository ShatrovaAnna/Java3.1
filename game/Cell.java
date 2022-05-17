package com.nsu.anya.game;

public class Cell {

    public enum Type {
        X, O, EMPTY;

        public Type opposite() {
            return switch (this) {
                case X -> Type.O;
                case O -> Type.X;
                case EMPTY -> Type.EMPTY;
            };
        }
    };

    private final int x;
    private final int y;
    private final Type type;

    public Cell(int x, int y) {
        this.type = Type.EMPTY;
        this.x = x;
        this.y = y;
    }

    public Cell(int x, int y, Type type) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type == Type.EMPTY ? "" : type.toString();
    }
}
