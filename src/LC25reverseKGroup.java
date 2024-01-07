public class LC25reverseKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);

        dummy.next = head;

        ListNode pre = dummy;

        while (head != null) {
            ListNode end = head.next;

            for (int i = 0; i<k && end!=null; i++)  end = end.next;
            if (end == null) break;
            ListNode next = end.next;

            ListNode[] reverse = newReverse(head, end);
            head = reverse[0];
            end = reverse[1];

            pre.next = head;
            end.next = next;

            //继续循环
            pre = end;
            head = end.next;
        }

        return dummy.next;
    }

    public ListNode[] newReverse(ListNode head, ListNode end) {
        ListNode prev = end.next;
        ListNode p = head;
        while (prev != end) {
            ListNode next = p.next;
            p.next = prev;
            prev = p;
            p = next;
        }
        return new ListNode[] {end, head};
    }

//    public ListNode reverseKGroup(ListNode head, int k) {
//
//        //找到退出条件
//        //找到循环条件
//        //执行具体操作
//
//        if (head == null) return null;
//        ListNode dummy = new ListNode(0);
//
//        dummy.next = head;
//        ListNode pre = dummy;
//        ListNode end = dummy;
//
//        while(end.next != null) {
//
//            //找第k个元素
//            for (int i = 0; i < k && end!=null; i++) end = end.next;
//
//            //先找退出条件LC25reverseKGroup
//            if (end == null) break;
//
//            //找到翻转的开始节点
//            ListNode start = pre.next;
//
//            //记录下一个节点
//            ListNode next = end.next;
//
//            //翻转这一块
//            pre.next = reverse(start);
//
//            //翻转后接上
//            start.next = next;
//
//            //再开始下一轮的初始化
//            pre = start;
//            end = pre;
//        }
//        return dummy.next;
//    }
//
//    public ListNode reverse(ListNode head) {
//        ListNode pre = null;
//
//        while(head!= null) {
//
//            ListNode next = head.next;
//            head.next = pre;
//            pre = head;
//            head = next;
//        }
//        return pre;
//
//    }

}
