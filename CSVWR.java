import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.*;

public class CSVWR {
    public static void main(String[] args) {
        // CSVに格納するデータ群
        RecordData rd1 = new RecordData("Record1", "ABC", "あいう", 123);
        RecordData rd2 = new RecordData("Record2", "DEF", "えおか", 456);
        RecordData rd3 = new RecordData("Record3", "GHI", "きくけ", 789);
        RecordData rd4 = new RecordData("Record4", "JKL", "こさし", 1011);
        RecordData rd5 = new RecordData("Record5", "MNO", "すせそ", 1112);

        // レコードデータ(List<RecordData>型)
        // -> 各要素は"1つ"のクラスオブジェクトデータとなり、各レコードはダブルクオテーション("")で囲まれる
        // <- データ内にカンマ(,)が含まれる場合、そのデータはダブルクオテーションで囲まれる
        // => レコードとして出力する際は、クラスオブジェクトの各フィールドを取り出す必要がある
        List<RecordData> listRD = new ArrayList<>();
        listRD.add(rd1);
        listRD.add(rd2);
        listRD.add(rd3);
        listRD.add(rd4);
        listRD.add(rd5);

        // -- CSV形式データをCSVファイルに出力 --
        try (
            /*
                データの流れ:
                JVM (-> CSVFormat) -> CSVPrinter -> BufferedWriter
                    -> FileWriter -> CSVファイル
            */

            // 「テキストファイル」に文字列を書き込むFileWriterオブジェクトの生成
            FileWriter fw = new FileWriter("src/test.csv", true);

            // バッファリングしながら「文字列」を書き込むBufferedOutputStreamオブジェクトの生成
            BufferedWriter bw = new BufferedWriter(fw);

            // CSVFormatオブジェクトの定義・CSVPrinterオブジェクトの取得(=CSVファイルの作成)
            // -> CSVPrinterオブジェクトの生成時にCSVファイルが作成される
            CSVPrinter p = new CSVPrinter(bw, CSVFormat
                // CSV形式の指定
                .DEFAULT
                // カラム名(=ヘッダ)の書き込み
                .withHeader("Record", "data 1", "data 2", "data 3")
            );    
        ) {
            // レコードデータ(List<String>型)の書き込み
            // -> RecordData型だと"1つ"のオブジェクトとして扱われるため、各フィールドの値を個別に取り出して指定
            for (RecordData data : listRD) {
                p.printRecord(
                    data.getRec(), 
                    data.getStr1(), 
                    data.getStr2(), 
                    Integer.toString(data.getI())
                );
            }

            // 強制書き込み
            p.flush();
        }
        catch (Exception e) {
            System.out.println("Some exception(Output) occurred.");
        }

        // -- 書き込みの終了待機 --
        // -> 待機しない場合は書き込みが間に合わずIOExceptionが送出
        try {
            Thread.sleep(700);
        }
        catch (Exception e) {
            System.out.println("Exception occurred in sleep().");
        }
        
        // -- CSVファイルの読み込み --
        try (
            /*
                データの流れ:
                CSVファイル(クラスパス基準) -> InputStream -> InputStreamReader -> FileReader 
                    -> BufferedReader -> CSVParser -> CSVRecord -> JVM
            */

            // CSVファイルを「バイトストリーム」経由で読み込むInputStreamオブジェクト(クラスパス経由)
            // -> InputStreamは「バイトストリーム」を表すため、バイトストリーム -> 文字ストリーム への変換が必要
            // <- バイトストリームを利用することで、「あらゆる文字コードに対応」かつ「読み込みが最速」となる
            InputStream is = Main.class.getResourceAsStream("src/test.csv");

            // バイトストリーム -> 文字ストリーム への変換を行うInputStreamReaderオブジェクトの生成
            InputStreamReader isr = new InputStreamReader(is);

            // BufferedReaderオブジェクトの生成
            BufferedReader br = new BufferedReader(isr);
        ) {
            if (Files.exists(Paths.get("src/test.csv"))) {
                // CSVファイルの「テーブルデータ」を保持するCSVParserオブジェクトの取得
                // -> CSVFormatのメソッドを利用してCSV解析を行う
                // <- CSVParserはIterable<CSVRecord>の実装クラス
                CSVParser csvp = CSVFormat
                    // CSV形式の指定
                    .DEFAULT
                    // カラムの指定
                    .withHeader("Record", "data A", "data B", "data C")
                    // 1行目をカラム名(=ヘッダ)としてスキップ
                    .withFirstRecordAsHeader()
                    // 値のトリム(=前後の空白を除外)
                    .withIgnoreSurroundingSpaces()
                    // 空白行のスキップ
                    .withIgnoreEmptyLines()
                    // CSV解析の実行、CSVParserオブジェクトの取得
                    .parse(br);
            
                // CSVRecordを取得・コンソールへの出力
                for (CSVRecord csvr : csvp) {
                    // CSVRecordオブジェクトが保持する値の取得
                    // -> get()メソッドはインデックス・カラム名を指定した値の取得が可能
                    String rec = csvr.get(0);
                    String str1 = csvr.get(1);
                    String str2 = csvr.get(2);
                    int i = Integer.parseInt(csvr.get(3));

                    System.out.println(rec + ": " + "(" + str1 + ", " + str2 + ", " + i + ")");
                }
            }
        }
        catch (NumberFormatException e) {
            System.out.println("NumberFormatException occurred.");
        }
        catch (Exception e) {
            System.out.println("Some exception(Input) occurred.");
        }  
    }
}

class RecordData {
    // フィールド
    private String rec;
    private String str1;
    private String str2;
    private int i;

    // コンストラクタ
    RecordData(String rec, String str1, String str2, int i) {
        this.rec = rec;
        this.str1 = str1;
        this.str2 = str2;
        this.i = i;
    }

    // ゲッタ(=アクセサ)
    public String getRec() {
        return this.rec;
    }
    public String getStr1() {
        return this.str1;
    }
    public String getStr2() {
        return this.str2;
    }
    public int getI() {
        return this.i;
    }
}