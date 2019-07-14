package problem;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ProblemTree {

    private BinaryTreeNode root;

    public BinaryTreeNode getRoot() {
        return root;
    }

    public void setRoot(BinaryTreeNode root) {
        this.root = root;
    }

    public class BinaryTreeNode{
        int value;
        BinaryTreeNode left;
        BinaryTreeNode right;
        BinaryTreeNode parent;

        public BinaryTreeNode() {
        }

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
        BinaryTreeNode root = construct(preOder, inOrder, 0, preOder.length - 1,0, inOrder.length - 1);
        this.root = root;
        return root;
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

    /**
     * 给定一个二叉树和其中的一个节点，找出中序遍历序列的下一个节点，树中每一个节点都有一个左节点，右节点和父节点的引用
     * @param root
     * @return
     */
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
    public void printTreePreOrderZHIStack(){
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

    private static boolean isBST(int[] seq, int start, int end){
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

    public void printBack(){
        printBack(root);
    }

    private void printBack(BinaryTreeNode node){
        if (node != null){
            printBack(node.left);
            printBack(node.right);
            System.out.println(node.value + " ");
        }
    }
}
