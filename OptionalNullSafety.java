import java.util.Optional;

public class OptionalNullSafety {
    // 引数のOptional<String>型データが「null」または「文字数0」であれば置換した文字列を返却
    static String nullCheck(Optional<String> str) {
        // 引数strがnullまたは文字数0である場合
        if ( !str.isPresent() || str.get().length() == 0 ) {
            return str.orElse("Value is null or its length is 0.");
        }
        else {
            return str.get();
        }
    }

    public static void main(String[] args) {
        // nullableなString型フィールド
        Optional<String> str1 = Optional.ofNullable("ABC");
        Optional<String> str2 = Optional.ofNullable(null);

        System.out.println("str1: " + nullCheck(str1));
        System.out.println("str2: " + nullCheck(str2));
    }
}
