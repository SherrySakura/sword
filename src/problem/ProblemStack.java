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
}
