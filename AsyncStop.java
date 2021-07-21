import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

class AsyncStop {
    public static void main(String[] args) {
        // サブスレッドの生成
        SubThreadStoppable t = new SubThreadStoppable();

        // サブスレッド上での処理実行
        t.start();

        // メインスレッド上での処理
        String input = "";
        try (
            Scanner stdIn = new Scanner(System.in);
        ) {
            // "1"が入力されるまでメインスレッドが稼働
            while (!input.equals("1")) {
                input = stdIn.nextLine();
            }

            // "1"が入力された場合は停止フラグをtrueに変更
            if (input.equals("1")) {
                t.stopFlg.set(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // サブスレッドでの処理終了待機
            // -> 既にサブスレッドが消滅している場合はスキップされる
            t.join();

            System.out.println("stop all threads");
        }
        catch (InterruptedException e) { ; }
    }
}

class SubThreadStoppable extends Thread {
    // スレッドセーフな停止フラグ
    // <- メインスレッドによる「値の書き換え」とサブスレッドによる「値の読み取り」が競合する可能性があるため、
    //    スレッドセーフなAtomicBooleanを利用
    // <- 参照先の書き換えを禁止するためfinalキーワードを付与
    final AtomicBoolean stopFlg = new AtomicBoolean(false);
    
    // サブスレッド上で動作する処理
    @Override
    public void run() {
        for (int i = 9; i >= 1; i--) {
            // スレッドセーフな停止フラグ
            if (this.stopFlg.get()) {
                // 停止フラグがtrueである場合はループ処理を抜ける
                break;
            }

            System.out.print(i + ".., ");

            try {
                // スリープ処理
                TimeUnit.SECONDS.sleep(1);
            }
            catch (Exception e) { ; }
        }

        if (!this.stopFlg.get()) {
            System.out.println(0);

            System.out.println("input \"1\" to exit");
        }
    }
}