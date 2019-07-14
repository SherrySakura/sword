package problem;

public class ProblemBit {

    public static int numOf1(int number){
        int count = 0;
        while (number != 0){
            ++count;
            number = (number - 1) & number;
        }
        return count;
    }

    /**
     * 计算乘方，不使用任何库函数，且使用优化的公式进行求解
     * @param base
     * @param exp
     * @return
     */
    public static double power(double base, int exp){
        if (exp < 0){
            exp = 0 - exp;
            double result = powerCore(base, exp);
            return 1 / result;
        }else {
            return powerCore(base, exp);
        }
    }

    private static double powerCore(double base, int exp){
        if (exp == 0){
            return 1;
        }
        if (exp == 1){
            return base;
        }
        double result = powerCore(base, exp >> 1);
        result = result * result;
        if ((exp & 0x1) == 0x01){
            result = result * base;
        }
        return  result;
    }
}
