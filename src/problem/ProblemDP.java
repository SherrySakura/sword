package problem;

public class ProblemDP {

    public static int maxOfCuttingWithDP(int length){
        if (length < 2){
            return 0;
        }
        if (length == 2){
            return 1;
        }
        if (length == 3){
            return 2;
        }
        int[] res = new int[length + 1];
        res[0] = 0;
        res[1] = 1;
        res[2] = 2;
        res[3] = 3;
        int max = 0;
        /**
         * i在此循环中即为公式中的n，当走完循环时，res[i]即为所要求的的f(n)
         */
        for (int i = 4; i <= length; i++) {
            max = 0;
            /**
             * 由于res[j]即为公式中的f(i)，而根据对称性，j不能超过i/2，不然和j-i对称了
             */
            for (int j = 1; j <= i / 2; j++) {
                int mun = res[j] * res[i - j];//f(n) = f(i)*f(n-i)
                if (max < mun){
                    max = mun;
                }
                res[i] = max;//计算出每一个f(n)的最大值
            }
        }
        return res[length];
    }
}
