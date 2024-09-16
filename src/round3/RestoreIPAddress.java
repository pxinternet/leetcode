package round3;

import java.util.ArrayList;
import java.util.List;

public class RestoreIPAddress {

    public static List<String> restoreIpAddress(String s) {
        List<String> result = new ArrayList<>();

        if (s == null || s.length() < 4 || s.length() > 12)
            return result;

        StringBuilder current = new StringBuilder();

        backtrack(s, 0, 0, current, result);

        return result;
    }

    private static void backtrack(String s, int start, int segment, StringBuilder current, List<String> res) {
        if (segment == 4 && current.length() == s.length()) {
            res.add(current.toString());
            return;
        }

        if (segment >= 4 || start >= s.length()) {
            return;
        }

        for (int i = 1; i <= 3; i++) {
            if (start + i > s.length()) {
                break;
            }

            String segmentStr = s.substring(start, start + i);
            if (isValidSegment(segmentStr)) {
                if (current.isEmpty()) {
                    current.append(segment);
                } else {
                    current.append(".").append(segmentStr);
                }
                backtrack(s, start + i, segment + 1, current, res);
            }
        }
    }

    private static boolean isValidSegment(String segmentStr) {
        if (segmentStr.length() > 1 && segmentStr.startsWith("0")) {
            return false;
        }

        int value = Integer.parseInt(segmentStr);
        return value >= 0 && value <= 255;
    }


}
