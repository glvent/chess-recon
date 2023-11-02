package org.example.piece;

import org.example.game.Move;
import org.example.game.Position;

public class Rook extends Piece {

    public Rook(boolean isWhite, Position pos) {
        super(pos, isWhite);

        setName('R');
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece capturedPiece, Move prevMove) {
        return false;
    }
}
