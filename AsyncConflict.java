class AsyncConflict {
    public static void main(String[] args) {
        ConflictTest test = new ConflictTest(1);

        Thread m = Thread.currentThread();
        Thread t1 = new SubThread1(test);
        Thread t2 = new Thread(new SubThread2(test));

        // スレッド情報の取得
        System.out.println("-- Thread Information(threadName: priority) --");
        System.out.println(m.getName() + ": " + m.getPriority());
        System.out.println(t1.getName() + ": " + t1.getPriority());
        System.out.println(t2.getName() + ": " + t2.getPriority());

        System.out.println("-- test --");

        System.out.println("initialized:" + test.id);

        // 共有インスタンスを扱う並列処理
        // -> 本来であれば「スレッドの競合」が発生するが、synchronizedブロックによって
        //    最初にsynchronizedブロックを呼び出したスレッド -> synchronizedブロックを呼び出す待機状態のスレッド -> ...
        //    というように、スレッド間の同期が行われる。
        //    ※この場合、待機順は実行順位とは無関係
        t1.start();
        t2.start();
        synchronized (test) {
            test.id *= 5;
            System.out.println("main thread: " + test.id + "(multiply 5)");
        }

        try {
            // サブスレッドの終了待機
            t1.join();
            t2.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ended: " + test.id);
    }
}

// 共有インスタンスを保持するクラス
class ConflictTest {
    public int id;

    // コンストラクタ
    public ConflictTest(int id) {
        this.id = id;
    }
}

// サブスレッド(SubThread1)
class SubThread1 extends Thread {
    private ConflictTest test;

    // コンストラクタ
    public SubThread1(ConflictTest test) {
        this.test = test;
    }

    // サブスレッド(SubThread1)で動作する処理
    @Override
    public void run() {
        // 
        synchronized (test) {
            try {
                // 指定ミリ秒の待機(=ウェイトセットへの退避)
                // -> 同期状態が解除され、他スレッドの動作が許可される
                test.wait(1000);
            } catch (Exception e) { ; }

            test.id += 1;
            System.out.println("SubThread1: " + test.id + "(plus 1)");
        }
    }
}

// サブスレッド(SubThread2)
class SubThread2 implements Runnable {
    private ConflictTest test;

    // コンストラクタ
    public SubThread2(ConflictTest test) {
        this.test = test;
    }

    // サブスレッド(SubThread2)で動作する処理
    @Override
    public void run() {
        synchronized (test) {
            test.id *= 2;
            System.out.println("SubThread2: " + test.id + "(multiply 2)");
        }
    }
}