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
        game = new Game();
        sendMessage(session, "NEW_GAME", game);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            Map parsedMessage = gson.fromJson(message.getPayload(), Map.class);
            String messageType = (String) parsedMessage.get("type");
            Map messageData = (Map) parsedMessage.get("data");
            switch (messageType) {
                case "MOVE_PIECE":
                    Position from = getPositionFromMap((Map) messageData.get("from"));
                    Position to = getPositionFromMap((Map) messageData.get("to"));
                    game.movePiece(from, to);
                    if (game.invalidMove) {
                        sendMessage(session, "INVALID_MOVE", null);
                    } else {
                        sendMessage(session, "UPDATE_BOARD", game);
                    }
                    break;
                default:
                    // Handle unknown messageType
                    break;
            }
        } catch (JsonSyntaxException | IOException e) {
            // Handle exception - could notify client of bad request, for example
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private Position getPositionFromMap(Map map) {
        return new Position(((Double) map.get("rank")).intValue(), ((Double) map.get("file")).intValue());
    }

    private void sendMessage(WebSocketSession session, String type, Object data) throws IOException {
        Map<String, Object> message = new HashMap<>();
        message.put("type", type);
        message.put("data", data);
        session.sendMessage(new TextMessage(gson.toJson(message)));
    }
}
