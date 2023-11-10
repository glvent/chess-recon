package org.example.game.piece;

import org.example.game.Move;
import org.example.game.Position;

import java.util.ArrayList;

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

    // pieces of same color are checked in the movePiece() method
    public abstract boolean isValidMove(Position from, Position to, Piece[][] board, ArrayList<Move> history);

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

    // check if the path is clear between from and to positions
    protected boolean isPathClear(Position from, Position to, Piece[][] board) {
        System.out.println(intToChar(to.getFile()) + to.getRank());
        int rankDirection = Integer.compare(to.getRank(), from.getRank());
        int fileDirection = Integer.compare(to.getFile(), from.getFile());

        // check from the next cell
        int currentRank = from.getRank() + rankDirection;
        int currentFile = from.getFile() + fileDirection;

        // while not at its intended destination
        while (currentRank != to.getRank() || currentFile != to.getFile()) {
            if (board[currentRank][currentFile] != null) {
                return false; // path is not clear
            }
            currentRank += rankDirection;
            currentFile += fileDirection;
        }

        // no pieces are in the way
        return true;
    }

}
