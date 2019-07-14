package problem;

import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

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

    }
}
