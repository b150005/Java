class MainA {
    public static void main(String[] args) {
        // 「静的メソッド」(=関数オブジェクト)の「ポインタ」を関数インタフェースに格納
        // -> 「静的メソッド」のポインタは"<クラス>::<静的メソッド>"で表現
        TernaryStringToStringFunction<String, String, String, String> staticFunc = FuncInterfaceTest::staticUnion;

        // 動的メソッドの利用にあたってインスタンスを生成
        FuncInterfaceTest fit = new FuncInterfaceTest();

        // 「動的メソッド」(=関数オブジェクト)の「ポインタ」を関数インタフェースに格納
        // -> 「動的メソッド」のポインタは"<クラスオブジェクト>::<動的メソッド>"で表現
        TernaryStringToStringFunction<String, String, String, String> dynamicFunc = fit::dynamicUnion;

        // ラムダ式による関数の記述
        TernaryStringToStringFunction<String, String, String, String> lambdaFunc = 
            (String str1, String str2, String str3) -> { return str1 + str2 + str3; };

        // 関数オブジェクト・ラムダ式関数の呼び出し
        // -> 関数オブジェクト・ラムダ式関数(のポインタ)の格納先は「関数インタフェース」であるため、
        //    "<関数インタフェース>.<抽象メソッド>"で関数オブジェクトを呼び出す
        String staticUnionStr = staticFunc.union("A", "B", "C");
        String dynamicUnionStr = dynamicFunc.union("1", "2", "3");
        String lambdaUnionStr = lambdaFunc.union("あ", "い", "う");

        System.out.println("Static Method: " + staticUnionStr);
        System.out.println("Dynamic Method: " + dynamicUnionStr);
        System.out.println("Lambda Method: " + lambdaUnionStr);
    }
}

class FuncInterfaceTest {
    // 静的メソッド
    public static String staticUnion(String str1, String str2, String str3) {
        return str1 + str2 + str3;
    }
    // 動的メソッド
    public String dynamicUnion(String str1, String str2, String str3) {
        return str1 + str2 + str3;
    }
}

// 独自定義した関数インタフェース
// -> "@FunctionalInterface"アナテイションを付与することで、「関数インタフェース」であることを明示的に宣言
// -> 「関数インタフェース」であるため、「ただ1つの抽象メソッド」のみを定義
@FunctionalInterface
interface TernaryStringToStringFunction<F, S, T, R> {
    public abstract String union(String str1, String str2, String str3);
}