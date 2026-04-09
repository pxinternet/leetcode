package leetCode;

/**
 * LC134CanCompleteCircuit - 加油站
 *
 * 题目（概要）：环形路上有 n 个加油站，gas[i] 为 i 站可加油量，cost[i] 为 i 到 i+1 的耗油。
 * 求从哪站出发可绕一圈。有唯一解或无解（返回 -1）。
 *
 * 解法说明：若总油量 < 总耗油则无解。否则从 0 开始遍历，维护当前剩余油量；若为负则重置起点为下一站。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 示例：gas=[1,2,3,4,5], cost=[3,4,5,1,2] → 3（从索引 3 出发）
 */
public class LC134CanCompleteCircuit {

    /**
     * 若 sum(gas) >= sum(cost) 则必有解，遍历找起点
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {

        int n = gas.length;

        int sumGas = 0;
        for (int i = 0; i < n; i++) {
            sumGas += gas[i];
        }

        int sumCost = 0;
        for (int i = 0; i < n; i++) {
            sumCost += cost[i];
        }

        if (sumGas < sumCost) return -1;

        //如果sumGas >= sumCost 那么一定能找到

        int startIndex = -1;
        int gasTobeCost = 0;

        for (int i = 0; i < n; i++) {
            gasTobeCost += gas[i] - cost[i];

            if (gasTobeCost >= 0) {
                if (startIndex == -1) {
                    startIndex = i;
                }
            }
            if (gasTobeCost < 0) {
                startIndex = -1;
                gasTobeCost = 0;
            }
        }
        return startIndex;
    }


    public int canCompleteBetterCode(int[] gas, int[] cost) {
        int n = gas.length;

        int totalGus = 0;
        int currentGus = 0;
        int startStation = 0;

        for (int i = 0; i < n; i++) {
            totalGus += gas[i] - cost[i];
            currentGus += gas[i] - cost[i];

            if (currentGus < 0) {
                currentGus = 0;
                startStation = i + 1;
            }
        }
        return totalGus >= 0 ? startStation : -1;

    }

    public static void main(String[] args) {
        int[] gas = new int[]{1,2,3,4,5};
        int[] cost = new int[]{3,4,5,1,2};

        LC134CanCompleteCircuit lc134CanCompleteCircuit = new LC134CanCompleteCircuit();
        int res = lc134CanCompleteCircuit.canCompleteCircuit(gas, cost);

        System.out.println(res);


    }
}
