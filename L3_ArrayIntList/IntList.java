// An interface which defines expected behavior of a list (ordered collection of elements)
// capable of only storing integers
public interface IntList {
    // Adds the given value to the end of the list
    public void add(int value);
    
    // Adds the given value at the given index of the list.
    // Throws an IndexOutOfBoundsException if the provided index is invalid
    //      (index < 0 || index >= size)
    public void add(int index, int value);
    
    // Removes the value at the given index of the list.
    // Throws an IndexOutOfBoundsException if the provided index is invalid
    //      (index < 0 || index >= size)
    public void remove(int index);
    
    // Updates the value at the given index to the one provided.
    // Throws an IndexOutOfBoundsException if the provided index is invalid
    //      (index < 0 || index >= size)
    public void set(int index, int value);
    
    // Returns the value at the given index of the list.
    // Throws an IndexOutOfBoundsException if the provided index is invalid
    //      (index < 0 || index >= size)
    public int get(int index);
    
    // Returns the size (number of elements added to this list)
    public int size();
    
    // Returns the index of the provided value within the list, -1 if not present
    public int indexOf(int value);
    
    // Returns whether or not the given value is present within the list
    public boolean contains(int value);
    
    // Returns a comma-separated string representation of the list surrounded
    // by square brackets
    public String toString();
}
