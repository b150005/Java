import java.io.*;
import java.util.Enumeration;
import java.util.*;

class PropertyFileWR {
    public static void main(String[] args) {
        // 「キー」と「値」をもつプロパティデータ
        Properties p = new Properties();

        // プロパティデータのセット
        p.setProperty("key1", "value1");
        p.setProperty("key2", "value2");
        p.setProperty("key3", "value3");

        // プロパティファイルへの書き込み
        try (
            // プロパティファイル(=.properties)に「テキストデータ」を書き込むFileWriterオブジェクト
            FileWriter fw = new FileWriter("src/p.properties");
        ) {
            // プロパティファイルの出力・コメントの指定
            p.store(fw, "Test Properties");
        } catch (Exception e) {
            System.out.println("Some exceptions occurred in output.");
        }

        // プロパティファイルの読み込み
        try {
            // 「クラスパス」を起点としたファイルの読み込み
            // -> プロパティファイルの「読み込み」時のみ、クラスパスを起点とするResourceBundleクラスが利用可能
            ResourceBundle rb = ResourceBundle.getBundle("src.p");

            // プロパティの「キー」のリスト
            Enumeration<String> keyList = rb.getKeys();

            // プロパティの「値」の読み込み
            List<String> valList = new ArrayList<>();
            valList.add(rb.getString("key1"));
            valList.add(rb.getString("key2"));
            valList.add(rb.getString("key3"));
            
            // 取得したプロパティをコンソールに出力
            while (keyList.hasMoreElements()) {
                for (int i = 0; i < valList.size(); i++) {
                    System.out.println(keyList.nextElement() + ": " + valList.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println("Some exceptions occurred in input.");
        }
    }
}