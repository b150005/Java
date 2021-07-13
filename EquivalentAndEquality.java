class EquivalentAndEquality {
    public static void main(String[] args) {
        // 同じ綴りをもつ文字列リテラルは、同一のString型インスタンスを参照
        String s1 = "ABC";
        String s2 = "ABC";

        // 実行時に生成される文字列は、異なるインスタンスとして扱われる
        String s3 = new String("ABC");
        String s4 = new String("ABC");

        // 文字列リテラルで定義したString型インスタンス間の「等価」判定
        if (s1.equals(s2)) {
            System.out.println("Both strings are equivalent.");
        }
        else {
            System.out.println("Both strings are not equivalent.");
        }
        // 文字列リテラルで定義したString型インスタンス間の「等値」判定
        if (s1 == s2) {
            System.out.println("Both strings have equality.");
        }
        else {
            System.out.println("Both strings does not have equality.");
        }

        // 実行時に生成した文字列(Stringオブジェクト)間の「等価」判定
        if (s3.equals(s4)) {
            System.out.println("Both strings are equivalent.");
        }
        else {
            System.out.println("Both strings are not equivalent.");
        }
        // 実行時に生成した文字列(Stringオブジェクト)間の「等値」判定
        if (s3 == s4) {
            System.out.println("Both strings have equality.");
        }
        else {
            System.out.println("Both strings do not have equality.");
        }
    }
}