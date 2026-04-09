package leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * LC560 - 和为 K 的子数组
 *
 * 题目（概要）：整数数组，求连续子数组和为 k 的个数。
 *
 * 算法原理：
 * - 前缀和转化：sum[j..i] = pre[i] - pre[j-1] = k 等价于 pre[j-1] = pre[i] - k。即「以 i 结尾、和为 k 的子数组数」=「前缀和为 pre[i]-k 的 j 的个数」。
 * - map 存 (前缀和 -> 出现次数)：遍历时累计 pre，查询 map.get(pre-k) 即「前面有多少个位置 j 使得 pre[j]=pre-k」，这些 j 到当前 i 的子数组和为 k。
 * - map.put(0,1)：pre[0]=0（空前缀）也算一种，对应子数组从 0 开始的情况。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：pre=0，map={0:1}。
 * - 步骤 2：对每个 x，pre+=x；count+=map.getOrDefault(pre-k,0)；map.put(pre, map.getOrDefault(pre,0)+1)。
 * - 步骤 3：先查再更新，保证查的是「当前元素之前」的前缀和，不会包含自身。
 *
 * 关键洞察：
 * - 「pre-k 计数」原理：pre[i] - pre[j] = k 等价于 pre[j] = pre[i]-k。map 存的是到当前为止的前缀和频次。
 * - 与两数之和类似：都是「当前值 - 目标 = 是否存在」的哈希查表。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 示例：nums=[1,1,1], k=2 → 2（[1,1] 与 [1,1]）
 */
public class LC560subarraySum {

    /** 返回和为 k 的连续子数组个数 */
    public int subarraySum(int[] num, int k) {
        int count = 0, pre = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);  // 空前缀，对应子数组从下标 0 开始
        for (int x : num) {
            pre += x;
            count += map.getOrDefault(pre - k, 0);  // 前面有多少 pre[j]=pre-k，则 sum[j+1..i]=k
            map.put(pre, map.getOrDefault(pre, 0) + 1);
        }
        return count;
    }
}
