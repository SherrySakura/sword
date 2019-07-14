package problem;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ProblemRetrospective {

    /**
     * 设计一个
     * @param matrix
     * @param destPath
     * @return
     * @throws Exception
     */
    public static List<PathNode> getPathInMatrix(char[][] matrix, char[] destPath) throws Exception {
        if (matrix == null || destPath == null){
            throw new Exception("路径输入错误");
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean visited[][] = new boolean[rows][cols];
        Stack<PathNode> path = new Stack<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (getPath(matrix, row, col, visited, destPath, path)){
                    List<PathNode> pathNodeList = new ArrayList<>();
                    Stack<PathNode> temp = new Stack<>();
                    int pathSize = path.size();
                    for (int i = 0; i < pathSize; i++) {
                        temp.push(path.pop());
                    }
                    int tempSize = temp.size();
                    for (int i = 0; i < tempSize; i++) {
                        pathNodeList.add(temp.pop());
                    }
                    return pathNodeList;
                }
            }
        }
        return null;
    }

    private static boolean getPath(char[][] matrix, int row, int col, boolean[][] visited, char[] destPath, Stack<PathNode> path){
        boolean hasPath = false;
        int rows = matrix.length;
        int cols = matrix[0].length;
        if (path.size() == destPath.length){
            return true;
        }
        if (row >= 0 && row < rows && col >= 0 && col < cols && matrix[row][col] == destPath[path.size()] && visited[row][col] == false){
            PathNode p = new PathNode(row, col);
            path.push(p);
            visited[row][col] = true;
            hasPath = getPath(matrix, row, col - 1, visited, destPath, path) || getPath(matrix, row - 1, col, visited, destPath, path) ||
                    getPath(matrix, row, col + 1, visited, destPath, path) || getPath(matrix, row + 1, col, visited, destPath, path);
            if (!hasPath){
                path.pop();
                visited[row][col] = false;
            }
        }
        return hasPath;
    }

    public static List<PathNode> getRobotMoveRange(int threshold, int rows, int cols){
        if (threshold <= 0 || rows <= 0 || cols <= 0){
            return null;
        }
        boolean[][] visited = new boolean[rows][cols];
        List<PathNode> pathRange = new ArrayList<>();
        getNextRange(threshold, rows, cols, 0, 0, visited, pathRange);
        return pathRange;
    }

    private static void getNextRange(int threshold, int rows, int cols, int row, int col, boolean[][] visited, List<PathNode> pathRange){
        if (check(threshold, rows, cols, row, col, visited)){
            visited[row][col] = true;
            pathRange.add(new PathNode(row, col));
            getNextRange(threshold, rows, cols, row - 1, col, visited, pathRange);
            getNextRange(threshold, rows, cols, row, col - 1, visited, pathRange);
            getNextRange(threshold, rows, cols, row + 1, col, visited ,pathRange);
            getNextRange(threshold, rows, cols, row, col + 1, visited, pathRange);
        }
    }

    /**
     * 判断是否能够进入这个格子
     * @param threshold
     * @param rows
     * @param cols
     * @param row
     * @param col
     * @param visited
     * @return
     */
    private static boolean check(int threshold, int rows, int cols, int row, int col, boolean[][] visited){
        if (row >= 0 && col >= 0 && row < rows && col < cols && getDigitalSum(row) + getDigitalSum(col) <= threshold && visited[row][col] == false){
            return true;
        }
        return false;
    }

    /**
     * 获得一个数字的所有数位之和
     * @param number
     * @return
     */
    private static int getDigitalSum(int number){
        int sum = 0;
        while (number > 0){
            sum = sum + number % 10;
            number = number / 10;
        }
        return sum;
    }
}
