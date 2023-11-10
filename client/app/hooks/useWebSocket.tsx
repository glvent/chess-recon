"use client";
import { useEffect, useState } from "react";
import { v4 as uuidv4 } from "uuid";
import { Position, Piece, Move } from "@/app/types/chessTypes";

const useWebSocket = (url: string) => {
  const [ws, setWs] = useState<WebSocket | null>(null);
  const [board, setBoard] = useState<Piece[][] | null>(null);
  const [history, setHistory] = useState<Move[] | null>(null);
  const [gameId, setGameId] = useState<string | null>(null);
  const [player, setPlayer] = useState<any>(null);

  let audio: HTMLAudioElement;

  useEffect(() => {
    const wsInstance = new WebSocket(url);

    wsInstance.onopen = () => {
      console.log("Connected to the WebSocket");
    };

    wsInstance.onmessage = (event) => {
      const message = JSON.parse(event.data);
      console.log(message);
      switch (message.type) {
        case "INIT_GAME":
          setBoard(message.data?.board.layout);
          setGameId(message.data?.uid);
          break;
        case "INIT_PLAYER":
          setPlayer(message.data);
          break;
        case "UPDATE_BOARD":
          audio = new Audio("/sounds/move-self.mp3");
          audio.play();

          setTimeout(() => {
            audio.pause();
          }, 2000);

          setBoard(message.data.layout);
          setHistory(message.data.history);
          break;
        case "INVALID_MOVE":
          audio = new Audio("/sounds/wrong-47985.mp3");
          audio.volume = 0.1;
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

  return { ws, board, history, gameId, player };
};

export default useWebSocket;
