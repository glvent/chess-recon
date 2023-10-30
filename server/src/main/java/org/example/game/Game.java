package org.example.game;

import com.google.gson.Gson;
import org.example.piece.*;
import java.util.ArrayList;

public class Game {
    public final int BOARD_RANKS = 8;
    public final int BOARD_FILES = 8;
    public Piece[][] board;
    public ArrayList<String> history;

    public Game() {
        board = new Piece[BOARD_RANKS][BOARD_FILES];
        history = new ArrayList<>();
        initGame();
        printBoard();
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

    public void printBoard() {
        int i = BOARD_RANKS;
        for (Piece[] rank : board) {
            System.out.print(i + "  ");
            for (Piece file : rank) {
                if (file != null) {
                    System.out.print(file.name + " ");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println(" ");
            i--;
        }
        System.out.print("   ");
        for (int j = 0; j < BOARD_FILES; j++) {
            System.out.print(Piece.intToChar(j) + " ");
        }
    }

    public void movePiece(Position from, Position to) {
        Piece fromPiece = board[from.getRank()][from.getFile()];
        Piece toPiece = board[to.getRank()][to.getFile()];

        if (fromPiece.isValidMove(from, to) && fromPiece.isWhite() != toPiece.isWhite()) {
            board[to.getRank()][to.getFile()] = fromPiece;
            board[from.getRank()][from.getFile()] = null;

            fromPiece.setPosition(to);
            history.add(generateMoveNotation(from, to, fromPiece, toPiece));
        }
    }

    public String generateMoveNotation(Position from, Position to, Piece fromPiece, Piece toPiece) {
        String notation = "";

        if (fromPiece.name == 'P') {
            if (toPiece != null) {
                notation += Piece.intToChar(from.getFile());
                notation += "x";
            }
        }
        return notation;
    }

}
