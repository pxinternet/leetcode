package leetCode;

/**
 * LC230 - 二叉搜索树中第 K 小的元素
 *
 * 题目（概要）：给定一棵二叉搜索树（BST）的根节点 root 和整数 k，返回 BST 中第 k 小的元素值。
 *
 * 算法原理：
 * - BST 性质：左子树 < 根 < 右子树，中序遍历（左-根-右）得到严格升序序列。
 * - 第 k 小的定义：中序遍历的第 k 个节点。计数到 k 时即为答案。
 * - k 递减的等价思路：也可用「k 递减、到 0 时返回」的写法，本质相同。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：中序遍历，先递归左子树（所有比根小的）。
 * - 步骤 2：访问根，count++；若 count==k 则记录 result 并 return（可提前终止右子树）。
 * - 步骤 3：递归右子树。
 *
 * 关键洞察：
 * - 使用实例变量 count、result 便于在递归中传递；也可用 int[] 或 AtomicInteger 替代。
 * - 提前 return 后，上层递归仍会继续，但 result 已写入，最终答案正确。
 *
 * 时间复杂度：O(k) 到 O(n)
 * 空间复杂度：O(h)
 *
 * 示例：root=[5,3,6,2,4,null,null,1], k=3 → 3
 */
public class LC230kthSmallest {

    /** 中序遍历已访问的节点计数 */
    private int count = 0;
    /** 第 k 小元素的值 */
    private int result = 0;

    /**
     * 返回 BST 中第 k 小的元素
     *
     * 关键点：中序遍历按升序访问，第 k 个即为答案；用 count 跟踪已访问个数。
     *
     * @param root BST 根节点
     * @param k    第 k 小（从 1 开始）
     * @return 第 k 小的元素值
     */
    public int kthSmallest(TreeNode root, int k) {
        inOrder(root, k);
        return result;
    }

    /**
     * 中序遍历，当访问到第 k 个节点时记录 result
     *
     * 递归顺序：左 → 根（count++，若 count==k 则记录）→ 右
     *
     * @param node 当前节点
     * @param k    目标排名
     */
    private void inOrder(TreeNode node, int k) {
        if (node == null) return;

        // 1. 先遍历左子树（比根小的都在左边）
        inOrder(node.left, k);

        // 2. 访问根节点：中序第 count+1 个
        count++;
        if (count == k) {
            result = node.val;
            return;  // 已找到，可提前返回，后续递归无影响
        }

        // 3. 再遍历右子树
        inOrder(node.right, k);
    }
}
