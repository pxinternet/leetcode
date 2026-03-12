package round2;

import java.util.HashSet;
import java.util.Set;

/**
 * UniqueNumberInString - 字符串中不同整数的数目
 *
 * 题目（概要）：给定字符串，提取其中所有"整数"（连续数字组成的子串），去前导零后求不同整数的个数。
 * 如 "a123b34c123" 中为 "123","34","123" → 去重后 2 个。
 *
 * 算法原理：
 * - 遍历按字符分类：数字则追加到 currentNumber，非数字则截断并加入 Set，注意末尾数字需单独处理。
 * - trimLeadingZeros：前导零归一化为 "0"（全零串）或去掉前导零的子串。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：遍历 s，遇到数字 append 到 currentNumber。
 * - 步骤 2：遇到非数字时，若 currentNumber 非空则 trimLeadingZeros 后加入 Set 并清空。
 * - 步骤 3：循环结束后再次处理末尾数字。
 * - 步骤 4：返回 Set.size()。
 *
 * 关键洞察：前导零需统一处理，避免 "007" 与 "7" 被视为不同；全零串规范为 "0"。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例："a123b34c123" → 2
 */
public class UniqueNumberInString {

    public static int numUniqueNumbers(String s) {
        Set<String> uniqueNumbers = new HashSet<>();

        int n = s.length();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currentNumber.append(c);
            } else {
                if (currentNumber.length() > 0) {
                    String num = trimLeadingZeros(currentNumber.toString());
                    uniqueNumbers.add(num);
                    currentNumber = new StringBuilder();
                }
            }

        }

        if (currentNumber.length() > 0) {
            String num = trimLeadingZeros(currentNumber.toString());
            uniqueNumbers.add(num);
        }
        return uniqueNumbers.size();

    }

    private static String trimLeadingZeros(String num) {
        int i = 0;
        while (i < num.length() && num.charAt(i) == '0') {
            i++;
        }
        return i == num.length() ? "0" : num.substring(i);
    }

}
