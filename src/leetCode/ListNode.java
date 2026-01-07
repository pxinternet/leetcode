package leetCode;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * 单链表节点工具类（含构造、数组转换、字符串化与 main 测试）
 *
 * 约定：节点值为 int，next == null 表示链表尾部。
 * 提供的静态方法便于在各题解中快速创建/比较链表。
 */
public class ListNode {

    public int val;

    public ListNode next;

    public ListNode() {};

    public ListNode(int val) {this.val = val;}

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /**
     * 从数组创建链表，返回头节点（若 nums 为空则返回 null）
     */
    public static ListNode createFromArray(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        ListNode dummy = new ListNode(0);
        ListNode header = dummy;

        for (int i = 0; i < nums.length; i++) {
            header.next = new ListNode(nums[i]);
            header = header.next;
        }
        return dummy.next;
    }

    /**
     * 将链表转换为数组（按节点值顺序）
     */
    public static int[] toArray(ListNode head) {
        List<Integer> tmp = new ArrayList<>();
        while (head != null) {
            tmp.add(head.val);
            head = head.next;
        }
        int[] res = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) res[i] = tmp.get(i);
        return res;
    }

    /**
     * 打印链表（每个值换行），用于快速观察
     */
    public static void printList(ListNode header) {
        while (header != null) {
            System.out.println(header.val);
            header = header.next;
        }
    }

    public void printBetter(String prefix) {
        System.out.println(prefix + " : " + this.toString());
    }

    public void print() {
        System.out.println(this.val);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode temp = this;
        Set<ListNode> visited = new HashSet<>();
        while (temp != null) {
            if (visited.contains(temp)) {
                sb.append(temp.val).append("(LOOP)");
                break;
            }
            visited.add(temp);
            sb.append(temp.val);
            if (temp.next != null) {
                sb.append("->");
            }
            temp = temp.next;
        }
        return sb.toString();
    }

    /**
     * main 测试用例：创建多个链表并打印，同时验证 createFromArray 与 toArray 的一致性
     */
    public static void main(String[] args) {
        int[] a = new int[]{1,2,3};
        ListNode l = ListNode.createFromArray(a);
        System.out.println("链表 l: " + l);

        int[] b = new int[]{};
        ListNode l2 = ListNode.createFromArray(b);
        System.out.println("空链表 l2: " + l2);

        int[] arr = ListNode.toArray(l);
        System.out.print("toArray: ");
        for (int x : arr) System.out.print(x + ",");
        System.out.println();

        System.out.println("示例完成 —— 若输出符合预期，则通过 smoke test。");
    }

}
