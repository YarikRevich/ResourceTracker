import com.resourcetracker.tools.parsers;

import org.javatuples.Pair;
import java.util.Calendar;
import java.util.Date;

public class ReportFrequencyParser {
    public static Pair<Calendar.Field, Integer> parse(String reportFrequency) {
        Calendar.Field firstVal = null;
        Integer secondVal = 0;

        if (reportFrequency.endsWith("d")) {
            firstVal = Calendar.DAY_OF_YEAR;
        } else if (reportFrequency.endsWith("w")) {
            firstVal = Calendar.WEEK_OF_YEAR;
        } else if (reportFrequency.endsWith("m")) {
            firstVal = Calendar.MONTH_OF_YEAR;
        } else if (reportFrequency.endsWith("h")) {
            firstVal = Calendar.HOUR_OF_DAY;
        } else if (reportFrequency.endsWith("s")) {
            firstVal = Calendar.SECOND_OF_DAY;
        }

        secondVal = Integer.parseInt(reportFrequency.substring(0, reportFrequency.length() - 1));

        return new Pair<Calendar.Field, Integer>(firstVal, secondVal);
    }
}
