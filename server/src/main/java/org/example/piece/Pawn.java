package org.example.piece;

import org.example.game.Position;

public class Pawn extends Piece {

    public Pawn(boolean isWhite, Position pos) {
        super(pos, isWhite);
        name = 'P';
    }

    @Override
    public boolean isValidMove(Position from, Position to) {
        return false;
    }
}
