import java.util.Comparator;
import java.util.PriorityQueue;

public class LCR078MergeKListSHeap {

    //优先级队列 参考https://www.liaoxuefeng.com/wiki/1252599548343744/1265120632401152
    public ListNode mergeKLists(ListNode[] lists) {

        //实现优先队列接口
        PriorityQueue<ListNode> heap = new PriorityQueue<>(
                new Comparator<ListNode>() {
                    @Override
                    public int compare(ListNode o1, ListNode o2) {
                        return o1.val - o2.val;
                    }
                }
        );

        //k个列表头元素的初始化
        for (ListNode n: lists) {
            if (n!=null) {
                heap.add(n);
            }
        }

        //peek 获取队列头，但是不移除元素
        ListNode head = heap.peek();

        while (!heap.isEmpty()) {
            //poll 移除队列头元素
            ListNode min = heap.poll();

            //把min的下一个元素放进堆里面
            if (min.next!=null) {
                //会自动排序
                heap.add(min.next);
            }
            //指针继续指向堆的头结点
            min.next = heap.peek();
        }
        return head;
    }
}
