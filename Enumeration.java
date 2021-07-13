public class Enumeration {
    public static void main(String[] args) {
        ABCStr abcStrA = new ABCStr(ABC.A);
        ABCStr abcStrB = new ABCStr(ABC.B);
        ABCStr abcStrC = new ABCStr(ABC.C);

        System.out.println(abcStrA);
        System.out.println(abcStrB);
        System.out.println(abcStrC);
    }
}

// 安全に値を制約する列挙型
// -> 定義した列挙子以外の入力は許されない
// <- アクセス修飾子として"public"を付与すると、同一ソースファイルに"public"でアクセス修飾子が複数存在する場合、
//    「実行時」には2番目以降の"public"が付与された識別子は認識されないため、値がnullとなることに注意
enum ABC {
    A, B, C;
}

class ABCStr {
    private ABC abc;

    // コンストラクタ
    public ABCStr(ABC abc) {
        this.abc = abc;
    }

    @Override
    public String toString() {
        // 継承先で同名プロパティが存在した場合に、クラスABCStrのプロパティを参照するため
        // "this"キーワードを付与
        return "Value: " + this.abc;
    }
}