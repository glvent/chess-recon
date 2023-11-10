package org.example.game.piece;

import org.example.game.Move;
import org.example.game.Position;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(boolean isWhite, Position pos) {
        super(pos, isWhite);

        setName('B');
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board, ArrayList<Move> history) {
        // Bishops must move diagonally, hence the absolute difference between from's rank and to's rank
        // must be equal to the absolute difference between from's file and to's file
        if (Math.abs(from.getRank() - to.getRank()) != Math.abs(from.getFile() - to.getFile())) {
            System.out.println(intToChar(to.getFile()) + to.getRank());
            return false;
        }

        // Now we can use the isPathClear method you provided, with a slight adjustment for diagonal movement
        return isPathClear(from, to, board);
    }
}
