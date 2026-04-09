package leetCode;

import java.util.PriorityQueue;

/**
 * LC23mergeKList - 合并 K 个升序链表
 *
 * 题目（概要）：给定一个链表数组，每个链表均为升序排列，请将所有链表合并为一个升序链表并返回。
 *
 * 解法说明：
 * - 核心思想：使用最小堆（PriorityQueue）维护 k 个链表当前头节点中的最小值
 * - 每次弹出堆顶节点，将其接入结果链表，若该节点有 next 则入堆
 * - 堆中最多同时存在 k 个节点，每次操作 O(log k)
 *
 * 时间复杂度：O(N log k)，N 为所有节点总数，k 为链表个数
 * 空间复杂度：O(k)，堆中最多 k 个节点
 *
 * 边界与注意事项：
 * - lists 为空或全为 null 时，返回 null
 * - 单个空链表不影响，直接跳过
 *
 * 示例：lists = [[1,4,5],[1,3,4],[2,6]] → [1,1,2,3,4,4,5,6]
 */
public class LC23mergeKList {

    /**
     * 使用最小堆合并 k 个有序链表
     *
     * 关键点：堆按节点 val 排序；弹出节点后若 next 非空则入堆
     *
     * @param lists k 个有序链表的头节点数组
     * @return 合并后的链表头节点
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        PriorityQueue<ListNode> nodeQueue = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.val, b.val)
        );

        // 将所有非空链表的头节点入堆
        for (ListNode node : lists) {
            if (node != null) {
                nodeQueue.offer(node);
            }
        }

        while (!nodeQueue.isEmpty()) {
            ListNode tmp = nodeQueue.poll();
            tail.next = tmp;
            tail = tail.next;
            if (tmp.next != null) nodeQueue.offer(tmp.next);  // 该链表下一节点入堆，继续参与比较
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        LC23mergeKList solver = new LC23mergeKList();
        ListNode[] lists = new ListNode[]{
                ListNode.createFromArray(new int[]{1,4,5}),
                ListNode.createFromArray(new int[]{1,3,4}),
                ListNode.createFromArray(new int[]{2,6})
        };
        ListNode res = solver.mergeKLists(lists);
        System.out.println("merged k lists: " + res);
        System.out.println("期望: 1->1->2->3->4->4->5->6");
    }

}
