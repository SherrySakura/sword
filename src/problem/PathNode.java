package problem;

public class PathNode {
    int row;
    int col;

    public PathNode(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "( " + row + " , " + col + " )";
    }
}
