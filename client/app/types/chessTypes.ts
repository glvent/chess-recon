export interface Position {
  rank: number;
  file: number;
}

export interface Piece {
  pos: Position;
  isWhite: boolean;
  name: string;
}

export interface Move {
  from: Position;
  to: Position;
  movedPiece: Piece;
  capturedPiece: Piece | null;
  notation: string;
}
