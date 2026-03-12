package leetCode;

import java.util.ArrayList;
import java.util.List;

/**
 * LC229 - 多数元素 II
 *
 * 题目（概要）：给定大小为 n 的数组 nums，找出所有出现次数严格大于 n/3 的元素。
 * 最多存在 2 个这样的元素（因为 3 个各占 1/3 时都不超过 n/3）。
 *
 * 解法说明：
 * - 核心思想：Boyer-Moore 投票法的扩展。出现次数 > n/3 的元素最多 2 个，
 *   维护 2 个候选及对应计数，第一遍扫描用「对拼消耗」筛出候选，第二遍验证是否真的超过 n/3。
 * - 第一遍：若当前数等于某候选则其 count++；若某候选 count==0 则替换为该数；
 *   否则两个候选的 count 都--（三数对拼，各减一）。
 * - 第二遍：重新统计两候选的实际出现次数，超过 n/3 的加入结果。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 边界与注意事项：
 * - 空数组返回空列表
 * - 候选初值可任意，count=0 时会立即被替换
 * - 必须第二遍验证：对拼后剩余的候选不一定都 > n/3（如 [1,2,3,4,5,...]）
 *
 * 示例：nums=[3,2,3] → [3]；nums=[1,1,1,3,3,2,2,2] → [1,2]
 */
public class LC229majorityElement {

    /**
     * 使用 Boyer-Moore 投票法找所有出现次数 > n/3 的元素
     *
     * 关键点逐行说明：
     * - candidate1/candidate2：两个候选，count1/count2 为其「存活」计数
     * - 相等则加票；某候选为 0 则替换；否则对拼（两计数都减）
     * - 第二遍统计真实出现次数，筛选出真正 > n/3 的
     *
     * @param nums 整数数组
     * @return 所有出现次数严格大于 n/3 的元素列表
     */
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;

        int candidate1 = 0, candidate2 = 0;
        int count1 = 0, count2 = 0;

        // 第一遍：Boyer-Moore 对拼，筛出最多 2 个候选
        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            } else if (count1 == 0) {
                // 候选1 无票，替换为当前数
                candidate1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } else {
                // 三数对拼：当前数、候选1、候选2 各抵消一票
                count1--;
                count2--;
            }
        }

        // 第二遍：验证候选是否真的超过 n/3
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == candidate1) count1++;
            else if (num == candidate2) count2++;
        }

        if (count1 > nums.length / 3) res.add(candidate1);
        // 注意：candidate1 可能与 candidate2 相同（数组元素少时），但题目保证最多 2 个不同答案
        if (count2 > nums.length / 3 && candidate1 != candidate2) res.add(candidate2);

        return res;
    }
}
