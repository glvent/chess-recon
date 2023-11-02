"use client";
import React, { useEffect, useState } from "react";
import useWebSocket from "./hooks/useWebSocket";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRotateRight } from "@fortawesome/free-solid-svg-icons";
import { Piece, Position } from "@/app/types/chessTypes";
import Board from "./components/Board";
import History from "@/app/components/History";

const pieceImages = {
  whiteImages: {
    P: "/images/white-pawn.png",
    R: "/images/white-rook.png",
    N: "/images/white-knight.png",
    K: "/images/white-king.png",
    Q: "/images/white-queen.png",
    B: "/images/white-bishop.png",
  },
  blackImages: {
    P: "/images/black-pawn.png",
    R: "/images/black-rook.png",
    N: "/images/black-knight.png",
    K: "/images/black-king.png",
    Q: "/images/black-queen.png",
    B: "/images/black-bishop.png",
  },
};

const Page = () => {
  const [pieceDragged, setPieceDragged] = useState<Piece | null>(null);
  const [enteredTile, setEnteredTile] = useState<Position | null>(null);
  const { ws, board, history, clientUID } = useWebSocket(
    "ws://localhost:8080/ws",
  );

  const files = "abcdefgh";

  const handleDragOver = (
    e: React.DragEvent<HTMLDivElement>,
    rankIndex: number,
    fileIndex: number,
  ) => {
    e.preventDefault();

    if (board) {
      if (
        rankIndex !== pieceDragged?.pos.rank ||
        fileIndex !== pieceDragged?.pos.file
      ) {
        e.currentTarget.classList.add("border", "border-red-600");
      }
    }
  };

  const handleDragLeave = (
    e: React.DragEvent<HTMLDivElement>,
    rankIndex: number,
    fileIndex: number,
  ) => {
    e.preventDefault();
    e.currentTarget.classList.remove("border", "border-red-600");
  };

  const handleDrop = (
    e: React.DragEvent<HTMLDivElement>,
    rankIndex: number,
    fileIndex: number,
  ) => {
    e.preventDefault();
    e.currentTarget.classList.remove("border", "border-red-600");

    if (board) {
      if (
        rankIndex === pieceDragged?.pos.rank &&
        fileIndex === pieceDragged?.pos.file
      )
        return;

      const moveInfo = {
        from: pieceDragged?.pos,
        to: { rank: rankIndex, file: fileIndex },
      };

      console.log(
        "moved to -> " +
          files.charAt(moveInfo.to.file) +
          (moveInfo.to.rank + 1),
      );

      if (ws) {
        ws.send(JSON.stringify({ type: "MOVE_PIECE", data: moveInfo }));
      }
    }
  };

  function handleDragStart(e: React.DragEvent<HTMLImageElement>, piece: Piece) {
    setPieceDragged(piece);
  }

  function handleDragEnd(e: React.DragEvent<HTMLImageElement>) {
    setPieceDragged(null);
    setEnteredTile(null);
  }

  function handleDrag(e: React.DragEvent<HTMLImageElement>) {}

  return !board ? (
    <h1 className="h-screen grid place-content-center ">loading board...</h1>
  ) : (
    <div className="grid place-content-center gap-2 h-screen w-screen grid-flow-col">
      <Board
        board={board}
        handleDragOver={handleDragOver}
        handleDragLeave={handleDragLeave}
        handleDrop={handleDrop}
        handleDragStart={handleDragStart}
        pieceImages={pieceImages}
      />
      <div className="mx-auto">
        <button className="w-6 h-6 p-1 grid hover:brightness-95 rounded bg-violet-600">
          <FontAwesomeIcon className="w-auto h-auto" icon={faRotateRight} />
        </button>
      </div>
      <History history={history} />
    </div>
  );
};

export default Page;
