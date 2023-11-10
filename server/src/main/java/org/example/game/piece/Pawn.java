package org.example.game.piece;

import org.example.game.Move;
import org.example.game.Position;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(boolean isWhite, Position pos) {
        super(pos, isWhite);
        setName('P');
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board, ArrayList<Move> history) {
        Piece capturedPiece = board[to.getRank()][to.getFile()];
        Move prevMove = history.isEmpty() ? null : history.get(history.size() - 1);

        int fromRank = from.getRank();
        int fromFile = from.getFile();
        int toRank = to.getRank();
        int toFile = to.getFile();

        // checks for move direction
        if ((getIsWhite() && toRank > fromRank) || (!getIsWhite() && toRank < fromRank)) {
            int rankDelta = Math.abs(toRank - fromRank);
            int fileDelta = Math.abs(toFile - fromFile);

            if (rankDelta == 1) {
                // if the move is diagnol and onto a piece or en passant
                // or if the move is forward one and not onto a piece
                if (fileDelta == 1) {
                    if (capturedPiece != null) {
                        return true;
                    } else if (isValidEnPassant(from, to, prevMove)) {
                        return true;
                    }
                } else if (fileDelta == 0 && capturedPiece == null) {
                    return true;
                }
            } else if (rankDelta == 2 && fileDelta == 0 && getIsFirstMove()) {
                if (capturedPiece == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidEnPassant(Position from, Position to, Move prevMove) {
        if (prevMove != null && prevMove.getMovedPiece() instanceof Pawn) {
            // if the previous pawn moved 2 squares forward from its starting position
            if (Math.abs(prevMove.getFrom().getRank() - prevMove.getTo().getRank()) == 2) {
                //  if the capturing pawn is adjacent to the target pawn's destination square
                if (Math.abs(prevMove.getTo().getFile() - from.getFile()) == 1 && prevMove.getTo().getRank() == from.getRank()) {
                    // if the square is empty behind the target pawn (same file, different rank)
                    if (to.getFile() == prevMove.getTo().getFile() && to.getRank() != prevMove.getTo().getRank()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}