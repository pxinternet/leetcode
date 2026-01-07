package leetCode;

/**
 * LC2 两数相加（链表表示）
 *
 * 题意：两个非空链表表示两个非负整数，数字按逆序存储，链表的每个节点包含一位数字。将两数相加并以相同形式返回一个新链表。
 * 约定：输入链表的每个节点只包含 0-9 的值，结果链表也按逆序存储。
 *
 * 本文件提供两种实现：
 * - addTwoNumbers：原始作者实现（修正为尾插构造以避免额外 reverse）
 * - addTwoNumbers2：更常见的尾插实现
 *
 * 并在 main 中给出示例用例与输出对比。
 */
public class LC2 {

    /**
     * 使用 leetCode.ListNode 构造结果链表（尾插），时间 O(max(m,n))。
     *
     * 思路：同时遍历 l1, l2，逐位相加并维护进位 carry；使用 dummy 节点简化链表构造。
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;

            int sum = x + y + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        if (carry > 0) curr.next = new ListNode(carry);
        return dummy.next;
    }

    /**
     * 另一个实现（与上面等价），保留作为参考
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;

            int sum = x + y + carry;

            carry = sum / 10;
            int val = sum % 10;

            curr.next = new ListNode(val);

            curr = curr.next;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }

        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummy.next;
    }

    // 简单的 main 测试
    public static void main(String[] args) {
        LC2 solver = new LC2();
        // 链表表示 342 -> [2,4,3]
        ListNode l1 = ListNode.createFromArray(new int[]{2,4,3});
        // 链表表示 465 -> [5,6,4]
        ListNode l2 = ListNode.createFromArray(new int[]{5,6,4});

        ListNode res = solver.addTwoNumbers(l1, l2); // 807 -> [7,0,8]
        System.out.println("result: " + res);

        // 断言（人工观察）
        System.out.println("期望: 7->0->8");
    }
}
