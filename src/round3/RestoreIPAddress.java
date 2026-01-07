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
        // 终止条件：已经构造了 4 段且刚好用完字符串，则将结果加入
        if (segment == 4 && start == s.length()) {
            res.add(current.toString());
            return;
        }

        // 如果段数已经达到 4 或者起始位置已经超出字符串长度，则无法继续
        if (segment >= 4 || start >= s.length()) {
            return;
        }

        // 尝试取长度为 1～3 的段
        for (int i = 1; i <= 3; i++) {
            if (start + i > s.length()) {
                break;
            }

            String segmentStr = s.substring(start, start + i);
            if (isValidSegment(segmentStr)) {
                // 记录修改前的长度以便回溯时恢复
                int prevLen = current.length();
                // 只有当 current 非空时才添加 '.' 分隔符
                if (prevLen != 0) {
                    current.append('.');
                }
                // 正确地追加该段字符串（原代码错误地使用了 segment 整数）
                current.append(segmentStr);

                // 递归处理下一段
                backtrack(s, start + i, segment + 1, current, res);

                // 回溯：恢复 StringBuilder 到之前的长度，去掉刚才追加的段和可能的 '.'
                current.setLength(prevLen);
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
