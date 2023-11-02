import React, { FC } from "react";
import { Piece } from "@/app/types/chessTypes";

interface ChessBoardProps {
  board: Piece[][];
  handleDragOver: (
    e: React.DragEvent<HTMLDivElement>,
    rankIndex: number,
    fileIndex: number,
  ) => void;
  handleDragLeave: (
    e: React.DragEvent<HTMLDivElement>,
    rankIndex: number,
    fileIndex: number,
  ) => void;
  handleDrop: (
    e: React.DragEvent<HTMLDivElement>,
    rankIndex: number,
    fileIndex: number,
  ) => void;
  handleDragStart: (e: React.DragEvent<HTMLImageElement>, piece: Piece) => void;
  pieceImages: {
    whiteImages: { [key: string]: string };
    blackImages: { [key: string]: string };
  };
}

const Board: FC<ChessBoardProps> = ({
  board,
  handleDragOver,
  handleDragLeave,
  handleDrop,
  handleDragStart,
  pieceImages,
}) => {
  const files = "abcdefgh";

  return (
    <div>
      {board
        .slice()
        .reverse()
        .map((rank, reversedRankIndex) => {
          const rankIndex = board.length - 1 - reversedRankIndex; // Convert back to original index
          return (
            <div key={rankIndex} className="flex h-auto">
              {rank.map((piece: Piece, fileIndex: number) => (
                <div
                  key={`${rankIndex}-${fileIndex}`}
                  className={`w-20 h-20 p-2 select-none relative ${
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
                      className={`absolute text-opacity-75 h-min w-min bottom-0 right-1 ${
                        (rankIndex + fileIndex) % 2 === 0
                          ? "text-lime-100"
                          : "text-green-700"
                      }`}
                    >
                      {files.charAt(fileIndex)}
                    </h1>
                  )}
                  {fileIndex === 0 && (
                    <h1
                      className={`absolute text-opacity-75 h-min w-min top-0 left-1 ${
                        (rankIndex + fileIndex) % 2 === 0
                          ? "text-lime-100"
                          : "text-green-700"
                      }`}
                    >
                      {rankIndex + 1}
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
              ))}
            </div>
          );
        })}
    </div>
  );
};

export default Board;
