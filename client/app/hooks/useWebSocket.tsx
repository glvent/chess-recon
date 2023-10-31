"use client";
import { useEffect, useState } from "react";
import { v4 as uuidv4 } from "uuid";

interface Position {
  rank: number;
  file: number;
}

interface Piece {
  pos: Position;
  isWhite: boolean;
  name: string;
  isValidMove(from: Position, to: Position): boolean;
}

const useWebSocket = (url: string) => {
  const [ws, setWs] = useState<WebSocket | null>(null);
  const [board, setBoard] = useState<Piece[][] | null>(null);
  const [history, setHistory] = useState<String[] | null>(null);
  const [clientUID] = useState<String>(uuidv4());

  useEffect(() => {
    console.log(clientUID);
    const wsInstance = new WebSocket(url);

    wsInstance.onopen = () => {
      console.log("Connected to the WebSocket");
      wsInstance.send("Hello from client");
    };

    wsInstance.onmessage = (event) => {
      const message = JSON.parse(event.data);
      console.log(message);
      switch (message.type) {
        case "NEW_GAME":
          setBoard(message.data.board);
          break;
        case "UPDATE_BOARD":
          setBoard(message.data);
          break;
      }
    };

    wsInstance.onclose = () => {
      console.log("Disconnected from the WebSocket");
    };

    setWs(wsInstance);

    return () => {
      wsInstance.close();
    };
  }, [url]);

  return { ws, board, history, clientUID };
};

export default useWebSocket;
