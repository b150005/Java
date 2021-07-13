import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

class SystemProperties {
    public static void main(String[] args) {
        // システムプロパティ一覧の取得
        Properties p = System.getProperties();

        // システムプロパティのキー群の取得
        Set<String> keySet = p.stringPropertyNames();

        // Set<String>型 -> Iterator<String>型 への変換
        // -> Iterator<E>型は要素の走査が可能
        Iterator<String> keyItr = keySet.iterator();

        // システムプロパティ一覧の表示
        System.out.println("-- System Properties --");
        while (keyItr.hasNext()) {
            // キーの抽出
            String key = keyItr.next();

            System.out.println(key + ": " + System.getProperty(key));
        }
    }
}