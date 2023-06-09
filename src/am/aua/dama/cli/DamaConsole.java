package am.aua.dama.cli;

import am.aua.dama.core.Dama;
import am.aua.dama.core.Move;
import am.aua.dama.core.Position;


import java.util.ArrayList;
import java.util.Scanner;

public class DamaConsole {
    private Dama game;

    public void play() {
        Scanner sc = new Scanner(System.in);
        String inputLine;

        try {
            this.game = new Dama();
        } catch (Exception e) {
            System.out.println(e);
        }


        print();

        while (!game.isGameOver()) {
            if (game.getTurn() == Dama.PieceColor.WHITE)
                System.out.println("White's move: ");
            else
                System.out.println("Black's move: ");

            inputLine = sc.nextLine();
            String[] input = inputLine.split(" ");
            Position p1 = null, p2 = null;

            if (input.length >= 1) {
                p1 = Position.generateFromString(input[0]);

                if (p1 == null || game.getPieceAt(p1) == null) {
                    System.out.println("Invalid position. Please try again.");
                    continue;
                }

                if (input.length == 1) {
                    if (game.getPieceAt(p1).getPieceColor() != game.getTurn())
                        System.out.println("That piece belongs to the opponent.");
                    print(p1);
                }
                else if (input.length == 2) {
                    if (game.getPieceAt(p1).getPieceColor() != game.getTurn()) {
                        System.out.println("That piece belongs to the opponent.");
                        continue;
                    }

                    boolean success = false;

                    p2 = Position.generateFromString(input[1]);

                    if (p2 != null) {
                        try {
                            Move m = new Move(p1, p2);
                            Dama d = new Dama();
                            ArrayList<Position> arr = d.mustEatPositions(game.getBoard().clone());
                            if (arr.size() != 0) {
                                for (int i = 0; i < arr.size(); i++) {
                                    if (arr.get(i).getRank() == p1.getRank() && arr.get(i).getFile() == p1.getFile()) {
                                        success = game.performMove(m);
                                    } else {
                                        continue;
                                    }
                                }
                            } else {
                                success = game.performMove(m);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }


                    }
                    if (!success)
                        System.out.println("Invalid move. Please try again.");
                    print();
                }
            }
        }
    }
    /**
     *Print method make for highlighting the reachable positions
     * @param origin the origin of the move
     * @return Arraylist of hightlighted positons where figure can go
     * */
    public void print(Position origin) {
        ArrayList<Position> highlights = null;
        if (origin != null)
            highlights = game.reachableFrom(origin);

        for (int i = 0; i < Dama.BOARD_RANKS; i++) {
            System.out.print((Dama.BOARD_RANKS - i) + " ");

            for (int j = 0; j < Dama.BOARD_FILES; j++) {
                boolean isHighlighted = false;

                if (origin != null
                        && origin.getRank() == i && origin.getFile() == j)
                    isHighlighted = true;

                for (int k = 0; highlights != null && k < highlights.size(); k++)
                    if (highlights.get(k).getRank() == i
                            && highlights.get(k).getFile() == j)
                    {
                        isHighlighted = true;
                        break;
                    }

                if (isHighlighted)
                    System.out.print("\u001b[31m");

                System.out.print("[");

                Position current = Position.generateFromRankAndFile(i, j);
                if (game.isEmpty(current))
                    System.out.print(" ");
                else
                    System.out.print(game.getPieceAt(current));

                System.out.print("]");

                if (isHighlighted)
                    System.out.print("\u001b[0m");
            }
            System.out.println();
        }
        System.out.println("   A  B  C  D  E  F  G  H ");
        System.out.println();
    }

    public void print() {
        print(null);
    }
}
