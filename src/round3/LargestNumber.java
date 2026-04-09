package round3;

import java.util.Arrays;

/**
 * LargestNumber - 最大数组合（LeetCode 179）
 *
 * 题目（概要）：给定非负整数数组，将其重新排列成最大的数（字符串形式）。如 [3,30,34] → "34330"。
 *
 * 算法原理：
 * - 自定义排序：a、b 谁应在前，比较 a+b 与 b+a 的字典序，大的排前面。
 * - 字符串比较：order2.compareTo(order1) 实现降序（大数在前）。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：转字符串数组，按 (o1,o2) -> (o2+o1).compareTo(o1+o2) 排序。
 * - 步骤 2：若首字符为 "0" 则全为 0，返回 "0"。
 * - 步骤 3：依次拼接排序后的字符串。
 *
 * 关键洞察：拼接比较保证传递性；全零需特判。
 *
 * 时间复杂度：O(n log n)
 * 空间复杂度：O(n)
 *
 * 示例：[3,30,34,5,9] → "9534330"
 */
public class LargestNumber {

    public static String largestNumber(int[] nums) {

        String[] strNums = new String[nums.length];

        for (int i = 0; i < nums.length; i++) {
            strNums[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(strNums,
                (o1, o2) -> {
                    String order1 = o1 + o2;
                    String order2 = o2 + o1;
                    return order2.compareTo(order1);
                });

        if (strNums[0].equals("0")) {
            return "0";
        }

        StringBuilder res = new StringBuilder();

        for (String str : strNums) {
            res.append(str);
        }

        return res.toString();
}
