package org.example.ws;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.example.game.Position;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import org.example.game.Game;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class WebSocketHandler extends TextWebSocketHandler {

    private final Gson gson = new Gson();
    public Game game;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // This will be called after the connection is established
        game = new Game();
        Map<String, Object> message = new HashMap<>();

        message.put("type", "NEW_GAME");
        message.put("data", game);

        session.sendMessage(new TextMessage(getJSON(message)));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        try {
            Map parsedMessage = (Map) parseJSON(message.getPayload());
            System.out.println("Parsed message: " + parsedMessage);

            String messageType = (String) parsedMessage.get("type");
            Map messageData = (Map) parsedMessage.get("data");

            switch (messageType) {
                case "MOVE_PIECE" -> {
                    Map fromMap = (Map) messageData.get("from");
                    Position from = new Position(((Double) fromMap.get("rank")).intValue(), ((Double) fromMap.get("file")).intValue());

                    Map toMap = (Map) messageData.get("to");
                    Position to = new Position(((Double) toMap.get("rank")).intValue(), ((Double) toMap.get("file")).intValue());

                    game.movePiece(from, to);

                    Map<String, Object> clientMessage = new HashMap<>();

                    clientMessage.put("type", "UPDATE_BOARD");
                    clientMessage.put("data", game.board);


                    session.sendMessage(new TextMessage(getJSON(clientMessage)));
                }
            }
        } catch (JsonSyntaxException e) {
            System.out.println("handleTextMessage: " + e.getMessage());
            // Handle exception - could notify client of bad request, for example
        }
    }

    public <T> String getJSON(T obj) {
        return gson.toJson(obj);
    }

    public Object parseJSON(String json) throws JsonSyntaxException {
        return gson.fromJson(json, Object.class);
    }
}
