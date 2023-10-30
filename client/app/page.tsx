"use client"
import {useEffect, useState} from 'react';

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

interface PieceImageMap {
    [key: string]: string;
}

const pieceImages: { whiteImages: PieceImageMap, blackImages: PieceImageMap } = {
    whiteImages: {
        "P": "/images/white-pawn.png",
        "R": "/images/white-rook.png",
        "N": "/images/white-knight.png",
        "K": "/images/white-king.png",
        "Q": "/images/white-queen.png",
        "B": "/images/white-bishop.png"
    },
    blackImages: {
        "P": "/images/black-pawn.png",
        "R": "/images/black-rook.png",
        "N": "/images/black-knight.png",
        "K": "/images/black-king.png",
        "Q": "/images/black-queen.png",
        "B": "/images/black-bishop.png"
    }
}

const Board = () => {
    const [board, setBoard] = useState<Piece[][] | null>(null);

    useEffect(() => {
        const ws = new WebSocket('ws://localhost:8080/ws');

        ws.onopen = () => {
            console.log('Connected to the WebSocket');
            ws.send("Hello from client");
        };

        ws.onmessage = (event) => {
            const message = JSON.parse(event.data);
            console.log(message);
            switch (message.type) {
                case "NEW_GAME":
                    setBoard(message.data.board);
            }
        };

        ws.onclose = () => {
            console.log('Disconnected from the WebSocket');
        };


        return () => {
            ws.close();
        };
    }, []);

    return (
        <div className="grid place-content-center h-screen">
            {board?.map((row, rowIndex) => (
                <div key={rowIndex} className="flex">
                    {row.map((piece, colIndex) => (
                        <div
                            key={colIndex}
                            className={`w-20 h-20 ${((rowIndex + colIndex) % 2 === 0) ? 'bg-amber-900' : 'bg-orange-950'}`}
                        >
                            {piece && (
                                <img src={piece.isWhite ? pieceImages.whiteImages[piece.name!] : pieceImages.blackImages[piece.name!]}  alt={piece.name}/>
                            )}
                        </div>
                    ))}
                </div>
            ))}
        </div>
    );
};

export default Board;
