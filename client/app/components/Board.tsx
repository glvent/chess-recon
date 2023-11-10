import React, { FC } from "react";
import { Piece } from "@/app/types/chessTypes";

interface ChessBoardProps {
  board: Piece[][];
  isWhite: boolean; // Indicates the player's color
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
  isWhite,
  handleDragOver,
  handleDragLeave,
  handleDrop,
  handleDragStart,
  pieceImages,
}) => {
  const files = "abcdefgh";
  const displayedBoard = !isWhite ? board : [...board].reverse();

  return (
    <div>
      {displayedBoard.map((rank, rankIndex) => {
        const displayRankIndex = !isWhite
          ? rankIndex
          : board.length - 1 - rankIndex;
        return (
          <div key={displayRankIndex} className="flex h-auto">
            {rank.map((piece: Piece, fileIndex: number) => {
              const displayFileIndex = !isWhite
                ? fileIndex
                : rank.length - 1 - fileIndex;
              return (
                <div
                  key={`${displayRankIndex}-${displayFileIndex}`}
                  className={`w-20 h-20 p-2 select-none relative ${
                    (displayRankIndex + displayFileIndex) % 2 === 0
                      ? "bg-green-700"
                      : "bg-lime-100"
                  }`}
                  onDragOver={(e) =>
                    handleDragOver(e, displayRankIndex, displayFileIndex)
                  }
                  onDragLeave={(e) =>
                    handleDragLeave(e, displayRankIndex, displayFileIndex)
                  }
                  onDrop={(e) =>
                    handleDrop(e, displayRankIndex, displayFileIndex)
                  }
                >
                  {displayRankIndex === (!isWhite ? board.length - 1 : 0) && (
                    <h1
                      className={`absolute text-opacity-75 h-min w-min bottom-0 right-1 ${
                        (displayRankIndex + displayFileIndex) % 2 === 0
                          ? "text-lime-100"
                          : "text-green-700"
                      }`}
                    >
                      {files.charAt(displayFileIndex)}
                    </h1>
                  )}
                  {displayFileIndex === (!isWhite ? 0 : rank.length - 1) && (
                    <h1
                      className={`absolute text-opacity-75 h-min w-min top-0 left-1 ${
                        (displayRankIndex + displayFileIndex) % 2 === 0
                          ? "text-lime-100"
                          : "text-green-700"
                      }`}
                    >
                      {displayRankIndex + 1}
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
              );
            })}
          </div>
        );
      })}
    </div>
  );
};

export default Board;
