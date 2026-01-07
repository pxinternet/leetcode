package leetCode;

/*
  题目：删除链表倒数第 N 个节点（LeetCode 19）
  常见解法（本文件实现）：双指针（或称为快慢指针/间隔法）＋虚拟头结点(dummy)

  方法思路（快慢指针/间隔法）：
  1. 维护两个指针：一个先向前走 n 步（称为 fast），另一个从虚拟头开始（称为 slowPrev，指向待删节点的前一个节点）。
  2. 当 fast 已经领先 n 步后，同时移动 fast 和 slowPrev（每次一步），直到 fast 到达链表末端（null）。
  3. 此时 slowPrev 的下一个节点就是倒数第 n 个节点，直接修改 slowPrev.next = slowPrev.next.next 即可删除目标节点。

  为什么正确（直观与形式化理由）：
  - 在开始同时移动前，fast 比 slowPrev 领先 n 步：fast = slowPrev + n
  - 每次同步向前走一步保持该距离不变。直到 fast 变为 null（即 fast 指向链表末端的后继），此时 slowPrev 必然位于倒数第 n 个节点的前一个位置（因为从 slowPrev 再走 n 步会到 null）。
  - 因此直接跳过 slowPrev.next 就是删除倒数第 n 个节点，算法不会丢失或错删节点。

  边界处理（使用虚拟头 dummy 的理由）：
  - 当需要删除的是头节点（即删除倒数第 n 个节点恰好是第一个节点）时，直接操作 head 会比较繁琐。通过在链表前加入虚拟头 dummy，让删除操作统一为 pre.next = pre.next.next，从而无需单独处理删除头节点的情况。

  复杂度：
  - 时间复杂度：O(L)，其中 L 为链表长度。快指针先走 n 步（O(n)），随后两个指针同时走（至末端），总共至多走 O(L) 步。
  - 空间复杂度：O(1)，只使用常数个指针。
*/

public class LC19removeNthFromEnd {

    // 方法一：基于快指针先走 n 步，然后同步移动 pre 指针直到快指针到达末尾
    public ListNode removeNthFromEnd(ListNode head, int n) {

        int right = 0; // 计数器：记录快指针已经前进了多少步

        ListNode node = head; // node 用作快指针（先向前走 n 步）

        ListNode dummy = new ListNode(0); // 虚拟头结点，简化删除头节点的情况

        ListNode pre = dummy; // pre 将作为慢指针的前驱（最终指向要删除节点的前一个节点）
        pre.next = head; // 将 dummy 与原链表连接

        // 让快指针 node 向前走 n 步，或直到链表结束
        while (node != null && right < n) {
            right++;
            node = node.next;
        }

        // 到这里有两种情况：
        // 1) node == null 且 right == n：说明链表长度恰好为 n，
        //    此时倒数第 n 个节点就是原链表的头节点（head），而 pre 仍指向 dummy，
        //    后续 while(node != null) 不会执行，直接进行 pre.next = pre.next.next 会删除头节点。
        // 2) node != null：说明链表长度 > n，node 现在位于第 n+1 个节点（相对于 head），
        //    接下来同步移动 node 与 pre，直到 node 到达末端；pre 将到达待删节点的前一位。

        // 同步移动：每次 node 和 pre 都向前移动一位，直到 node 到达 null（遍历完剩余部分）
        while(node != null) {
            node = node.next; // 快指针继续前进
            pre = pre.next;    // 慢指针的前驱也向前一位
        }

        // 此时 pre.next 指向要删除的节点（倒数第 n 个），直接跳过它
        pre.next = pre.next.next;

        // 返回去掉目标节点后的链表头（可能与原 head 不同，当删除头节点时 dummy.next 已更新）
        return dummy.next;
    }


    // 方法二：与方法一逻辑等价，仅对变量初始化顺序稍作调整（更常见的实现写法）
    public ListNode removeNthFromEndRound2(ListNode head, int n) {
        int right = 0; // 计数器

        ListNode dummy = new ListNode(0);
        dummy.next = head; // 将 dummy 与链表连接
        ListNode pre = dummy; // pre 指向待删除节点的前一个节点（初始化为 dummy）

        ListNode node = head; // node 作为快指针
        // 快指针先走 n 步
        while (node != null && right < n) {
            node = node.next;
            right++;
        }

        // 同步移动直到快指针到达末端
        while(node != null) {
            node = node.next;
            pre = pre.next;
        }
        // 删除目标节点
        pre.next = pre.next.next;
        return dummy.next;
    }


}
