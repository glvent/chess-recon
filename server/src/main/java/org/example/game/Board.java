package org.example.game;

import org.example.game.piece.*;

import java.util.ArrayList;

import static org.example.game.piece.Piece.intToChar;

public class Board {
    public static final int BOARD_RANKS = 8;
    public static final int BOARD_FILES = 8;
    private Piece[][] layout = new Piece[BOARD_RANKS][BOARD_FILES];

    public Board() {
        initBoard();
    }

    public static Piece[][] deepCloneBoard(Piece[][] board) {
        Piece[][] deepClone = new Piece[8][8];
        for (int i = 0; i < deepClone.length; i++) {
            for (int j = 0; j < deepClone[i].length; j++) {
                deepClone[i][j] = board[i][j];
            }
        }
        return deepClone;
    }

    public boolean checkMove(Position from, Position to, ArrayList<Move> history, boolean isWhiteTurn, boolean isWhiteMove) {
        Piece movedPiece = layout[from.getRank()][from.getFile()];
        Piece capturedPiece = layout[to.getRank()][to.getFile()];
        Move prevMove = history.isEmpty() ? null : history.get(history.size() - 1);

        // Check if the move is valid without changing the board state
        return movedPiece.isValidMove(from, to, layout, history) &&
                ((capturedPiece == null || movedPiece.getIsWhite() != capturedPiece.getIsWhite()) && movedPiece.getIsWhite() == isWhiteTurn && isWhiteTurn == isWhiteMove);
    }

    public void makeMove(Position from, Position to, ArrayList<Move> history) {
        // Assume isValidMove has already been called and returned true
        Piece movedPiece = layout[from.getRank()][from.getFile()];
        Piece capturedPiece = layout[to.getRank()][to.getFile()];
        Move prevMove = history.isEmpty() ? null : history.get(history.size() - 1);

        // Move the piece
        layout[to.getRank()][to.getFile()] = movedPiece;
        layout[from.getRank()][from.getFile()] = null;
        movedPiece.setPosition(to);

        // Handle capture
        if (capturedPiece != null) {
            handleCapture(capturedPiece);
        } else if (Pawn.isValidEnPassant(from, to, prevMove)) {
            handleEnPassant(prevMove);
        }

        // Add move to history
        Move move = new Move(from, to, movedPiece, capturedPiece, generateMoveNotation(from, to, layout, history));
        history.add(move);
    }

    private void handleCapture(Piece capturedPiece) {
        capturedPiece.setPosition(null);
        capturedPiece.setIsCaptured(true);
    }

    private void handleEnPassant(Move prevMove) {
        // En passant logic
        Piece pieceToRemove = layout[prevMove.getTo().getRank()][prevMove.getTo().getFile()];
        pieceToRemove.setPosition(null);
        pieceToRemove.setIsCaptured(true);
        layout[prevMove.getTo().getRank()][prevMove.getTo().getFile()] = null;
    }

    public String generateMoveNotation(Position from, Position to, Piece[][] board, ArrayList<Move> history) {
        Piece movedPiece = board[from.getRank()][from.getFile()];
        Piece capturedPiece = board[to.getRank()][to.getFile()];

        String notation = "";

        System.out.println("to " + intToChar(to.getFile()) + (to.getRank() + 1));
        System.out.println("captured " + (capturedPiece == null ? "null" : capturedPiece.getName()));

        if (movedPiece != null) {
            if (movedPiece.getName() != 'P') {
                notation += movedPiece.getName();
            }
        }

        // handle disambiguating moves
        // (moves where 2 or more of the same piece and color can move to the same position)

       /* ArrayList<Position> disambiguatedPositions = new ArrayList<Position>();
        for (int i = 0; i < BOARD_RANKS; i++) {
            for (int j = 0; j < BOARD_FILES; j++) {
                Piece piece = board[i][j];

                if (piece != null && piece.getName() == movedPiece.getName() && piece.getIsWhite() == movedPiece.getIsWhite()) {
                    if (piece.isValidMove(new Position(i, j), to, capturedPiece, history.isEmpty() ? null : history.get(history.size() - 1))) {
                        disambiguatedPositions.add(new Position(i, j));
                    }
                }
            }
        }

        // Check if there is a need to disambiguate
        if (disambiguatedPositions.size() > 1) {
            // Add either the file or the rank, or both
            boolean sameFile = true, sameRank = true;
            for (Position pos : disambiguatedPositions) {
                if (pos.getFile() != from.getFile()) {
                    sameFile = false;
                }
                if (pos.getRank() != from.getRank()) {
                    sameRank = false;
                }
            }
            if (!sameFile) {
                notation += intToChar(from.getFile());
            }
            if (!sameRank) {
                notation += (from.getRank() + 1);
            }
        } */

        if (capturedPiece != null) {
            notation += "x";
        }

        notation += intToChar(to.getFile());
        notation += (to.getRank() + 1);

        // handle pawn promotion

        // handle castling

        // handle check & checkmate

        // handle en passant

        return notation;
    }

    public void initBoard() {
        // black pieces
        for (int i = 0; i < BOARD_RANKS; i++) {
            layout[6][i] = new Pawn(false, new Position(6, i));
        }

        layout[7][0] = new Rook(false, new Position(7, 0));
        layout[7][1] = new Knight(false, new Position(7, 1));
        layout[7][2] = new Bishop(false, new Position(7, 2));
        layout[7][3] = new Queen(false, new Position(7, 3));
        layout[7][4] = new King(false, new Position(7, 4));
        layout[7][5] = new Bishop(false, new Position(7, 5));
        layout[7][6] = new Knight(false, new Position(7, 6));
        layout[7][7] = new Rook(false, new Position(7, 7));

        // white pieces
        for (int i = 0; i < BOARD_RANKS; i++) {
            layout[1][i] = new Pawn(true, new Position(1, i));
        }

        layout[0][0] = new Rook(true, new Position(0, 0));
        layout[0][1] = new Knight(true, new Position(0, 1));
        layout[0][2] = new Bishop(true, new Position(0, 2));
        layout[0][3] = new Queen(true, new Position(0, 3));
        layout[0][4] = new King(true, new Position(0, 4));
        layout[0][5] = new Bishop(true, new Position(0, 5));
        layout[0][6] = new Knight(true, new Position(0, 6));
        layout[0][7] = new Rook(true, new Position(0, 7));

    }

    public static void logBoard(Piece[][] board) {
        int rankSize = board.length;
        int fileSize = board[0].length;

        for (int rank = rankSize - 1; rank >= 0; rank--) {
            System.out.print((rank + 1) + "  ");
            for (int file = 0; file < fileSize; file++) {
                Piece currentPiece = board[rank][file];
                if (currentPiece != null) {
                    String name = String.valueOf(currentPiece.getName());
                    // Assuming getName() returns a single-character name.
                    if (currentPiece.getIsWhite()) {
                        System.out.print(Character.toUpperCase(name.charAt(0)) + " ");
                    } else {
                        System.out.print(Character.toLowerCase(name.charAt(0)) + " ");
                    }
                } else {
                    // Using '.' to represent an empty square.
                    System.out.print(". ");
                }
            }
            System.out.println();
        }

        System.out.print("   ");
        for (int file = 0; file < fileSize; file++) {
            System.out.print(intToChar(file) + " ");
        }
        System.out.println();
    }

    public Piece[][] getLayout() {
        return layout;
    }
}
