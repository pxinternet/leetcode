package leetCode;

public class LC24SwapPairs {

    /*
     题目：两两交换链表中的节点（LeetCode 24）

     算法说明（迭代版 - 本类默认实现）
     - 思路：使用虚拟头节点(dummy) + 指针操作在常数空间内原地交换相邻节点。
     - 维护两个指针：pre（指向已处理部分的尾，或待交换对的前驱）和 node（指向当前待交换对的第一个节点）。
     - 当 node != null 且 node.next != null 时：保存第二个节点 temp=node.next；
       然后按顺序进行三次重连：
         pre.next = temp;
         node.next = temp.next;
         temp.next = node;
       这会把 (node,temp) 由 node->temp 变为 temp->node，同时保持链表连通。
     - 更新指针：pre = node（已处理对的尾），node = node.next（下一个待交换对的首）。

     证明正确性（不变式）：
     - 不变式：在每次循环开始时，pre 指向已处理部分的尾部，node 指向未处理部分的第一个节点。
     - 每次交换不会影响未处理部分的连通性（因为在修改 node.next 前已保存 temp，并在修改 pre.next 前后确保所有指针指向有效节点）。
     - 循环在链表长度 < 2 或到达链表尾时终止，已处理的部分均被正确两两交换，未处理的单个尾节点（若存在）保留在末尾。

     复杂度：
     - 时间复杂度：O(n)，每个节点被有限次访问与重连。
     - 空间复杂度：O(1)，仅使用常数个额外指针（递归版会使用 O(n) 调用栈）。

     边界情况：
     - 空链表（head == null） => 返回 null。
     - 单节点链表（head.next == null） => 返回原链表未变。
     - 奇数长度链表 => 最后一个节点保持不动。

     递归版思路（swapPairsRecursive）：
     - 递归定义：对于链表 head，若 head==null 或 head.next==null，返回 head（基线）；
       否则令 second=head.next，head.next = swapPairsRecursive(second.next)，second.next = head，返回 second。
     - 直观上：把前两节点交换，然后递归处理后续链表并把交换后的结果链接到当前对的尾部。
    */

    // 迭代实现（原有代码，保持不变）
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode node = head;

        while (node != null && node.next!=null) {
            ListNode temp = node.next;
            pre.next = node.next;
            node.next = node.next.next;
            temp.next = node;
            pre = node;
            node = node.next;
        }

        return dummy.next;
    }

    // 递归实现：交换前两节点并递归处理剩余部分
    public ListNode swapPairsRecursive(ListNode head) {
        if (head == null || head.next == null) return head; // 基线
        ListNode second = head.next;
        // 先递归处理后续子链表，再把前两节点交换
        head.next = swapPairsRecursive(second.next);
        second.next = head;
        return second;
    }

    // 工具：将数组构建为链表，返回头节点（方便 main 使用）
    private static ListNode buildList(int[] vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    // 工具：将链表字符串化便于打印
    private static String toString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        ListNode cur = head;
        while (cur != null) {
            sb.append(cur.val);
            if (cur.next != null) sb.append("->");
            cur = cur.next;
        }
        return sb.toString();
    }

    // main：演示迭代与递归两种实现的结果
    public static void main(String[] args) {
        LC24SwapPairs solver = new LC24SwapPairs();

        int[][] tests = {
                {},
                {1},
                {1,2},
                {1,2,3},
                {1,2,3,4},
                {1,2,3,4,5}
        };

        for (int[] t : tests) {
            ListNode head = buildList(t);
            System.out.println("原始: " + toString(head));

            ListNode it = solver.swapPairs(copyList(head));
            System.out.println("迭代后: " + toString(it));

            ListNode rec = solver.swapPairsRecursive(copyList(head));
            System.out.println("递归后: " + toString(rec));

            System.out.println();
        }
    }

    // 辅助：复制链表（因为同一个链表将被两次修改）
    private static ListNode copyList(ListNode head) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        ListNode p = head;
        while (p != null) {
            cur.next = new ListNode(p.val);
            cur = cur.next;
            p = p.next;
        }
        return dummy.next;
    }

}
