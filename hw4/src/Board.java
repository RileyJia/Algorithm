import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;


/**
 * Created by jia on 8/22/17.
 */
public class Board {
    private final int[] blocks;
    private final int N;
    private int blank = 0;
    private int hammingCount = 0;
    private int manhattanCount = 0;



    // construct a board from an n-by-n array of blocks
    private Board(Board b) {
        N = b.N;
        blocks = b.blocks.clone();
        hammingCount = b.hammingCount;
        manhattanCount = b.manhattanCount;
    }

    public Board(int[][] blocks) {
        N = blocks[0].length;
        this.blocks = new int[N * N];
        hammingCount = 0;
        manhattanCount = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i * N + j] = blocks[i][j];
            }
        }
        for (int i = 0; i < N * N; i++) {
            if (this.blocks[i] != i + 1 && this.blocks[i] != 0) hammingCount++;
            if (this.blocks[i] != 0) {
                int col = Math.abs((this.blocks[i] - 1) / N - i / N);
                int row = Math.abs((this.blocks[i] - 1) % N - i % N);
                if (col != 0 || row != 0) manhattanCount += col + row;
            }
        }
    }
    // (where blocks[i][j] = block in row i, column j)

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        return this.hammingCount;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return this.manhattanCount;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (hamming() == 0 || manhattan() == 0);
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board twinedB = new Board(this);
        if (blocks[ N * N - 1] != 0 && blocks[ N * N - 2] != 0) {
            exchg(twinedB, N * N - 1, N * N - 2);
        } else exchg(twinedB, 0, 1);
        return twinedB;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() == this.getClass()) {
            Board yBoard = (Board) y;
            if (yBoard.dimension() == this.dimension()) {
                for (int i = 0; i < this.dimension() * this.dimension(); i++) {
                    if (yBoard.blocks[i] != this.blocks[i])
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Board neibor;
        Queue<Board> queue = new Queue<>();

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                blank = i;
                break;
            }
        }


        if (blank >= N) {
            neibor = new Board(this);
            exchg(neibor, blank, blank - N);
            reCount(neibor, blank - N,blank, this.hammingCount, this.manhattanCount);
            queue.enqueue(neibor);

        }

        if (blank < (N * N - N)) {
            neibor = new Board(this);
            exchg(neibor, blank, blank + N);
            reCount(neibor, blank + N,blank, this.hammingCount, this.manhattanCount);
            queue.enqueue(neibor);
        }

        if (blank % N != 0) {
            neibor = new Board(this);
            exchg(neibor, blank, blank - 1);
            reCount(neibor, blank - 1,blank, this.hammingCount, this.manhattanCount);
            queue.enqueue(neibor);
        }

        if ((blank + 1) % N != 0) {
            neibor = new Board(this);
            exchg(neibor, blank, blank + 1);
            reCount(neibor, blank + 1,blank, this.hammingCount, this.manhattanCount);
            queue.enqueue(neibor);
        }

        return queue;
    }


    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N);
        s.append("\n");
        for (int i = 0; i < N; i++) {
            s.append(" ");
            for (int j = 0; j < N; j++) {
                s.append(blocks[i * N + j]);
                if (j != N - 1){
                    s.append("   ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    private void exchg (Board b, int x, int y) {
        int temp = b.blocks[x];
        b.blocks[x] = b.blocks[y];
        b.blocks[y] = temp;
    }

    private void reCount (Board b, int x, int y, int orignH, int orignM){
        int colDec, rowDec, colInc, rowInc;
        int N = this.N;

        if (b.blocks[y] == y + 1) b.hammingCount = orignH - 1;
        if (b.blocks[y] == x + 1) b.hammingCount = orignH + 1;


        colDec = Math.abs((b.blocks[y] - 1) / N - x / N);
        rowDec = Math.abs((b.blocks[y] - 1) % N - x % N);

        colInc = Math.abs((b.blocks[y] - 1) / N - y / N);
        rowInc = Math.abs((b.blocks[y] - 1) % N - y % N);

        b.manhattanCount =  orignM + colInc + rowInc - colDec - rowDec;

    }

    // unit tests (not graded)
    public static void main(String[] args) {
    // create initial board from file
        // In in = new In(args[0]);
        In in = new In("8puzzle/puzzle3x3-10.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Board test = new Board(blocks);
        System.out.println(initial.equals(test));
        System.out.println("hamming = " + initial.hamming());
        System.out.println("manhattan = " + initial.manhattan());
        System.out.println(initial.toString());

        for(Board b : initial.neighbors()){
            System.out.println(b.toString());
            System.out.println("hamming: " + b.hammingCount);
            System.out.println("manhattan: " + b.manhattanCount);
        }
    }
}
