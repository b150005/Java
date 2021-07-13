import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DeclarativeStream {
    public static void main(String[] args) {
        // ストリームの構成要素をリスト(コレクション)で定義
        List<CompareAWithB> list = new ArrayList<>();
        list.add(new CompareAWithB("A", 9));
        list.add(new CompareAWithB("B", 7));
        list.add(new CompareAWithB("C", 12));

        // コレクションからストリームを生成
        Stream<CompareAWithB> stm = list.stream();
        // ストリームの要素数
        // -> Streamは"一度"利用すると閉栓されるため、再利用不可
        // => 利用するたびにコレクションからストリームを生成して利用する
        long c = stm.count();
        System.out.println("components in Stream: " + c);

        // プロパティiの値が10未満である各要素に対して行う処理
        list.stream()
            // 中間処理
            .filter( i -> i.getInt() < 10)
            // 終端処理
            .forEach(i -> i.addString("(less than 10)"));

        // Optional<T>型は「Optional.empty」で初期化される
        // <- 「ローカル変数」は初期化必須
        Optional<CompareAWithB> comOpt;
        CompareAWithB com = new CompareAWithB();

        System.out.println("-- findAny() by Multi Thread--");
        for (int i = 0; i < 100; i++) {
            // 順次ストリーム -> 並列ストリーム への変換
            Stream<CompareAWithB> palStm = list.stream().parallel();

            // 並列ストリームで最初に見つかった要素を返却
            comOpt = palStm.findAny();

            // Optional<T>型を取り扱う際はnull安全に配慮
            if (comOpt.isPresent()) {
                // Optional<CompareAWithB>型 -> CompareAWithB型 への変換
                com = comOpt.get();
            }
            if (com != null) {
                System.out.println( (i + 1) + ": " + com );
            }
        }

        // 全要素のプロパティiの値が3より大きいである場合の処理
        if (list.stream().allMatch( (CompareAWithB i) -> {
            return i.getInt() > 3;
        })) {
            System.out.println("All components have properties with 3 or greater.");
        }

        // プロパティiの値が最小である要素
        // -> 「比較対象」(=コンパレータ)を指定
        Optional<CompareAWithB> minCon = list.stream().min((x, y) -> x.getInt() - y.getInt());

        System.out.println("component with the minimum value: " + minCon.get());
    }
}

class CompareAWithB {
    private String str;
    private int i;

    // デフォルトコンストラクタ
    public CompareAWithB() {}

    // コンストラクタ
    public CompareAWithB(String str, int i) {
        this.str = str;
        this.i = i;
    }

    // ゲッタ(=アクセサ)
    public String getString() {
        return this.str;
    }
    public int getInt() {
        return this.i;
    }

    // 文字列を追加
    public void addString(String s) {
        this.str += s;
    }

    @Override
    public String toString() {
        return "CompareAWithB(" + this.str + ", " + this.i + ")";
    }
}