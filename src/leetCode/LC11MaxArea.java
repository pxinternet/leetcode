package leetCode;

/*
 中文详解：双指针算法正确性证明（针对“盛最多水的容器”，LeetCode 11）

 问题重述：
 给定数组 height[0..n-1]，选择 i<j，使得面积 area(i,j) = min(height[i], height[j]) * (j - i) 最大。

 算法要点（复述）：
 - 初始化 left=0, right=n-1。
 - 每步计算 area(left,right)，更新最大值。
 - 若 height[left] < height[right] 则 left++，否则 right--（相等时任意移动一边）。
 - 重复直到 left>=right。

 不变式（核心）：在每次迭代开始时，存在至少一组最优解 (i*, j*) 满足 left <= i* < j* <= right。
 初始显然成立（left=0, right=n-1 覆盖全部可能对）。

 关键不等式与移动理由（数学化描述）：
 - 设当前窗口为 [L, R]，当前面积 A = min(h[L], h[R]) * (R-L)。
 - 若 h[L] < h[R]，则对于任何 k 满足 L < k <= R，
     area(L,k) = min(h[L], h[k]) * (k-L) <= h[L] * (k-L) <= h[L] * (R-L) = A.
   因此，所有以 L 为左端的容器，其面积上界为 A，不可能严格大于 A。
 - 由此可得：如果存在更优解（面积 > A），它不可能以 L 为左端，必然有其左端 i* > L。
   因而把 left 移动到 L+1 并不会丢弃任何面积严格大于 A 的最优解，保持不变式成立。
 - 对于 h[R] < h[L] 情形，结论对称成立；若 h[L]==h[R]，移动任意一端都安全（不会丢失严格更优的解）。

 反证法角度（简洁）：假设算法移动越过了某个属于某最优对的索引并因此丢失该最优解。
 - 若越过的是左端 L 且 h[L] < h[R]，那么任何包含 L 的容器面积最多为 h[L]*(R-L)=A，不能严格优于 A，与“丢失更优解”矛盾。
 - 若最优对的左端 i* > L，则移动 L 不会排除该最优对。
 - 故无可能丢失严格更优的最优对。

 直觉（一句话）：容器高度由较短那边限制，缩小宽度时只有当能找到更高的边界（超过当前短边）时才可能增加面积；因此每步应移动“可能带来更高边界”的那一端，即较短的一端。

 复杂度：
 - 时间复杂度：O(n)，每次移动至少一个指针，left 和 right 总共移动不超过 n 次。
 - 空间复杂度：O(1)，只使用常数额外空间。

 与 LC10（正则表达式匹配）的比较（“LC10ismath” 注）：
 - LC10 是 DP/自动机类问题，需要穷尽状态与转移、验证覆盖性；其证明依赖递归关系与状态转移的归纳。
 - LC11 属于几何/贪心类问题，证明依赖不等式与不变式（即移动操作不会丢失更优解），数学上更侧重边界不等式而非状态穷尽。
 - 两题都强调“正确性的严格证明”：LC10 用 DP 归纳覆盖所有情况，LC11 用不等式与反证保证贪心移动的安全性。

 结论：上述双指针策略（每次移动较短的指针）既是必要的（移动较高的一边不会产生比当前更大的面积），也是充分的（不变式保证不会丢失严格更优的解），故算法正确且最优（线性时间）。

*/

public class LC11MaxArea {

    // 题目：盛最多水的容器（LeetCode 11）
    // 思路：双指针（two pointers），从数组两端向中间移动。
    // 证明直观：容器的容量由两条垂直线的较短者决定；为尝试更大容量，必须移动较短的一边以寻找更高的边界。

    public int maxArea(int[] height) {
        // 边界检查：如果数组为 null 或长度小于 2，则无法形成容器，返回 0
        if (height == null || height.length < 2) return 0;
        // n 为数组长度
        int n = height.length;
        // left 指向左端起始位置（索引 0）
        int left = 0;
        // right 指向右端起始位置（索引 n-1）
        int right = n -1;

        // maxArea 用于记录当前发现的最大面积，初始为 0
        int maxArea = 0;

        // 当 left < right 时，才形成有效的容器
        while(left < right) {

            // 当前容器的高度由两边较短的柱子决定，宽度为 right-left
            // 计算当前面积并与 maxArea 比较保留最大值
            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));

            // 移动策略：每次移动较短的那一边（如果相等，可任意移动一边）
            // 原因：如果我们移动较高的一边，宽度变小，而高度至多被较短那边限制，
            // 所以不可能得到比当前更大的面积；只有移动较短的一边才有机会找到更高的高度从而增大面积。
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxArea;
    }

    // 一个增强版的方法：在运行时打印每一步的 left/right/height/area 以便演示算法过程
    // 这个方法与 maxArea 的逻辑相同，但每次迭代会输出详细信息，便于验证与教学
    public int maxAreaVerbose(int[] height) {
        // 边界检查：如果数组为 null 或长度小于 2，则无法形成容器，返回 0
        if (height == null || height.length < 2) return 0;
        int n = height.length;
        int left = 0;
        int right = n - 1;
        int maxArea = 0;
        System.out.println("开始计算 maxArea（详细过程）：");
        while (left < right) {
            int hLeft = height[left];
            int hRight = height[right];
            int width = right - left;
            int area = Math.min(hLeft, hRight) * width;
            System.out.printf("left=%d (h=%d), right=%d (h=%d), width=%d, area=%d, currentMax=%d\n",
                              left, hLeft, right, hRight, width, area, maxArea);
            if (area > maxArea) {
                System.out.printf("  => 更新最大面积: %d -> %d\n", maxArea, area);
                maxArea = area;
            }

            if (hLeft < hRight) {
                // 移动左指针
                left++;
            } else {
                // 移动右指针（当两边相等时也移动右指针，行为与原实现一致）
                right--;
            }
        }
        System.out.println("计算结束。最终 maxArea=" + maxArea);
        return maxArea;
    }

    // main 方法：包含若干示例，并演示普通调用与详细 trace 调用
    public static void main(String[] args) {
        LC11MaxArea solver = new LC11MaxArea();

        // 示例 1：典型的示例
        int[] h1 = {1,8,6,2,5,4,8,3,7};
        System.out.println("示例 1: [1,8,6,2,5,4,8,3,7]");
        System.out.println("普通计算结果: " + solver.maxArea(h1));
        System.out.println("详细过程: ");
        solver.maxAreaVerbose(h1);
        System.out.println();

        // 示例 2：单调递增
        int[] h2 = {1,2,3,4,5};
        System.out.println("示例 2: [1,2,3,4,5]");
        System.out.println("普通计算结果: " + solver.maxArea(h2));
        solver.maxAreaVerbose(h2);
        System.out.println();

        // 示例 3：峰值在中间
        int[] h3 = {5,4,3,2,1};
        System.out.println("示例 3: [5,4,3,2,1]");
        System.out.println("普通计算结果: " + solver.maxArea(h3));
        solver.maxAreaVerbose(h3);
        System.out.println();

        // 示例 4：重复高度
        int[] h4 = {2,2,2,2};
        System.out.println("示例 4: [2,2,2,2]");
        System.out.println("普通计算结果: " + solver.maxArea(h4));
        solver.maxAreaVerbose(h4);
        System.out.println();

        // 示例 5：最小边界（仅两个元素）
        int[] h5 = {1,1};
        System.out.println("示例 5: [1,1]");
        System.out.println("普通计算结果: " + solver.maxArea(h5));
        solver.maxAreaVerbose(h5);
    }
}
