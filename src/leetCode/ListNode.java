package leetCode;

public class ListNode {

    public int val;

    ListNode next;

    ListNode() {};

    ListNode(int val) {this.val = val;}

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode createFromArray(int[] nums) {
        if (nums.length == 0) return null;
       ListNode dummy = new ListNode(0);

       ListNode header = new ListNode(nums[0]);
       dummy.next = header;

       for (int i = 1; i < nums.length; i++) {
           header.next = new ListNode(nums[i]);
           header = header.next;
       }
       return dummy.next;
    }

    public static void printList(ListNode header) {
        while (header != null) {
            System.out.println(header.val);
            header = header.next;
        }
    }

    public void print() {
        System.out.println(this.val);
    }

}
