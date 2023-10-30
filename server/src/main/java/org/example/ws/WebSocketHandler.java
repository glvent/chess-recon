package org.example.ws;

import com.google.gson.Gson;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.example.game.Game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class WebSocketHandler extends TextWebSocketHandler {

    private final Gson gson = new Gson();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // This will be called after the connection is established
        Game game = new Game();
        Map<String, Object> message = new HashMap<>();

        message.put("type", "NEW_GAME");
        message.put("data", game);

        session.sendMessage(new TextMessage(getJSON(message)));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Handle received message
        // For example, you can send the message back
        System.out.println(message.toString());
    }

    public <T> String getJSON(T obj) {
        return gson.toJson(obj);
    }
}
