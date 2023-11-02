package org.example.piece;

import org.example.game.Move;
import org.example.game.Position;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, Position pos) {
        super(pos, isWhite);

        setName('B');
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece capturedPiece, Move prevMove) {
        return false;
    }
}
