package piece;

public abstract class Piece {
    Position pos;
    boolean isWhite;
    public char name;

    public Piece(Position pos, boolean isWhite) {
        this.pos = pos;
        this.isWhite = isWhite;
    }

    public static char intToChar(int i) {
        String letter = "abcdefghijklmnopqrstuvwxyz";
        return letter.charAt(i);
    }

    public void setPosition(Position pos) {
        this.pos = pos;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public abstract boolean isValidMove(Position from, Position to);

}
