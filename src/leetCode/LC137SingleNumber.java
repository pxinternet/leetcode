package leetCode;

/**
 * LC137SingleNumber - 只出现一次的数字 II
 *
 * 题目（概要）：数组中除一个数字外其余都出现三次，找出只出现一次的数字。要求 O(n) 时间、O(1) 空间。
 *
 * 解法说明：按位统计，每位上 1 的个数模 3 的余数即为答案该位的值（因出现三次的会抵消）。
 *
 * 时间复杂度：O(32*n) = O(n)
 * 空间复杂度：O(1)（32 维数组为常数）
 *
 * 边界与注意事项：
 * - 答案唯一存在
 *
 * 示例：nums=[2,2,3,2] → 3
 */
public class LC137SingleNumber {

    /**
     * 按位统计，每位 1 的个数模 3 得到答案该位的值
     *
     * 关键点：出现 3 次的数每位贡献可被 3 整除，只有出现 1 次的数会留下余数
     *
     * @param nums 除一个数外均出现三次
     * @return 只出现一次的数
     */
    public int singleNumber(int[] nums) {
        int[] bits = new int[32];
        int result = 0;

        for (int i = 0; i < 32; i++) {
            for (int num : nums) {
                bits[i] += (num >> i) & 1;    // 统计第 i 位为 1 的个数
            }
            result |= (bits[i] % 3) << i;      // 模 3 余数即为答案第 i 位
        }

        return result;
    }
}
