import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadAndWrite {
    public static void main(String[] args) {
        // try-with-resource文を利用したリソース生成
        try (
            // FileWriterオブジェクトの生成
            // -> ファイルの新規作成(末尾に追記)
            FileWriter fw = new FileWriter("src/test.txt", true);

            // BufferedWriterオブジェクト(バッファリングフィルタ)の生成
            BufferedWriter bw = new BufferedWriter(fw);
        ) {
            // ファイルへの書き込み
            // -> 改行する場合は改行文字(\n)を挿入
            bw.write("1st sentence is written by BufferedWriter.\n");
            bw.write("2nd sentence is appended.");

            // 強制書き出し
            bw.flush();
        }
        // 例外処理
        catch (IOException e) {
            System.out.println("Output Exception occurred.");
        }
        
        try (
            // FileReaderオブジェクトの生成
            FileReader fr = new FileReader("src/test.txt");

            // BufferedReaderオブジェクトの生成
            BufferedReader br = new BufferedReader(fr);
        ) {
            // 入力ストリームが利用可能であればファイルを読み込む
            if (br.ready()) {
                // 読み込んだ文字列をnullで初期化(null安全の保証)
                String line = null;

                System.out.println("-- Output by BufferedReader --");

                // 読み込んだ文字列がnullでない限り、コンソールに出力
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }
        //例外処理
        catch (IOException e) {
            System.out.println("Input Exception occurred.");
        }

        // try節中で定義するとcatch, finally節からは"見えない"ため、
        // tryブロック外でnullで初期化しておく必要がある
        BufferedReader br2 = null;

        // Paths/Filesクラスを用いたテキストファイルの読み込み
        try {
            // Pathオブジェクトの取得
            Path p = Paths.get("src/test.txt");

            // ファイルのコピー
            Files.copy(p, Paths.get("src/test_cp.txt"));
            Files.copy(p, Paths.get("src/test_cp_delete.txt"));

            Path pd = Paths.get("src/test_cp_delete.txt");

            // ファイル名の変更
            Files.move(p, Paths.get("src/test_renamed.txt"));

            Path pr = Paths.get("src/test_renamed.txt");

            // ファイルが存在する場合はファイルを削除
            if (Files.exists(pd)) {
                Files.delete(pd);
            }
            
            System.out.println("-- Output by Paths and Files --");

            // ファイルの読み込み・出力(BufferedReaderを利用しない場合)
            String str = Files.readString(pr);
            System.out.println(str);

            System.out.println("-- Output by Files and BufferedReader --");

            // ファイルの読み込み・出力(BufferedReaderを利用する場合)
            br2 = Files.newBufferedReader(pr);
            if (br2.ready()) {
                String line = null;
                while ((line = br2.readLine()) != null ) {
                    System.out.println(line);
                }
            }

            // ファイルサイズの取得
            System.out.print("File size: ");
            System.out.printf("%7.2f[KB]\n", ((double) Files.size(pr)) / 1024.0);
        }
        // 例外処理
        catch (Exception e) {
            System.out.println("Some exceptions occurred.");
        }
        finally {
            try {
                // BufferedReaderオブジェクトの解放処理
                // <- close()メソッドも例外送出の可能性があるため、「finally節内」でtry節を用いて記述
                br2.close();
            }
            catch (Exception e) {
                System.out.println("BufferedReader br2 cannot be closed.");
            }
        }
    }
}