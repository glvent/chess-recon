package org.example.ws;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.tomcat.util.json.JSONParser;
import org.example.game.Game;
import org.example.game.Player;
import org.example.game.Position;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler extends TextWebSocketHandler {
    private final Gson gson = new Gson();
    private final Map<String, Game> games = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        Game game = findGameToJoinOrCreateNew();
        session.getAttributes().put("game", game);
        boolean joined = game.addPlayer(session, !game.getPlayers().isEmpty());
        Player currentPlayer = getCurrentPlayer(game, session);

        if (joined) {
            sendMessage(session, "INIT_GAME", game);
            sendMessage(session, "INIT_PLAYER", currentPlayer);
        } else {
            sendMessage(session, "ERROR", "Could not join game.");
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        try {
            Map<String, Object> parsedMessage = gson.fromJson(message.getPayload(), Map.class);
            String messageType = (String) parsedMessage.get("type");
            Map<String, Object> messageData = (Map<String, Object>) parsedMessage.get("data");

            switch (messageType) {
                case "MOVE_PIECE":
                    handleMovePiece(session, messageData);
                    break;
                default:
                    sendMessage(session, "ERROR", "Unknown message type: " + messageType);
                    break;
            }
        } catch (Exception e) {
            sendMessage(session, "ERROR", "An error occurred: " + e.getMessage());
        }
    }

    private void handleMovePiece(WebSocketSession session, Map<String, Object> messageData) throws IOException {
        Game game = getGameFromSession(session);

        if (game != null) {
            Position from = getPositionFromMap((Map<String, Object>) messageData.get("from"));
            Position to = getPositionFromMap((Map<String, Object>) messageData.get("to"));
            boolean isWhiteMoved = (boolean) messageData.get("isWhiteMoved");

            System.out.println(isWhiteMoved);

            boolean result = game.playerMove(from, to, isWhiteMoved);

            if (!result) {
                sendMessage(session, "INVALID_MOVE", null);
            } else {
                broadcastToPlayers(game, "UPDATE_BOARD", game.getBoard());
            }
        } else {
            sendMessage(session, "ERROR", "Could not find game for session.");
        }
    }

    private Position getPositionFromMap(Map<String, Object> map) {
        return new Position(((Double) map.get("rank")).intValue(), ((Double) map.get("file")).intValue());
    }

    private Game findGameToJoinOrCreateNew() {
        synchronized (this) {
            for (Game game : games.values()) {
                if (game.getPlayers().size() < 2) {
                    return game;
                }
            }
            String gameID = UUID.randomUUID().toString();
            Game newGame = new Game(gameID);
            games.put(gameID, newGame);
            return newGame;
        }
    }
    private Player getCurrentPlayer(Game game, WebSocketSession session) {
        for (Player p : game.getPlayers()) {
            if (p.getSession().equals(session)) {
                return p;
            }
        }
        return null;
    }

    private void sendMessage(WebSocketSession session, String type, Object data) throws IOException {
        Map<String, Object> message = new HashMap<>();
        message.put("type", type);
        message.put("data", data);
        String json = gson.toJson(message);
        session.sendMessage(new TextMessage(json));
    }

    private void broadcastToPlayers(Game game, String type, Object data) throws IOException {
        for (Player player : game.getPlayers()) {
            System.out.println(type);
            sendMessage(player.getSession(), type, data);
        }
    }

    private Game getGameFromSession(WebSocketSession session) {
        return (Game) session.getAttributes().get("game");
    }
}
