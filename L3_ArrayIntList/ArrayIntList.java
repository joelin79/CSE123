// An implementation of the IntList interface which uses
// an array to store elements
public class ArrayIntList implements IntList {
    private int[] elementData;
    private int size;

    public static final int DEFAULT_CAPACITY = 10;

    public ArrayIntList() {
        this.elementData = new int[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void add(int value) {
        // TODO: Implement
        if(size >= elementData.length){
            int[] temp = new int[elementData.length * 2];
            for(int i = 0; i < elementData.length; i++){
                temp[i] = elementData[i];
            }
            elementData = temp;
        }
        elementData[size] = value;
        size += 1;
    }

    public void add(int index, int value) {
        // TODO: Implement
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        elementData[index] = value;
    }

    public void remove(int index) {
        // TODO: Implement
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        for(int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
        }
        size -= 1;
        elementData[size - 1] = 0;
    }

    public void set(int index, int value) {
        // TODO: Implement
        elementData[index] = value;
    }

    public int get(int index) {
        // TODO: Implement
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        return elementData[index];
    }

    public int size() {
        // TODO: Implement
        return size;
    }

    public int indexOf(int value) {
        // TODO: Implement
        for(int i = 0; i < size; i++) {
            if(elementData[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(int value) {
        // TODO: Implement
        if(indexOf(value) >= 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        // TODO: Implement
        String result = "[";
        for(int i = 0; i < size; i++) {
            result += elementData[i];
            if(i!=size - 1) {
                result += ", ";
            }
        }
        return result + "]";
    }
}
