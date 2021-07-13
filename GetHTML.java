import java.io.*;
import java.net.URL;

public class GetHTML {
    public static void main(String[] args) {
        URL url = null;
        try {
            // 「URL情報」を保持するURLオブジェクトの生成
            url = new URL("http://httpbin.org/get");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (
            // URLオブジェクトによるバイトストリームの生成
            InputStream is = url.openStream();

            // バイトストリーム -> 文字ストリーム への変換を行うInputStreamReaderフィルタの生成
            InputStreamReader isr = new InputStreamReader(is);

            // 「バッファリング」を行うBufferedReaderフィルタの生成
            BufferedReader br = new BufferedReader(isr);
        ) {
            // HTML情報の取得・コンソールへの出力
            String html = br.readLine();
            while (!(html.equals(null))) {
                System.out.println(html);
                html = br.readLine();
            }
        }
        catch (NullPointerException e) {
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}