public class CopyInstance {
    public static void main(String[] args) {
        // 複製元オブジェクト
        Y b1 = new Y("b1", 1, new X("a1"));
        
        // 複製先オブジェクト
        Y b2 = b1.clone();

        System.out.println("-- Before --");
        System.out.println("b1: " + b1);
        System.out.println("b2: " + b2);

        System.out.println();

        // 複製先オブジェクトの値の書き換え
        b2.setY("b2", 2, new X("a2"));

        System.out.println("-- After --");
        System.out.println("b1: " + b1);
        System.out.println("b2: " + b2);
    }
}

// インスタンスの複製を行うクラス
// -> Cloneableインタフェースの実装クラス
class X implements Cloneable {
    private String str;

    // デフォルトコンストラクタ
    public X() {}

    // コンストラクタ
    public X(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    // 深い複製(deep copy)を行うclone()メソッドの定義
    // -> clone()メソッドのオーバーライド(※Cloneableインタフェースでは未宣言(=マーカーインタフェース))
    @Override
    public X clone() {
        X a = new X();
        a.str = this.str;

        return a;
    }
}

// インスタンスの複製を行うクラス
// -> Cloneableインタフェースの実装クラス
class Y implements Cloneable {
    // 参照型(特殊)プロパティ
    private String str;
    // 値型プロパティ
    private int n;
    // 参照型(一般)プロパティ
    // -> 深い複製を行う場合は、参照先プロパティの値を複製する必要がある
    private X aObj;

    // デフォルトコンストラクタ
    public Y() {}

    // コンストラクタ
    public Y(String str, int n, X aObj) {
        this.str = str;
        this.n = n;
        this.aObj = aObj;
    }

    // セッタ(=アクセサ)
    public void setY(String str, int n, X aObj) {
        this.str = str;
        this.n = n;
        this.aObj = aObj;
    }

    @Override
    public String toString() {
        return ( "Y(" + str + ", " + n + ", " + aObj.getStr() + ")" );
    }

    // 深い複製(deep copy)を行うclone()メソッドの定義
    @Override
    public Y clone() {
        Y b = new Y();
        b.str = this.str;
        b.n = this.n;
        // 深い複製(deep copy)
        // -> 参照先インスタンスを複製
        b.aObj = this.aObj.clone();

        return b;
    }
}