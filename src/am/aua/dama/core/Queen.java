package am.aua.dama.core;
import java.util.ArrayList;
/**
 * Queen class
 * */
public class Queen extends Piece {

    /**
     * no arg constructor to initialize queen as white
     * */
    public Queen() {
        this(Dama.PieceColor.WHITE);
    }

    /**
     * constructor based on given color
     * */
    public Queen(Dama.PieceColor color) {
        super(color);
    }

    /**
     * method to return Strung representation of Queen piece. p for black Queen, Q for White queen
     * */
    public String toString() {
        if (this.getPieceColor() == Dama.PieceColor.WHITE)
            return "Q";
        else
            return "q";
    }


    /**
     * method which returns the positions where piece can go based on the logic it has opponents piece to eat or not
     * */
    public ArrayList<Position> allDestinations(Dama Board, Position p) {
        if (Queen.canEat(Board, p).size() == 0) {
            return Queen.move(Board, p);
        }
        return Queen.canEat(Board, p);
    }

    /**
     * method which returns th Positions which player can eat from the given origin. A piece can eat left, right, up with jumping.
     * Queen can eat more than 1 Position with jump
     * @return Positions
     * @param dama current bord
     * @param p given current position to find positions to eat for
     * */

    static ArrayList<Position> canEat(Dama dama, Position p) {
        ArrayList<Position> result = new ArrayList<>(0);
        Dama.PieceColor myColor = dama.getPieceAt(p).getPieceColor();

        if (myColor == Dama.PieceColor.WHITE) {
            int[] rankOffsets = {1, -1, 0, 0};
            int[] fileOffsets = {0, 0, 1, -1};
            for (int d = 0; d < rankOffsets.length; d++) {
                boolean bool = false;
                boolean k = false;
                int i = p.getRank() + rankOffsets[d];
                int j = p.getFile() + fileOffsets[d];
                while (i >= 0 && i < Dama.BOARD_RANKS
                        && j >= 0 && j < Dama.BOARD_FILES) {
                    Position current = Position.generateFromRankAndFile(i, j);
                    Position afterCurrent = Position.generateFromRankAndFile(i + rankOffsets[d],
                            j + fileOffsets[d]);
                    if (!k && afterCurrent != null && afterCurrent.getRank() < 8 && afterCurrent.getRank() >= 0
                            && afterCurrent.getFile() < 8 && afterCurrent.getFile() >= 0) {
                        if (bool && dama.getPieceAt(afterCurrent) != null && !dama.isEmpty(afterCurrent)) {
                            k = true;
                        } else if (bool && dama.isEmpty(afterCurrent)) {
                            result = Position.appendPositionsToArray(result, afterCurrent);
                        }
                        if (dama.getPieceAt(current) != null && dama.getPieceAt(current).getPieceColor() == Dama.PieceColor.BLACK
                                && dama.isEmpty(afterCurrent)) {
                            result = Position.appendPositionsToArray(result, afterCurrent);
                            bool = true;
                        } else if (dama.getPieceAt(current) != null) {
                            k = true;
                        }
                    }
                    i += rankOffsets[d];
                    j += fileOffsets[d];
                }
            }
        } else {
            int[] rankOffsets = {1, -1, 0, 0};
            int[] fileOffsets = {0, 0, 1, -1};
            for (int d = 0; d < rankOffsets.length; d++) {
                boolean bool = false;
                boolean k = false;
                int i = p.getRank() + rankOffsets[d];
                int j = p.getFile() + fileOffsets[d];
                while (i >= 0 && i < Dama.BOARD_RANKS
                        && j >= 0 && j < Dama.BOARD_FILES) {
                    Position current = Position.generateFromRankAndFile(i, j);
                    Position afterCurrent = Position.generateFromRankAndFile(i + rankOffsets[d],
                            j + fileOffsets[d]);
                    if (!k && afterCurrent != null && afterCurrent.getRank() < Dama.BOARD_RANKS && afterCurrent.getRank() >= 0
                            && afterCurrent.getFile() < Dama.BOARD_FILES && afterCurrent.getFile() >= 0) {
                        if (bool && dama.getPieceAt(afterCurrent) != null && !dama.isEmpty(afterCurrent)) {
                            k = true;
                        } else if (bool && dama.isEmpty(afterCurrent)){
                            result = Position.appendPositionsToArray(result, afterCurrent);
                        }
                        if (dama.getPieceAt(current) != null && dama.getPieceAt(current).getPieceColor() == Dama.PieceColor.WHITE && dama.isEmpty(afterCurrent)) {
                            result = Position.appendPositionsToArray(result, afterCurrent);
                            bool = true;
                        } else if (dama.getPieceAt(current) != null) {
                            k = true;
                        }
                    }
                    i += rankOffsets[d];
                    j += fileOffsets[d];
                }
            }
        }
        return result;
    }


    /**
     *Move method make ArrayList of moves figure has
     * @param dama current board
     * @param p the current position to look for changes
     * @return Arraylist of positons where it can go based on if it eats or just makes a move
     * */

    static ArrayList<Position> move(Dama dama, Position p) {
        //Accessed by Queens from the same package, does not need to be public.
        ArrayList<Position> result = new ArrayList<>(0);
        int[] rankOffsets = new int[]{1, -1, 0, 0};
        int[] fileOffsets = new int[]{0, 0, 1, -1};
        Dama.PieceColor myColor = dama.getPieceAt(p).getPieceColor();

        if (myColor == Dama.PieceColor.WHITE) {
            for (int d = 0; d < rankOffsets.length; d++) {
                int i = p.getRank() + rankOffsets[d];
                int j = p.getFile() + fileOffsets[d];
                while (i >= 0 && i < Dama.BOARD_RANKS
                        && j >= 0 && j < Dama.BOARD_FILES) {
                    Position current = Position.generateFromRankAndFile(i, j);
                    if (dama.isEmpty(current)){
                        result = Position.appendPositionsToArray(result,current);
                    } else {
                        break;
                    }
                    i += rankOffsets[d];
                    j += fileOffsets[d];
                }
            }
        } else {
            for (int d = 0; d < rankOffsets.length; d++) {
                int i = p.getRank() + rankOffsets[d];
                int j = p.getFile() + fileOffsets[d];
                while (i >= 0 && i < Dama.BOARD_RANKS
                        && j >= 0 && j < Dama.BOARD_FILES) {
                    Position current = Position.generateFromRankAndFile(i, j);
                    if (dama.isEmpty(current)) {
                        result = Position.appendPositionsToArray(result, current);
                    } else {
                        break;
                    }
                    i += rankOffsets[d];
                    j += fileOffsets[d];
                }
            }
        }
        return result;
    }
}

