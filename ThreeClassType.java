public class ThreeClassType {
    public static void main(String[] args) {
        // 静的メンバクラスオブジェクトの生成
        // -> 外部クラスから抽出してオブジェクトを生成可能
        Outer.InnerStaticMember ism = new Outer.InnerStaticMember();
        ism.innerStaticMemberMethod();

        // 外部クラスの静的メソッドは外部クラスオブジェクトの生成が不要
        Outer.outerStaticMethod();

        // 「動的メンバクラスオブジェクトの生成」・「外部クラスの動的メソッド利用」時は、外部クラスオブジェクトの生成が必要
        Outer o = new Outer();
        o.outerMethod();

        // 動的メンバクラスオブジェクトは外部クラスオブジェクトを紐づけて生成
        // -> "<外部クラスオブジェクト>.new"で宣言
        Outer.InnerMember im = o.new InnerMember();
        im.innerMemberMethod();

        // ローカル変数(final)
        final int localFieldFinal = 0;
        // ローカル変数(非final)
        int localField = 0;

        // 匿名クラスの定義・匿名クラスオブジェクトの生成・匿名クラスメソッドの利用
        // -> "new文"で「親クラス」を指定して匿名クラスを定義
        // -> 「実質的にfinal」であるローカル変数(および外部クラスのフィールド)にのみアクセス可能
        new Object() {
            int anonymousField = 0;
            // 匿名クラスの動的メソッド
            // -> 匿名クラスはオブジェクトを即時生成するため利用可能
            void anonymousMethod() {
                ++anonymousField;
                System.out.println("local field(final) in Anonymous Class: " + localFieldFinal);
                System.out.println("local field(non-final) in Anonymous Class: " + localField);
                System.out.println("anonymousField in Anonymous Class: " + anonymousField);
            }
        }.anonymousMethod();
    }
}

// 外部クラス
class Outer {
    // 外部クラスの静的フィールド
    static int outerStaticField = 0;
    // 外部クラスの動的フィールド
    int outerField = 0;

    // 静的メンバクラス
    // -> 「クラスOuter」の一部であるため、Outerオブジェクトの生成は不要であり、クラスOuterから抽出して利用可能
    // -> Outerオブジェクトからは独立(=静的)しているため、「静的フィールド」にのみアクセス可能
    static class InnerStaticMember {
        void innerStaticMemberMethod() {
            ++outerStaticField;
            System.out.println("static field in Static Member Class: " + outerStaticField);
        }
    }

    // 動的メンバクラス
    // -> 「Outerオブジェクト」の一部であるため、Outerオブジェクトを生成し紐づける必要がある
    // -> 「Outerオブジェクト」と紐づいているため、「動的フィールド」にもアクセス可能
    class InnerMember {
        void innerMemberMethod() {
            ++outerStaticField;
            ++outerField;
            System.out.println("static field in Member Class: " + outerStaticField);
            System.out.println("field in Member Class: " + outerField);
        }
    }

    // 動的メソッド
    void outerMethod() {
        // ローカル変数(final)
        final int outerLocalFieldFinal = 0;
        // ローカル変数(非final)
        int outerLocalField = 0;

        // ローカルクラス
        // -> 「実質的にfinal」であるローカル変数(および外部クラスのフィールド)にのみアクセス可能
        class InnerLocal {
            void innerLocalMethod() {
                ++outerStaticField;
                ++outerField;
                System.out.println("local field(final) in Local Class(defined in Dynamic Method): " + outerLocalFieldFinal);
                System.out.println("local field(non-final) in Local Class(defined in Dynamic Method): " + outerLocalField);
                System.out.println("static field in Local Class(defined in Dynamic Method): " + outerStaticField);
                System.out.println("field in Local Class(defined in Dynamic Method): " + outerField);
            }
        }

        // ローカルクラスは定義したメソッド内でのみ利用可能
        InnerLocal il = new InnerLocal();
        il.innerLocalMethod();
    }

    // 静的メソッド
    static void outerStaticMethod() {
        // ローカル変数(final)
        final int outerLocalFieldFinal = 0;
        // ローカル変数(非final)
        int outerLocalField = 0;

        // ローカルクラス
        // -> 「実質的にfinal」であるローカル変数(および外部クラスのフィールド)にのみアクセス可能
        class InnerLocal {
            void innerLocalMethod() {
                ++outerStaticField;
                System.out.println("local field(final) in Local Class(defined in Static Method): " + outerLocalFieldFinal);
                System.out.println("local field(non-final) in Local Class(defined in Static Method): " + outerLocalField);
                System.out.println("static field in Local Class(defined in Static Method): " + outerStaticField);
            }
        }

        // ローカルクラスは定義したメソッド内でのみ利用可能
        InnerLocal il = new InnerLocal();
        il.innerLocalMethod();
    }
}