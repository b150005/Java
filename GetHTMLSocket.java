import java.io.*;
import java.net.Socket;

class GetHTMLSocket {
    public static void main(String[] args) {
        try (
            // 「ホスト名」「ポート番号」を指定してSocketオブジェクトを生成
            Socket s = new Socket("httpbin.org", 80);

            // Socketオブジェクト -> 入出力バイトストリームの生成
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            // バイトストリーム -> 文字ストリーム への変換を行うInputStreamReaderフィルタ、
            // 文字ストリーム -> バイトストリーム への変換を行うOutputStreamWriterフィルタの生成
            InputStreamReader ism = new InputStreamReader(is);
            OutputStreamWriter osw = new OutputStreamWriter(os);

            // バッファリングフィルタの生成
            BufferedReader br = new BufferedReader(ism);
            BufferedWriter bw = new BufferedWriter(osw);
        ) {
            // GETリクエストの送信
            bw.write("GET /get HTTP/1.1\n");
            bw.write("Host: httpbin.org\n");
            bw.write("\n");

            // 強制書き込み
            bw.flush();

            // GETメソッドによる取得結果の表示
            String str = "";
            while (true) {
                str = br.readLine();
                if (str == null) {
                    break;
                }
                System.out.println(str);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}