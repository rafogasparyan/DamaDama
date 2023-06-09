package am.aua.dama.core;
import java.util.ArrayList;

public class Position {

    private int rank;

    private int file;

    public Position() {
        this.rank = 0;
        this.file = 0;
    }

    public Position(Position other) {
        this.setRank(other.rank);
        this.setFile(other.file);
    }


    public Position(int rank, int file) {
        this.setRank(rank);
        this.setFile(file);
    }


    public int getRank() {
        return this.rank;
    }

    public int getFile() {
        return this.file;
    }


    public void setRank(int rank) {
        if (rank >= 0 && rank < Dama.BOARD_RANKS)
            this.rank = rank;
    }


    public void setFile(int file) {
        if (file >= 0 && file < Dama.BOARD_FILES)
            this.file = file;
    }

    public String toString() {
        return "" + (char)('A' + this.file) + (Dama.BOARD_RANKS - this.rank);
    }

    /**
     *generateFromString method make for generating the user input to move
     * @param s user input
     * */
    public static Position generateFromString(String s) {
        s = s.toLowerCase();
        if (s.length() != 2
                || (s.charAt(0) < 'a' || s.charAt(0) >= 'a' + Dama.BOARD_FILES)
                || (s.charAt(1) < '1' || s.charAt(1) >= '1' + Dama.BOARD_RANKS)
        )
            return null;
        return generateFromRankAndFile(Dama.BOARD_RANKS - s.charAt(1) + '0',
                s.charAt(0) - 'a');
    }

    public static Position generateFromRankAndFile(int rank, int file) {
        Position result = null;
        if (rank >= 0 && rank < Dama.BOARD_RANKS
                && file >= 0 && file < Dama.BOARD_FILES
        )
            result = new Position(rank, file);
        return result;
    }

    public static ArrayList<Position> appendPositionsToArray(ArrayList<Position> arrayList, Position... elements) {
        for (Position pos : elements) {
            arrayList.add(pos);
        }
        return arrayList;
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != Position.class)
            return false;
        Position otherPosition = (Position) other;
        return this.rank == otherPosition.rank && this.file == otherPosition.file;
    }
}
