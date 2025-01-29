// Class that represents a single node containing an
// integer value.
public class ListNode {
    public final int data;
    public ListNode next;
    
    // Constructs a ListNode with the given data
    public ListNode(int data) {
        // Sets the next field to null, meaning there
        // is no next linked node.
        this(data, null);
    }
    
    // Constructs a ListNode with the given data
    // and given next node.
    public ListNode(int data, ListNode next) {
        this.data = data;
        this.next = next;
    }
}
