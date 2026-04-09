package leetCode;

/**
 * LC273 - 整数转换英文表示
 *
 * 题目（概要）：将非负整数 num 转为英文单词表示。如 1234567891 → "One Billion Two Hundred Thirty Four Million Five Hundred Sixty Seven Thousand Eight Hundred Ninety One"。
 *
 * 解法说明：
 * - 核心思想：每三位一组（Thousand、Million、Billion），每组用 helper 转换为英文；helper 处理 0-999。
 * - helper：<20 查表；<100 用 TENS[num/10] + helper(num%10)；>=100 用 LESS_THAN_20[num/100] + " Hundred " + helper(num%100)。
 *
 * 时间复杂度：O(log num)
 * 空间复杂度：O(1)（不计输出）
 *
 * 边界与注意事项：
 * - num=0 返回 "Zero"
 *
 * 示例：123 → "One Hundred Twenty Three"
 */
public class LC273nums2word {

    private static final String[] LESS_THAN_20 = {
        "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
        "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
        "Eighteen", "Nineteen"
    };

    private static final String[] TENS = {
        "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };

    private static final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};

    /**
     * 将整数转为英文单词
     *
     * 关键点：从低到高每 1000 一组，helper 处理 0-999
     *
     * @param num 非负整数
     * @return 英文表示
     */
    public String numberToWords(int num) {
        if (num == 0) return "Zero";

        int i = 0;
        StringBuilder res = new StringBuilder();

        while (num > 0) {
            if (num % 1000 != 0) {
                res.insert(0, helper(num % 1000) + THOUSANDS[i] + " ");
            }
            num /= 1000;
            i++;
        }

        return res.toString().trim();
    }

    /**
     * 将 0-999 转为英文（不含 Thousand 等单位）
     */
    private String helper(int num) {
        if (num == 0) return "";
        if (num < 20) return LESS_THAN_20[num] + " ";
        if (num < 100) return TENS[num / 10] + " " + helper(num % 10);
        return LESS_THAN_20[num / 100] + " Hundred " + helper(num % 100);
    }
}
