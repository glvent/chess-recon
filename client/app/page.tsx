"use client";
import { useEffect, useState } from "react";
import useWebSocket from "./hooks/useWebSocket";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRotateRight } from "@fortawesome/free-solid-svg-icons";

interface Position {
  rank: number;
  file: number;
}

interface Piece {
  pos: Position;
  isWhite: boolean;
  name: string;
}

interface PieceImageMap {
  [key: string]: string;
}

const pieceImages: { whiteImages: PieceImageMap; blackImages: PieceImageMap } =
  {
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
  const [isFlipped, setIsFlipped] = useState<boolean>(false);

  // @ts-ignore
  const displayBoard = board
    ? isFlipped
      ? [...board].reverse()
      : board
    : null;
  const letters = "abcdefghijklmnopqrstuvwxyz";

  const handleDragOver = (
    e: React.DragEvent<HTMLDivElement>,
    rankIndex: number,
    fileIndex: number,
  ) => {
    e.preventDefault();

    if (board) {
      const realRankIndex = getRealIndex(
        rankIndex,
        board.length - 1,
        isFlipped,
      );
      const realFileIndex = getRealIndex(
        fileIndex,
        board[0].length - 1,
        isFlipped,
      );

      if (
        realRankIndex !== pieceDragged?.pos.rank ||
        realFileIndex !== pieceDragged?.pos.file
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
      const realRankIndex = getRealIndex(
        rankIndex,
        board.length - 1,
        isFlipped,
      );
      const realFileIndex = getRealIndex(
        fileIndex,
        board[0].length - 1,
        isFlipped,
      );

      if (
        realRankIndex === pieceDragged?.pos.rank &&
        realFileIndex === pieceDragged?.pos.file
      )
        return;

      const moveInfo = {
        from: pieceDragged?.pos,
        to: { rank: realRankIndex, file: realFileIndex },
      };

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

  function getRealIndex(
    index: number,
    maxIndex: number,
    isFlipped: boolean,
  ): number {
    return isFlipped ? maxIndex - index : index;
  }

  return !board ? (
    <h1 className="h-screen grid place-content-center">loading board...</h1>
  ) : (
    <div className="grid place-content-center h-screen">
      <button onClick={() => setIsFlipped(!isFlipped)}>Flip Board</button>
      {displayBoard?.map((rank, rankIndex) => (
        <div key={rankIndex} className="flex">
          {(isFlipped ? [...rank].reverse() : rank).map(
            (piece: Piece, fileIndex: number) => (
              <div
                key={`${rankIndex}-${fileIndex}-${enteredTile?.rank}-${enteredTile?.file}`}
                className={`w-20 h-20 p-2 relative ${
                  (rankIndex + fileIndex) % 2 === 0
                    ? "bg-green-700"
                    : "bg-lime-100"
                }`}
                onDragOver={(e) => handleDragOver(e, rankIndex, fileIndex)}
                onDragLeave={(e) => handleDragLeave(e, rankIndex, fileIndex)}
                onDrop={(e) => handleDrop(e, rankIndex, fileIndex)}
              >
                {rankIndex === 0 && (
                  <h1
                    className={`absolute text-white h-min w-min top-0 right-1 ${
                      (rankIndex + fileIndex) % 2 === 0
                        ? "text-lime-100"
                        : "text-green-700"
                    }`}
                  >
                    {letters.charAt(fileIndex)}
                  </h1>
                )}

                {fileIndex === 0 && (
                  <h1
                    className={`absolute text-opacity-75 h-min w-min bottom-0 left-1 ${
                      (rankIndex + fileIndex) % 2 === 0
                        ? "text-lime-100"
                        : "text-green-700"
                    }`}
                  >
                    {!isFlipped ? rankIndex + 1 : board.length - rankIndex}
                  </h1>
                )}

                {piece && (
                  <img
                    src={
                      piece.isWhite
                        ? pieceImages.whiteImages[piece.name]
                        : pieceImages.blackImages[piece.name]
                    }
                    alt={piece.name}
                    draggable
                    onDragStart={(e) => handleDragStart(e, piece)}
                  />
                )}
              </div>
            ),
          )}
        </div>
      ))}
    </div>
  );
};

export default Page;
