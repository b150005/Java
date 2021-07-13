import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class LocaleAndTimeZone {
    public static void main(String[] args) {
        // デフォルトロケールの取得
        Locale loc = Locale.getDefault();

        // ロケール情報の表示
        System.out.println("Locale: " + loc.getDisplayName());
        System.out.println("Country Code: " + loc.getCountry());
        System.out.println("Country Name: " + loc.getDisplayCountry());
        System.out.println("Language Code: " + loc.getLanguage());
        System.out.println("Language Name: " + loc.getDisplayLanguage());

        // デフォルトタイムゾーンの取得
        TimeZone tz = TimeZone.getDefault();

        // カレンダーの取得
        Calendar cal = Calendar.getInstance();
        // カレンダー情報の時間値[ms]
        long calDate = cal.getTimeInMillis();

        // タイムゾーン情報の表示
        System.out.println("TimeZone: " + tz.getDisplayName());
        
        // サマータイムの採用情報をもとに分岐処理
        if (tz.useDaylightTime()) {
            System.out.println("DST is adopted.");
            
            // サマータイムを考慮して世界標準時(UTC)との時差を表示
            System.out.print("time difference from UTC: ");
            System.out.println(tz.getOffset(calDate) / 3600000 + "[hrs.]");
        }
        else {
            System.out.println("DST is not adopted.");

            // サマータイムを考慮せず世界標準時(UTC)との時差を表示
            System.out.print("time difference from UTC: ");
            System.out.println(tz.getRawOffset() / 3600000 + "[hrs.]");
        }
    }
}