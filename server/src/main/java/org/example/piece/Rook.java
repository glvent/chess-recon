package piece;

public class Rook extends Piece {

    public Rook(boolean isWhite, Position pos) {
        super(pos, isWhite);

        name = 'R';
    }

    @Override
    public boolean isValidMove(Position to, Position from) {
        return false;
    }
}
