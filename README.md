[TOC]

#### 从尾到头打印链表

> 例题1 输入一个链表的头节点，从尾到头反过来打印出每个结点的值

==思路== 借助一个栈结构，先将列表内的所有元素入栈，然后再把栈内的每一个元素一个个弹出，即实现了反向打印所有的元素，实现如下

```java
package problem;

import java.util.*;

public class ProblemLinkList {

    /**
     * 输入一个链表的头结点，从尾到头反过来打印出每个结点的值
     * 用栈实现
     * @param e
     * @return
     */
    public static List<Integer> printLinkListReserveWithStack(List<Integer> e){
        Stack<Integer> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        for (Integer i :
                e) {
            stack.push(i);
        }
        while (!stack.empty()){
            res.add(stack.pop());
        }
        return res;
    }
}

```
---
> 例题2 输入某二叉树的前序遍历和中序遍历的结果，重建该二叉树。假设输入的前序遍历和中序遍历结果中不会含有重复数字。

==思路== 由于前序遍历中的第一个元素为树的根，再根据这个找出来的根在中序遍历中找出根所在的位置

例如前序遍历序列为：{1，2，4，7，3，5，6，8}，那么该树的根为1，

若中序遍历序列为：{4，7，2，1，5，3，8，6}，则树根所在元素为1，由中序遍历可知，1左侧均为左子树，1右边为右子树，如果左子树的长度不为0，那么递归的构建左子树，如果右子树的长度不为0，则递归的构建右子树，每一次都是找目前所要构建子树的根。

```java
package problem;

public class ProblemTree {

    public class BinaryTreeNode{
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;
        BinaryTreeNode parent;

        public BinaryTreeNode(int value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        @Override
        public String toString() {
            return this.value + " left = " + this.left + " /  right = " + this.right;
        }
    }

    public BinaryTreeNode construct(int[] preOder, int[] inOrder) throws Exception {
        if (preOder == null || inOrder == null){
            return null;
        }
        return construct(preOder, inOrder, 0, preOder.length - 1,0, inOrder.length - 1);
    }

    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，重建该二叉树。假设输入的前序遍历和中序遍历结果中不会含有重复数字。
     * 例如前序遍历序列为：{1，2，4，7，3，5，6，8}
     *     中序遍历序列为：{4，7，2，1，5，3，8，6}
     *     则结果应为：
     *                          1
     *                         / \
     *                        2   3
     *                       /   / \
     *                      4   5   6
     *                      \      /
     *                       7    8
     * @param preOder
     * @param inOrder
     * @param startPreIndex
     * @param endPreIndex
     * @param startInIndex
     * @param endInIndex
     * @return
     * @throws Exception
     */
    private BinaryTreeNode construct(int[] preOder, int[] inOrder, int startPreIndex, int endPreIndex, int startInIndex, int endInIndex) throws Exception {
        int rootValue = preOder[startPreIndex];
        BinaryTreeNode root = new BinaryTreeNode(rootValue);
        if (preOder[startPreIndex] == preOder[endPreIndex]){
            if (inOrder[startInIndex] == inOrder[endInIndex]){
                return root;
            }else {
                throw new Exception();
            }
        }
        int rootIndex = startInIndex;
        while (startInIndex <= endInIndex && inOrder[rootIndex] != rootValue){
            rootIndex++;
        }
        if (rootIndex == endInIndex && inOrder[rootIndex] != rootValue){
            throw new Exception();
        }
        int leftLength = rootIndex - startInIndex;
        int leftPreEnd = startPreIndex + leftLength;
        if (leftLength > 0){
            root.left = construct(preOder, inOrder, startPreIndex + 1, leftPreEnd, startInIndex, rootIndex - 1);
            root.left.parent = root;
        }
        if (leftLength < endPreIndex - startPreIndex){
            root.right = construct(preOder, inOrder, leftPreEnd + 1, endPreIndex, rootIndex + 1, endInIndex);
            root.right.parent = root;
        }
        return root;
    }
    
}

```
---
> 例题3 给定一个二叉树和其中的一个节点，找出中序遍历序列的下一个节点，树中每一个节点都有一个左节点，右节点和父节点的引用

==思路== 分三种情况，：

1. 该节点有右子树，则一直找到他的右子树中的最左边的节点，就是不停的找他右边节点的左节点，若一直有则一直继续找下去，否则就把目前的左节点作为他的下一个节点
2. 该节点没有右子树，且为他的父节点的左子树，那么下一个节点就是他的父节点
3. 该节点既没有右子树，且不是他的父节点的左子树，沿着父节点一直向上找，直到找到该节点为父节点的左节点，若是右节点则继续往他的父节点寻找

树的结构使用的是例题2的结构，构建也由例题2中的代码来构建

```java
public BinaryTreeNode getNextViaInorder(BinaryTreeNode root){
        BinaryTreeNode node = null;
        if (root == null){
            return null;
        }
        if (root.right != null){
            BinaryTreeNode right = root.right;
            while (right.left != null){
                right = right.left;
            }
            node = right;
        }else if (root.parent != null && root.parent.right == root){
            BinaryTreeNode current = root;
            BinaryTreeNode parent = root.parent;
            while (parent != null && current == parent.right){
                current = parent;
                parent = parent.parent;
            }
            node = current.parent.left;
        }else {
            node = root.parent;
        }
        return node;
    }
```
---

#### 替换空格

> 例题4 实现一个函数，把输入的字符串中的每一个空格替换成“%20”

==思路== 利用JDK自带的```StringBuilder```先将原始的字符串按空格用```spli()```方法进行拆分，得到一个```String```数组，接着使用循环，在数组的每一个元素后面附加一个%20，最后将```StringBuilder```转换成```String```

```java
package problem;

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
}

```
---

#### 用两个栈实现一个队列

> 例题5 用两个栈实现一个队列

==思路== 维护两个栈，一个栈用于不断接收新数据的压栈，也就是入队，另一个栈用于出队列操作，若第二个栈有元素，则进行出队操作时直接将第二个栈的元素一一弹出即可，如果第二个栈内没有元素，则先将第一个栈内的元素全部出栈后再全部压入第二个栈内，再进行出队列操作

```java
package problem;

import java.util.Stack;

public class ProblemStack {
    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    /**
     * 用两个栈实现一个队列
     * @param item
     */
    public void appendTail(Integer item){
        stack1.push(item);
    }

    public Integer deleteHead(){
        if (stack2.size() <= 0){
            while (stack1.size() > 0){
                Integer data = stack1.pop();
                stack2.push(data);
            }
        }
        if (stack2.size() == 0){
            return null;
        }
        Integer head = stack2.pop();
        return head;
    }
}

```
---

#### 斐波那契数列

> 例题6 斐波那契数列的两种求法（迭代法和递归法）

==思路== 迭代法是每次计算前两个，通过前两个元素相加计算出第三个元素，然后将第二个赋给第一个元素，刚刚计算出的第三个元素赋给第二个元素，实现步进。递归则是不断自己调用自己

*注意 递归在计算比较大的项时会远远慢于迭代*

```java
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

```
---

#### 数组中重复的数字

> 例题7 找出数组中重复的数字
>
> 在一个长度为n的数组里的所有数字都在0~n-1范围内，数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次，请找出数组中任意一个重复的数字，例如，若数组为{2，3，1，0，2，5，3}，那么对应的输出是重复的数字2，3

==思路== 若不采用HashSet这种JDK自带的数据结构，那么应该如下操作：从头到尾依次扫描这个数组中的每一个数字，当扫描到下标为```i```数字时，首先比较这个数字（假设为```m```）是否等于```i```。如果是，则接着扫描下一个数字，若不是，则那他和第```m```个数字进行比较，若和第```m```个数字相等，那么就找到了一个重复的数字（数字在下标为```m```和下标为```i```的位置都出现了）。若不相等，把下标为```m```的数字和下标为```i```的数字交换，把他放在属于它的位置。下标等于数字

```java
package problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
}

```
---
> 例题8 在一个长度为n+1的数组里面的所有数字都在1~n的范围内，所以数组中至少有一个数字是重复的，请找出数组中任意一个重复的数字，但不能修改数组如输入的数组为{2，3，5，4，3，2，6，7}，则输出重复数字为2，3

==思路== 使用```HashSet```实现这个功能，从左到右依次扫描每一个数字，对每一个数字先判断该数字是否在```HashSet```中存在过，若已经存在了，则判断该数字为重复的数字，若不存在，则将这个元素放进```HashSet```中

```java
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
```
---

#### 二维数组中的查找

> 例题9 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列也是按照从上到下递增的顺序排序，完成函数，输入一个二维数组和一个整数，判断这个二维数组中是否存在这个整数，并返回该整数的下标位置

==思路== 先选择最右上角的数字，有一下几种情况

1. 若该数字等于要查找的数字，则查找过程结束，返回该数字的下标位置
2. 若该数字大于要查找的数字，那么去除该数字所在的列，在接下来的数组里面的最右上叫进行查找
3. 若该数字小于要查找的数字，那么去除该数字所在的行，在接下来的数字里面的最右上角进行查找

*注意 不能找左下角或者右下角的数字进行开始*

```java
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
```
---

#### 快速排序数组

> 例题10 快速排序数组

==思路== 在数组的两端设置两个索引```low```和```high```，再在这两个索引中间取一个中间索引

```int m = low + (high - low) / 2```

设原数组为```k[]```，直接先比较```k[low],k[m],k[high]```，这三者按小到大的顺序交换好，然后把中间的数字的值给```low```，且作为参考点```point```返回。

总体思想为，先选择一个数组中的参考点，将数组分为两个部分，左边的始终比参考点的数字小，右边的始终比参考点的数字大。通过交换移动实现，接着利用递归的思想，先排序左边的数组区，再排序右边的数组区。

```java
package problem;

public class ProblemSort {

    /**
     * 快速排序数组
     * @param src
     */
    public static void quickSort(int[] src){
        quickSort(src, 0, src.length - 1);
    }

    private static void quickSort(int[] src, int low, int high){
        if (low < high){
            int point = partition(src, low, high);
            quickSort(src, low, point - 1);
            quickSort(src, point + 1, high);
        }
    }

    private static int partition(int[] src, int low, int high){
        int point;
        int m = low + (high - low) / 2;
        if (src[low] > src[high]){
            swap(src, low, high);
        }
        if (src[m] > src[high]){
            swap(src, m, high);
        }
        if (src[m] > src[low]){
            swap(src, m, low);
        }
        point = src[low];
        while (low < high){
            while (low < high && src[high] >= src[low]){
                high--;
            }
            swap(src, low, high);
            while (low < high && src[low] <= point){
                low++;
            }
            swap(src, low, high);
        }
        return low;
    }

    private static void swap(int[] src, int indexFor, int indexAft){
         int temp = src[indexFor];
         src[indexFor] = src[indexAft];
         src[indexAft] = temp;
    }
}

```
---
#### 旋转数组中最小的数字

> 例题11 把一个数组最开始的若干个元素搬到数组的末尾，称为数组的旋转，输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。如：数组{3，4，5，1，2}为{1，2，3，4，5}的一个旋转，最小值为1

==思路== 类似于二分法查找，用两个指针分别指向数组的第一个元素```low```和数组的最后一个元素```high```，根据这两个元素找到数组中间的元素，则有以下两种情况：

1. 中间元素```mid```位于==前面==的递增子数组，那么它会大于或等于第一个指针```low```指向的元素，推断出数组中最小的元素在中间元素的后面，则把原本指向第一个元素的指针指向中间元素，```low = mid```
2. 中间元素```mid```位于==后面==的递增子数组，那么它会小于或等于第二个指针```high```指向的元素，推断出数组中最小的元素在中间元素的前面，则把原本指向第二个元素的指针指向中间元素，```high = mid```
3. 循环的终止条件为：第一个指针指向前面递增数组的最后一个元素，第二个指针指向后面数组的第一个元素，那么这两个指针将相邻，计算为：```high - low == 1```，那么第二个元素就是最小的元素
4. 若输入数组就是一个排好序的递增数组，即把0个元素旋转到了后面，则应该在初始化时就应该把```mid = low```，可以直接返回
5. 若数组中第一个，第二个和中间指针指向的元素全部相等，则不能很好的判断出处于哪个半区，那么只能按照顺序查找，一个一个区进行搜索查找。

```java
/**
     * 把一个数组最开始的若干个元素搬到数组的末尾，称为数组的旋转，输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
     * 如：数组{3，4，5，1，2}为{1，2，3，4，5}的一个旋转，最小值为1
     * @param src
     * @return
     */
    public static int minInRotateArray(int[] src) throws Exception {
        if (src == null || src.length <= 0){
            throw new Exception("数组输入错误");
        }
        int length = src.length;
        int low = 0;
        int high = length - 1;
        int mid = low;
        while (src[low] >= src[high]){
            if (high - low == 1){
                return src[high];
            }
            mid = (high + low) / 2;
            if (src[low] == src[mid] && src[mid] == src[high]){
                return minInOrder(src, low, high);
            }
            if (src[mid] >= src[low]){
                low = mid;
            }else if (src[mid] <= src[high]){
                high = mid;
            }
        }
        return src[mid];
    }

    /**
     * 按顺序查找最小值，当三者都一样大时
     * @param src
     * @param low
     * @param high
     * @return
     */
    private static int minInOrder(int[] src, int low, int high){
        int result = src[low];
        for (int i = low + 1; i <= high; i++) {
            if (result > src[i]){
                result = src[i];
            }
        }
        return result;
    }
```

---

> [回溯法](https://baike.baidu.com/item/%E5%9B%9E%E6%BA%AF%E6%B3%95/86074?fr=aladdin)：回溯法（探索与回溯法）是一种选优搜索法，又称为试探法，按选优条件向前搜索，以达到目标。但当探索到某一步时，发现原先选择并不优或达不到目标，就退回一步重新选择，这种走不通就退回再走的技术为回溯法，而满足回溯条件的某个状态的点称为“回溯点”。

#### 矩阵中的路径

> 例题12 设计一个函数，判断在一个矩阵中是否存在一条包含某字符串左右的路径。路径可以从矩阵中的任意一格开始， 每一步可以在矩阵中向左、右、上、下移动一格。如果一条路径经过了矩阵的某一格，那么该路径不能再次进入格子，例如，在下面的3x4的矩阵中包含一条字符串“bfce”的路径，但矩阵中不包含字符串“abfb”的路径，因为字符串的第一个字符b占据了矩阵的第一行第二个各自之后，路径不能再次进入这个格子
>
> a	b	t	g
>
> c	f	c	s
>
> j	d	e	h

==思路== 利用回溯法，在矩阵中先任选一个格子作为起点，设该格子在矩阵中为```ch```，且这个格子将对应于路径上的第```i```个字符，如果路径上第```i```个字符不是```ch```，那么这个格子不可能处在路径上的第```i```个位置。反之，到相邻的格子寻找路径上的第```i+1```个字符，除矩阵边界上的格子之外，其他格子都有4个相邻的格子，重复该过程知道路径上所有的字符都在矩阵中找到位置。使用一个堆栈来保存路径节点

使用一个路径节点类来保存路径的坐标位置，方便输出查看。

```java
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

```



```java
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
}

```

---

#### 机器人运动范围

> 例题13 机器人运动范围
>
> 地上有一个m行n列的方格，一个机器人从（0，0）的位置开始移动，每次可以左，右，下，上移动一格，但不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k大于18时，机器人能够进入方格（35，37），因为3+5+3+7=18，但是他不能进入方格（35，38），因为3+5+3+8=19，请编写函数，求出机器人能够到达多少格子，并输出这个格子

==思路== 和例题12类似，都是先从起始位置开始，判断能否进入该点，然后再向四周发散寻找，并且节点的定义与例题12一致。

```java
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
```

---

#### 剪绳子

> 例题14 有一根长度为n的绳子，请把绳子剪成m段（m，n为整数，且n>1, m>1），每段绳子的长度记为k[0], k[1], ... , k[m]，问k[0], k[1], ... , k[m]的最大乘积是多少，例如，当绳子长度为8时，剪成长度为2，3，3的三段，最大乘积为18

==思路== 设$f(n)$为把长度为$n$的绳子剪成若干段后各长度乘积的最大值。在剪第一刀的时候，有$n-1$种选择，剪出来的长度可能分别为：$1,2,3,...,n-1$因此我们有表达式：$f(n)=max\{f(i)f(n-i)\}$  其中$0<i<n$

若采用迭代的方法，那我们应该先计算$f(2)，f(3)$再得到$f(4),f(5)$最终得到$f(n)$

对于初始情况，我们直接分析并得出结论：

1. 当绳子长度$n<2$时，由于$m>1$那么不可能剪出符合要求的段，此时$f(1)=0$
2. 当绳子长度$n=2$时，只能为```1*1=2```的组合，此使$f(2)=1$
3. 当绳子长度$n=3$时，可以为```1*1*1=1```或者```1*2```的组合，最大为$f(3)=2$
4. 当绳子长度$n>4$时，就可以利用公式$f(n)=f(i)f(n-i)$

```java
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
```

---

#### 二进制中1的个数

> 例题15 实现一个函数，输入一个整数，输出该数二进制表示中1的个数

==思路== 注意到如果输入的数为负数，若一直右移，那么左边为了保持负数，会在高位一直补1，会出现无尽的死循环。正确思路应为先将该数与1进行与操作，判断是否为1，为1的话则1的个数加1，然后把1进行左移一位。

*移位的数位参考数而不是待计算的数*

```java
package problem;

public class ProblemBit {

    public static int numOf1(int number){
        int flag = 1;
        int count = 0;
        while (flag != 0) {
            if ((number & flag) != 0){
                count++;
            }
            flag = flag << 1;
        }
        return count;
    }
}
```

但是若采用上面的解法，会导致一个问题，不管判断多少位的数字，都需要经过32次判断，因为flag位一个32位的整型值

新的思路，把一个整数减去1，再和原整数做与运算，会把该整数最右边的1变成0，那么一个整数的二进制表示中有多少个1，就可以进行多少次这样的操作

分析：例如整数1100，减去1后为1011，再将1100 & 1011得到1000，这样就统计出了一个1

```java
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
}
```

---

#### 数值的整数次方

> 例题16 实现一个乘方函数，不使用任何函数库

==思路== 首先要考虑指数的正负号的问题：

1. 若为整数，则直接进行乘方运算；
2. 若为负数，则先将指数进行相反数处理，然后把结果进行倒数之后返回

考虑到一下公式，可以避免大规模的迭代，
$$
a^n=\begin{cases}
a^{n/2}\cdot a^{n/2}, \quad x为偶数 \\
a^{(n-1)/2}\cdot a^{(n-1)/2}\cdot a ,\quad x为奇数
\end{cases}
$$
通过使用递归来实现

```java
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
```

---

#### 打印从1到最大的n位数

> 例题17 输入数字m，按顺序打印从1到最大的m位十进制数，例如，输入数字3，则应该从1，2，3打印到999

==思路== 由于不确定数字有多少位，不能直接使用long或者int型变量，而应该使用扩展性更好的char型数组来实现任意位数，那么使用char[]来模拟加1操作

```java
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
```

---

#### 删除链表的节点

> 例题18 以O(1)的时间复杂度删除链表种的某一个已经存在节点

==思路== 不能考虑从头到尾遍历找到待删除的节点然后再执行删除操作，我们应该想到：

 把待删除的节点的下一个节点的内容复制到待删除的节点，然后待删除的节点的next属性指向下两个节点，但是，要做如下考虑

1. 如果待删除的节点为中间节点，则直接采用上述方法进行删除
2. 如果待删除的节点为尾节点且链表内只有一个节点，那么直接将头节点设置为```null```即可
3. 如果链表不止一个节点且待删除的节点为尾节点，则必须通过循环找到尾节点，然后进行常规删除，因为该尾节点没有下一个节点

```java
class Node{
        int value;
        Node next;

        public Node() {
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node root;

    public void add(int e){
        if (root == null){
            root = new Node(e, null);
        }else {
            Node cur = root;
            while (cur.next != null){
                cur = cur.next;
            }
            cur.next = new Node(e, null);
        }
    }

    public void printList(){
        Node cur = root;
        delete(root.next.next.next);
        while (cur != null){
            System.out.print(cur.value + "->");
            cur = cur.next;
        }
    }

    /**
     * 以O(1)的时间复杂度删除链表种的某一个已经存在节点
     * @param e
     */
    public void delete(Node e){
        if (e == null || root == null){
            return;
        }
        if (e.next != null){
            Node node = e.next;
            e.value = e.next.value;
            e.next = node.next;
        }else if (root == e){
            root = null;
        }else {
            Node cur = root;
            while (cur.next != e){
                cur = cur.next;
            }
            cur.next = null;
        }
    }
```

---

#### 正则表达式的匹配

> 例题19 实现一个函数用来匹配包含'.','*'的正则表达式。模式匹配中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次数
>
> 例如，字符串'aaa'能够与'a.a','ab*ac*a'匹配，但是不能与'aa.a','ab*a'匹配

==思路== 每次从字符串里面拿一个字符出来进行和模式字符串匹配，如果模式字符串的字符为'.'，可以匹配串中的任意字符，如果模式字符串的字符不为'.'，且模式字符串和源字符串相同，也是互相匹配，那么直接进行后面字符串的匹配。

当第二个字符为'*'时，有三种移动方式

1. 由于'\*'可以匹配0个字符，那么就算'\*'前面的字符没有相等，源字符串不向后移动，而模式字符串向后移动+2
2. 如果'*'前的字符匹配了，模式串可以不动也可以向后移动两个+2，源字符串+1

```java
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
```

---

#### 表示数值的字符串

> 例题21 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分。

==思路== 维护两个游标（p1， p2），一个指向奇数元素，一个指向偶数元素，且在初始化时，指向奇数元素的游标位于第一个元素位置，而指向偶数元素的游标位于最后一个元素的位置。

只要p1在p2前面，就交换p1，p2指向的元素，这样，偶数就被交换到了前面。

```java
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
```

---

#### 链表中倒数第k个节点

> 例题22 输入一个链表，输出链表中倒数第k个元素的值，假定倒数的计数从1开始，即最后一个元素为倒数第一个元素。例如：链表1，2，3，4，5，6。倒数第3个元素为4

==思路== 首先由于链表中没有指向前一个元素的指针，考虑到倒数第k个元素就是正数第n-k+1个元素。那么第一个想到的便是先遍历一遍链表，获取到链表的长度n，再次遍历一次，找到第n-k+1个元素，但是这样复杂度变成了$O(n^2)$ 。

我们考虑使用两个游标来完成一次遍历就找了出来，首先第一个游标(p1)指向根元素，第二个游标(p2)也一样，也指向根元素，第一个游标先向后移动k-1个元素，此时第二个游标开始移动，之后两个游标一起向后移动，当第一个游标移动到链表的末尾时，第二个游标刚好指向第n-k+1个元素，完成了遍历。注意，在第一个游标到达k-1个元素之前，每一次移动都要考虑是否移除了范围，有可能k>n。

```java
public int getLastKNodeValue(int k){
        Node head = root;
        Node behind = null;

        for (int i = 0; i < k - 1; i++) {
            if (head.next != null){
                head = head.next;
            }else {
                return Integer.parseInt(null);
            }
        }
        behind = root;
        while (head.next != null){
            head = head.next;
            behind = behind.next;
        }

        return behind.value;
    }
```

---

#### 链表中环的入口节点

> 例题23 如果一个链表中包含环，如何找到环的入口节点

==思路== 将整体步骤分为两步：

1. 先判断有没有环，若有环，得出环的长度
2. 有了环的长度之后，找到入口点

对于1：使用快慢指针，一个指针一次走两步（fast，即每次调用两次next），另一个指针一次走一步（slow，即每次调用一次next），如果两个指针在慢指针到达链表尾部之前相遇了，说明有环，并返回这个相遇点；反之，没有环。

对于2：利用第一步获取到的相遇点，创建一个辅助指针，让该指针继续向前走，每走一步计数器加1，直到又和相遇点重合，那么计数器的值即为环的长度n，接着使用两个指针p1,p2，p1先走n步，接着p1，p2同时移动，每次一步，直到p1=p2，那么p1或者p2即为环的入口点

```java 
/**
     * 判断链表是否有环，若有环，则返回两个指针相遇的节点
     * @return
     */
    private Node getMeetNode(){
        Node fast = root;
        Node slow = root;
        if (slow == null){
            return null;
        }
        fast = slow.next;
        while (fast != null && slow != null){
            if (fast == slow){
                return fast;
            }
            slow = slow.next;
            fast = fast.next;
            if (fast != null){
                fast = fast.next;
            }
        }
        return null;
    }

    /**
     * 获取环的入口点的值
     * @return
     */
    public int getEntryLoopValue(){
        Node meetNode = getMeetNode();
        if (meetNode == null){
            return Integer.parseInt(null);
        }
        int loopLength = 1;
        Node p = meetNode;
        while (p.next != meetNode){
            p = p.next;
            loopLength++;
        }
        p = root;
        for (int i = 0; i < loopLength; i++) {
            p = p.next;
        }
        Node cur = root;
        while (p != cur){
            p = p.next;
            cur = cur.next;
        }
        return cur.value;
    }
```

---

#### 反转链表

> 例题24 反转一个链表并输出反转列表的头结点的值

==思路== 需要定义三个辅助节点，分别保存当前节点，当前节点的前一个节点和当前节点的后一个节点，先把当前节点和前一个节点断开，让当前节点指向前一个节点，然后再移动到下一个节点，如果下一个节点为空的话，则说明该节点即为反转后的头节点。

```java
/**
     * 反转一个链表并输出反转列表的头结点的值
     * @return
     */
    public int getReverseHead(){
        Node reverseHead = null;
        Node cur = root;
        Node prev = null;
        while (cur != null){
            Node next = cur.next;
            if (next == null){
                reverseHead = cur;
            }
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        root = reverseHead;
        return reverseHead.value;
    }
```

---

#### 合并两个排序的链表

> 例题25 有两个递增排序的链表，合并这两个链表并且使得新链表中的节点仍然是递增排序的例如：
>
> 1-3-5-7；2-4-6-8 合并后为：1-2-3-4-5-6-7-8

==思路== 利用递归的思想，每次都是在两个链表中比较第一个元素，谁小就作为合并后链表的第一个元素。

```java
/**
     * 有两个递增排序的链表，合并这两个链表并且使得新链表中的节点仍然是递增排序的例如：
     *
     * 1-3-5-7；2-4-6-8 合并后为：1-2-3-4-5-6-7-8
     * @param list1
     * @param list2
     * @return
     */
    public static ProblemLinkList.Node mergeWithASEC(ProblemLinkList.Node list1, ProblemLinkList.Node list2){
        if (list1 == null){
            return list2;
        }
        if (list2 == null){
            return list1;
        }
        ProblemLinkList.Node newNode = null;
        if (list1.value < list2.value){
            newNode = list1;
            newNode.next = mergeWithASEC(list1.next, list2);
        }else {
            newNode = list2;
            newNode.next = mergeWithASEC(list1, list2.next);
        }
        return newNode;
    }
```

---

#### 树的子结构

> 例题26 给定两棵二叉树A和B，判断B是不是A的子结构

==思路== 本题应当分两步进行

1. 先以递归的形式判断两棵树的根节点的值是否相同。
2. 当找到一个根节点相同时进行左右子树的分别相同与否的判断，注意null引用和递归的推出条件
3. 当第二步没有发现左右子树分别相同时，返回第一步进行继续查找下一个相同的根节点。

```java
 /**
     * 给定两棵二叉树A和B，判断B是不是A的子结构
     * 该函数判断根节点是不是相同
     * @param root1
     * @param root2
     * @return
     */
    public static boolean hasSubTree(ProblemTree.BinaryTreeNode root1, ProblemTree.BinaryTreeNode root2){
        boolean result = false;
        if (root1 != null && root2 != null){
            if (root1.value == root2.value){
                isSubTree(root1, root2);
            }
            if (!result){
                hasSubTree(root1.left, root2);
            }
            if (!result){
                hasSubTree(root1.right, root2);
            }
        }
        return result;
    }

    /**
     * 该函数判断左子树和右子树是不是分别相同
     * @param root1
     * @param root2
     * @return
     */
    private static boolean isSubTree(ProblemTree.BinaryTreeNode root1, ProblemTree.BinaryTreeNode root2){
        if (root2 == null){
            return true;
        }
        if (root1 == null){
            return false;
        }
        if (root1.value != root2.value){
            return false;
        }
        return isSubTree(root1.left, root2.left) && isSubTree(root1.right, root2.right);
    }
```

---

#### 二叉树的镜像

> 例题27 输入一棵二叉树，输出它的镜像

==思路== 前序遍历该树，遇到所有的非叶子节点都交换他们左右子节点，直到遍历到叶子节点后返回

```java
/**
     * 输入一棵二叉树，输出它的镜像
     * 例如输入为：        8         镜像后的输出为:        8
     *                  / \                            /\
     *                 6  10                         10  6
     *                /\  /\                         /\  /\
     *               5 7 9 11                      11 9 7 5
     */
    public void mirror(){
        mirror(root);
    }

    private void mirror(BinaryTreeNode node) {
        BinaryTreeNode cur = node;
        if (cur == null){
            return;
        }
        if (cur.left == null && cur.right == null){
            return;
        }
        BinaryTreeNode temp = cur.left;
        cur.left = cur.right;
        cur.right = temp;
        if (cur.left != null){
            mirror(cur.left);
        }
        if (cur.right != null){
            mirror(cur.right);
        }
    }
```

---

#### 对称的二叉树

> 例题28 判断一个二叉树是否为对称二叉树

==思路== 前序遍历一个二叉树可以得到一个遍历序列，针对于这个前序遍历，想到使用一个和前序遍历对称的遍历方式，即先父节点然后右子节点的方式进行对称的遍历，注意，把为空的子节点也要加入比较，然后比较每一次遍历的结果即可直到是否是对称的，如果相同均为null或者值相同则为对称的，只要有一个地方不一样就不是对称的。

```java
/**
     * 判断一个二叉树是否为对称二叉树
     * @return
     */
    public boolean isSymmertrical(){
        return isSymmertrical(root, root);
    }

    private boolean isSymmertrical(BinaryTreeNode roo1, BinaryTreeNode roo2) {
        if (roo1 == null && roo2 == null){
            return true;
        }
        if (roo1 == null || roo2 == null){
            return false;
        }
        if (roo1.value != roo2.value){
            return false;
        }
        return isSymmertrical(roo1.left, roo2.right) && isSymmertrical(roo1.right, roo2.left);
    }
```

---

#### 顺时针打印矩阵

> 例题29 输入一个矩阵，按照从里向外以顺时针的方式依次打印每一个数字
>
> 例如输入一下矩阵：
>
> 1	2	3	4
>
> 5	6	7	8
>
> 9	10    11     12
>
> 13     14    15     16  
>
> 打印输出：1 2 3 4 8 12 16 15 14 13 6 5 6 7 11 10

==思路== 把整个打印输出任务看作一圈一圈的打印，先要确定每次打印的开始坐标，如上述例子，第一圈的开始坐标为(0,0)，第二圈为(1, 1)，第三圈为(2, 2)，且通过找规律发现，当满足下列条件时就没有可打印的圈了
$$
n\le start * 2
$$
n为数组的行数或者列数，行列均要满足这个等式就能够退出。

找到圈数退出条件之后，就要考虑打印一圈的方法，分为四个可能的情况：

1. 从左向右打印一行（→）
2. 从上向下打印一行（↓）
3. 从右向左打印一行（←）
4. 从下向上打印一行（↑）

需要分别搞清楚每一个打印方案的约束条件和边界

```java
/**
     * 输入一个矩阵，按照从里向外以顺时针的方式依次打印每一个数字
     *
     * 例如输入一下矩阵：
     *
     * 1	2	3	4
     * 5	6	7	8
     * 9	10  11  12
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
```

---

#### 包含min函数的栈

> 例题30 定义栈的数据结构，在该类型中实现一个能够得到栈的最小值元素的min函数，在该栈中，调用min，push，pop的时间复杂度均为$O(1)​$。

==思路== 当遇到要求时间复杂度为$O(1)$的题目时，首先要想到构造辅助空间的方法，本题目应当构建一个辅助栈，当有新元素压栈时，先判断辅助栈的栈顶元素是否小于该新来的元素，若大于，则将该新元素也压入辅助栈，否则，辅助栈将自己栈顶的元素压入栈。

```java
/**
     * 定义栈的数据结构，在该类型中实现一个能够得到栈的最小值元素的min函数，在该栈中，调用min，push，pop的时间复杂度均为O(1)。
     * @param item
     */
    public void push(Integer item){
        stack1.push(item);
        if (stack2.size() == 0 || item < stack2.peek()){
            stack2.push(item);
        }else {
            stack2.push(stack2.peek());
        }
    }

    public Integer pop(){
        if (stack1.size() <= 0 || stack2.size() <= 0){
            return null;
        }
        stack2.pop();
        return stack1.pop();
    }

    public Integer min(){
        return stack2.peek();
    }
```

---

#### 栈的压入弹出序列

> 例题31 输入两个整数序列，第一个序列表示栈的压入顺序，判断第二个序列是否为栈的弹出顺序，假设压入栈的数字均不相等。例如，{1，2，3，4，5}为压栈序列，{4，5，3，2，1}为正确的弹出序列，而{4，3，5，1，2}不是。

==思路== 同样使用一个辅助栈，如果下一个弹出的数字刚好是栈顶元素，那么直接弹出，如果下一个数字不在栈顶，则把压栈序列中还没有入栈的数字压入辅助栈，直到把下一个需要弹出的数字压入栈为止，如果所有数字都压栈了仍然没有找到下一个弹出的数字，则不为一个弹出序列。

```java
/**
     * 输入两个整数序列，第一个序列表示栈的压入顺序，判断第二个序列是否为栈的弹出顺序，
     * 假设压入栈的数字均不相等。例如，{1，2，3，4，5}为压栈序列，{4，5，3，2，1}为正确的弹出序列，而{4，3，5，1，2}不是。
     * @param push
     * @param pop
     * @return
     */
    public static boolean isPopOrder(int[] push, int[] pop){
        if (push == null || pop == null || push.length == 0 || pop.length == 0){
            return false;
        }
        Stack<Integer> tempStack = new Stack<>();
        int lengthPop = pop.length;
        int lengthPush = push.length;
        int indexPush = 0;
        int indexPop = 0;
        while (indexPop < lengthPop){
            while (tempStack.size() == 0 || tempStack.peek() != pop[indexPop]){
                if (indexPush == lengthPush){
                    break;
                }
                tempStack.push(push[indexPop]);
                indexPush++;
            }
            if (tempStack.peek() != pop[indexPop]){
                return false;
            }
            tempStack.pop();
            indexPop++;
        }
        if (tempStack.size() == 0 && indexPop == lengthPop){
            return true;
        }else {
            return false;
        }
    }
```

---

#### 从上到下打印二叉树

> 例题32 从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印

==思路== 借助一个队列，本题实质上是考察图的广度优先搜索，先将根节点入队，然后出队，并打印，接着如果有左右子树，则分别将左右子树入队，直到队列为空，停止打印

```java
/**
     * 从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印，例如树：
     *                          1
     *                         / \
     *                        2   3
     *                       /   / \
     *                      4   5   6
     *                      \      /
     *                       7    8
     * 打印结果应为1 2 3 4 5 6 7 8
     */
    public void printTreePreOrder(){
        if (root == null){
            return;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.size() != 0){
            BinaryTreeNode node = queue.poll();
            System.out.print(node.value + ", ");
            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }
        }
    }
```

---

> 例题 33 从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印一行

==思路== 同上题一致，借助一个队列，并保存两个变量， 一个用于存储要打印的变量，一个用于存储下一层的节点数目。当从队列里取出一个节点时，要打印的数量减一，并且在这之后去判断左右子树是否为空，不为空则入队且下一下层节点数目加一，直到要打印的数目为0，结束本层，打印回车换行，并把下一层的数目重新赋值给要打印的数目，最后清空下一层的节点数目，结束本层循环。

```java
/**
     * 从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印一行
     *                          1
     *                         / \
     *                        2   3
     *                       /   / \
     *                      4   5   6
     *                      \      /
     *                       7    8
     * 打印结果为：
     * 1
     * 2 3
     * 4 5 6
     * 7 8
     */
    public void printTreePreOrderLine(){
        if (root == null){
            return;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int numPrint = 1;
        int nextLevel = 0;
        while (queue.size() != 0){
            BinaryTreeNode node = queue.poll();
            System.out.print(node.value + " ");
            if (node.left != null){
                queue.offer(node.left);
                nextLevel++;
            }
            if (node.right != null){
                queue.offer(node.right);
                nextLevel++;
            }
            numPrint--;
            if (numPrint == 0){
                System.out.println();
                numPrint = nextLevel;
                nextLevel = 0;
            }
        }
    }
```

---

> 例题34 按照之字形打印二叉树，第一行按从左到右的顺序打印，第二层按照从右到左的顺序打印，第三层再按照从左到右的顺序打印，以此类推

==思路== 注意到奇数层都是从左到右打印，偶数层都是从右到左打印，例如，第二行，先打印3，再打印2，可以看作先将2入栈，再将3入栈，因此按照层数分为两类情况：

1. 奇数层：先入栈左子树再入栈右子树
2. 偶数层：先入栈右子树再入栈左子树

对于上述的任一一种情况，在处理时和正常的宽度优先搜索类似，先从栈中取出，再将子树按对应的顺序入栈到对应情况的栈中。当本栈空时改变层数类型条件。

```java
/**
     * 按照之字形打印二叉树，第一行按从左到右的顺序打印，第二层按照从右到左的顺序打印，第三层再按照从左到右的顺序打印，以此类推
     *                          1
     *                         / \
     *                        2   3
     *                       /   / \
     *                      4   5   6
     *                      \      /
     *                       7    8
     * 打印结果为：
     * 1
     * 3 2
     * 4 5 6
     * 8 7
     */
    public void printTreePreOrderZHI(){
        if (root == null){
            return;
        }
        int current = 0;
        Stack<BinaryTreeNode> odd = new Stack<>();
        Stack<BinaryTreeNode> even = new Stack<>();
        odd.push(root);
        while (odd.size() != 0 || even.size() != 0){
            if (current == 0){
                BinaryTreeNode node = odd.pop();
                System.out.print(node.value + " ");
                if (node.left != null){
                    even.push(node.left);
                }
                if (node.right != null){
                    even.push(node.right);
                }
                if (odd.size() == 0){
                    System.out.println();
                    current = 1 - current;
                }
            }else {
                BinaryTreeNode node = even.pop();
                System.out.print(node.value + " ");
                if (node.right != null){
                    odd.push(node.right);
                }
                if (node.left != null){
                    odd.push(node.left);
                }
                if (even.size() == 0){
                    System.out.println();
                    current = 1 - current;
                }
            }
        }
    }
```

---

#### 二叉搜索树的后序遍历序列

> 例题35 输入一个整形数组，判断该数组是否为一个二叉搜索树的后序遍历，假设输入的数字互不相同。例如输入{5，7，6，9，11，10，8}返回true，若输入为{7，4，6，5}返回false。

==思路== 首先通过后序遍历的序列确定该序列的根，序列的最后一个元素即为该子树的根。然后数组的前半部分为树的左子树且都小于根元素，数组的后半部分为树的右子树且都大于根元素。接着使用递归的思想，不断缩小判断的子树范围。

```java
/**
     * 输入一个整形数组，判断该数组是否为一个二叉搜索树的后序遍历，假设输入的数字互不相同。
     * 例如输入{5，7，6，9，11，10，8}返回true，若输入为{7，4，6，5}返回false。
     * @param seq
     * @return
     */
    public static boolean isBST(int[] seq){
        if (seq == null || seq.length == 0){
            return false;
        }
        int length = seq.length;
        int root = seq[seq.length - 1];
        int index = 0;
        for (int i = 0; i < length - 1; i++) {
            if (seq[i] > root){
                break;
            }
            index++;
        }
        int start = index;
        for (int i = start; i < length - 1; i++) {
            if (seq[i] < root){
                return false;
            }
        }
        boolean left = true;
        if (start > 0){
            left = isBST(seq, 0, start);
        }
        boolean right = true;
        if (start < length - 1){
            right = isBST(seq, start + 1, length - 1);
        }
        return left && right;
    }

    public static boolean isBST(int[] seq, int start, int end){
        if (seq == null || seq.length == 0){
            return false;
        }
        int index = start;
        int root = seq[end - 1];
        for (int i = start; i < end - 1; i++) {
            if (seq[i] > root){
                break;
            }
            index++;
        }
        int j = index;
        for (int i = j; i < end - 1; i++) {
            if (seq[i] < root){
                return false;
            }
        }
        boolean left = true;
        if (start > 0){
            left = isBST(seq, 0, start);
        }
        boolean right = true;
        if (start < end - 1){
            right = isBST(seq, start + 1, end - 1);
        }
        return left && right;
    }
```

---

#### 二叉树中和为某一值的路径

> 例题36 输入一个二叉树，打印出二叉树中节点值的和为输入整数的所有路径，从树的根节点开始往下一直到叶节点所经过的节点形成一条路径

==思路== 由于要从根节点开始变遍历，因此采用前序遍历的方案。将该节点值加上目前节点值，和目标值进行比较，如果相同且为叶子节点，则直接输出这条路径。否则若不是叶子节点，则判断是否有左右节点，并将左右节点以递归的形式加入判断，查找完了一次完整的路径后，需要回溯回上一个父节点，因此采用递归的和压栈的形式模拟回溯。将每一次加入判断的点压栈，需要向上回溯时进行出栈即可。

```java
/**
     * 输入一个二叉树，打印出二叉树中节点值的和为输入整数的所有路径，从树的根节点开始往下一直到叶节点所经过的节点形成一条路径
     * @param expectSum
     */
    public void findSumPath(int expectSum){
        if (root == null){
            return;
        }
        Stack<Integer> path = new Stack<>();
        int curSum = 0;
        findSumPath(path, expectSum, curSum, root);
    }

    private void findSumPath(Stack<Integer> path, int expectSum, int curSum, BinaryTreeNode node) {
        curSum += node.value;
        path.push(node.value);
        boolean isLeaf = node.left == null && node.right == null;
        if (curSum == expectSum && isLeaf == true){
            System.out.println("A path founded:");
            Iterator<Integer> iterator = path.iterator();
            while (iterator.hasNext()){
                System.out.print(iterator.next() + " -> ");
            }
            System.out.println();
        }
        if (node.left != null){
            findSumPath(path, expectSum, curSum, node.left);
        }
        if (node.right != null){
            findSumPath(path, expectSum, curSum, node.right);
        }
        path.pop();
    }
```

---

#### 复杂链表的复制

> 例题37 复制一个复杂链表，每个节点除了有指向下一个节点的指针next之外还有一个指向链表中任意节点的sibling。

==思路== 分三步的策略：

1. 先无视sibling属性，整体先复制一份链表，每一个节点复制出来的新节点就跟在原始节点的后面，克隆节点的后面跟着原始的下一个节点，代码如下：

```java
private void cloneNode(){
        Node node = root;
        while (node != null){
            Node clone = new Node();
            clone.value = node.value;
            clone.next = node.next;
            node.next = clone;
            node = clone.next;
        }
    }
```

2. 复制原先链表中的sibling属性，由于已经复制了一份原先的所有节点，那么克隆后的sibling属性就为原先节点的sibling属性的下一个节点。代码如下：

```java
private void connectSibling(){
        Node node = root;
        while (node != null){
            Node sibling = node.next;
            if (node.slibling != null){
                sibling.slibling = node.slibling.next;
            }
            node = sibling.next;
        }
    }
```

3. 最后需要断开整个链表，由于奇数元素均为原始链表，偶数元素均为克隆的链表。因此，二者可以交替查找，整个链表的头为原始链表的头，原始链表的头的下一个节点为克隆链表的头，接着克隆链表的下一个节点为原始节点的下一个节点，而原始节点的下一个节点又为克隆链表的下一个节点，以此类推。代码如下：

```java
private Node reconnectList(){
        Node cur = root;
        Node cloneRoot = null;
        Node clone = null;
        if (cur != null){
            cloneRoot = cur.next;
            clone = cloneRoot;
            cur = cloneRoot.next;
        }
        while (cur != null){
            clone.next = cur.next;
            clone = clone.next;
            cur.next = clone.next;
            cur = cur.next;
        }
        return cloneRoot;
    }
```

4. 最后整合到一个方法中：

```java
/**
     * 复制一个复杂链表，每个节点除了有指向下一个节点的指针next之外还有一个指向链表中任意节点的sibling。
     * @return
     */
    public Node cloneList(){
        cloneNode();
        connectSibling();
        return reconnectList();
    }
```

---

#### 二叉搜索树与双向链表

> 例题37 输入一棵二叉树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的节点，智能调整树中节点指针的指向。

---

#### 序列化二叉树

> 例题38 实现两个函数，分别用来序列化和反序列化二叉树

==思路== 序列化的思路：

通过前序遍历，从根节点开始，使用递归的方式进行遍历，若碰到null指针，则表述成一个特殊字符

```java
public String serializeBST(){
        StringBuilder builder = new StringBuilder();
        serializeBST(root, builder);
        return builder.toString();
    }

    private void serializeBST(BinaryTreeNode node, StringBuilder builder) {
        if (node == null){
            builder.append("$");
            return;
        }
        builder.append(node.value);
        serializeBST(node.left, builder);
        serializeBST(node.right, builder);
    }
```

反序列化的思路：

从一个字符串读入序列化之后的值，也是采用前序遍历的方式进行递归，若没有遇到预定的特殊字符，则不断进行左子树递归和右子树递归，直到遇到一个特殊字符

```java
public BinaryTreeNode deserializeBST(String stream){
        return deserializeBST(stream, 0);
    }

    public BinaryTreeNode deserializeBST(String stream, int index){
        if (stream.charAt(index) != '$'){
            int value = Integer.parseInt(String.valueOf(stream.charAt(index)));
            BinaryTreeNode root = new BinaryTreeNode(value);
            index++;
            root.left = deserializeBST(stream, index);
            root.right = deserializeBST(stream, index);
            return root;
        }
        return null;
    }
```

---

#### 字符串的排列

> 例题39 输入一个字符串，打印出该字符串中字符的所有排列，例如，输入字符串abc，则打印出由a、b、c所能排列出来的所有字符串abc、acb、bac、bca、cab和cba。

==思路== 具体状态图如下图所示：

 ![avatar](./sword-image/permutation.png)

每次都是先固定第一个字符，然后求解后面字符的排列，之后每次固定剩余字符中的第一个字符。

```java
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
```

---

#### 数组中出现次数超过一半的数字

> 例题40 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如，输入一个长度为9的数组，{1，2，3，2，2，2，5，4，2}，由于数字2出现了5次，超过数组长度的一半，因此输出2

==思路== 注意到：数组中如果一个数字出现的次数超过数组长度的一半，那么它出现的次数比其他所有数字出现的次数都要多。因此，遍历一遍数组，保存两个值，一个是目前数组中的数字，另一个是出现的次数，若下一个数字为上一个数字，则次数加一，否则，次数减一，若次数为0，那么保存的数字为下一个数字，不再保存上一个数字。

```java
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
```

---

#### 最小的K个数

> 例题41 输入n个整数，找出其中最小的k个数，例如，输入4，5，1，6，2，7，3，8.则最小的4个数字是1，2，3，4

==思路== 维护一个长度为k的大顶堆或者红黑树，每次从数组中读入一个元素。进行一下判断操作：

1. 若大顶堆或红黑树的大小小于k，则直接将该数字存入数据结构
2. 若大于k，取出容器中的最大值，若该数字大于最大值，则直接略过该数字，进行下一个数字的判断
3. 反之，删除容器内的最大元素，将当前元素存储容器中。

```java
/**
     * 输入n个整数，找出其中最小的k个数，例如，输入4，5，1，6，2，7，3，8.则最小的4个数字是1，2，3，4
     * @param data
     * @param k
     * @return
     */
    public static TreeSet<Integer> getMaxOfK(int[] data, int k){
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < data.length; i++) {
            if (set.size() < k){
                set.add(data[i]);
            }else {
                Integer value = set.last();
                if (data[i] < value){
                    set.pollLast();
                    set.add(data[i]);
                }
            }
        }
        return set;
    }
```

#### 数据流中的中位数

> 例题42 如何得到一个数据流中的中位数，如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值

==思路== 将整个数组分为两个部分，左边为比较小的数，右边为比较大的数，且左边最大的数要比右边最小的数小，那么可以考虑使用两个堆来完成这一功能。

- 左边为大顶堆，右边为小顶堆
- 使用jdk自带的```TreeSet```类来实现大顶堆小顶堆，对于大顶堆，访问堆顶用```getLast()```，对于小顶堆，访问堆顶用```getFirst()```。

且需要保证大顶堆和小顶堆之间的元素数目之差不超过1，那么在插入总数目为偶数的时候插入小顶堆，插入总数目为奇数的时候插入大顶堆，且要注意一下调整。

1. 当插入的数小于大顶堆的最大数时，应当先将其插入大顶堆中，再把大顶堆的堆顶取出来插入小顶堆中
2. 当插入的数小于小顶堆的最小值时，应当先将其插入小顶堆中，并将小顶堆的最小数插入大顶堆中

```java
/**
     * 如何得到一个数据流中的中位数，如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
     * 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值
     * @param data
     * @return
     */
    public static int getMedian(int[] data){
        TreeSet<Integer> max = new TreeSet<>();
        TreeSet<Integer> min = new TreeSet<>();
        for (int i = 0; i < data.length; i++) {
            int num = data[i];
            if (((max.size() + min.size()) & 1) == 0){
                if (max.size() > 0 && data[i] < max.last()){
                    max.add(data[i]);
                    num = max.last();
                    max.pollLast();
                }
                min.add(num);
            }else{
                if (min.size() > 0 && min.first() < data[i]){
                    min.add(data[i]);
                    num = min.first();
                    min.pollFirst();
                }
                max.add(num);
            }
        }
        int size = min.size() + max.size();
        int result = 0;
        Iterator<Integer> minIterator = min.iterator();
        Iterator<Integer> maxInterator = max.iterator();
        while (minIterator.hasNext()){
            System.out.print(minIterator.next() + " ");
        }
        System.out.println();
        while (maxInterator.hasNext()){
            System.out.print(maxInterator.next() + " ");
        }
        System.out.println();
        if ((size & 1) == 1){
            return min.first();
        }else {
            return (min.first() + max.last()) / 2;
        }
```

---

#### 连续子数组的最大和

> 例题43 输入一个整形数组，数组里有正数也有负数。数组中的一个或连续多个整数组成一个子数组，求所有子数组的和的最大值。

==思路== 从数组的开始进行累加，当加的数字出现了负数，那么加上下一个数字之后也会比下一个数字更小，因此放弃以之前开头的数字，而最大值只可能出现在目前的数字开始的子数组中。并保存目前的最大和，若发现下一次的加和后的结果大于目前的最大和，那么更新当最大和的值。

```java
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
```

---

#### 1~n整数中1出现的次数

> 例题44 输入一个整数n，求1~n这n个整数的十进制表示中1出现的次数，例如，输入12，1-12这些整数中包含1的数字有1，10，11，12，“1”一共出现了5次

==思路== 分析数字的出现规律，例如大数：**21345**

分为三步去考察：

1. 对于第一位数字固定后所包含的所有数字，例如，21345可以看作1346~21345，正好把整个数字分为两个部分，1-1345和1346-21345，且也是除去第一个数字这样的分法。那在1346-21345中，对于首位数大于1，则首位数为1的次数$c_1$为：

$$
c_1=10^{n-1},n为数字长度
$$

如果首位数字等于1，例如在10000-12345中，首位只出现了2345次，因此$c_1$为去掉首位的剩余数字

2. 在计算完首位为1的次数后，计算在首位固定的情况下剩余部分的为1的次数$c_2$，

$$
c_2=n*C^{1}_{n-1}*(C^{1}_{10})^{n-2},n为数字长度
$$

3. 最后计算1-1346，即除去首位后的数字的所有的1的和$c_3$，进行递归
4. 递归结束的条件为只有一位数字，当首位为0时，返回0，首位大于0时，返回1
5. 整个计算结果就为：$c_1+c_2+c_3$

```java
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
```

---

#### 数字序列中某一位的数字

> 例题45 数字以0123456789101112131415...的格式序列化到一个字符序列中。在这个序列中，第5位（从0开始计数）是5，第13位是1，第19位是4等等。求第n位对应的数字

==思路== 可以找出数字排列的某些规律，有选择性的跳过一些规律。。以数字1001为例：

首先由于个位数只有10个，10<1001，因此不在这10个数里面，那么看剩下1001-10=991个数字在哪；接着2位数的数字个数位90个共计180位数字，由于991>180，因此，不在2位数的范围内，991-180=811，那么看剩下的811的位数在哪；由于3位数的数字个数位2700个，2700>811，因此，落在三位数的范围内，又因为：811=270*3+1，说明，第811位是在从100开始的第270个数（370）的第二位，那么为7

```java
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
```

---

#### 把数组排成最小的数

> 例题46 输入一个正整数数组，把数组里面所有数字拼起来排成一个数，打印能拼接出的所有数字中最小的一个，例如：输入数组{3，32，321}，则打印出这3个数能排成的最小的数字321323

==思路== 将数组里面的数变为字符串，我们可以这么考虑，例如3,32，将这两个数合并，得到两种情况：332和323，只要比较这两数的大小，确定出最小的，接着比较第二个和第三个数的合并后的大小，一次类推，就能将原数组变为拼合按最小的排列了。

```java
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
```

---

#### 把数字翻译成字符串

> 例题47 给定一个数字，我们按照如下规则把它翻译成字符串：0翻译成“a”，1翻译成“b”，。。。11翻译成“l”，25翻译成“z”，一个数字可能有多种翻译，例如，12258有5中不同的翻译，分别为“bccfi”、“bwfi”、“bczi”、“mcfi”和“mzi”。实现一个函数，用来计算一个数字有多少种不同的翻译方法

==思路==：利用递归的思想，但是注意要从数字的尾部开始进行递归。
$$
f(i)=f(i-1)+g(i,i-1)*f(i-2)
$$
$$f(i)$$表示从倒数第$$i$$位数字开始的不同的翻译数目，以12258为例，答案为5，分析如下：count为当前的方法数，counts为数字长度的数组，记录了每一个数字的方法数。

1. 从最后一位开始---8，count=1，counts=[0,0,0,0,1]
2. 倒数第二位---58，count=1，counts=[0,0,0,1,1]
3. 倒数第三位---258，此时25在10~25内，有两种方案，因此count=3，counts=[0,0,2,1,1]
4. 以此类推，最后counts=[5,3,2,1,1]

```java
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
```

---

#### 礼物的最大价值

> 例题48 在一个mxn的棋盘的每一个格子都放有一个礼物，每一个礼物都有一定价值（价值大于0）。你可以从棋盘的左上角开始拿格子里面的礼物，并每次向右或者向下移动一格，直到到达棋盘的右下角。给定一个棋盘及其上面的礼物，请计算你最多能拿到多少价值的礼物。

==思路== 利用动态规划的思想。设$f(i,j)$为到达坐标$(i,j)$的格子时索能拿到的最大的礼物值。我们仅仅只有两种方法能够获得这个格子，

通过格子$f(i-1,j)$或者$f(i, j-1)$，所以有通项公式：$f(i,j)=max(f(i-1,j),f(i,j-1))+gift[i,j]$，其中。max部分表示前面的最大值，而后面是当前格子的礼物价值

因此考虑构建二维数组，坐标中的$(i,j)$元素表示到达坐标为$(i,j)$格子时所能拿到的礼物的最大价值

```java
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
```

---

#### 最长不含重复字符的子字符串

> 例题49 请从字符串中找出一个最长的不包含重复字符串的子字符串，计算该最长子字符串的长度。假设字符串中只包含'a'~'z'的字符。例如，在字符串"arabcacfr"中，最长的不含重复字符的子字符串是"acfr"，长度为4

==思路== 我们注意到，首先需要有一个保存每一个字符在上一次出现的下标的数组，并且用$f(i)$来表示以第$i$个字符为结尾的不包含重复字符的子字符串的最长长度，用$d$表示第$i$个字符出现时和它上次出现在字符串中的位置的距离

1. 如果字符ch没有出现过

   1. 则直接$f(i)=f(i-1)+1$

2. 如果字符ch已经出现过

   1. 计算距离$d$

   2. 如果$d \le f(i-1​$)

      $f(i)=d$

   3. 否则

      $f(i)=f(i-1)+1$

```java
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
                ++curLength;  //i - prevIndex就是上述思路中说的距离d
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
```

---

#### 丑数

> 例题50 我们把只包含因子2、3、5的数称作丑数，求按从小到大的顺序的第1500个丑数，例如6、8都是丑数，但是14不是。因为它包含因子7。习惯上1也是丑数

==思路== 考虑用空间换时间的思路。首先最开始的丑数是已知的---1，并且下一个丑数\*2也是丑数，同理，\*3也是丑数，那么\*5也是丑数。所以一开始约定一个本轮次最大的丑数$M$，记录下\*2时超过$M$的数和下标，分别记为：$M_2$和$i_2$，\*3和\*5同理为$M_3$和 $i_3$，$M_5$和$i_5$，根据这三个值里面的最小值来确定下一次的边界

```java
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
```

---

#### 第一个只出现一次的字符

> 例题51 在字符串中找出第一个只出现一次的字符，如输入“abaccdeff”，则输出'b'

==思路== 注意到这个字符是可打印的英文字符，因此我们只需要创建一个长度为256的int数组来记录每一个字符出现的次数即可，数组的下标为出现的字符，作为键，而当前下标的值作为该祖父出现的次数。

第一次遍历整个字符串设置好每个字符的出现次数，第二次遍历的时候按照字符串中字符出现的顺序去读这个数组，找到第一次值为1的下标，返回。

```java
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
```

---

> 例题52 实现一个函数，用来找出字符流中第一个只出现一次的字符，例如，当字符流读到“go”时，第一个只出现的字符是'g'，当从字符流读到“google”时，第一个只出现一次的字符是'l'

==思路== 实现思路和题50一致，只是每次在插入时就是检查是否并返回第一个只出现一次的字符

```java
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
```

---

#### 数组中的逆序对

> 例题53 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。例如，在数组{7, 5, 6, 4}中，一共存在5个逆序对，分别为：(7, 6), (7, 5), (7, 4), (6, 4), (5, 4)

==思路== 可先将这个数组进行两两分组，并归并排序，每次排序后计算本次中的逆序对。例如：

57  46这个组合中，从后往前看，7比6大，而且6的下标为第二个数组中的第二个，即index=1，而我们知道这两个数组都是经过了排序的，因此，7比第二数组的两个数都要大，因此存在两个逆序对。所以，count += index - start - length；index为目前第二个数组的下标，start为第一个数组的开始的下标，length为这个区段数组的长度。

```java
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
```

---

#### 两个链表的第一个公共节点

> 例题54 输入两个链表，找出他们的第一个公共节点。节点定义如下
>
> ```java
> class Node{
>     int value;
>     Node next;
> }
> ```

==思路== 既然是两个链表有共同节点，，由于是单向表的链表，那么在有一个公共节点之后，后面的节点都会重合，因此两个链表会形成一个Y形，而不会形成X形。因此可以先确定两个链表谁长，长的那个先走多出来的步数，然后再一起移动，直到碰到的第一个相同的节点就可以返回了，即找到了第一个共同的节点。

```java
/**
     * 输入两个链表，找出他们的第一个公共节点。节点定义如下
     * @param target
     * @return
     */
    public Node getFirstCommonNode(ProblemLinkList target){
        int thisLength = size();
        int targetLength = target.size();
        Node longCur = root;
        Node shortCur = target.getRoot();
        int lengthDiff = thisLength - targetLength;
        if (thisLength  < targetLength){
            lengthDiff = targetLength - thisLength;
            longCur = target.getRoot();
            shortCur = root;
        }
        for (int i = 0; i < lengthDiff; i++) {
            longCur = longCur.next;
        }
        while (longCur != null && shortCur != null && longCur != shortCur){
            longCur = longCur.next;
            shortCur = shortCur.next;
        }
        Node res = longCur;
        return res;
    }

    public int size(){
        int size = 0;
        Node cur = root;
        while (cur != null){
            size++;
            cur = cur.next;
        }
        return size;
    }
```

---

#### 在排序数组中查找数字

##### 数字在排序数组中出现的次数

> 例题55 统计一个数字在排序数组中出现的次数。例如：输入排序数组{1， 2， 3， 3，3， 3， 4， 5}和数字3，由于3在这个数字中出现了4次，因此输出4

==思路== 采用两个二分法找出这个数字在第一次出现的下标和最后一次出现的下标。然后进行坐标相减+1就能得到结果

```java
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
```

##### 0~n-1中缺失的数字

> 例题56 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0\~n-1内。在范围0\~n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字

==思路== 可以继续采用二分法查找找出这个漏掉的数字：考虑到数组已经按增序排列，那么第m个数字应该在下标为m的地方，如果第k个数不在数组中，那么k+1应该是在k下标处，因此只要找出数组中第一个值和下标不相等的元素。

1. 如果中间元素值和下标相等，那么下一轮查找只需要查找右边即可；
2. 如果中间元素和下标不相等，且前一个元素和下标相等，那么中间这个数字正好是第一个下标和元素值不一致的元素，直接返回该下标即为丢失的元素；
3. 如果中间的元素和下标不相等，且前一个元素也不相等，那么下一轮查找在前半部分找即可。

```java
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
```

##### 数组中数值和下标相等的元素

> 例题57 假设一个单调递增的数组里面的每个元素都是整数并且是唯一的。请写一个函数，找出数组中任意一个数值等于其下标的元素。例如：在数组{-3, -1, 1, 3, 5}中，数字3和它的下标相等

==思路== 同样采用二分法查找。如果mid正好是和下标相等，那么直接返回这个mid，如果mid>index

，那么说明mid之后的数都是大于它的下标的，所以需要再左边进行查找。反之，需要在右边查找

```java
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
```

---

#### 二叉搜索树的第K大节点

> 例题58 给定一个二叉搜索树，请找出其中第K大的节点。例如，在图中的二叉搜索树里，
>
> ```java
> *                       5
> *                      / \
> *                     3   7
> *                    /\  /\
> *                   2 4 6  8
> ```
>
> 按照节点数值大小顺序，第3大节点的值为4

==思路== 由于给定了一个二叉搜索树，证明里面已经被排好序了，因此对其进行中序遍历就能得到排序完成的数组。注意在传递数值k的时候不能传数值类型，而应该包装成一个类

```java
/**
     * 给定一个二叉搜索树，请找出其中第K大的节点。例如，在图中的二叉搜索树里，按照节点数值大小顺序，第3大节点的值为4
     *                       5
     *                      / \
     *                     3   7
     *                    /\  /\
     *                   2 4 6  8
     * @param k
     * @return
     */
    public BinaryTreeNode getKthNode(int k){
        if (k == 0){
            return null;
        }
        return getKthNodeCore(root, new Value(k));
    }

    public class Value{
        int value;

        public Value(int value) {
            this.value = value;
        }
    }

    private BinaryTreeNode getKthNodeCore(BinaryTreeNode cur, Value k) {
        BinaryTreeNode target = null;
        if (cur.left != null){
            target = getKthNodeCore(cur.left, k);
        }
        if (target == null){
            if (k.value == 1){
                target = cur;
            }
            k.value--;
        }
        if (target == null && cur.right != null){
            target = getKthNodeCore(cur.right, k);
        }
        return target;
    }
```

---

#### 二叉树的深度

##### 二叉树的深度

> 例题59 输入一棵二叉树的根节点，求该树的深度。从根节点到叶节点一依次经过的节点（含根、叶节点）形成树的一条路径，最长路径的长度为树的深度

==思路== 利用递归的思想，如果一棵树只有一个节点，那么其深度为1，如果根节点只有左子树没有右子树，那么深度就是左子树加1，反之，那就是右子树的深度加1。如果既有左子树又有右子树，那么为更深的深度加1.

```java
/**
     * 输入一棵二叉树的根节点，求该树的深度。
     * 从根节点到叶节点一依次经过的节点（含根、叶节点）形成树的一条路径，最长路径的长度为树的深度
     * @return
     */
    public int getDepth(){
        return getDepth(root);
    }

    private int getDepth(BinaryTreeNode node){
        if (node == null){
            return 0;
        }
        int left = getDepth(node.left);
        int right = getDepth(node.right);
        return (left > right) ? (left + 1) : (right + 1);
    }
```

##### 平衡二叉树

> 例题60 输入一棵二叉树的根节点，判断该树是不是平衡二叉树。如果某二叉树中任意节点的左右子树的深度相差不超过1，那么他就是一棵平衡二叉树

==思路== 采用后序遍历，先一直递归到左叶子和右叶子，然后每遍历一个就判断一次是不是为平衡二叉树。这样利用后序遍历的方式，可以避免重复遍历某些根节点。

```java
/**
     * 输入一棵二叉树的根节点，判断该树是不是平衡二叉树。
     * 如果某二叉树中任意节点的左右子树的深度相差不超过1，那么他就是一棵平衡二叉树
     * @return
     */
    public boolean isBalanced(){
        return isBalanced(root, new Value(0));
    }

    private boolean isBalanced(BinaryTreeNode node, Value depth) {
        if (node == null){
            depth.value = 0;
            return true;
        }
        Value left = new Value(0);
        Value right = new Value(0);
        if (isBalanced(node.left, left) && isBalanced(node.right, right)){
            int diff = left.value - right.value;
            if (diff <= 1 && diff >= -1){
                depth.value = 1 + (left.value > right.value ? left.value : right.value);
                return true;
            }
        }
        return false;
    }
```

---

#### 数组中数字出现的次数

##### 数组中只出现一次的两个数字

> 例题61 一个整型数组里除了两个数字之外，其他数字都出现了两次。请找出这两个只出现一次的数字。

==思路== 首先想到如何在一个数组里面找到只出现一次的数字：利用异或的原理，任何一个数字异或他自己都等于0.那么从头到尾异或每一个数字，结果就是只出现一次的数字，因为其他出现两次的数字都自我两两异或等于0了。

接着解决如何找到两个出现一次的数字。

1. 先遍历一遍数组，找到异或的结果。由于有两个出现一次的数字，那么结果肯定有某些位是1.那么以这个结果的第一次出现的1为分界，将整个数组分为两个部分，前面一部分为该位为0，后一部分为该位为1.
2. 如此一来，就能将整个数组中只出现一次的数字的两个数字分在了两个数组中。因为这两个数字是肯定不一样的，所以会被分到两个不同的组中
3. 接着在这两个组中分别进行每个数字异或就可以了。

```java
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
```

##### 数组中唯一出现一次的数字

> 例题62 在一个数组中除了一个数字只出现一次之外，其他数字出现了三次。请找出那个只出现一次的数字。

==思路== 如果一个数字出现了3次，那么它的二进制表示的每一位也将会出现3次，如果我们把所有出现3次的数字的二进制表示的每一位分别相加，那么每一位的和都能被3整除。接着我们再加上单独出现的一个数字，那么把所有每一位的二进制表示加起来，得到一个总的32位长度的表示数组，如果某一位能被3整除，那么单独的那个数字的这一位为0，反之为1，因为出现的次数都是3或1，所以相加后要么为3，要么为4

```java
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
```

---

#### 和为s的数字

##### 和为s的两个数字

> 例题63 输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和为s，则输出任意一对即可

==思路== 由于这个数组是一个已经排好序的增序数组。那么用两个指针，一个指向数组头部（最小的元素），一个指向数组的尾部（最大的元素）。每次比较这两个指针相加的和，如果小于目标，那么把小指针向右边移动一格，反之，大指针向左边移动一格。

```java
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
```

##### 和为s的连续正数序列

> 例题64 输入一个正数s，打印出所有和为s的连续正数序列（至少含有两个数）。例如，输入15，由于1+2+3+4+5=4+5+6=7+8=15，所以打印出3个连续序列1\~5,4\~6，和7-8

==思路== 同样维持两个游标，这次一个指向第一个，一个指向第二个，首先计算这两者指针指向的数字之和，如果等于目标数字就直接填充进返回值里面，否则，如果小于目标值，那么大端指针向右移动一格，继续比较，如果大于目标值，那么小端指针向右移动一格，继续比较，直到小端指针增加到和的一半为止。

例如：目标和为9，

那么从1,2开始，一开始为3，小于9，则大端右移一格，1-3，之前相加的数为3，保留，再加上3，为6还是小于，则继续右移到4，发现此时和为10，大了，那么小端向右移动一格，2-4，此时为9，那么保留这个组合填充进返回值。继续大端右移，2-5，为14，大了，小端右移，3-5，为12，还是大了，小端继续右移，4-5，为9，最后直到移到(9+1)/2的位置结束。

```java
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
```

---

#### 翻转字符串

##### 翻转单词顺序

> 例题65 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串“I am a student.”则输出“student. a am I”

==思路== 利用Java的String类型自带的split方法，将原始字符串按照空格进行分割，然后反向拼接就能得到所需的倒转字符串

```java
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
```

##### 左旋转字符串

> 例题66 字符串的坐旋转操作是把字符串前面的若干个字符转移到字符串的尾部。例如，输入字符串“abcdefg”和数字2，该函数将返回左旋转两位得到的结果“cdefgab”

==思路== 以abcdefg为例，可以先将原来的字符串分为两个部分，一个是ab，一个是cdefg，先将这两个字符串分别翻转，得到：ba，gfedc，合并这两个字符串再一起翻转就能得到所要的字符串cdefgab

```java
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
```

---

#### 队列的最大值

##### 滑动窗口的最大值

> 例题67 给定一个数组和滑动窗口的大小，请找出所有滑动窗口里面的最大值。例如，输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}

==思路== 我们采用双端队列来实现这个功能，不是每次都把三个数放进窗口在进行比较大小，而是让窗口一步一步往右移，使得最大值都存在于队列的头部，向里面插入数据的时候始终插入到队列的尾部。

以题目中的数组为例，注意，存放的是下标而不是数值，这样做是因为可以方便的比较队列的最前头的数是否已经被移除窗口了，只需要比较它的下标和(i-size)就可以了

1. 2入队列， 队列中[2(0)]
2. 当3准备入队列时，发现2比它小，那么2不可能是这一轮窗口的最大值，先移除，再把3入队列，队列中[3(1)]
3. 4要入队列和3入队列情况相同，3要出去，4再进去，此时第一个窗口已经形成，队列中[4(2)]
4. 这时候不再按照上述一次循环窗口大小的次数，而是每读入一个数字，就相当于窗口向右滑动一格。每次在开始的时候把队列的头部元素加入到最大值的链表，因为每一轮都是头部的元素为最大值
5. 在新读入的数进入队列之前，需要先判断队列中的数是不是小于这个数，要把小于这个数的元素出队列，因为他们不可能是这一轮的最大数了
6. 移除了小于新读入的队列中的数后还需要再次判断存留在队列中的最大值，也就是头部的元素是不是已经被移除窗口了，判断条件是看它的下标是不是小于了当前循环下标和窗口大小的差值，如果小于，那么也要移除
7. 最后判断完了5,6步之后把新的数字读进队列。
8. 最后循环结束后还要手动将队列中的最大值插进链表中，作为最后一轮的最大值

```java
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
```

##### 队列中的最大值

> 例题68 定义一个队列并实现函数max得到对垒的最大值，要求push，pop和max的复杂度都为$O(1)$

==思路== 参考67的实现，也是构造一个辅助队列来完成队列的最大值操作，实现思路和67题完全一致，只是少了窗口大小的比较

```java
package problem;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class ProblemQueue {

    public class QueueWithMax<T extends Comparable>{
        class QueueNode<T extends Comparable>{
            T data;
            int index;

            public QueueNode() {
            }

            public QueueNode(T data, int index) {
                this.data = data;
                this.index = index;
            }
        }

        private LinkedList<QueueNode> data = new LinkedList<>();
        private LinkedList<QueueNode> maxs = new LinkedList<>();
        private int curIndex = 0;

        public void offer(T item){
            while (!maxs.isEmpty() && item.compareTo(maxs.getLast().data) > 0){
                maxs.pollLast();
            }
            QueueNode node = new QueueNode(item, curIndex);
            data.offerLast(node);
            maxs.offerLast(node);
            curIndex++;
        }

        public T pop() throws Exception {
            if (maxs.isEmpty() || data.isEmpty()){
                throw new Exception("队列为空");
            }
            if (maxs.getFirst().index == data.getFirst().index){
                maxs.pollFirst();
            }
            return (T) data.pollFirst().data;
        }

        public T max() throws Exception {
            if (maxs.isEmpty()){
                throw new Exception("队列为空");
            }
            return (T) maxs.getFirst().data;
        }
    }
}

```

---

#### n个骰子的点数

> 例题69 把n个骰子扔在地上，所有骰子朝上的一面的点数之和为s。输入n，打印s的所有可能的值的概率

==思路== 采用两个数组存放骰子点数的每个总数出现的次数，第一轮中，第一个数组的第n个数字表示骰子和为n出现的次数，在下一轮循环中，加上一个骰子，此时和为n的骰子出现的次数应该为上一轮和为n-1,n-2...n-6的次数之和。但是要记住每次加了一个骰子要加上相应的和的种类

```java
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
```



#### 不用加减乘除做加法

> 例题70 写一个函数，求两个数的和，要求在函数体内不得使用+-*/四则运算符号

==思路== 既然不能用四则运算符号，我们想到使用位运算来进行。位运算肯定要针对二进制表示进行。由于在二进制中，我们不考虑进位的话，0+0=0,1+0=1,1+1=0,0+1=1，这个和异或运算所得到的的结果是一样的。那么我们考虑进位的话，就可以把原来的相加的两个数相与，因为只有出现1+1才会有进位产生。进位会比原来的1+1的位置要左移以为，所以与完左移一位即可。最后结束的条件是没有进位了，就算加完了

```java
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
```

---

#### 构建乘积数组

> 例题71 给定一个数组$A[0,1,...,n-1]​$，请构建一个数组$B[0,1,...n-1]​$，其中$B​$中的元素$B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]​$，不能使用除法

==思路== 可以将$B[i]$看做两部分乘积构成：$B[i]=C[i]*D[i]$，其中$C[i]=A[0]*A[1]*...*A[i-1]$，且$D[i]=A[i+1]*A[i+2]*...*A[n-2]*A[n-1]$，对于$C[i]$可以考虑从上到下的顺序计算，即：$C[i]=C[i-1]*A[i-1]$，而对于$D[i]$可以考虑从下到上的顺序计算，即$D[i]=D[i+1]*A[i+1]$。

```java
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
```

#### 股票的最大利润

> 例题72 假设把某股票的价格按照时间先后顺序存储在数组中，请问买卖该股票一次可能获得的最大利润是多少，例如，一只股票在某些时间节点的价格为{9,11,8,5,7,12,16,14}。如果我们能在价格为5的时候买入并在价格为16时卖出，则能收获最大的利润为11

==思路== 只需要扫描一遍整个数组，每次遍历时，记下当前最小的数min，然后每次都要比较当前的数和min的差值，如果更小则更新maxIntrest。

```java
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
```

---

#### 扑克牌中的顺子

> 例题73 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2\~10为数字本身，A为1，J为11，Q为12，K为13，而大小王可以看成任意数字

==思路== 我们可以考虑这样表示，大小王可以看做0，然后把抽到的5张牌进行排序，然后统计数组中0的个数，因为这些0可以作为任意牌，接下来计算两两之间的间距，如果间距大于了0的个数，说明不能构成顺子，否则，可以构成顺子。

```java
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
```

---

#### 圆圈中最后剩下的数字

> 例题74 $0,1...n-1​$这$n​$个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字。求这个圆圈里剩下的最后一个数字

==思路== 设$f(n,m)$为在$n$个数字中删除第$m$个数字最后剩下的数字。那么第一个被删除的数字是$(m-1)\%n$。那么最初序列最后剩下的数字$f(n,m)$一定是删除一个数字之后的序列最后剩下的数字，即$f(n,m)=f'(n-1,m)$。我们定义一个映射$p$，$p(x)=(x-k-1)\%n$，表示如果映射前的数字是$x$，那么映射后的数字是$(x-k-1)\%n$，那么逆映射是$p^{-1}(x)=(x+k+1)\%n$。

映射之前的序列中最后剩下的数字
$$
f'(n-1,m)=p^{-1}[f(n-1,m)]=[f(n-1,m)+k+1]\%n
$$
把$k=(m-1)\%n$代入得到：
$$
f(n,m)=f'(n-1,m)=[f(n-1,m)+m]\%n
$$
所以我们得到迭代公式：
$$
f(n,m)=\begin{cases}
0 & n=1 \\
[f(n-1,m)+m]\%n & n>1
\end{cases}
$$

```java
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
```

