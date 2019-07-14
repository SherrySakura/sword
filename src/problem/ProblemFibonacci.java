package problem;

public class ProblemFibonacci {

    /**
     * 用迭代实现计算斐波那契数列
     * @param n
     * @return
     */
    public static long fibonacciWithLoop(int n){
        long[] result = new long[]{0, 1};
        if (n < 2){
            return result[n];
        }
        long first = 0;
        long second = 1;
        long fiN = 0;
        for (int i = 2; i <= n; i++) {
            fiN = first + second;
            first = second;
            second = fiN;
        }
        return fiN;
    }

    /**
     * 用递归实现计算斐波那契数列
     * @param n
     * @return
     */
    public static long fibonacciWithRev(int n){
        if (n <= 0){
            return 0;
        }
        if (n == 1){
            return 1;
        }
        return fibonacciWithRev(n - 1) + fibonacciWithRev(n - 2);
    }


}
