import java.lang.reflect.*;

public class Reflection {
    public static void main(String[] args) throws Exception {
        // 「クラス」からクラス情報を取得
        Class<?> cStatic = ReflectionTest.class;

        // 「クラス情報」から引数を指定してコンストラクタ情報を取得
        // -> NoSuchMethodException(検査例外)が発生する可能性があるため、
        //    以下の方法でエラーハンドリングを行う必要がある
        //    1. 呼び出すメソッド(=main())に"throws文"を付与
        //    2. "try-catch文"で例外発生時の処理を記述
        Constructor<?> con = cStatic.getConstructor(String.class, int.class);

        // 「コンストラクタ情報」からオブジェクトの生成
        // -> Constructor<?>型 -> 外部クラス型 へのキャストが必要
        ReflectionTest ref = (ReflectionTest) con.newInstance("A", 5);
        System.out.println("-- Before --");
        System.out.println(ref);
        System.out.println();

        // 「オブジェクト」からクラス情報を取得
        Class<?> cDynamic = ref.getClass();

        // 「クラス情報」からフィールド情報を取得
        // -> privateなメンバを取得する場合は"getDeclared*()"メソッドを利用
        Field fStr = cStatic.getDeclaredField("str");
        Field fInt = cDynamic.getDeclaredField("i");

        // privateなフィールドのアクセス権限を変更
        fStr.setAccessible(true);
        fInt.setAccessible(true);

        // 「フィールド情報」からオブジェクトの動的フィールドの値を変更
        fStr.set(ref, "Changed");
        fInt.set(ref, 13);
        System.out.println("-- After --");
        System.out.println(ref);
        System.out.println();

        // 「クラス情報」や各「メンバ情報」の修飾子を特定
        // -> Modifierクラスの「クラス定数」を利用する場合
        switch (cStatic.getModifiers()) {
            case Modifier.PUBLIC:
                System.out.println("Class " + cStatic.getSimpleName() + " is declared as public."); break;
            case Modifier.PROTECTED:
                System.out.println("Class " + cStatic.getSimpleName() + " is declared as protected."); break;
            case Modifier.PRIVATE:
                System.out.println("Class " + cStatic.getSimpleName() + " is declared as private."); break;
            default:
                System.out.println("Class " + cStatic.getSimpleName() + " is declared as default."); break;
        }
        Field fREF_S = cDynamic.getDeclaredField("REF_STATIC");
        int modfREF_S = fREF_S.getModifiers();
        // Modifierクラスの「クラスメソッド」を利用する場合
        if (Modifier.isPrivate(modfREF_S)) {
            System.out.print("Field " + fREF_S.getName() + " is declared as private");
            if (Modifier.isStatic(modfREF_S)) {
                System.out.print(", static");
                if (Modifier.isFinal(modfREF_S)) {
                    System.out.println(", final.");
                }
            }
        }
    }
}

class ReflectionTest {
    private String str = "";
    private int i = 0;
    // 動的final変数(利用しない)
    private final String REF = "Final String(dynamic)";
    // 静的final変数
    private static final String REF_STATIC = "Final String(static)";

    // デフォルトコンストラクタ
    public ReflectionTest() {}

    // オーバーロードしたコンストラクタ
    public ReflectionTest(String str) {
        this.str = str;
    }
    public ReflectionTest(int i) {
        this.i = i;
    }
    public ReflectionTest(String str, int i) {
        this.str = str;
        this.i = i;
    }

    // オーバーロードした動的メソッド
    public void add(String addStr) {
        this.str += addStr;
    }
    public void add(int j) {
        this.i += j;
    }
    public void add(String addStr, int j) {
        this.str += addStr;
        this.i += j;
    }

    @Override
    public String toString() {
        return "ReflectionTest(" + this.str + ", " + this.i + ") " + "[" + REF + ", " + REF_STATIC + "]";
    }
}