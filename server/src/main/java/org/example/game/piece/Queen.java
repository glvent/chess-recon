package org.example.game.piece;

import org.example.game.Move;
import org.example.game.Position;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(boolean isWhite, Position pos) {
        super(pos, isWhite);

        setName('Q');
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board, ArrayList<Move> history) {

        // rook logic
        boolean straightMove = from.getRank() == to.getRank() || from.getFile() == to.getFile();

        // bishop logic
        boolean diagonalMove = Math.abs(to.getRank() - from.getRank()) == Math.abs(to.getFile() - from.getFile());

        // if it's neither a straight nor a diagonal move, it's not valid
        if (!straightMove && !diagonalMove) {
            return false;
        }

        // if no pieces are in the way return true
        return isPathClear(from, to, board);
    }
}
