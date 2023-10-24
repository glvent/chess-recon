package server.piece;

public class Queen extends Piece {

    public Queen(boolean isWhite, Position pos) {
        super(pos, isWhite);

        name = 'Q';
    }

    @Override
    public boolean isValidMove(Position to, Position from) {
        return false;
    }
}
