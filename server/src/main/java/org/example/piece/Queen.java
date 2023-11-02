package org.example.piece;

import org.example.game.Move;
import org.example.game.Position;

public class Queen extends Piece {

    public Queen(boolean isWhite, Position pos) {
        super(pos, isWhite);

        setName('Q');
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece capturedPiece, Move prevMove) {
        return false;
    }
}
