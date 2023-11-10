package org.example.game.piece;

import org.example.game.Move;
import org.example.game.Position;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(boolean isWhite, Position pos) {
        super(pos, isWhite);
        setName('N');
    }
    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board, ArrayList<Move> history) {
        // calculate the difference in the rank and file
        int rankDifference = Math.abs(from.getRank() - to.getRank());
        int fileDifference = Math.abs(from.getFile() - to.getFile());

        // either knight moves in an l shape vertically or horizontally.
        // can also be forward or backwards and over pieces!
        return (rankDifference == 2 && fileDifference == 1) || (rankDifference == 1 && fileDifference == 2);
    }

}
