package org.example.piece;

import org.example.game.Position;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, Position pos) {
        super(pos, isWhite);

        name = 'B';
    }

    @Override
    public boolean isValidMove(Position to, Position from) {
        return false;
    }
}
