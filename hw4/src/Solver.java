import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

/**
 * Created by jia on 8/22/17.
 */
public class Solver {

    private Node selectedMove;
    private Node selectedTwinMove;
    private boolean solved;



    private class Node {
        private int move;
        private Board board;
        private Node pre;
        private int manhattanC;

        public Node(Board b) {
            board = b;
            move = 0;
            pre = null;
            manhattanC = b.manhattan();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node node = new Node(initial);
        Node twinNode = new Node(initial.twin());

        PriorityKey key = new PriorityKey();

        MinPQ<Node> min = new MinPQ<>(key);
        MinPQ<Node> twinMin = new MinPQ<>(key);

        selectedMove = node;
        selectedTwinMove = twinNode;

        solved = selectedMove.board.isGoal();

        while (!solved && !selectedTwinMove.board.isGoal()) {

            for (Board b : selectedMove.board.neighbors()){
                if ((selectedMove.pre == null) || (!b.equals(selectedMove.pre.board))) {
                    Node bNode = new Node(b);
                    bNode.move = selectedMove.move + 1;
                    bNode.pre = selectedMove;
                    min.insert(bNode);
                }
            }

           for (Board twinb : selectedTwinMove.board.neighbors()) {
                if ((selectedTwinMove.pre == null) || (!twinb.equals(selectedTwinMove.pre.board))) {
                    Node twinbNode = new Node(twinb);
                    twinbNode.move = selectedTwinMove.move + 1;
                    twinbNode.pre = selectedTwinMove;
                    twinMin.insert(twinbNode);
                }
            }

            selectedMove = min.delMin();
            selectedTwinMove = twinMin.delMin();

            solved = selectedMove.board.isGoal();
        }
    }

    private class PriorityKey implements Comparator<Node> {

        @Override
        public int compare(Node d1, Node d2) {
            int priority1 = d1.manhattanC + d1.move;
            int priority2 = d2.manhattanC + d2.move;

            if (priority1 > priority2) return 1;
            else if (priority1 < priority2) return -1;
            else return 0;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return  solved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solved) return selectedMove.move;
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        else {
            Stack<Board> finalSelect = new Stack<>();
            for (Node i = selectedMove; i != null; i = i.pre){
                finalSelect.push(i.board);
            }
            return finalSelect;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        //In in = new In("8puzzle/puzzle4x4-unsolvable.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible " + solver.moves());
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}