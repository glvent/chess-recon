package org.example.game.piece;

import org.example.game.Move;
import org.example.game.Position;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(boolean isWhite, Position pos) {
        super(pos, isWhite);

        setName('R');
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board, ArrayList<Move> history) {
        // if move is in a straight line (either vertical or horizontal)
        if (from.getFile() == to.getFile() || from.getRank() == to.getRank()) {
            // if nothing is blocking the rook return true
            return isPathClear(from, to, board);
        }
        return false;
    }



}
