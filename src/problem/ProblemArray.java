package problem;

import java.util.*;

/**
 *
 */
public class ProblemArray {

    /**
     * <b>找出数组中重复的数字</b>
     * 在一个长度为n的数组里的所有数字都在0~n-1范围内，数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字
     * 重复了几次，请找出数组中任意一个重复的数字，例如，若数组为{2，3，1，0，2，5，3}，那么对应的输出是重复的数字2，3
     * 复杂度O(n)
     * @param numbers
     * @param length
     * @return
     */
    public static Integer[] duplicate(int[] numbers, int length){
        List<Integer> dup = new ArrayList<>();
        //边界检查
        if (numbers == null || numbers.length <= 0){
            return null;
        }
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < 0 || numbers[i] >= length){
                return null;
            }
        }
        for (int i = 0; i < numbers.length; i++) {
            while (numbers[i] != i){
                if (numbers[i] == numbers[numbers[i]]){
                    dup.add(numbers[i]);
                    break;
                }else {
                    int temp = numbers[numbers[i]];
                    numbers[numbers[i]] = numbers[i];
                    numbers[i] = temp;
                }
            }
        }
        Integer[] res = new Integer[dup.size()];
        res = dup.toArray(res);
        return res;
    }

    /**
     * 在一个长度为n+1的数组里面的所有数字都在1~n的范围内，所以数组中至少有一个数字是重复的，请找出数组中任意一个重复的数字，但不能修改数组
     * 如输入的数组为{2，3，5，4，3，2，6，7}，则输出重复数字为2，3
     * 解法1：
     * 采用HashSet
     * @param numbers
     * @param range
     * @return
     */
    public static Integer[] duplicateWithoutModify(int[] numbers, int range){
        HashSet set = new HashSet();
        List<Integer> list = new ArrayList<>();
        if (numbers == null || numbers.length <= 0){
            return null;
        }
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < 0 || numbers[i] > range){
                return null;
            }
        }
        for (int i = 0; i < numbers.length; i++) {
            if (set.contains(numbers[i])){
                list.add(numbers[i]);
            }else {
                set.add(numbers[i]);
            }
        }
        Integer[] res = new Integer[list.size()];
        res = list.toArray(res);
        return res;
    }

    /**
     * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列也是按照从上到下递增的顺序排序，完成函数，输入一个二维数组和一个整数，
     * 判断这个二维数组中是否存在这个整数，并返回该整数的下标位置
     * @param matrix
     * @param row 二维数组的行数
     * @param col 二维数组的列数
     * @param target
     * @return
     */
    public static Integer[] findInTwoDimensionalArray(int[][] matrix, int row, int col, int target){
        if (matrix == null || row <= 0 || col <= 0){
            return null;
        }
        Integer[] res = new Integer[2];
        int r = 0;
        int c = col - 1;
        while (r < row && c < col){
            if (matrix[r][c] == target){
                res[0] = r;
                res[1] = c;
                break;
            }else {
                if (matrix[r][c] > target){
                    c--;
                }else {
                    r++;
                }
            }
        }
        return res;
    }

    /**
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分。
     * @param ori
     * @return
     */
    public static int[] reorderOddEven(int[] ori){
        int start = 0;
        int end = ori.length - 1;
        while (start < end){
            while (start < end && (ori[start] & 0x01) == 1){
                start++;
            }
            while (start < end && (ori[end] & 0x01) == 0){
                end--;
            }
            if (start < end){
                int temp = ori[start];
                ori[start] = ori[end];
                ori[end] = temp;
            }
        }
        return ori;
    }

    /**
     * 输入一个矩阵，按照从里向外以顺时针的方式依次打印每一个数字
     *
     * 例如输入一下矩阵：
     *
     * 1	2	3	4
     *
     * 5	6	7	8
     *
     * 9	10  11  12
     *
     * 13   14  15  16
     *
     * 打印输出：1 2 3 4 8 12 16 45 14 13 6 5 6 7 11 10
     * @param nums
     */
    public static void printMatrixWithCircle(int[][] nums){
        int start = 0;
        if (nums == null || nums.length == 0){
            return;
        }
        int row = nums.length;
        int col = nums[0].length;
        while (row > start * 2 && col > start * 2){
            printCircle(nums, start, row, col);
            start++;
        }
    }

    private static void printCircle(int[][] nums, int start, int row, int col) {
        int endX = row - 1 - start;
        int endY = col - 1 - start;
        for (int i = start; i <= endX; i++) {
            System.out.print(nums[start][i] + ", ");
        }
        if (start < endY){
            for (int i = start + 1; i <= endY; i++) {
                System.out.print(nums[i][endX] + ", ");
            }
        }
        if (start < endX && start < endY){
            for (int i = endX - 1; i >= start; i--) {
                System.out.print(nums[endY][i] + ", ");
            }
        }
        if (start < endX && start < endY - 1){
            for (int i = endY - 1; i >= start + 1; i--) {
                System.out.print(nums[i][start] + ", ");
            }
        }
    }

    /**
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
     * 例如，输入一个长度为9的数组，{1，2，3，2，2，2，5，4，2}，由于数字2出现了5次，超过数组长度的一半，
     * 因此输出2
     * @param numbers
     * @return
     */
    public static int moreThanHalf(int[] numbers){
        int result = numbers[0];
        int times = 1;
        for (int i = 0; i < numbers.length; i++) {
            if (times == 0){
                result = numbers[i];
            }else if (result == numbers[i]){
                times++;
            }else {
                times--;
            }
        }
        if (!checkHalf(numbers, result)){
            return Integer.parseInt(null);
        }
        return result;
    }

    private static boolean checkHalf(int[] numbers, int result) {
        int times = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == result){
                times++;
            }
        }
        boolean isHalf = true;
        if (times * 2 <= numbers.length){
            isHalf = false;
        }
        return isHalf;
    }

    /**
     * 输入一个整形数组，数组里有正数也有负数。
     * 数组中的一个或连续多个整数组成一个子数组，求所有子数组的和的最大值。
     * @param numbers
     * @return
     */
    public static int findGreatestSum(int[] numbers){
        if (numbers == null || numbers.length == 0){
            return Integer.parseInt(null);
        }
        int currentSum = 0;
        int greatestSum = Integer.MIN_VALUE;
        for (int i = 0; i < numbers.length; i++) {
            if (currentSum <= 0){
                currentSum = numbers[i];
            }else {
                currentSum += numbers[i];
            }
            if (greatestSum < currentSum){
                greatestSum = currentSum;
            }
        }
        return greatestSum;
    }

    public static int numberOf1(int number){
        if (number <= 0){
            return 0;
        }
        String numbers = String.valueOf(number);
        return numberOf1(numbers, 0);
    }

    /**
     * 输入一个整数n，求1~n这n个整数的十进制表示中1出现的次数，
     * 例如，输入12，1-12这些整数中包含1的数字有1，10，11，12，“1”一共出现了5次
     * @param numbers
     * @param index
     * @return
     */
    private static int numberOf1(String numbers, int index) {
        if (numbers.charAt(index) <'0' || numbers.charAt(index) > '9'){
            return 0;
        }
        int first = numbers.charAt(index) - '0';
        int length = numbers.length() - index;
        if (length == 1 && first == 0){
            return 0;
        }
        if (length == 1 && first > 0){
            return 1;
        }
        int numOfFirst = 0;
        if (first > 1){
            numOfFirst = (int) Math.pow(10, length - 1);
        }else if (first == 1){
            numOfFirst = Integer.parseInt(numbers.substring(index + 1)) + 1;
        }
        int numOther = (int) (first * (length - 1) * Math.pow(10, length - 2));
        index++;
        int numRecur = numberOf1(numbers, index);
        return numOfFirst + numOther + numRecur;
    }

    /**
     * 数字以0123456789101112131415...的格式序列化到一个字符序列中。
     * 在这个序列中，第5位（从0开始计数）是5，第13位是1，第19位是4等等。求第n位对应的数字
     * @param index
     * @return
     */
    public static int digitAt(int index){
        if (index < 0){
            return -1;
        }
        int digit = 1;
        while (true){
            int numbers = countOfDigit(digit);
            if (index < numbers * digit){
                return digitAt(index, digit);
            }
            index -= numbers * digit;
            digit++;
        }
    }

    private static int digitAt(int index, int digit) {
        int crossNumber = 0;
        if (digit == 1){
            crossNumber = 0 + index / digit;
        }else {
            crossNumber = (int) (Math.pow(10, digit - 1) + index / digit);
        }
        int offset = digit - index % digit;
        for (int i = 0; i < offset - 1; i++) {
            crossNumber /= 10;
        }
        return crossNumber % 10;
    }

    private static int countOfDigit(int digit) {
        if (digit == 1){
            return 10;
        }
        int count = (int) Math.pow(10, digit - 1);
        return 9 * count;
    }

    /**
     * 输入一个正整数数组，把数组里面所有数字拼起来排成一个数，打印能拼接出的所有数字中最小的一个，
     * 例如：输入数组{3，32，321}，则打印出这3个数能排成的最小的数字321323
     * @param numbers
     */
    public static void printMinNumber(int[] numbers){
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            strings.add(String.valueOf(numbers[i]));
        }
        strings.sort((o1, o2) -> {
            String temp = o1;
            o1 = o1 + o2;
            o2 = o2 + temp;
            if (o1.compareTo(o2) < 0){
                return -1;
            }else if (o1.compareTo(o2) > 0){
                return 1;
            }else {
                return 0;
            }
        });
        for (int i = 0; i < strings.size(); i++) {
            System.out.print(strings.get(i));
        }
    }

    /**
     * 给定一个数字，我们按照如下规则把它翻译成字符串：0翻译成“a”，1翻译成“b”，。。。11翻译成“l”，25翻译成“z”，一个数字可能有多种翻译，
     * 例如，12258有5中不同的翻译，分别为“bccfi”、“bwfi”、“bczi”、“mcfi”和“mzi”。实现一个函数，用来计算一个数字有多少种不同的翻译方法
     * @param number
     * @return
     */
    public static int getTranslationCount(int number){
        if (number < 0){
            return 0;
        }
        String strNumber = String.valueOf(number);
        int length = strNumber.length();
        int count = 0;
        int[] counts = new int[length];
        for (int i = length - 1; i >= 0; i--) {
            count = 0;
            if (i < length - 1){
                count = counts[i + 1];
            }else {
                count = 1;
            }
            if (i < length - 1){
                int digit1 = strNumber.charAt(i) - '0';
                int digit2 = strNumber.charAt(i + 1) - '0';
                int convert = digit1 * 10 + digit2;
                if (convert >= 10 && convert <= 25){
                    if (i < length - 2){
                        count += counts[i + 2];
                    }else {
                        count += 1;
                    }
                }
            }
            counts[i] = count;
        }
        return counts[0];
    }

    /**
     * 在一个mxn的棋盘的每一个格子都放有一个礼物，每一个礼物都有一定价值（价值大于0）。
     * 你可以从棋盘的左上角开始拿格子里面的礼物，并每次向右或者向下移动一格，直到到达棋盘的右下角。
     * 给定一个棋盘及其上面的礼物，请计算你最多能拿到多少价值的礼物。
     * @param values
     * @return
     */
    public static int getMaxGiftValue(int[][] values){
        int rows = values.length;
        int cols = values[0].length;
        int[][] maxValue = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left = 0;
                int up = 0;
                if (i > 0){
                    up = maxValue[i - 1][j];
                }
                if (j > 0){
                    left = maxValue[i][j - 1];
                }
                maxValue[i][j] = Math.max(left, up) + values[i][j];
            }
        }
        return maxValue[rows - 1][cols - 1];
    }

    /**
     * 我们把只包含因子2、3、5的数称作丑数，求按从小到大的顺序的第1500个丑数，
     * 例如6、8都是丑数，但是14不是。因为它包含因子7。习惯上1也是丑数
     * @param offset
     * @return
     */
    public static int getUglyNumber(int offset){
        int[] uglyNumbers = new int[offset];
        int nextIndex = 1;
        uglyNumbers[0] = 1;
        int indexOf2 = 0, indexOf3 = 0, indexOf5 = 0;
        while (nextIndex < offset){
            int min = getMin(uglyNumbers[indexOf2] * 2, uglyNumbers[indexOf3] * 3, uglyNumbers[indexOf5] * 5);
            uglyNumbers[nextIndex] = min;

            while (uglyNumbers[indexOf2] * 2 <= uglyNumbers[nextIndex]){
                indexOf2++;
            }
            while (uglyNumbers[indexOf3] * 3 <= uglyNumbers[nextIndex]){
                indexOf3++;
            }
            while (uglyNumbers[indexOf5] * 5 <= uglyNumbers[nextIndex]){
                indexOf5++;
            }
            nextIndex++;
        }
        return uglyNumbers[nextIndex - 1];
    }

    private static int getMin(int num1, int num2, int num3){
        int min = (num1 < num2) ? num1 : num2;
        return (min < num3) ? min : num3;
    }

    public static int inversePairsCounts(int[] data){
        int length = data.length;
        int[] copy = new int[length];
        for (int i = 0; i < length; i++) {
            copy[i] = data[i];
        }
        return inversePairsCounts(data, copy, 0, length - 1);
    }

    /**
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。
     * 例如，在数组{7, 5, 6, 4}中，一共存在5个逆序对，分别为：(7, 6), (7, 5), (7, 4), (6, 4), (5, 4)
     * @param data
     * @param copy
     * @param start
     * @param end
     * @return
     */
    private static int inversePairsCounts(int[] data, int[] copy, int start, int end) {
        if (start == end){
            copy[start] = data[start];
            return 0;
        }
        int length = (end - start) / 2;
        int left = inversePairsCounts(data, copy, start, start + length);
        int right = inversePairsCounts(data, copy, start + length + 1, end);
        int i = start + length;
        int j = end;
        int indexOfCopy = end;
        int count = 0;
        while (i >= start && j >= start + length + 1){
            if (data[i] > data[j]){
                copy[indexOfCopy--] = data[i--];
                count += j - start - length;
            }else {
                copy[indexOfCopy--] = data[j--];
            }
        }

        while (i >= start){
            copy[indexOfCopy--] = data[i];
            i--;
        }

        while (j >= start + length + 1){
            copy[indexOfCopy--] = data[j];
            j--;
        }
        return left + right + count;
    }

    /**
     * 统计一个数字在排序数组中出现的次数。
     * 例如：输入排序数组{1， 2， 3， 3，3， 3， 4， 5}和数字3，由于3在这个数字中出现了4次，因此输出4
     * @param data
     * @param k
     * @return
     */
    public static int getNumberOfK(int[] data, int k){
        int length = data.length;
        int number = 0;
        if (data != null && length > 0){
            int first = getFirstOfK(data, k, 0, length - 1);
            int last = getLastOfK(data, k, 0, length - 1);
            if (first > -1 && last > -1){
                number = last - first + 1;
            }
        }
        return number;
    }

    private static int getLastOfK(int[] data, int k, int start, int end) {
        if (start > end){
            return -1;
        }
        int length = data.length;
        int middleIndex = (start + end) / 2;
        int mid = data[middleIndex];
        if (mid == k){
            if ((middleIndex < length - 1 && data[middleIndex + 1] != k) || middleIndex == length - 1){
                return middleIndex;
            }else {
                start = middleIndex + 1;
            }
        }else if (mid < k){
            start = middleIndex + 1;
        }else {
            end = middleIndex - 1;
        }
        return getLastOfK(data, k, start, end);
    }

    private static int getFirstOfK(int[] data, int k, int start, int end) {
        if (start > end){
            return -1;
        }
        int middleIndex = (start + end) / 2;
        int mid = data[middleIndex];
        if (mid == k){
            if ((middleIndex > 0 && data[middleIndex - 1] != k) || middleIndex == 0 ){
                return middleIndex;
            }else {
                end = middleIndex - 1;
            }
        }else if (mid > k){
            end = middleIndex - 1;
        }else {
            start = middleIndex + 1;
        }
        return getFirstOfK(data, k, start, end);
    }

    /**
     * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0\~n-1内。
     * 在范围0\~n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字
     * @param data
     * @return
     */
    public static int getMissingNumber(int[] data){
        int length = data.length;
        if (data == null || length < 0){
            return -1;
        }
        int left = 0;
        int right = length - 1;
        while (left <= right){
            int mid = (left + right) / 2;
            if (data[mid] != mid){
                if (mid == 0 || data[mid - 1] == mid - 1){
                    return mid;
                }else {
                    right = mid - 1;
                }
            }else {
                left = mid + 1;
            }
        }
        if (left == length){
            return length;
        }
        return -1;
    }

    /**
     * 假设一个单调递增的数组里面的每个元素都是整数并且是唯一的。请写一个函数，找出数组中任意一个数值等于其下标的元素。
     * 例如：在数组{-3, -1, 1, 3, 5}中，数字3和它的下标相等
     * @param data
     * @return
     */
    public static int getNumberSameAsIndex(int[] data){
        if (data == null || data.length <= 0){
            return -1;
        }
        int length = data.length;
        int left = 0;
        int right = length - 1;
        while (left <= right){
            int mid = (left + right) / 2;
            if (data[mid] == mid){
                return mid;
            }else if (data[mid] > mid){
                right = mid - 1;
            }else {
                left = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 一个整型数组里除了两个数字之外，其他数字都出现了两次。请找出这两个只出现一次的数字。
     * @param data
     * @return
     */
    public static int[] getNumbersAppearOnce(int[] data){
        int length = data.length;
        if (data == null || length < 2){
            return null;
        }
        int resultOfXOR = 0;
        for (int i = 0; i < length; i++) {
            resultOfXOR ^= data[i];
        }
        int indexOfBit1 = getFirstBitOf1(resultOfXOR);
        int[] res = new int[2];
        int number1 = 0, number2 = 0;
        for (int i = 0; i < length; i++) {
            if (isBit1(data[i], indexOfBit1)){
                number1 ^= data[i];
            }else {
                number2 ^= data[i];
            }
        }
        res[0] = number1;
        res[1] = number2;
        return res;
    }

    private static boolean isBit1(int num, int index) {
        num = num >> index;
        return (num & 1) == 1;
    }

    private static int getFirstBitOf1(int xor) {
        int index = 0;
        while (((xor & 1) == 0) && (index < 32)){
            xor = xor >> 1;
            index++;
        }
        return index;
    }

    /**
     * 在一个数组中除了一个数字只出现一次之外，其他数字出现了三次。请找出那个只出现一次的数字。
     * @param data
     * @return
     */
    public static int getNumberAppearOnce(int[] data) throws Exception {
        if (data == null || data.length <= 0){
            throw new Exception("输入数组错误");
        }
        int[] bitSum = new int[32];
        for (int i = 0; i < 32; i++) {
            bitSum[i] = 0;
        }
        for (int i = 0; i < data.length; i++) {
            int bitMask = 1;
            for (int j = 31; j >= 0; j--) {
                int bit = data[i] & bitMask;
                if (bit != 0){
                    bitSum[j] += 1;
                }
                bitMask = bitMask << 1;
            }
        }
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = result << 1;
            result += bitSum[i] % 3;
        }
        return result;
    }

    /**
     * 输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和为s，则输出任意一对即可
     * @param data
     * @param sum
     * @return
     */
    public static int[] getNumberWithSum(int[] data, int sum){
        int[] res = new int[2];
        if (data == null || data.length < 1){
            return null;
        }
        int left = 0;
        int right = data.length - 1;
        while (left < right){
            int curSum = data[left] + data[right];
            if (curSum == sum){
                res[0] = data[left];
                res[1] = data[right];
                break;
            }else if (curSum > sum){
                right--;
            }else {
                left++;
            }
        }
        return res;
    }

    /**
     * 输入一个正数s，打印出所有和为s的连续正数序列（至少含有两个数）。
     * 例如，输入15，由于1+2+3+4+5=4+5+6=7+8=15，所以打印出3个连续序列1\~5,4\~6，和7-8
     * @param sum
     * @return
     */
    public static List<String> getContinuousSeq(int sum){
        if (sum < 3){
            return null;
        }
        List<String> res = new ArrayList<>();
        int small = 1;
        int big = 2;
        int mid = (1 + sum) / 2;
        int curSum = small + big;
        while (small < mid){
            if (curSum == sum){
                genTolist(res, small, big);
            }

            while (curSum > sum && small < mid){
                curSum -= small;
                small++;
                if (curSum == sum){
                    genTolist(res, small, big);
                }
            }
            big++;
            curSum += big;
        }
        return res;
    }

    private static void genTolist(List<String> res, int small, int big) {
        StringBuilder builder = new StringBuilder();
        for (int i = small; i <= big; i++) {
            builder.append(i + ", ");
        }
        res.add(builder.toString());
    }

    /**
     * 给定一个数组和滑动窗口的大小，请找出所有滑动窗口里面的最大值。
     * 例如，输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}
     * @param numbers
     * @param windowSize
     * @return
     */
    public static int[] maxInWindows(int[] numbers, int windowSize){
        if (numbers == null || numbers.length < windowSize || windowSize < 1){
            return numbers;
        }
        List<Integer> maxs = new ArrayList<>();
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < windowSize; i++) {
            while (!queue.isEmpty() && numbers[i] >= queue.getLast()){
                queue.pollLast();
            }
            queue.offerLast(i);
        }
        for (int i = windowSize; i < numbers.length; i++) {
            maxs.add(numbers[queue.getFirst()]);
            while (!queue.isEmpty() && numbers[i] >= numbers[queue.getLast()]){
                queue.pollLast();
            }
            if (!queue.isEmpty() && queue.getFirst() <= (i - windowSize)){
                queue.pollFirst();
            }
            queue.offerLast(i);
        }
        maxs.add(numbers[queue.getFirst()]);
        return maxs.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少，
     * 例如，一只股票在某些时间节点的价格为{9,11,8,5,7,12,16,14}。
     * 如果我们能在价格为5的时候买入并在价格为16时卖出，则能收获最大的利润为11
     * @param profile
     * @return
     */
    public static int maxIntrest(int[] profile){
        if (profile == null || profile.length < 2){
            return 0;
        }
        int min = profile[0];
        int maxIntrest = profile[1] = min;
        for (int i = 2; i < profile.length; i++) {
            if (profile[i - 1] < min){
                min = profile[i];
            }
            int curIntrest = profile[i] - min;
            if (curIntrest > maxIntrest){
                maxIntrest = curIntrest;
            }
        }
        return maxIntrest;
    }

    /**
     * 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。
     * 2~10为数字本身，A为1，J为11，Q为12，K为13，而大小王可以看成任意数字
     * @param pokes
     * @return
     */
    public static boolean isContinuous(int[] pokes){
        if (pokes == null || pokes.length < 5){
            return false;
        }
        Arrays.sort(pokes);
        int numberOfZero = 0;
        int numberOfGap = 0;
        for (int i = 0; i < pokes.length; i++) {
            if (pokes[i] == 0){
                numberOfZero++;
            }
        }
        int left = numberOfZero;
        int right = left + 1;
        while (right < pokes.length){
            if (pokes[left] == pokes[right]){
                return false;
            }
            numberOfGap += pokes[right] - pokes[left] - 1;
            left = right;
            right++;
        }
        return (numberOfGap > numberOfZero) ? false : true;
    }

    /**
     *  $0,1...n-1$这$n$个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字。
     *  求这个圆圈里剩下的最后一个数字
     * @param n
     * @param m
     * @return
     */
    public static int getLastRemaining(int n, int m){
        if (n < 1 || m < 1){
            return -1;
        }
        int last = 0;
        for (int i = 2; i <= n; i++) {
            last = (last + m) % i;
        }
        return last;
    }

    /**
     * 把n个骰子扔在地上，所有骰子朝上的一面的点数之和为s。输入n，打印s的所有可能的值的概率
     * @param number
     */
    public static void printAllProbability(int number){
        int maxValue = 6;
        if (number < 1){
            return;
        }
        int[][] probilitys = new int[2][number * maxValue + 1];
        for (int i = 0; i < number * maxValue + 1; i++) {
            probilitys[0][i] = 0;
            probilitys[1][i] = 0;
        }
        int flag = 0;

        //第一轮扔骰子，不可能为0，因此从1开始，第一轮由于是一个骰子，因此每一个和出现的次数都为1
        for (int i = 1; i <= maxValue; i++) {
            probilitys[flag][i] = 1;
        }

        for (int i = 2; i <= number; i++) {
            //第j个骰子开始，由于最小值都为j，所以和为j之前的次数都为0
            for (int j = 0; j < i; j++) {
                probilitys[1 - flag][j] = 0;
            }

            for (int j = i; j <= maxValue * i; j++) {
                probilitys[1 - flag][j] = 0;

                //接下来加入一个骰子，上一个骰子的和为n的次数应当加上上一轮的n-1,n-2....n-6
                for (int k = 1; k <= j && k <= maxValue; k++) {
                    probilitys[1 - flag][j] += probilitys[flag][j - k];
                }
            }
            flag = 1 - flag;
        }
        double total = Math.pow(maxValue, number);
        for (int i = number; i <= maxValue * number; i++) {
            double ratio = probilitys[flag][i] / total;
            System.out.println(ratio);
        }
    }
}
