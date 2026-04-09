package leetCode;

/**
 * LC860 - 柠檬水找零
 *
 * 题目（概要）：每杯 5 元，顾客付 5/10/20。能否正确找零？（5 元无需找，10 元找 5，20 元找 15 即 10+5 或 5*3）
 *
 * 解法说明：
 * - 核心思想：贪心。优先用 10+5 找 20（保留 5 元更有用），否则用 5*3。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 示例：bills=[5,5,5,10,20] → true
 */
public class LC860lemonadeChange {

    /**
     * 维护 five、ten 数量，按面额贪心找零
     */
    public boolean lemonadeChange(int[] bills) {
        int five = 0;
        int ten = 0;

        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                if (five == 0) return false;
                five--;
                ten++;

            } else {
                if (five >0 && ten > 0) {
                    five--;
                    ten--;
                } else if (five >= 3) {
                    five-= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

}
