package leetCode;

public class LC25reverseKGroup {

    /*
     题目：将链表每 k 个一组反转（LeetCode 25）

     本文件包含三种相关实现/辅助：
     - reverseKGroup：使用 newReverse 辅助函数对区间原地反转（修正版，已处理边界与指针更新）
     - newReverse：反转区间 [head, end]（包含 end），返回反转后的 {newHead, newTail}
     - reverseKGroup2：另一种更直观的实现，先断开区间然后迭代反转再拼回（保留原实现，便于对比）

     下面注释会把算法思路、每一步的目的、不变式与正确性证明写入代码中，便于阅读与维护。
    */

    /**
     * 修正后的 reverseKGroup：使用分组定位 + newReverse 辅助反转
     * 关键要点：
     * - 使用虚拟头 dummy 来统一处理头节点可能被反转的情况。
     * - 每轮以 pre（组前驱）为起点，查找当前组的 end（向前走 k 步）。
     * - 如果剩余节点不足 k（end == null），则不再反转，结束返回。
     * - 否则，保存 start = pre.next, next = end.next，调用 newReverse(start,end) 进行区间反转。
     * - newReverse 返回 {newHead, newTail}（即原 end, 原 start），把它接到 pre，并把 newTail.next 指向 next。
     * - 更新 pre = newTail，继续处理下一组。
     *
     * 正确性证明（要点）：
     * - 不变式：在每次循环开始时，pre 指向已处理链表的尾部（已完成组的尾），pre.next 指向下一组的起点（start）。
     * - 通过从 pre 开始向前走 k 步来确定 end，可保证我们处理的区间是紧接在 pre 之后的 k 个节点。
     * - newReverse 实现了闭区间反转，并以 prev=end.next 的形式在反转过程中保持外部连接，故反转后的 newTail（原 start）可以直接和 next 接回。
     * - 因此整个流程不会丢失节点或产生环，且对每一完整的 k-组都执行了一次恰当的反转；不足 k 的尾部保持原样。
     *
     * 复杂度：
     * - 时间：O(N)，每个节点在定位、反转过程中被常数次访问。
     * - 空间：O(1)，使用常数级辅助指针（newReverse 内部也只用常数指针）。
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 虚拟头简化头节点被反转的情况
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // pre 始终指向已处理链表的尾部（也即下一组的前驱）
        ListNode pre = dummy;

        // 循环处理每一个组
        while (true) {
            // end 从 pre 开始向前走 k 步以定位当前组的尾节点
            ListNode end = pre;
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            // 如果不足 k 个节点，则不再反转，直接跳出
            if (end == null) break;

            // start 是当前组的头节点，next 是下一组的起点（保留以便拼接）
            ListNode start = pre.next;
            ListNode next = end.next;

            // 反转闭区间 [start, end]
            ListNode[] rev = newReverse(start, end);
            // newHead 是反转后区间的头（等同于原 end），newTail 是反转后区间的尾（等同于原 start）
            ListNode newHead = rev[0];
            ListNode newTail = rev[1];

            // 把反转后的区间接回主链表
            pre.next = newHead;
            newTail.next = next;

            // 为下一轮更新 pre
            pre = newTail;
        }

        return dummy.next;
    }


    /**
     * 反转区间 [head..end]（包含 end），返回 {newHead, newTail}
     * 实现说明（经典技巧）：
     * - 令 prev = end.next（反转后 newTail.next 应指向的节点），p=head。
     * - 不断把 p 插到 prev 的前面（p.next=prev; prev=p; p=next），直到 prev==end（表示已把所有节点移动过来）。
     * - 结束时，prev 指向反转后的头（等于原 end），head（原 start）变为反转后的尾。
     * - 返回 {prev, head} 即 {newHead, newTail}。
     *
     * 注意：调用方必须保证 head 和 end 都在同一条链上且 end 在 head 或之后。
     */
    public ListNode[] newReverse(ListNode head, ListNode end) {
        ListNode prev = end.next; // 保存区间后继，用于把当前节点连接到已反转部分
        ListNode p = head;
        // 把区间内的节点逐一移动到 prev 前面，直到 prev == end（表示完成）
        while (prev != end) {
            ListNode next = p.next;
            p.next = prev;
            prev = p;
            p = next;
        }
        // 反转后新的 head 是原 end，新的 tail 是原 head
        return new ListNode[] {end, head};
    }


    /**
     * 另一个实现：reverseKGroup2
     * 此实现的策略是：定位区间 -> 断开 end.next -> 在局部使用标准迭代反转算法反转整个子链 -> 然后把反转后的链接回
     * 相比于 newReverse 的插入法，这种做法更直观（把子链当作独立链表反转），但操作上需要断开并恢复连接。
     * 该实现保留在文件中，便于对比和测试。
     */
    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = pre;

        while (head != null) {
            // 定位 end（从 pre 开始向前 k 步）
            end = pre;
            for (int i = 0; i < k && end != null; i++) end = end.next; // 如果 end==null 表示剩余节点不足 k

            if (end == null) return dummy.next;

            // start 是当前组起点，head 用作保存下一组的起点
            ListNode start = pre.next;
            head = end.next; // 保存下一组的头
            end.next = null; // 断开当前组

            // 在断开的子链上做标准迭代反转
            ListNode curr = start, tmp;
            ListNode prev = null;

            while (curr != null) {
                tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            // prev 是反转后的头（即原 end）
            // 把反转后的部分接回主链表
            pre.next = prev;
            start.next = head;
            // 更新 pre 到反转后组的尾（即原 start）
            pre = start;
        }

        return dummy.next;
    }

    // main 用于简单的手工测试：构建链表并运行两种实现
    public static void main(String[] args) {
        LC25reverseKGroup lc = new LC25reverseKGroup();
        int[] arr = new int[] {1, 2, 3, 4, 5};
        ListNode head = ListNode.createFromArray(arr);
        head.printBetter("orig");

        ListNode r1 = lc.reverseKGroup(ListNode.createFromArray(arr), 2);
        r1.printBetter("reverseKGroup result");

        ListNode r2 = lc.reverseKGroup2(ListNode.createFromArray(arr), 2);
        r2.printBetter("reverseKGroup2 result");
    }

}
