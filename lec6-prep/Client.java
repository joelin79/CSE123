

public class Client {
    public static void main(String[] args) {
//        ListNode front = new ListNode(1, new ListNode(3, new ListNode(4)));
//        printAll(front);
//        System.out.println();
//        insertAfterLast(front, 5);
//        printAll(front);
//        System.out.println();
        LinkedIntList list = new LinkedIntList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.printList();
    }

    public static void insertAfterLast(ListNode node, int x) {
        while (node != null) {
            if (node.next == null) {
                node.next = new ListNode(x);
                node = node.next;
            }
            node = node.next;
        }
    }

    public static void printAll(ListNode node) {
        ListNode current = node;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}
