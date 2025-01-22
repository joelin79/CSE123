public class Client {
    public static void main(String[] args) {
        IntList al = new ArrayIntList();
        
        System.out.println(al);
        al.add(2);
        System.out.println(al);
        al.add(5);
        System.out.println(al);
        al.add(-1);
        System.out.println(al);
        al.add(0, 0);
        System.out.println(al);

        // Fill up to capacity
        for (int i = 5; i <= ArrayIntList.DEFAULT_CAPACITY; i++) {
            al.add(i);
        }
        System.out.println(al);
        
        // al.add(0);
        // System.out.println(al);
    }
}
