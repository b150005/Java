import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HashSetEquivalent {
    public static void main(String[] args) {
        // HashSetオブジェクトの生成
        Set<A> list = new HashSet<>();

        // ユーザ定義クラスのオブジェクトを生成・HashSetオブジェクトへの追加
        A a1 = new A("Object A", 1);
        list.add(a1);

        System.out.println("components: " + list.size());

        // ユーザ定義クラスの(等値でない)オブジェクトの再生成・HashSetオブジェクトから削除
        a1 = new A("Object A", 1);
        // HashSetクラスのremove()メソッドは、以下の手順で等価である構成要素を走査
        // 1. 構成要素に対してhashCode()メソッドを呼び出しハッシュ値を生成
        // 2. ハッシュ値が同じである構成要素を走査(->ハッシュ値はint型であり比較が容易であるため)
        // 3. ハッシュ値が同じである構成要素に対してequals()メソッドで等価判定を実行
        // 4. 等価である構成要素をHashSetから削除
        list.remove(a1);

        System.out.println("components: " + list.size());
    }
}

// ユーザ定義クラス
class A {
    private String str;
    private int n;

    // コンストラクタ
    public A(String str, int n) {
        this.str = str;
        this.n = n;
    }

    // 等価判定
    // -> 「等価」の基準を定義するためにオーバーライドする必要がある
    @Override
    public boolean equals(Object o) {
        // ①「等値」であれば「等価」である
        if (o == this) return true;

        // ②「引数」がnullであればそもそも「等価」でない(=「等価」判定が実施できない)
        if (o == null) return false;
        
        // ③「引数」が「クラスA」または「クラスAの下位クラス」でなければそもそも「等価」でない
        if (!(o instanceof A)) return false;
        // また、「引数」が「クラスAの下位クラス」である場合に備えてアップキャストを行う
        A h = (A) o;
        
        // ④String.trim()メソッドを用いて先頭・末尾の「空白文字」を削除し、「等価」でなければfalseを返却
        if (!this.str.trim().equals(h.str.trim())) {
            return false;
        }

        // 上記の条件にすべて当てはまらない場合はtrueを返却
        return true;
    }

    // ハッシュ値の生成
    // -> 「ハッシュ値」の算出方法を定義するためにオーバーライドする必要がある
    @Override
    public int hashCode() {
        // ユーザ定義の引数をもとにハッシュ値を生成
        return Objects.hash(this.str, this.n);
    }
}