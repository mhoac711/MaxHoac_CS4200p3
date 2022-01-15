import java.util.ArrayList;

public class Algorithm {
    private Board board;
    private int[] calculatedMove;
    private long maxRuntime;

    public Algorithm(Board b, int t) {
        board = b;
        maxRuntime = System.currentTimeMillis() + t * 1000;
        for(int i = 2; i < 65; i+=2) {
            alphaBetaPrune(board, true, Integer.MIN_VALUE, Integer.MAX_VALUE, i);
        }
    }

    public int[] getMove() {
        return calculatedMove;
    }

    private int alphaBetaPrune(Board b, boolean maxPlayer, int alpha, int beta, int depth) {
        //Cutoff test, non-threaded
        if(System.currentTimeMillis() >= maxRuntime || depth == 0) {
            return b.getEvaluationValue();
        }

        ArrayList<Board> children = populateChildren(b, maxPlayer ? 'x' : 'o');

        int score = 0;
        if (maxPlayer) {
            for (Board child : children) {
                score = alphaBetaPrune(child, false, alpha, beta, depth - 1);
                if (score > alpha) {
                    alpha = score;
                } else if (alpha >= beta) {
                    break;
                }
            }
            return alpha;
        } else {
            for (Board child : children) {
                score = alphaBetaPrune(child, true, alpha, beta, depth - 1);
                if (score < beta) {
                    calculatedMove = child.getLastMove();
                    beta = score;
                } else if (alpha <= beta) {
                    break;
                }
            }
            return beta;
        }

    }

    private ArrayList<Board> populateChildren(Board b, char token) {
        ArrayList<Board> children = new ArrayList<Board>();
        char[][] parentBoard = b.getBoard();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (parentBoard[i][j] == '-') {
                    char[][] childBoard = new char[8][8];
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            childBoard[k][l] = parentBoard[k][l];
                        }
                    }
                    childBoard[i][j] = token;
                    int[] move = new int[2];
                    move[0] = i;
                    move[1] = j;
                    children.add(new Board(childBoard, move, b.getMoveCount() + 1));
                }
            }
        }

        return children;
    }
}
