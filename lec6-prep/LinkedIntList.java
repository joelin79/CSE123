// LinkedIntList.java
// Represents a list of integers.
public class LinkedIntList implements IntList {
    private ListNode front;
    
    // post: constructs an empty LinkedIntList
    public LinkedIntList() {
        this.front = null;
    }

    // post: Adds the given value to the end of this list
    public void add(int value) {
        if (this.front == null) {
            this.front = new ListNode(value);
        } else {
            ListNode temp = this.front;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new ListNode(value);
        }

    }
    
    // post: prints out this list's values separated by spaces
    public void printList() {
        // From last class
        ListNode current = front; // We *definitely* need this now! why? 
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    // post: returns the index of the first occurence of the provided value
    //       within this list, -1 if not present
    public int indexOf(int value) {
        // TODO
        ListNode curr = front;
        int index = 0;
        while (curr != null) {
            if(front.data == value) { return index; }
            curr = curr.next;
            index++;
        }
        return -1;
    }

    // post: removes the first occurence of the given value from the list.
    public void remove(int value) {
        // TODO: Implement!
        if (this.front == null) { return; }
        if (front.data == value) { front = front.next;  return;}
        ListNode curr = front;
        while(front.next != null) {
            if (front.data == value) {
                front = front.next;
                return;
            }
            curr = curr.next;
        }

    }

    // post: returns the number of elements in this list
    public int size() {
        // TODO
        return -1;
    }

    // post: returns a comma-separated String representation of this list
    //       surrounded by square brackets
    public String toString() {
        // TODO: Implement!
        return "";
    }

    // post: returns the value of the element at the given int index of the list
    //       throws an IllegalArgumentException if the index is out of bounds
    public int get(int index) {
        // TODO: Implement!
        return -1;
    }

    // post: Adds the given value at the given index of this list
    //       throws an IllegalArgumentException if the index is out of bounds
    public void add(int index, int value) {
        // TODO: Implement!
    }

    // post: constructs a LinkedIntList from the given int[] nums
    //       constructs an empty LinkedIntList if nums is null or has a size of 0
    public LinkedIntList(int[] nums) {
        // TODO: implement!
    }

    // Class that represents a single node containing an
    // integer value.
    public static class ListNode {
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
}
