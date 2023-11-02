package org.example.game;

import org.example.piece.*;
import java.util.ArrayList;

import static org.example.piece.Piece.intToChar;

public class Game {
    public static final int BOARD_RANKS = 8;
    public static final int BOARD_FILES = 8;
    public Piece[][] board;
    public ArrayList<Move> history;
    public boolean isWhiteTurn = true;

    public boolean invalidMove = false;


    public Game() {
        board = new Piece[BOARD_RANKS][BOARD_FILES];
        history = new ArrayList<>();
        initGame();
        logBoard();
    }

    public void initGame() {
        // black pieces
        for (int i = 0; i < BOARD_RANKS; i++) {
            board[6][i] = new Pawn(false, new Position(6, i));
        }

        board[7][0] = new Rook(false, new Position(7, 0));
        board[7][1] = new Knight(false, new Position(7, 1));
        board[7][2] = new Bishop(false, new Position(7, 2));
        board[7][3] = new Queen(false, new Position(7, 3));
        board[7][4] = new King(false, new Position(7, 4));
        board[7][5] = new Bishop(false, new Position(7, 5));
        board[7][6] = new Knight(false, new Position(7, 6));
        board[7][7] = new Rook(false, new Position(7, 7));

        // white pieces
        for (int i = 0; i < BOARD_RANKS; i++) {
            board[1][i] = new Pawn(true, new Position(1, i));
        }

        board[0][0] = new Rook(true, new Position(0, 0));
        board[0][1] = new Knight(true, new Position(0, 1));
        board[0][2] = new Bishop(true, new Position(0, 2));
        board[0][3] = new Queen(true, new Position(0, 3));
        board[0][4] = new King(true, new Position(0, 4));
        board[0][5] = new Bishop(true, new Position(0, 5));
        board[0][6] = new Knight(true, new Position(0, 6));
        board[0][7] = new Rook(true, new Position(0, 7));

    }

    public void logBoard() {
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


    public void movePiece(Position from, Position to) {
        invalidMove = true;
        Piece movedPiece = board[from.getRank()][from.getFile()];
        Piece capturedPiece = board[to.getRank()][to.getFile()];
        Move previousMove = history.isEmpty() ? null : history.get(history.size() - 1);

        if (movedPiece.isValidMove(from, to, capturedPiece, history.isEmpty() ? null : history.get(history.size() - 1) )
                && movedPiece.getIsWhite() == isWhiteTurn) {
            System.out.println("moved to " + intToChar(to.getFile()) + (to.getRank() + 1));
            if (capturedPiece == null || movedPiece.getIsWhite() != capturedPiece.getIsWhite()) {
                invalidMove = false;
                // update the new board
                board[to.getRank()][to.getFile()] = movedPiece;
                board[from.getRank()][from.getFile()] = null;

                movedPiece.setPosition(to);

                // is setting internal position really necessary?

                if (capturedPiece != null) {
                    capturedPiece.setPosition(null);
                    capturedPiece.setIsCaptured(true);
                } else if (Pawn.isValidEnPassant(from, to, previousMove)) {
                    board[previousMove.getTo().getRank()][previousMove.getTo().getFile()].setPosition(null);
                    board[previousMove.getTo().getRank()][previousMove.getTo().getFile()].setIsCaptured(true);

                    board[previousMove.getTo().getRank()][previousMove.getTo().getFile()] = null;
                }
                Move move = new Move(from, to, movedPiece, capturedPiece, generateMoveNotation(from, to, movedPiece, capturedPiece));

                System.out.println("to " + intToChar(to.getFile()) + (to.getRank() + 1));

                history.add(move);
                isWhiteTurn = !isWhiteTurn;
            }
            //logBoard();
        }

    }

    public String generateMoveNotation(Position from, Position to, Piece movedPiece, Piece capturedPiece) {
        String notation = "";

        if (movedPiece.getName() != 'P') {
            notation += movedPiece.getName();
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

}
