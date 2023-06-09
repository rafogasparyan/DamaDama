package am.aua.dama.core;
import java.util.ArrayList;
public class Dama implements Cloneable {
    public enum PieceColor {WHITE, BLACK}

    public static final int BOARD_RANKS = 8;
    public static final int BOARD_FILES = 8;
    public static boolean isBlackWin;
    public static boolean isWhiteWin;

    private Piece[][] board;
    private int numberOfMoves;


    public Dama() throws IllegalArrangementException {
        //has to declare even though that exception can never happen here
        this("--------" +
             "pppppppp" +
             "pppppppp" +
             "--------" +
             "--------" +
             "PPPPPPPP" +
             "PPPPPPPP" +
             "--------",
             PieceColor.WHITE);
    }

    public static void verifyArrangement(String s) throws IllegalArrangementException {
        if (s.length() != 64)
            throw new IllegalArrangementException("The length of the arrangement must be 64");

    }

    public Dama(String arrangement, PieceColor turn) throws IllegalArrangementException {
        verifyArrangement(arrangement);
        this.numberOfMoves = turn.ordinal();
        this.board = new Piece[BOARD_RANKS][BOARD_FILES];

        for (int i = 0; i < arrangement.length(); i++) {
            switch (arrangement.charAt(i)) {
                case 'Q':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Queen(PieceColor.WHITE);
                    break;
                case 'q':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Queen(PieceColor.BLACK);
                    break;
                case 'P':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn(PieceColor.WHITE);
                    break;
                case 'p':
                    this.board[i/BOARD_RANKS][i%BOARD_FILES] = new Pawn(PieceColor.BLACK);
                    break;
            }
        }
    }

    public Piece[][] getBoard() {
        Piece[][] boardCopy = new Piece[BOARD_RANKS][BOARD_FILES];
        for (int i = 0; i < BOARD_RANKS; i++)
            for (int j = 0; j < BOARD_FILES; j++)
                if (this.board[i][j] != null)
                    boardCopy[i][j] = (Piece) this.board[i][j].clone();
        return boardCopy;
    }

    public Dama clone() {
        try {
            Dama copy = (Dama) super.clone();
            copy.board = this.getBoard();
            return copy;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public PieceColor getTurn() {
        return PieceColor.values()[this.numberOfMoves % 2];
    }

    public boolean isGameOver() {
        boolean isThereWhitePiece = false;
        boolean isThereBlackPiece = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null && board[i][j].getPieceColor() == PieceColor.WHITE) {
                    isThereWhitePiece = true;
                } else if (board[i][j] != null && board[i][j].getPieceColor() == PieceColor.BLACK) {
                    isThereBlackPiece = true;
                }
            }
        }
        if (isThereWhitePiece == false  ) {
            isBlackWin = false;
            return true;
        } else if (isThereBlackPiece == false) {
            isWhiteWin = true;
            return true;
        }
        return false;
    }

    public boolean isEmpty(Position p) {
        return this.board[p.getRank()][p.getFile()] == null;
    }

    public Piece getPieceAt(Position p) {
        return this.board[p.getRank()][p.getFile()];
    }

    public ArrayList<Position> reachableFrom(Position origin) {
        if (origin == null || this.isEmpty(origin))
            return null;
        return board[origin.getRank()][origin.getFile()].allDestinations(this, origin);
    }



    public ArrayList<Position> mustEatPositions(Piece[][] board) {
        ArrayList<Position> result = new ArrayList<>(0);
        PieceColor color;
        if (numberOfMoves % 2 == 0) {
            color = PieceColor.WHITE;
        } else color = PieceColor.BLACK;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != null && board[i][j].getPieceColor() == color) {
                    Position pos = new Position(i, j);
                    if (this.getPieceAt(pos) != null && (this.getPieceAt(pos).toString() == "Q" || this.getPieceAt(pos).toString() == "q")) {
                        if (Queen.canEat(this, pos).size() != 0) {
                            result = Position.appendPositionsToArray(result, pos);
                        }
                    } else if (this.getPieceAt(pos) != null) {
                        if (Pawn.canEat(this, pos).size() != 0) {
                            result = Position.appendPositionsToArray(result, pos);
                        }
                    }
                }
            }
        }
        return result;
    }




    public boolean performMove(Move m) {
        Position o = m.getOrigin();
        Position d = m.getDestination();
        if (this.getPieceAt(o).getPieceColor() != this.getTurn())
            return false;

        ArrayList<Position> reachable = this.reachableFrom(o);


        boolean canQueenJump = Queen.canEat(this, o).size() != 0;

        for (int i = 0; i < reachable.size(); i++)
            if (d.getRank() == reachable.get(i).getRank()
                    && d.getFile() == reachable.get(i).getFile()) {
                this.board[d.getRank()][d.getFile()] = this.board[o.getRank()][o.getFile()];
                this.board[o.getRank()][o.getFile()] = null;
                int k;
                int a = o.getRank() - d.getRank();
                int b = o.getFile() - d.getFile();



                if (a == 0) {
                    if (b < 0) {
                        for (int j = o.getFile() + 1; j < d.getFile(); j++) {
                            this.board[o.getRank()][j] = null;
                        }
                    } else {
                        for (int j = d.getFile() + 1; j < o.getFile(); j++) {
                            this.board[o.getRank()][j] = null;
                        }
                    }

                } else {
                    if (a < 0) {
                        for (int j = o.getRank() + 1; j < d.getRank(); j++) {
                            this.board[j][o.getFile()] = null;
                        }
                    } else {
                        for (int j = d.getRank() + 1; j < o.getRank(); j++) {
                            this.board[j][o.getFile()] = null;
                        }
                    }
                }
                if (this.getPieceAt(d).toString() == "Q" || this.getPieceAt(d).toString() == "q") {
                    if (Queen.canEat(this, d).size() != 0 && canQueenJump) {
                        this.numberOfMoves--;
                    }
                } else {
                    if (Pawn.canEat(this, d).size() != 0 &&
                            (Math.pow(o.getFile() - d.getFile(), 2) + Math.pow(o.getRank() - d.getRank(), 2)) != 1) {
                        this.numberOfMoves--;
                    }
                }
                this.numberOfMoves++;
                becomeQueen(m.getDestination());
                return true;
            }
        return false;
    }

    public void becomeQueen(Position position) {
        if (getPieceAt(position) != null && getPieceAt(position).getClass().getName() == "am.aua.dama.core.Pawn") {
            if (getPieceAt(position).getPieceColor() == PieceColor.WHITE && position.getRank() == 0) {
                this.board[position.getRank()][position.getFile()] = new Queen(PieceColor.WHITE);
            }
            if (getPieceAt(position).getPieceColor() == PieceColor.BLACK && position.getRank() == 7) {
                this.board[position.getRank()][position.getFile()] = new Queen(PieceColor.BLACK);
            }
        }
    }

}
