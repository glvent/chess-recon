package org.example.game;

import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;

public class Player implements Serializable {
    private transient WebSocketSession session;
    private boolean isWhite;
    public Player(WebSocketSession session, boolean isWhite) {
        this.session = session;
        this.isWhite = isWhite;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public boolean isWhite() {
        return isWhite;
    }
}
