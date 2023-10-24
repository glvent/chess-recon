package server.piece;

public class Position {
    int rank;
    int file;

    public Position(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

}
