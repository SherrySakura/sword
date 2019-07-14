package problem;

import utils.Result;

import java.util.ArrayList;
import java.util.List;

public class ProblemString {

    /**
     * 实现一个函数，把输入的字符串中的每一个空格替换成“%20”
     * @param ori
     * @return
     */
    public static String replaceBlank(String ori){
        if (ori == null || ori.length() <= 0){
            return null;
        }
        String[] after = ori.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < after.length - 1; i++) {
            builder.append(after[i] + "%20");
        }
        builder.append(after[after.length - 1]);
        return builder.toString();
    }

    /**
     * 输入数字m，按顺序打印从1到最大的m位十进制数，例如，输入数字3，则应该从1，2，3打印到999
     * @param n
     */
    public static void print1ToMaxOfNDigit(int n){
        if (n <= 0){
            return;
        }
        char[] number = new char[n];
        for (int i = 0; i < n; i++) {
            number[i] = '0';
        }
        while (true){
            try {
                number = increament(number);
                print(number);
            } catch (Exception e) {
                break;
            }
        }

    }

    /**
     * 模拟计算字符增加+1，并判断是否超出越界结束增加的过程
     * @param number
     * @return
     * @throws Exception
     */
    private static char[] increament(char[] number) throws Exception {
        int sum = 0;
        int token = 0;
        int length = number.length;
        for (int i = length - 1; i >= 0; i--) { //对于每一位都要判断是否有进位产生
            sum = number[i] - '0' + token;      //把当前的字符位和进位相加
            if (i == length - 1) {
                sum++;                          //如果为个位的数字，则直接自加1
            }
            if (sum >= 10){
                if (i == 0){                    //产生了进位且目前判断到了最高位，那么表示已经全部加完
                    throw new Exception("over");//产生异常来结束循环
                }else {
                    sum -= 10;                  //若不是最高位，那么-10表示进位后的各位：5+8=13->个位为3
                    token = 1;                  //产生进位值1
                    number[i] = (char) ('0' + sum);//更新该位的数值
                }
            }else {
                number[i] = (char) ('0' + sum); //如果没有产生进位，则直接更新该位的值且推出当前循环，进行后续的打印
                break;
            }
        }
        return number;
    }

    private static void print(char[] number){
        boolean begin = true;
        int length = number.length;
        for (int i = 0; i < length; i++) {
            if (begin && number[i] != '0'){     //从开始不为0的字符进行打印。
                begin = false;                  //若字符数组为0，0，1，2，则跳过前两个0，直接打印12
            }
            if (!begin){
                System.out.print(number[i]);
            }
        }
        System.out.print("  ");
    }

    /**
     * 实现一个函数用来匹配包含'.','*'的正则表达式。模式匹配中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次数
     * 例如，字符串'aaa'能够与'a.a','ab*ac*a'匹配，但是不能与'aa.a','ab*a'匹配
     * @param src
     * @param pattern
     * @return
     */
    public static boolean match(String src, String pattern){
        if (src == null || pattern == null){
            return false;
        }
        return matchCore(src, pattern, 0, 0);
    }

    private static boolean matchCore(String src, String pattern, int indexSrc, int indexPattern) {
        int srcLength = src.length();
        int patternLength = pattern.length();
        if (indexSrc == srcLength - 1 && indexPattern == patternLength - 1){
            return true;
        }
        if (indexSrc != srcLength - 1 && indexPattern == patternLength - 1){
            return false;
        }
        if (pattern.charAt(indexPattern + 1) == '*'){
            if (pattern.charAt(indexPattern) == src.charAt(indexSrc) || (pattern.charAt(indexPattern) == '.' && indexSrc != srcLength - 1)){
                return matchCore(src, pattern, indexSrc + 1, indexPattern + 2)
                        || matchCore(src, pattern, indexSrc, indexPattern + 2)
                        || matchCore(src, pattern, indexSrc + 1, indexPattern);
            }
            else {
                return matchCore(src, pattern, indexSrc, indexPattern + 2);
            }
        }
        if (src.charAt(indexSrc) == pattern.charAt(indexPattern) || (pattern.charAt(indexPattern) == '.' && indexSrc != srcLength - 1)){
            return matchCore(src, pattern, indexSrc + 1, indexPattern + 1);
        }
        return false;
    }

    public static boolean isNumberic(String src){
        if (src == null){
            return false;
        }
        int index = 0;
        boolean isNumberic = false;
        Result numberic = scanInteger(src, 0);
        isNumberic = numberic.result;
        index = numberic.offset;
        if (src.charAt(index) == '.'){
            ++index;
            Result dotAble = scanUnsignedInteger(src, index);
            isNumberic = dotAble.result || isNumberic;
            index = dotAble.offset;
        }
        if (src.charAt(index) == 'e' || src.charAt(index) == 'E'){
            ++index;
            Result eAble = scanInteger(src, index);
            isNumberic = isNumberic && eAble.result;
            index = eAble.offset;
        }
        return isNumberic && (index == src.length() - 1);
    }

    private static Result scanInteger(String src, int index) {
        if (src.charAt(index) == '+' || src.charAt(index) == '-'){
            return scanUnsignedInteger(src, index + 1);
        }
        return scanUnsignedInteger(src, index);
    }

    private static Result scanUnsignedInteger(String src, int index) {
        int before = index;
        while (index < src.length() - 1 && src.charAt(index) >= '0' && src.charAt(index) <= '9'){
            ++index;
        }
        Result result = new Result(index, index > before);
        return result;
    }

    /**
     * 输入一个字符串，打印出该字符串中字符的所有排列，
     * 例如，输入字符串abc，则打印出由a、b、c所能排列出来的所有字符串abc、acb、bac、bca、cab和cba。
     * @param str
     * @return
     */
    public static List<String> permutation(String str){
        if (str == null || str.length() == 0){
            return null;
        }
        List<String> result = new ArrayList<>();
        char[] ch = str.toCharArray();
        permutation(ch, 0, result);
        return result;
    }

    private static void permutation(char[] ch, int index, List<String> result) {
        if (index == ch.length - 1){
            if (!result.contains(String.valueOf(ch))){
                result.add(String.valueOf(ch));
            }
        }else {
            for (int j = index; j < ch.length; j++) {
                swap(ch, index, j);
                permutation(ch, index + 1, result);
                swap(ch, index, j);
            }
        }

    }

    /**
     * 交换函数，交换ch数组中第index和第j个元素
     * @param ch
     * @param index
     * @param j
     */
    private static void swap(char[] ch, int index, int j) {
        char temp = ch[index];
        ch[index] = ch[j];
        ch[j] = temp;
    }

    /**
     * 请从字符串中找出一个最长的不包含重复字符串的子字符串，计算该最长子字符串的长度。
     * 假设字符串中只包含'a'~'z'的字符。例如，在字符串"arabcacfr"中，最长的不含重复字符的子字符串是"acfr"，长度为4
     * @param str
     * @return
     */
    public static int getLongestSubString(String str){
        int curLength = 0;
        int maxLength = 0;
        int[] position = new int[26];
        for (int i = 0; i < 26; i++) {
            position[i] = -1;
        }
        for (int i = 0; i < str.length(); i++) {
            int prevIndex = position[str.charAt(i) - 'a'];
            if (prevIndex < 0 || (i - prevIndex) > curLength){
                ++curLength;
            }else {
                if (maxLength < curLength){
                    maxLength = curLength;
                }
                curLength = i - prevIndex;
            }
            position[str.charAt(i) - 'a'] = i;
        }
        if (maxLength < curLength){
            maxLength = curLength;
        }
        return maxLength;
    }

    /**
     * 在字符串中找出第一个只出现一次的字符，如输入“abaccdeff”，则输出'b'
     * @param str
     * @return
     */
    public static char getFirstRepeatingChar(String str){
        int[] counts = new int[256];
        for (int i = 0; i < str.length(); i++) {
            counts[str.charAt(i)]++;
        }
        for (int i = 0; i < str.length(); i++) {
            if (counts[str.charAt(i)] == 1){
                return str.charAt(i);
            }
        }
        return '\0';
    }

    /**
     * 实现一个函数，用来找出字符流中第一个只出现一次的字符，
     * 例如，当字符流读到“go”时，第一个只出现的字符是'g'，当从字符流读到“google”时，第一个只出现一次的字符是'l'
     */
    public class CharRepeating{
        private int occ[];
        private int index = 0;
        private List<Character> input = new ArrayList<>();

        public CharRepeating() {
            occ = new int[256];
            for (int i = 0; i < 256; i++) {
                occ[i] = -1;
            }
        }

        public char insert(char ch){
            if (occ[ch] == -1){
                occ[ch] = index;
                input.add(ch);
            }else if (occ[ch] >= 0){
                occ[ch] = -2;
            }
            index++;

            return getFirstApperaingOnce();
        }

        private char getFirstApperaingOnce(){
            for (int i = 0; i < input.size(); i++) {
                if (occ[input.get(i)] >= 0){
                    return input.get(i);
                }
            }
            return '\0';
        }
    }

    /**
     * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。
     * 例如输入字符串“I am a student.”则输出“student. a am I”
     * @param str
     * @return
     */
    public static String reverseSentence(String str){
        String[] split = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            builder.append(split[i] + " ");
        }
        builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }

    /**
     * 字符串的坐旋转操作是把字符串前面的若干个字符转移到字符串的尾部。
     * 例如，输入字符串“abcdefg”和数字2，该函数将返回左旋转两位得到的结果“cdefgab”
     * @param str
     * @param offset
     * @return
     */
    public static String leftRotateString(String str, int offset){
        if (str == null){
            return null;
        }
        String first = str.substring(0, offset);
        String last = str.substring(offset);
        StringBuilder builderFirst = new StringBuilder(first);
        StringBuilder stringBuilderLast = new StringBuilder(last);
        builderFirst.reverse();
        stringBuilderLast.reverse();
        builderFirst.append(stringBuilderLast);
        return builderFirst.reverse().toString();
    }
}
  