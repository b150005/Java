import java.io.BufferedReader;
import java.io.InputStreamReader;

class ShellScript {
    public static void main(String[] args){
        System.out.println("Make and Run .sh");

        // ProcessBuilderオブジェクトを用いてシェルスクリプトを作成
        ProcessBuilder pb = new ProcessBuilder("sh", "-c", "echo 'Hello!'");

        try {
            // シェルスクリプトの実行
            Process pc = pb.start();

            // 実行結果を格納するStringBuilderオブジェクト
            StringBuilder output = new StringBuilder();

            // 実行結果を読み込むBufferedReaderオブジェクト
            BufferedReader reader = new BufferedReader(new InputStreamReader(pc.getInputStream()));

            // 実行結果の読み込み
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            // 実行結果をコンソールに出力
            System.out.println(output.toString());
        }
        catch (Exception e) {
            // 例外発生時は標準エラーストリームに出力
            e.printStackTrace();

            // プログラムを終了
            System.exit(1);
        }
    }
}