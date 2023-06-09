
/**
 * The main window of Dama game.
 * @author Vahe Aleksanyan <a href="mailto:vahe_aleksanyan@edu.aua.am">vahe_aleksanyan@edu.aua.am</a>
 * @author Rafik Gasparyan <a href="mailto:rafik_gasparyan@edu.aua.am">rafik_gasparyan@edu.aua.am</a>
 * @author Arman Hovhannisyan <a href="mailto:arman_hovhannisyan@edu.aua.am">arman_hovhannisyan@edu.aua.am</a>
 * @version 6.0
 * @since 1.0
 */

package am.aua.dama.ui;
import am.aua.dama.core.Dama;
import am.aua.dama.core.Position;
import am.aua.dama.core.Move;
import am.aua.dama.core.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * class for window
 * */
public class DamaUI extends JFrame {
    private Dama game;
    private BoardSquare[][] board;
    private Position origin;
    private JLabel turn;
    private JButton restartButton;


    /**
     * contractor - initializes the main window, the 8x8 board on it, keeps the state of game,
     * label for showing turn and win, restart button
     * */
    public DamaUI() {
        this.setVisible(true);

        try {
            game = new Dama();
            board = new BoardSquare[Dama.BOARD_RANKS][Dama.BOARD_FILES];
        } catch(Exception e) {
            e.printStackTrace();
        }

        turn = new JLabel(game.getTurn().toString() + "'s" + " turn");
        turn.setFont(new Font("Verdana",Font.BOLD,20));
        this.setSize(new Dimension(630,700));
        this.setTitle("Dama");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout());
        JPanel panel = new JPanel();
        boolean isWhite = true;
        panel.setPreferredSize(new Dimension(600,600));
        restartButton = new JButton();
        restartButton.setText("Restart");
        restartButton.setSize(50, 25);
        setLocationRelativeTo(null);
        restartButton.addActionListener(new ActionListener() {
            @Override
            /**
             * event listener when user hits button on board
             * */
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == restartButton) {
                    try {
                     panel.setVisible(false);
                     game = new Dama();
                     board = new BoardSquare[Dama.BOARD_RANKS][Dama.BOARD_FILES];
                        clearLastWindow();
                     DamaUI damaUi = new DamaUI();

                     damaUi.setVisible(true);
                    } catch(Exception k) {
                        k.printStackTrace();
                    }
                }
            }
        });
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new BoardSquare(isWhite, i, j);
                isWhite = !isWhite;
                board[i][j].setPreferredSize(new Dimension(70,70));
                int x = i;
                int y = j;
                board[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        boardClicked(board[x][y].getCords());
                    }
                });

                panel.add(board[i][j]);
            }

            isWhite = !isWhite;
        }

        updatePieces();

        JPanel j = new JPanel();
        j.add(panel);
        j.add(turn);
        j.add(restartButton);
        this.setContentPane(j);


        this.setVisible(true);


    }
    private void clearLastWindow()
    {
        this.dispose();
    }

    /**
     * contains the logic of the click of button, keeps track of the positions a piece can go,
     * the positions where it can go by eat-jumping
     * */
    private void boardClicked(int[] coordinates) {

        if (origin == null) {
            if(game.getBoard()[coordinates[0]][coordinates[1]] != null
                    && game.getBoard()[coordinates[0]][coordinates[1]].getPieceColor() != game.getTurn()) {
                return;
            }
            System.out.println(coordinates[0] + " " + coordinates[1]);
            origin = new Position(coordinates[0], coordinates[1]);
            if(game.getBoard()[origin.getRank()][origin.getFile()] != null) {
                ArrayList<Position> available = game.reachableFrom(origin);
                ArrayList<Position> arr = game.mustEatPositions(game.getBoard());
                if (arr.size() != 0) {
                    if (arr.contains(origin)) {
                        for (Position p : available) {
                            board[p.getRank()][p.getFile()].setHighlight(true);
                        }
                    }
                } else {
                    if (available != null) {
                        for (Position p : available) {
                            board[p.getRank()][p.getFile()].setHighlight(true);
                        }
                    }
                }
            }
        } else {
            System.out.println(coordinates[0] + " " + coordinates[1]);
            Position destination = new Position(coordinates[0], coordinates[1]);
            ArrayList<Position> arr = game.mustEatPositions(game.getBoard().clone());
            if (game.reachableFrom(origin) != null && game.reachableFrom(origin).contains(destination)) {
                if (arr.size() != 0) {
                    if (arr.contains(origin)) {
                        game.performMove(new Move(origin, destination));
                    }
                } else {
                    game.performMove(new Move(origin, destination));
                }

            }

            for (BoardSquare[] row : board) {
                for (BoardSquare b : row) {
                    b.setHighlight(false);
                }
            }
            origin = null;
        }
        updatePieces();


    }

    /**
     * updated the board, turn and checks for the winner
     * */
    private void updatePieces()
    {
        Piece[][] m = game.getBoard();
        for(int i = 0;i < m.length;i++) {
            for(int j = 0;j < m[i].length;j++) {
                if(m[i][j] != null) {
                    board[i][j].setPiece(m[i][j].toString());
                } else {
                    board[i][j].setPiece();
                }
            }
        }
        if (!game.isGameOver()) {
            turn.setText(game.getTurn().toString() + "'s" + " turn");
        } else {
            if (Dama.isWhiteWin = true) {
                turn.setText("Game is over: Whites won");
            } else {
                turn.setText("Game is over: Blacks won");
            }
        }

    }
}


