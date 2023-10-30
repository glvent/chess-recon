package piece;

public class King extends Piece {

    public King(boolean isWhite, Position pos) {
        super(pos, isWhite);

        name = 'K';
    }

    @Override
    public boolean isValidMove(Position to, Position from) {
        return false;
    }
}
