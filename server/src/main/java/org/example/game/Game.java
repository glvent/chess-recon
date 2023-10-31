package org.example.game;

import org.example.piece.*;
import java.util.ArrayList;

public class Game {
    public static final int BOARD_RANKS = 8;
    public static final int BOARD_FILES = 8;
    public Piece[][] board;
    public ArrayList<String> history;

    public Game() {
        board = new Piece[BOARD_RANKS][BOARD_FILES];
        history = new ArrayList<>();
        initGame();
        logBoard();
    }

    public void initGame() {
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
            System.out.print(Piece.intToChar(file) + " ");
        }
        System.out.println();
    }


    public void movePiece(Position from, Position to) {
        Piece fromPiece = board[from.getRank()][from.getFile()];
        Piece toPiece = board[to.getRank()][to.getFile()];


        if (fromPiece == null) return;
        // if valid move...
        // fromPiece.isValidMove(from, to) && fromPiece.getIsWhite() != toPiece.getIsWhite()
        if (true) {
            // update the new board
            board[to.getRank()][to.getFile()] = fromPiece;
            board[from.getRank()][from.getFile()] = null;

            // update the piece
            fromPiece.setPosition(to);

            if (toPiece != null) {
                toPiece.setPosition(null);
                toPiece.setIsCaptured(true);
            }
            //history.add(generateMoveNotation(from, to, fromPiece, toPiece));
        }

        logBoard();
    }

    public String generateMoveNotation(Position from, Position to, Piece fromPiece, Piece toPiece) {
        String notation = "";

        if (fromPiece.getName() != 'P') {
            notation += fromPiece.getName();
        }

        // handle disambiguating moves
        // (moves where 2 or more of the same piece and color can move to the same position)

        ArrayList<Position> disambiguatedPositions = new ArrayList<Position>();
        for (int i = 0; i < BOARD_RANKS; i++) {
            for (int j = 0; j < BOARD_FILES; j++) {
                Piece piece = board[i][j];

                if (piece != null && piece.getName() == fromPiece.getName() && piece.getIsWhite() == fromPiece.getIsWhite()) {
                    if (piece.isValidMove(new Position(i, j), to)) {
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
                notation += Piece.intToChar(from.getFile());
            }
            if (!sameRank) {
                notation += from.getRank();
            }
        }

        if (toPiece != null) {
            notation += "x";
        }

        notation += Piece.intToChar(to.getRank());
        notation += to.getFile();

        // handle pawn promotion

        // handle castling

        // handle check & checkmate

        // handle en passant

        return notation;
    }

}
