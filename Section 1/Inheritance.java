class Bay extends Lake {
    public void method1() {
        System.out.println("Bay 1");
        super.method2();
    }

    public void method2() {
        System.out.println("Bay 2");
    }
}

class Pond {
    public void method2() {
        System.out.println("Pond 2");
    }
}
class Ocean extends Bay {
    public void method2() {
        System.out.println("Ocean 2");
    }
}
class Lake extends Pond {
    public void method3() {
        System.out.println("Lake 3");
        method2();
    }
}

public class Inheritance {
    public static void main(String[] args) {
        Lake var1 = new Ocean();
        Pond var2 = new Pond();
        Pond var3 = new Lake();
        Object var4 = new Bay();
        Lake var5 = new Bay();
        Bay var6 = new Ocean();
    }
}
