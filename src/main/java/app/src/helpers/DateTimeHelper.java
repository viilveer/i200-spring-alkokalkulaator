package app.src.helpers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by Mihkel on 08/12/2015.
 */
public class DateTimeHelper {

    public static String yesterday(LocalDateTime now)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.minus(1, ChronoUnit.DAYS).withHour(0).withMinute(0).withSecond(0).format(formatter);
    }
}
