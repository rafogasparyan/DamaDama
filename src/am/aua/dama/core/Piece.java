package am.aua.dama.core;

import java.util.ArrayList;

public abstract class Piece implements Cloneable {
    private Dama.PieceColor color;

    public Piece(Dama.PieceColor color) {
        this.color = color;
    }

    public Piece() {
        this(Dama.PieceColor.WHITE);
    }

    public abstract ArrayList<Position> allDestinations(Dama dama, Position p);

    public final Dama.PieceColor getPieceColor() {
        return this.color;
    }

    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
