package am.aua.dama.ui;

import javax.swing.*;
import java.awt.*;


/**
 * class for board squares
 * contains the frontend based logic data to display squere
 * contains the piece icons
 * */
public class BoardSquare extends JButton {
    private static final Color dark = new Color(118, 150, 86);
    public static final Color light = new Color(255, 255, 255);

    private Color color;
    private int xCord;
    private int yCord;

    public BoardSquare(boolean isWhite, int x, int y) {
        if (isWhite) {
            this.color = light;
        } else {
            this.color = dark;
        }
        this.setBackground(this.color);
        this.xCord = x;
        this.yCord = y;
    }

    public int[] getCords() {
        return new int[] {this.xCord, this.yCord};
    }
    public void setPiece(String letter) {
        switch (letter) {
            case "Q":
                this.setIcon(new ImageIcon("src/am/aua/dama/gfx/QueenW.png"));
                break;
            case "q":
                this.setIcon(new ImageIcon("src/am/aua/dama/gfx/QueenB.png"));
                break;
            case "P":
                this.setIcon(new ImageIcon("src/am/aua/dama/gfx/PawnW copy.png"));
                break;
            case "p":
                this.setIcon(new ImageIcon("src/am/aua/dama/gfx/PawnB copy.png"));
                break;
        }
    }

    public void setPiece() {
        this.setIcon(null);
    }


    /**
     * method for higlighting the squeres - the positions a piece can go
     * */
    public void setHighlight(boolean isHighlighted) {
        if(isHighlighted) {
            this.setBackground(Color.red);
        }
        else {
            this.setBackground(color);
        }
    }

}
