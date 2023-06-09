package am.aua.dama.core;

import java.util.ArrayList;

public class Pawn extends Piece {


    public Pawn() {
        this(Dama.PieceColor.WHITE);
    }

    public Pawn(Dama.PieceColor color) {
        super(color);
    }

    public String toString() {
        if (this.getPieceColor() == Dama.PieceColor.WHITE)
            return "P";
        else
            return "p";
    }
    /**
     *allDestinations method make ArrayList of the moves Pawn has
     * @param Board current board
     * @param p the current position of the Pawn
     * @return Arraylist of allowed moves
     * */
    public ArrayList<Position> allDestinations(Dama Board, Position p) {
        if (Pawn.canEat(Board, p).size() == 0) {
            return Pawn.move(Board, p);
        }
        return Pawn.canEat(Board, p);
    }
    /**
     *canEat method make checks the variants figure can eat and makes ArrayList of moves
     * @param dama current board
     * @param p the current position of the figure
     * @return Arraylist of positons where it can go after eating the figure
     * */
    static ArrayList<Position> canEat(Dama dama, Position p) {
        ArrayList<Position> result = new ArrayList<>(0);
        Dama.PieceColor myColor = dama.getPieceAt(p).getPieceColor();

        if (myColor == Dama.PieceColor.WHITE) {
            int[] rankOffsets = {-1, 0, 0};
            int[] fileOffsets = {0, 1, -1};
            for (int d = 0; d < rankOffsets.length; d++) {
                int i = p.getRank() + rankOffsets[d];
                int j = p.getFile() + fileOffsets[d];
                if (i >= 0 && i < Dama.BOARD_RANKS
                        && j >= 0 && j < Dama.BOARD_FILES) {
                    Position current = Position.generateFromRankAndFile(i, j);
                    Position afterCurrent = Position.generateFromRankAndFile(i + rankOffsets[d],
                            j + fileOffsets[d]);
                    if (afterCurrent != null && afterCurrent.getRank() < Dama.BOARD_RANKS && afterCurrent.getRank() >= 0
                            && afterCurrent.getFile() < Dama.BOARD_FILES && afterCurrent.getFile() >= 0) {
                        if (dama.getPieceAt(current) != null && dama.getPieceAt(current).getPieceColor() == Dama.PieceColor.BLACK && dama.isEmpty(afterCurrent)) {
                            result = Position.appendPositionsToArray(result, afterCurrent);
                        }
                    }
                }
            }
        } else {
            int[] rankOffsets = {1, 0, 0};
            int[] fileOffsets = {0, 1, -1};

            for (int d = 0; d < rankOffsets.length; d++) {
                int i = p.getRank() + rankOffsets[d];
                int j = p.getFile() + fileOffsets[d];
                if (i >= 0 && i < Dama.BOARD_RANKS
                        && j >= 0 && j < Dama.BOARD_FILES) {
                    Position current = Position.generateFromRankAndFile(i, j);
                    Position afterCurrent = Position.generateFromRankAndFile(i + rankOffsets[d],
                            j + fileOffsets[d]);
                    if (afterCurrent != null && afterCurrent.getRank() < Dama.BOARD_RANKS && afterCurrent.getRank() >= 0
                            && afterCurrent.getFile() < Dama.BOARD_FILES && afterCurrent.getFile() >= 0) {
                        if (dama.getPieceAt(current) != null && dama.getPieceAt(current).getPieceColor() == Dama.PieceColor.WHITE && dama.isEmpty(afterCurrent)) {
                            result = Position.appendPositionsToArray(result, afterCurrent);
                        }
                    }
                }
            }

        }
        return result;
    }
    /**
     *Move method make to check if the move is inside the board and the destination is empty to go
     * @param dama current board
     * @param p the current position to look for changes
     * @return Arraylist of positons
     * */
    static ArrayList<Position> move(Dama dama, Position p) {
        //Accessed by Queens from the same package, does not need to be public.
        ArrayList<Position> result = new ArrayList<>(0);
        Dama.PieceColor myColor = dama.getPieceAt(p).getPieceColor();

        if (myColor == Dama.PieceColor.WHITE) {
            int[] rankOffsets = {-1, 0, 0};
            int[] fileOffsets = {0, 1, -1};
            for (int d = 0; d < rankOffsets.length; d++) {
                int i = p.getRank() + rankOffsets[d];
                int j = p.getFile() + fileOffsets[d];
                if (i >= 0 && i < Dama.BOARD_RANKS
                        && j >= 0 && j < Dama.BOARD_FILES) {
                    Position current = Position.generateFromRankAndFile(i, j);
                    if (dama.isEmpty(current)){
                        result = Position.appendPositionsToArray(result,current);
                    }
                }
            }
        } else {
            int[] rankOffsets = {1, 0, 0};
            int[] fileOffsets = {0, 1, -1};

            for (int d = 0; d < rankOffsets.length; d++) {
                int i = p.getRank() + rankOffsets[d];
                int j = p.getFile() + fileOffsets[d];
                if (i >= 0 && i < Dama.BOARD_RANKS
                        && j >= 0 && j < Dama.BOARD_FILES) {
                    Position current = Position.generateFromRankAndFile(i, j);
                    if (dama.isEmpty(current)) {
                        result = Position.appendPositionsToArray(result, current);
                    }
                }
            }
        }
        return result;
    }
}

