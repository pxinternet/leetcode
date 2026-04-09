package leetCode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LC1475 - 商品折扣后的最终价格
 *
 * 题目（概要）：给定数组 prices，prices[i] 为第 i 件商品价格。对于每件商品 i，可享受下标 j>i 且 prices[j]<=prices[i] 的 First 折扣。
 * 求每件商品的最终价格（原价 - 折扣）。
 *
 * 解法说明：
 * - 核心思想：单调栈。栈存下标，栈底到栈顶单调增。遍历时若 prices[i]<=prices[栈顶]，则栈顶对应商品享受 i 的折扣，prices[栈顶]-=prices[i]，并弹出。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 边界与注意事项：
 * - 无折扣时保持原价
 *
 * 示例：prices=[8,4,6,2,3] → [4,2,4,2,3]
 */
public class LC1475finalPrices {

    /**
     * 单调栈求每件商品的折扣
     *
     * 关键点：栈单调增，当前 prices[i] 作为后续更大元素的折扣
     *
     * @param prices 原价数组
     * @return 折扣后价格（原地修改）
     */
    public int[] finalPrices(int[] prices) {
        Deque<Integer> stack = new LinkedList<>();

        for (int i = 0; i < prices.length; i++) {
            while (!stack.isEmpty() && prices[i] <= prices[stack.peek()]) {
                prices[stack.pop()] -= prices[i];
            }
            stack.push(i);
        }
        return prices;
    }
}
