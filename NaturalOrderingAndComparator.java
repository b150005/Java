import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Main {
    public static void main(String[] args) {
        // ArrayListオブジェクト
        List<CompareTo> list = new ArrayList<>();
        
        list.add(new CompareTo(1, 10, 9));
        list.add(new CompareTo(3, 7, 11));
        list.add(new CompareTo(8, 8, 5));

        System.out.println("-- Before --");
        for (int i = 0; i < list.size(); i++) {
            System.out.print("index " + i + ": ");
            System.out.println(list.toArray()[i]);
        }
        System.out.println();

        // ユーザ定義の「自然順序付け」によるソート
        Collections.sort(list);

        System.out.println("-- By Natural Ordering(num2) --");
        for (int i = 0; i < list.size(); i++) {
            System.out.print("index " + i + ": ");
            System.out.println(list.toArray()[i]);
        }
        System.out.println();

        // "num1"プロパティの「コンパレータ」によるソート
        Collections.sort(list, new Num1Comparator());

        System.out.println("-- By num1 --");
        for (int i = 0; i < list.size(); i++) {
            System.out.print("index " + i + ": ");
            System.out.println(list.toArray()[i]);
        }
        System.out.println();

        // "num3"プロパティの「コンパレータ」によるソート
        Collections.sort(list, new Num3Comparator());

        System.out.println("-- By num3 --");
        for (int i = 0; i < list.size(); i++) {
            System.out.print("index " + i + ": ");
            System.out.println(list.toArray()[i]);
        }
    }
}

// "num1"プロパティの値をもとに順序付けを行うコンパレータ
// -> Comparator<T>インタフェースの実装クラス
class Num1Comparator implements Comparator<CompareTo> {
    // Comparator<T>インタフェースのcompare()メソッドをオーバーライド
    public int compare(CompareTo x, CompareTo y) {
        return (x.getNum1() - y.getNum1());
    }
}

// "num2"プロパティの値をもとに順序付けを行うコンパレータ
class Num3Comparator implements Comparator<CompareTo> {
    // Comparator<T>インタフェースのcompare()メソッドをオーバーライド
    public int compare(CompareTo x, CompareTo y) {
        return (x.getNum3() - y.getNum3());
    }
}

// 大小関係を比較するクラス
// -> オブジェクトの大小関係を比較する場合、Comparable<T>インタフェースを実装し、
//    compareTo()メソッドをオーバーライドすることで「自然順序付け」を定義する必要がある。
// <- Comparable<T>の型は"実装クラス名"を指定
class CompareTo implements Comparable<CompareTo> {
    private int num1;
    private int num2;
    private int num3;

    // コンストラクタ
    public CompareTo(int num1, int num2, int num3) {
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
    }

    // ゲッタ(=アクセサ)
    public int getNum1() {
        return num1;
    }

    // ゲッタ(=アクセサ)
    public int getNum3() {
        return num3;
    }

    // toString()メソッドのオーバーライドによる出力形式の指定
    @Override
    public String toString() {
        return ( "CompareTo(" + num1 + ", " + num2 + ", " + num3 + ")" );
    }

    // 自然順序付け
    // -> Comparable<T>インタフェースのcompareTo()メソッドをオーバーライド
    // => "num2"プロパティの値をもとに自然順序付けが行われる
    @Override
    public int compareTo(CompareTo obj) {
        if (this.num2 < obj.num2) {
            return -1;
        }
        else if (this.num2 > obj.num2) {
            return 1;
        }
        // 比較対象の方が 大きくない かつ 小さくない(=「等価」である)場合
        return 0;
    }
}