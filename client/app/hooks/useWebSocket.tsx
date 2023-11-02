"use client";
import { useEffect, useState } from "react";
import { v4 as uuidv4 } from "uuid";
import { Position, Piece, Move } from "@/app/types/chessTypes";

const useWebSocket = (url: string) => {
  const [ws, setWs] = useState<WebSocket | null>(null);
  const [board, setBoard] = useState<Piece[][] | null>(null);
  const [history, setHistory] = useState<Move[] | null>(null);
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
          setBoard(message.data.board);
          setHistory(message.data.history);
          break;
        case "INVALID_MOVE":
          const audio = new Audio("/sounds/wrong-47985.mp3");
          audio.volume = 0.01;
          audio.play();
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
