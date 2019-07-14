import problem.*;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        testProbabiity();
    }

    public static void testPromble1_1(){
        Integer[] res = ProblemArray.duplicate(new int[]{2,3,1,0,2,5,3}, 10);
        for (Integer i :
                res) {
            System.out.print(i + " ");
        }
    }

    public static void testPromble1_2(){
        Integer[] res = ProblemArray.duplicateWithoutModify(new int[]{2,3,1,0,2,5,3,5,6,0,8}, 100);
        for (Integer i :
                res) {
            System.out.print(i + " ");
        }
    }

    public static void testProblem1_3(){
        int[][] param = new int[][]{
                new int[]{1,2,8,9},
                new int[]{2,4,9,12},
                new int[]{4,7,10,13},
                new int[]{6,8,11,15}
        };
        Integer[] res = ProblemArray.findInTwoDimensionalArray(param, 4, 4, 7);
        for (Integer i :
                res) {
            System.out.print(i + " ");
        }
    }

    public static void testProblem2_1(){
        System.out.println(ProblemString.replaceBlank("we are family"));
    }

    public static void testProblem3_1(){
        List<Integer> param = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            param.add(i);
        }
        List<Integer> res = ProblemLinkList.printLinkListReserveWithStack(param);
        for (Integer i :
                res) {
            System.out.print(i + " ");
        }
    }

    public static void testProblem4_1() throws Exception {
        ProblemTree problemTree = new ProblemTree();
        ProblemTree.BinaryTreeNode root = problemTree.construct(new int[]{1,2,4,7,3,5,6,8}, new int[]{4,7,2,1,5,3,8,6});
        System.out.println(problemTree.getNextViaInorder(root));
    }

    public static void testProblem5_1(){
        ProblemStack stack = new ProblemStack();
        stack.appendTail(1);
        stack.appendTail(2);
        stack.appendTail(5);
        System.out.println(stack.deleteHead());
    }

    public static void testFibonacci(){
        long start = System.currentTimeMillis();
        System.out.println("<循环>35项:" + ProblemFibonacci.fibonacciWithLoop(35));
        long end = System.currentTimeMillis();
        System.out.println("时间:" + (end - start));
        start = System.currentTimeMillis();
        System.out.println("<递归>35项:" + ProblemFibonacci.fibonacciWithRev(35));
        end = System.currentTimeMillis();
        System.out.println("时间:" + (end - start));
    }

    public static void testQuickSort(){
        int[] array = new int[]{5,2,6,7,4,3,0,8,2,1};
        ProblemSort.quickSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    public static void testRotateSort() throws Exception {
        int[] array = new int[]{3,4,5,1,2};
        System.out.println(ProblemSort.minInRotateArray(array));
    }

    public static void testPathInMatrix() throws Exception {
        char[][] martix = new char[][]{
                new char[]{'a', 'b', 't', 'g'},
                new char[]{'c', 'f', 'c', 's'},
                new char[]{'j', 'd','e', 'h'},
        };
        char[] destPath = new char[]{'b','f','c','e'};
        List<PathNode> path = ProblemRetrospective.getPathInMatrix(martix, destPath);
        for (PathNode p :
                path) {
            System.out.println(p);
        }
    }

    public static void testRobotRange(){
        List<PathNode> pathRange = ProblemRetrospective.getRobotMoveRange(18, 40 ,40);
        for (PathNode p :
                pathRange) {
            System.out.println(p);
        }
    }

    public static void testCutting(){
        System.out.println(ProblemDP.maxOfCuttingWithDP(8));
    }

    public static void testNumberOf1(){
        System.out.println(ProblemBit.numOf1(9));
    }

    public static void testPower(){
        System.out.println(ProblemBit.power(2, 3));
    }

    public static void testPrintNumber(){
        ProblemString.print1ToMaxOfNDigit(4);
    }

    public static void testList(){
        ProblemLinkList p = new ProblemLinkList();
        p.add(1);
        p.add(2);
        p.add(3);
        p.add(3);
        p.add(4);
        p.add(4);
        p.add(5);
        p.deleteDuplication();
        p.printList();
    }

    public static void testStringMatch(){
        System.out.println(ProblemString.match("aaa", "ab*a"));
    }

    public static void testIsNumberic(){
        System.out.println(ProblemString.isNumberic("+100"));
        System.out.println(ProblemString.isNumberic("+1.2"));
        System.out.println(ProblemString.isNumberic("-1e5"));
    }

    public static void testReorderOddEven(){
        int[] res = ProblemArray.reorderOddEven(new int[]{1,2,3,4,5});
        for (int i = 0; i < res.length; i++) {
            System.out.printf(res[i] + " ");
        }
    }

    public static void testGetLastOfKNode(){
        ProblemLinkList linkList = new ProblemLinkList();
        for (int i = 0; i < 6; i++) {
            linkList.add(i + 1);
        }
        System.out.println(linkList.getLastKNodeValue(7));
    }

    public static void testLoop(){
        ProblemLinkList linkList = new ProblemLinkList();
        linkList.add(1);
        linkList.add(2);
        linkList.add(3);
        linkList.add(4);
        linkList.add(5);
        linkList.addWithLoop(6, 3);
        System.out.println(linkList.getEntryLoopValue());
    }

    public static void testReverse(){
        ProblemLinkList linkList = new ProblemLinkList();
        linkList.add(1);
        linkList.add(2);
        linkList.add(3);
        linkList.add(4);
        linkList.add(5);
        linkList.add(6);
        System.out.println(linkList.getReverseHead());
    }
    
    public static void testMergeWithAESC(){
        ProblemLinkList list1 = new ProblemLinkList();
        list1.add(1);
        list1.add(3);
        list1.add(5);
        list1.add(7);
        ProblemLinkList list2 = new ProblemLinkList();
        list2.add(2);
        list2.add(4);
        list2.add(6);
        list2.add(8);;
        ProblemLinkList.Node root = ProblemSort.mergeWithASEC(list1.getRoot(), list2.getRoot());
    }

    public static void testPrintMatrixWithCircle(){
        ProblemArray.printMatrixWithCircle(new int[][]{
                new int[]{1, 2, 3, 4},
                new int[]{5, 6, 7, 8},
                new int[]{9, 10, 11, 12},
                new int[]{13, 14, 15, 16}
        });
    }

    public static void testStackMin(){
        ProblemStack stack = new ProblemStack();
        stack.push(1);
        stack.push(2);
        stack.push(5);
        stack.push(3);
        System.out.println(stack.min());
    }

    public static void testPopOrder(){
        System.out.println(ProblemStack.isPopOrder(new int[]{1,2,3,4,5}, new int[]{4,3,5,1,2}));
    }

    public static void testPrintTreePreOrder() throws Exception {
        ProblemTree tree = new ProblemTree();
        ProblemTree.BinaryTreeNode root = tree.construct(new int[]{1,2,4,7,3,5,6,8}, new int[]{4,7,2,1,5,3,8,6});
        tree.setRoot(root);
        tree.printTreePreOrder();
    }

    public static void testPrintTreePreOrderLine() throws Exception {
        ProblemTree tree = new ProblemTree();
        ProblemTree.BinaryTreeNode root = tree.construct(new int[]{1,2,4,7,3,5,6,8}, new int[]{4,7,2,1,5,3,8,6});
        tree.setRoot(root);
        tree.printTreePreOrderLine();
    }

    public static void testPrintTreePreOrderZHI() throws Exception {
        ProblemTree tree = new ProblemTree();
        ProblemTree.BinaryTreeNode root = tree.construct(new int[]{1,2,4,7,3,5,6,8}, new int[]{4,7,2,1,5,3,8,6});
        tree.setRoot(root);
        tree.printTreePreOrderZHIStack();
    }

    public static void testTreeSeq(){
        int[] seq = new int[]{7,4,6,5};
        System.out.println(ProblemTree.isBST(seq));
    }

    public static void testSumPath() throws Exception {
        ProblemTree tree = new ProblemTree();
        ProblemTree.BinaryTreeNode root = tree.construct(new int[]{1,2,4,7,3,5,6,8}, new int[]{4,7,2,1,5,3,8,6});
        tree.setRoot(root);
        tree.findSumPath(14);
    }

    public static void testSerializeBST() throws Exception {
        ProblemTree tree = new ProblemTree();
        ProblemTree.BinaryTreeNode root = tree.construct(new int[]{1,2,4,7,3,5,6,8}, new int[]{4,7,2,1,5,3,8,6});
        tree.setRoot(root);
        String serializeBST = tree.serializeBST();
        System.out.println(serializeBST);
    }

    public static void testDeserializeBST(){
        ProblemTree tree = new ProblemTree();
        System.out.println(tree.getRoot());
        ProblemTree.BinaryTreeNode root = tree.deserializeBST("124$7$$$35$$68$$$");
    }

    public static void testPermutation(){
        List<String> result = ProblemString.permutation("abc");
        Iterator<String> iterator = result.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    public static void testMoreThanHalf(){
        System.out.println(ProblemArray.moreThanHalf(new int[]{1,2,3,2,2,2,5,4,2}));
    }

    public static void testLeastOfK(){
        TreeSet<Integer> set = ProblemSort.getMaxOfK(new int[]{4,5,1,6,2,7,3,8}, 4);
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    public static void testMid(){
        System.out.println(ProblemSort.getMedian(new int[]{1,4,8}));
    }

    public static void testGreatestSum(){
        System.out.println(ProblemArray.findGreatestSum(new int[]{1,-2,3,10,-4,7,2,-5}));
    }

    public static void testNumberOf1In1Andn(){
        System.out.println(ProblemArray.numberOf1(12));
    }

    public static void testDigitAt(){
        System.out.println(ProblemArray.digitAt(1001));
    }

    public static void testCompare(){
        ProblemArray.printMinNumber(new int[]{3,32,321});
    }

    public static void testTranslation(){
        System.out.println(ProblemArray.getTranslationCount(12258));
    }

    public static void testMaxGiftValue(){
        System.out.println(ProblemArray.getMaxGiftValue(new int[][]{
                new int[]{1, 10, 3, 8},
                new int[]{12, 2, 9, 6},
                new int[]{5, 7, 4, 11},
                new int[]{3, 7, 16, 5},
        }));
    }

    public static void testMaxSubStringLength(){
        System.out.println(ProblemString.getLongestSubString("arabcacfr"));
    }

    public static void testUglyNumber(){
        System.out.println(ProblemArray.getUglyNumber(6));
    }

    public static void testFirstRepeatingChar(){
        System.out.println(ProblemString.getFirstRepeatingChar("abaccdeff"));
    }

    public static void testFirstStreamChar(){
        ProblemString.CharRepeating charRepeating = new ProblemString().new CharRepeating();
        System.out.println(charRepeating.insert('g'));
        System.out.println(charRepeating.insert('o'));
        System.out.println(charRepeating.insert('o'));
        System.out.println(charRepeating.insert('g'));
        System.out.println(charRepeating.insert('l'));
        System.out.println(charRepeating.insert('e'));
    }

    public static void testInversePiarsCount(){
        System.out.println(ProblemArray.inversePairsCounts(new int[]{7, 5, 6, 4}));
    }

    public static void testCommonNode(){
        ProblemLinkList listLong = new ProblemLinkList();
        listLong.add(1);
        listLong.add(2);
        listLong.add(3);
        ProblemLinkList.Node common1 = new ProblemLinkList().new Node(6, null);
        ProblemLinkList.Node common2 = new ProblemLinkList().new Node(7, null);

        listLong.add(common1);
        listLong.add(common2);

        ProblemLinkList listShort = new ProblemLinkList();
        listShort.add(4);
        listShort.add(5);

        listShort.add(common1);

        ProblemLinkList.Node res = listLong.getFirstCommonNode(listShort);
        System.out.println(res);
    }

    public static void testNumberOfK(){
        System.out.println(ProblemArray.getNumberOfK(new int[]{1,2,3,3,3,3,4,5}, 3));
    }

    public static void testMissingNumber(){
        System.out.println(ProblemArray.getMissingNumber(new int[]{0,2,3,4,5,7,8}));
    }

    public static void testNumberSameAsIndex(){
        System.out.println(ProblemArray.getNumberSameAsIndex(new int[]{-3, -1, 1, 3, 5}));
    }

    public static void testKthNodeInTree() throws Exception {
        ProblemTree tree = new ProblemTree();
        ProblemTree.BinaryTreeNode root = tree.construct(new int[]{5,3,2,4,7,6,8}, new int[]{2,3,4,5,6,7,8});
        tree.setRoot(root);
        System.out.println(tree.getKthNode(3));
    }

    public static void testTreeDepth() throws Exception {
        ProblemTree tree = new ProblemTree();
        ProblemTree.BinaryTreeNode root = tree.construct(new int[]{5,3,2,4,7,6,8}, new int[]{2,3,4,5,6,7,8});
        tree.setRoot(root);
        System.out.println(tree.getDepth());
    }

    public static void testIsBalancedTree() throws Exception {
        ProblemTree tree = new ProblemTree();
        ProblemTree.BinaryTreeNode root = tree.construct(new int[]{5,3,2,4,7,6,8}, new int[]{2,3,4,5,6,7,8});
        tree.setRoot(root);
        tree.printBack();
    }

    public static void testNunberAppearOnce(){
        int[] numbersAppearOnce = ProblemArray.getNumbersAppearOnce(new int[]{2, 4, 3, 6, 3, 2, 5, 5});
        for (int i = 0; i < numbersAppearOnce.length; i++) {
            System.out.println(numbersAppearOnce[i]);
        }
    }

    public static void testNumberAppearOnceIn3() throws Exception {
        System.out.println(ProblemArray.getNumberAppearOnce(new int[]{1,2,3,1,2,3,1,2,3,4}));
    }

    public static void testSumOfTwo(){
        int[] res = ProblemArray.getNumberWithSum(new int[]{1,2,4,7,11,15}, 15);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i] + " ");
        }
    }

    public static void testSumSeq(){
        List<String> res = ProblemArray.getContinuousSeq(15);
        for (String s :
                res) {
            System.out.println(s);
        }
    }

    public static void testReverseSentence(){
        System.out.println(ProblemString.reverseSentence("I am a student."));
    }

    public static void testLeftRotateString(){
        System.out.println(ProblemString.leftRotateString("abcdefg", 2));
    }

    public static void testMaxWindow(){
        int[] res = ProblemArray.maxInWindows(new int[]{2,3,4,2,6,2,5,1}, 3);
        Arrays.stream(res).forEach(System.out::println);
    }

    public static void testQueueWithMax() throws Exception {
        ProblemQueue.QueueWithMax queue = new ProblemQueue().new QueueWithMax();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        System.out.println(queue.pop());
        System.out.println(queue.max());
    }

    public static void testAdd(){
        System.out.println(ProblemOthers.add(5, 3));
    }

    public static void testMultiMatrix(){
        Arrays.stream(ProblemOthers.multipyMatrix(new int[]{1,2,3,4})).forEach(System.out::println);
    }

    public static void testMaxIntrest(){
        System.out.println(ProblemArray.maxIntrest(new int[]{9,11,8,5,7,12,16,14}));
    }

    public static void testIsContinuous(){
        System.out.println(ProblemArray.isContinuous(new int[]{2,3,4,5,6}));
    }

    public static void testLastRemaining(){
        System.out.println(ProblemArray.getLastRemaining(5, 3));
    }

    public static void testProbabiity(){
        ProblemArray.printAllProbability(2);
    }
}
