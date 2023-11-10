package org.example.game.piece;

import org.example.game.Board;
import org.example.game.Game;
import org.example.game.Move;
import org.example.game.Position;

import java.util.ArrayList;

public class King extends Piece {
    private boolean inCheck = false;

    public King(boolean isWhite, Position pos) {
        super(pos, isWhite);

        setName('K');
    }


    @Override
    public boolean isValidMove(Position from, Position to, Piece[][] board, ArrayList<Move> history) {
        if (Math.abs(to.getFile() - from.getFile()) > 1 || Math.abs(to.getRank() - from.getRank()) > 1) {
            return false;
        }

        return !isInCheck(from, to, board, history);
    }



    public boolean isInCheck(Position from, Position to, Piece[][] board, ArrayList<Move> history) {
        Piece[][] clonedBoard = Board.deepCloneBoard(board);
        clonedBoard[to.getRank()][to.getFile()] = board[from.getRank()][from.getFile()];
        clonedBoard[from.getRank()][from.getFile()] = null;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                Piece piece = board[row][col];
                // Check if it's an enemy piece
                if (piece != null && piece.getIsWhite() != getIsWhite()) {
                    Position pieceFrom = new Position(row, col);
                    // Check if the piece can move to the king's position
                    if (piece.isValidMove(pieceFrom, to, clonedBoard, history)) {
                        System.out.println("king would be in check ");
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
