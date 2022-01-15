import java.util.Scanner;
public class FourInALine {
    public static void main(String[] args) {
        boolean first;
        int time;
        Scanner scanner = new Scanner(System.in);
        Board playBoard = new Board();

        while (true) {
            System.out.print("Do you want to go first (y/n)? ");
            char in = scanner.nextLine().toLowerCase().charAt(0);
            if (in == 'y') {
                first = true;
                break;
            } else if (in == 'n') {
                first = false;
                break;
            } else {
                System.out.println("Not an option.");
            }
        }

        while (true) {
            System.out.print("How many seconds should the computer think? ");
            try {
                time = Integer.parseInt(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Not a number.");
            }
        }

        playBoard.printBoard();

        int[] userMove = null;

        if (first) {
            while (true) {
                System.out.print("Enter your move: ");
                String input = scanner.nextLine();
                userMove = moveToIndeces(input);
                if (userMove == null) {
                    System.out.println("That isn't a valid move!");
                } else if (!playBoard.testMove(userMove)) {
                    System.out.println("That isn't a valid move!");
                } else if (userMove[0] < 0 || userMove[0] > 7 || userMove[1] < 0 || userMove[1] > 7) {
                    System.out.println("That isn't a valid move!");
                } else {
                    break;
                }
            }

            playBoard.makeMove(userMove, 'o');

            playBoard.printBoard();
        }

        while (true) {
            //do computer move
            System.out.println("Calculating computer move...");
            Algorithm a = new Algorithm(playBoard, time);

            int[] compMove = a.getMove();
            playBoard.makeMove(compMove, 'x');

            playBoard.printBoard();

            System.out.println("Computer move: " + indecesToMove(compMove));

            if(playBoard.getWinner() != 0) {
                if (playBoard.getWinner() == 3) {
                    System.out.println("It's a Draw! Nobody Wins!");
                } else {
                    System.out.println(playBoard.getWinner() == 1 ? "You lost!" : "You won!");
                }
                return;
            }

            //do user move
            while (true) {
                System.out.print("Enter your move: ");
                String input = scanner.nextLine();
                userMove = moveToIndeces(input);
                if (userMove == null) {
                    System.out.println("That wasn't a valid move!");
                } else if (!playBoard.testMove(userMove)) {
                    System.out.println("That wasn't a valid move!");
                } else if (userMove[0] < 0 || userMove[0] > 7 || userMove[1] < 0 || userMove[1] > 7) {
                    System.out.println("That wasn't a valid move!");
                } else {
                    break;
                }
            }

            playBoard.makeMove(userMove, 'o');

            playBoard.printBoard();

            if(playBoard.getWinner() != 0) {
                if (playBoard.getWinner() == 3) {
                    System.out.println("It's a Draw! Nobody Wins!");
                } else {
                    System.out.println(playBoard.getWinner() == 1 ? "You lost!" : "You won!");
                }
                return;
            }
        }
    }

    public static int[] moveToIndeces(String input) {
        input = input.toLowerCase();
        if (input.length() != 2) {
            return null;
        }
        int[] ret = new int[2];
        switch (input.charAt(0)) {
            case 'a':
                ret[0] = 0;
                break;
            case 'b':
                ret[0] = 1;
                break;
            case 'c':
                ret[0] = 2;
                break;
            case 'd':
                ret[0] = 3;
                break;
            case 'e':
                ret[0] = 4;
                break;
            case 'f':
                ret[0] = 5;
                break;
            case 'g':
                ret[0] = 6;
                break;
            case 'h':
                ret[0] = 7;
                break;
            default:
                ret[0] = 500;
                break;
        }
        try {
            ret[1] = Integer.parseInt(input.substring(1)) - 1;
        } catch (Exception e) {
            return null;
        }
        return ret;
    }

    public static String indecesToMove(int[] move) {
        String ret = "";
        switch (move[0]) {
            case 0:
                ret = "a";
                break;
            case 1:
                ret = "b";
                break;
            case 2:
                ret = "c";
                break;
            case 3:
                ret = "d";
                break;
            case 4:
                ret = "e";
                break;
            case 5:
                ret = "f";
                break;
            case 6:
                ret = "g";
                break;
            case 7:
                ret = "h";
                break;
        }
        ret += Integer.toString(move[1] + 1);
        return ret;
    }
}
