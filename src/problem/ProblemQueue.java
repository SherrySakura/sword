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
