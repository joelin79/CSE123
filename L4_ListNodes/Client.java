import java.util.List;

public class Client {
    public static void main(String[] args) {
        // Build up the following list:
        // 1 -> 3 -> 4
        ListNode list = new ListNode(1);
        list.next = new ListNode(3);
        list.next.next = new ListNode(4);

        // Append 5 to the end of the list
        list.next.next.next = new ListNode(5);

        // Append 2 between the 1 and 3 nodes
        ListNode list2 = list.next;
        list.next = new ListNode(2);
        list.next.next = list2;

        // Print out the four (how many .nexts to get to that node?)
        System.out.println(list.next.next.next);
    }

    public static void printList(ListNode front) {
        // Print out all of the values in the list referenced by front 
        // separated by spaces
    }

    public static int indexOf(ListNode front, int value) {
        // Returns the index of the provided value within the list referenced
        // by front
    }
}
