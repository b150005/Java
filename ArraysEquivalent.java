import java.util.Arrays;

public class ArraysEquivalent {
    public static void main(String[] args) {
        int[] a = {1,2,3};
        int[] b = {1,2,3};

        // 配列間の比較では、Objectクラスのequals()メソッドは「等値」判定を行う
        System.out.println("Equality: " + a.equals(b));
        // 配列間で「等価」判定を行う場合は、Arraysクラスのequals()メソッドを用いる
        System.out.println("Equivalence: " + Arrays.equals(a, b));
    }
}