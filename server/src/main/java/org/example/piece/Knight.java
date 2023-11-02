package org.example.piece;

import org.example.game.Move;
import org.example.game.Position;

public class Knight extends Piece {

    public Knight(boolean isWhite, Position pos) {
        super(pos, isWhite);
        setName('N');
    }
    @Override
    public boolean isValidMove(Position from, Position to, Piece capturedPiece, Move prevMove) {
        return false;
    }
}
