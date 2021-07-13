import java.math.BigDecimal;
import java.math.RoundingMode;

public class JVMMemory {
    public static void main(String[] args) {
        // Runtimeインスタンスの取得
        Runtime rt = Runtime.getRuntime();
        
        System.out.println("-- Information of JVM Memory --");

        // 空きメモリ容量[byte]
        long freeL = rt.freeMemory();
        double freeD = (double) freeL;
        // 総容量[byte]
        long totalL = rt.totalMemory();
        double totalD = (double) totalL;
        // 最大容量[byte]
        long maxL = rt.maxMemory();
        double maxD = (double) maxL;

        // BigDecimalを用いたメモリデータ表示
        System.out.println("Free: " + new BigDecimal(freeD / 1024 / 1024 / 1024).setScale(2, RoundingMode.HALF_UP) + "[GB]");
        System.out.println("Total: " + new BigDecimal(totalD / 1024 / 1024 / 1024).setScale(2, RoundingMode.HALF_UP) + "[GB]");
        System.out.println("Use: " + new BigDecimal((totalD - freeD) / 1024 / 1024 / 1024).setScale(2, RoundingMode.HALF_UP) + "[GB]");
        System.out.println("Maximum: " + new BigDecimal(maxD / 1024 / 1024 / 1024).setScale(2, RoundingMode.HALF_UP) + "[GB]");
    }
}