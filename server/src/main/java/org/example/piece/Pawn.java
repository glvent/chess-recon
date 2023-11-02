package org.example.piece;

import org.example.game.Move;
import org.example.game.Position;

public class Pawn extends Piece {

    public Pawn(boolean isWhite, Position pos) {
        super(pos, isWhite);
        setName('P');
    }

    @Override
    public boolean isValidMove(Position from, Position to, Piece capturedPiece, Move prevMove) {
        System.out.println(
                "Pawn is from " + intToChar(from.getFile()) + (from.getRank() + 1) + " and to "
                        + intToChar(to.getFile()) + (to.getRank() + 1)+ ". " + (capturedPiece == null ? "" : "Captured " + capturedPiece.getName()));
        int fromRank = from.getRank();
        int fromFile = from.getFile();
        int toRank = to.getRank();
        int toFile = to.getFile();

        // pieces of same color are checked in the movePiece() method

        // checks for move direction
        if ((getIsWhite() && toRank > fromRank) || (!getIsWhite() && toRank < fromRank)) {
            int rankDelta = Math.abs(toRank - fromRank);
            int fileDelta = Math.abs(toFile - fromFile);

            System.out.println("rankDelta: " + rankDelta + " fileDelta: " + fileDelta + " firstMove: " + getIsFirstMove());
            if (rankDelta == 1) {
                // if the move is diagnol and onto a piece or en passant
                // or if the move is forward one and not onto a piece
                if (fileDelta == 1) {
                    if (capturedPiece != null) {
                        System.out.println("Pawn moved!");
                        return true;
                    } else if (isValidEnPassant(from, to, prevMove)) {
                        return true;
                    }
                } else if (fileDelta == 0 && capturedPiece == null) {
                    System.out.println("Pawn moved!");
                    return true;
                }
            } else if (rankDelta == 2 && fileDelta == 0 && getIsFirstMove()) {
                if (capturedPiece == null) {
                    System.out.println("Pawn moved!");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidEnPassant(Position from, Position to, Move prevMove) {
        if (prevMove != null && prevMove.getMovedPiece() instanceof Pawn) {
            // Verify the previous pawn moved 2 squares forward from its starting position
            if (Math.abs(prevMove.getFrom().getRank() - prevMove.getTo().getRank()) == 2) {
                // Check if the capturing pawn is adjacent to the target pawn's destination square
                if (Math.abs(prevMove.getTo().getFile() - from.getFile()) == 1 && prevMove.getTo().getRank() == from.getRank()) {
                    // Move to the empty square behind the target pawn (same file, different rank)
                    if (to.getFile() == prevMove.getTo().getFile() && to.getRank() != prevMove.getTo().getRank()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}