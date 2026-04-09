package leetCode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * LCR078 - 合并 K 个升序链表（堆实现）
 *
 * 题目（概要）：k 个升序链表，合并为一个升序链表。
 *
 * 解法说明：小顶堆存各链表当前头节点，每次 poll 最小、将其 next 入堆，串联成结果链表。
 *
 * 时间复杂度：O(N*log k)
 * 空间复杂度：O(k)
 */
public class LCR078MergeKListSHeap {

    /** 堆合并 K 个链表 */
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
