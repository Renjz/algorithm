public class ListNode {
    private int val;
    private ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder().append(val);
        for (ListNode next = this.next; null != next; next = next.next) {
            string.append(" -> ").append(next.val);
        }
        return string.toString();
    }

    /**
     * 反转链表
     *
     * @param head
     * @return
     * @link https://leetcode-cn.com/problems/reverse-linked-list/
     */
    public static ListNode reverse(ListNode head) {
        if (null == head) {
            return null;
        }
        ListNode prev = null;
        for (ListNode curr = head, next; curr != null; prev = curr, curr = next) {
            next = head.next;
            curr.next = prev;
        }
        return prev;
    }

    /**
     * 两两交换链表中的节点
     * 给定一个链表，两两交换其中相邻的节点
     *
     * @param head
     * @return
     * @link https://leetcode-cn.com/problems/swap-nodes-in-pairs/
     */
    public static ListNode swapPairs(ListNode head) {
        return null;
    }

    public static void main(String[] args) {
        final int NODE_SIZE = 6;

        ListNode head = new ListNode(1), curr = head, next;
        for (int i = 1; i < NODE_SIZE; i++) {
            next = new ListNode(i + 1);
            curr.setNext(next);
            curr = next;
        }
        System.out.println(head);
        System.out.println(reverse(head));
    }
}