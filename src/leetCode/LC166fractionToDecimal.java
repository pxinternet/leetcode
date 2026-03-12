package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC166 - 分数到小数
 *
 * 题目（概要）：给定分子 numerator 和分母 denominator，返回小数字符串。若为循环小数，用括号标出循环节。
 *
 * 解法说明：
 * - 核心思想：模拟竖式除法。整数部分整除，小数部分每次 remainder*10 再除，记录余数出现位置；重复余数即循环开始。
 * - 用 HashMap 记录 (remainder -> 插入位置)，遇重复则在对应位置插入 "("，末尾加 ")"。
 *
 * 时间复杂度：O(循环节长度)
 * 空间复杂度：O(循环节长度)
 *
 * 边界与注意事项：
 * - 分子为 0 直接返回 "0"；正负用 XOR 判断；用 long 防溢出
 *
 * 示例：numerator=1, denominator=2 → "0.5"；1/6 → "0.1(6)"
 */
public class LC166fractionToDecimal {

    /**
     * 将分数转为小数字符串，循环部分用括号标出
     *
     * @param numerator   分子
     * @param denominator 分母
     * @return 小数字符串
     */
    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";

        StringBuilder res = new StringBuilder();

        if ((numerator < 0) ^ (denominator < 0)) {
            res.append("-");
        }
        
        long dividend = Math.abs((long) numerator);
        long divisor = Math.abs((long) denominator);

        res.append(dividend / divisor);
        long remainder = dividend % divisor;

        if (remainder == 0) return res.toString();

        res.append('.');
        Map<Long, Integer> remainderMap = new HashMap<>();

        while (remainder != 0) {
            if (remainderMap.containsKey(remainder)) {
                res.insert(remainderMap.get(remainder), "(");
                res.append(")");
                break;
            }
            remainderMap.put(remainder, res.length());   // 记录该余数首次出现位置
            remainder *= 10;
            res.append(String.valueOf(remainder / divisor));
            remainder %= divisor;
        }
        return res.toString();

    }

    
}
