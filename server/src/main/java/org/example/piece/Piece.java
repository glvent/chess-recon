package org.example.piece;

import org.example.game.Position;

public abstract class Piece {
    Position pos;
    boolean isWhite;
    char name;
    boolean isCaptured;


    public Piece(Position pos, boolean isWhite) {
        this.pos = pos;
        this.isWhite = isWhite;
    }

    public static char intToChar(int i) {
        String letter = "abcdefghijklmnopqrstuvwxyz";
        return letter.charAt(i);
    }

    public void setPosition(Position pos) {
        this.pos = pos;
    }

    public boolean getIsWhite() {
        return isWhite;
    }

    public void setIsCaptured(boolean isCaptured) {
        this.isCaptured = isCaptured;
    }

    public boolean getIsCaptured() {
        return this.isCaptured;
    }

    public char getName() {
        return name;
    }

    public abstract boolean isValidMove(Position from, Position to);

}
