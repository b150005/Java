import java.util.*;
import java.util.zip.*;

public class Archive {
    public static void main(String[] args) {
        try (
            // 「圧縮ファイル」を読み込むZipFileオブジェクトの生成
            ZipFile file = new ZipFile("src/archivedData.zip");
        ) {
            // 圧縮ファイルの「中身(=ZipEntry)」を格納するArrayListオブジェクトの取得
            ArrayList<? extends ZipEntry> list = Collections.list(file.entries());
            
            // 要素毎にファイル情報を取得
            for (ZipEntry e : list) {
                // ファイル名
                String name = e.getName();

                // 圧縮後サイズ
                long compressedSize = e.getCompressedSize();

                // 圧縮前サイズ
                long size = e.getSize();

                System.out.println("file name: " + name);
                System.out.println("compressed size: " + compressedSize + "[Byte]");
                System.out.println("size: " + size + "[Byte]");
            }
        } catch (Exception e) {
            System.out.println("Opening archived file has failed.");
        }
    }
}
