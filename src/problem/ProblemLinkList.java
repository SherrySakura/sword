package problem;

import java.util.*;

public class ProblemLinkList {

    public class Node{
        int value;
        Node next;
        Node slibling;

        public Node() {
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        public Node(int value, Node next, Node slibling) {
            this.value = value;
            this.next = next;
            this.slibling = slibling;
        }

        @Override
        public String toString() {
            return "value: " + value;
        }
    }

    private Node root;

    public Node getRoot() {
        return root;
    }

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

    public void add(Node item){
        if (root == null){
            root = item;
        }else {
            Node cur = root;
            while (cur.next != null){
                cur = cur.next;
            }
            cur.next = item;
        }
    }

    public void addWithLoop(int e, int after){
        Node forward = null;
        if (root == null){
            root = new Node(e, null);
        }else {
            Node cur = root;
            while (cur.next != null){
                if (cur.value == after){
                    forward = cur;
                }
                cur = cur.next;
            }
            cur.next = new Node(e, forward);
        }
    }

    public void printList(){
        Node cur = root;
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

    /**
     * 删除排好序的链表中重复的节点
     * 例如一个有序链表1->2->3->3->4->4->5
     * 删除完重复的节点后为1->2->5
     */
    public void deleteDuplication(){
        if (root == null){
            return;
        }
        Node preNode = null;
        Node cur = root;
        while (cur != null){
            Node next = cur.next;
            boolean deleted = false;
            if (next != null && cur.value == next.value){
                deleted = true;
            }
            if (deleted == true){
                preNode = cur;
                cur = cur.next;
            }else {
                int value = cur.value;
                Node del = cur;
                while (del != null && del.value == value){
                    next = del.next;
                    del = next;
                }
                if (preNode == null){
                    root = next;
                }else {
                    preNode.next = next;
                }
                cur = next;
            }
        }
    }

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

    /***
     * 输入一个链表，输出链表中倒数第k个元素的值，假定倒数的计数从1开始，
     * 即最后一个元素为倒数第一个元素。例如：链表1，2，3，4，5，6。倒数第3个元素为4
     * @param k
     * @return
     */
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

    /**
     * 复制一个复杂链表，每个节点除了有指向下一个节点的指针next之外还有一个指向链表中任意节点的sibling。
     * @return
     */
    public Node cloneList(){
        cloneNode();
        connectSibling();
        return reconnectList();
    }

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
}
