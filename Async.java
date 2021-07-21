import java.util.Scanner;
import java.util.concurrent.*;

class MainThread {
    // mainスレッドで実行する処理
    public static void main(String[] args) {
        System.out.println("start to count..");
        
        // 新規スレッドの生成
        Thread t1 = new SubThreadA();
        Thread t2 = new Thread(new SubThreadB());
        // ラムダ式を利用する場合
        Thread t3 = new Thread(() -> {
            System.out.println("This sentence is written by lambda, executed by SubThreadC.");
        });

        // 新規作成したスレッドの実行
        // -> 指定したスレッドクラスのrun()メソッドが呼び出される
        t1.start();
        t2.start();
        t3.start();

        try {
            // 指定スレッドの終了待機
            t1.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // サブスレッド(SubThreadA)が終了してから実行される処理
        try (
            Scanner stdIn = new Scanner(System.in);
        ) {
            // スキャン範囲に既に文字列が存在する場合は入力待機を行わない
            // -> 2行以上入力されている場合は1行しか読み取らない
            String input = stdIn.nextLine();

            System.out.println("your input: " + input);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// -- Threadクラスの継承を利用する場合 --
class SubThreadA extends Thread{
    // サブスレッドで実行する処理
    // -> 呼び出し時にオーバーライドしたrun()メソッドが自動的に実行
    @Override
    public void run() {
        System.out.println("This procedure is executed by SubThreadA.");
        for (int i = 9; i >= 1; i--) {
            System.out.print(i + ".., ");

            try {
                // スリープ処理
                // -> 列挙型TimeUnitの定数を通じたThread.sleep()メソッドの実行
                TimeUnit.SECONDS.sleep(1);
            }
            // スレッド内で送出された例外は他のスレッドに伝播せず強制終了
            catch (InterruptedException e) { ; }
        }

        System.out.println(0);
    }
}

// -- Runnableインタフェースを利用する場合 --
class SubThreadB implements Runnable {
    // サブスレッドで実行する処理
    // -> 呼び出し時にオーバーライドしたrun()メソッドが自動的に実行
    @Override
    public void run() {
        System.out.println("This sentence is printed by SubThreadB.");
    }
}