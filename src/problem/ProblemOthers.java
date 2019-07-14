package problem;

public class ProblemOthers {

    /**
     * 写一个函数，求两个数的和，要求在函数体内不得使用+-x/四则运算符号
     * @param number1
     * @param number2
     * @return
     */
    public static int add(int number1, int number2){
        int sum, carry;
        do {
            sum = number1 ^ number2;
            carry = (number1 & number2) << 1;
            number1 = sum;
            number2 = carry;
        }while (number2 != 0);
        return number1;
    }

    /**
     * 给定一个数组$A[0,1,...,n-1]$，请构建一个数组$B[0,1,...n-1]$，
     * 其中$B$中的元素$B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]$，不能使用除法
     * @param array
     * @return
     */
    public static int[] multipyMatrix(int[] array){
        int lengthOfA = array.length;
        int[] matrixOfB = new int[lengthOfA];
        matrixOfB[0] = 1;
        for (int i = 1; i < lengthOfA; i++) {
            matrixOfB[i] = matrixOfB[i - 1] * array[i - 1];
        }
        int temp = 1;
        for (int i = lengthOfA - 2; i >= 0; i--) {
            temp *= array[i + 1];
            matrixOfB[i] *= temp;
        }
        return matrixOfB;
    }
}
