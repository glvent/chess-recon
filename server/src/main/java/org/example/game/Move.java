package org.example.game;

import org.example.piece.Pawn;
import org.example.piece.Piece;

public class Move {
    private Position from;
    private Position to;
    private Piece movedPiece;

    private Piece capturedPiece;

    private String notation;

    public Move(Position from, Position to, Piece movedPiece, Piece capturedPiece, String notation) {
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.notation = notation;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

}
