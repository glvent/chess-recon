package org.example.game;

import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private final Board board;
    private final ArrayList<Move> history;
    private boolean isWhiteTurn = true;

    private final String uid;

    private final ArrayList<Player> players = new ArrayList<>();



    public Game(String uid) {
        this.uid = uid;
        board = new Board();
        history = new ArrayList<>();
        //logBoard(board);
    }

    public synchronized boolean addPlayer(WebSocketSession session, boolean isWhite) {
        if (players.size() < 2) {
            players.add(new Player(session, isWhite));
            return true;
        }
        return false;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void endGame() {

    }

    public synchronized boolean playerMove(Position from, Position to, boolean isWhiteMove) {
        if (board.checkMove(from, to, history, isWhiteTurn, isWhiteMove)) {
            board.makeMove(from, to, history);
            togglePlayerTurn();
            return true;
        } else {
            return false;
        }
    }

    private void togglePlayerTurn() {
        isWhiteTurn = !isWhiteTurn;
    }

    public Board getBoard() {
        return board;
    }

}
