package org.example.piece;

import org.example.game.Move;
import org.example.game.Position;

public abstract class Piece {
    private Position pos;
    private boolean isWhite;
    private char name;
    boolean isCaptured = false;
    boolean isFirstMove = true;


    public Piece(Position pos, boolean isWhite) {
        this.pos = pos;
        this.isWhite = isWhite;
    }

    public static char intToChar(int i) {
        String letter = "abcdefghijklmnopqrstuvwxyz";
        return letter.charAt(i);
    }

    public abstract boolean isValidMove(Position from, Position to, Piece capturedPiece, Move prevMove);

    public void setPosition(Position pos) {
        this.pos = pos;
    }

    public Position getPosition() {
        return pos;
    }

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
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

    public void setName(char name) {
        this.name = name;
    }
    public char getName() {
        return name;
    }


    public boolean getIsFirstMove() {
        return isFirstMove;
    }
    public void setIsFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }
}
