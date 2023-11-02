package org.example.piece;

import org.example.game.Move;
import org.example.game.Position;

public class King extends Piece {
    private boolean inCheck = false;

    public King(boolean isWhite, Position pos) {
        super(pos, isWhite);

        setName('K');
    }


    @Override
    public boolean isValidMove(Position from, Position to, Piece capturedPiece, Move prevMove) {
        return false;
    }


}
