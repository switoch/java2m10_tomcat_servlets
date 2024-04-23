package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Display {
    public enum OffsetBase {
        GMT, UTC
    }

    public static List<String> getOffsetsList(OffsetBase base) {
        String[] availableZoneIds = TimeZone.getAvailableIDs();
        List<String> result = new ArrayList<>(availableZoneIds.length);

        for (String zoneId : availableZoneIds) {
            TimeZone curTimeZone = TimeZone.getTimeZone(zoneId);
            String offset = calculateOffset(curTimeZone.getRawOffset());
            result.add(String.format("%s", offset));
        }
        Collections.sort(result);
        return result;
    }

    private static String calculateOffset(int rawOffset) {
        if (rawOffset == 0) {
            return "+00";
        }
        TimeUnit TimeUnit = null;
        long hours = TimeUnit.MILLISECONDS.toHours(rawOffset);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(rawOffset);
        minutes = Math.abs(minutes - TimeUnit.HOURS.toMinutes(hours));

        return String.format("%+3d", hours, Math.abs(minutes));
    }
}
