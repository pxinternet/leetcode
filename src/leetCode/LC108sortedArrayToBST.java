package leetCode;

/**
 * LC108sortedArrayToBST - 将有序数组转换为二叉搜索树
 *
 * 题目（概要）：给定升序数组 nums，将其转换为高度平衡的二叉搜索树。高度平衡指每个节点左右子树高度差不超过 1。
 *
 * 解法说明：
 * - 核心思想：取中间元素为根，左半部分建左子树，右半部分建右子树，递归
 * - 这样保证左右子树节点数最多差 1，从而高度平衡
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(log n)，递归栈
 *
 * 边界与注意事项：
 * - 空数组返回 null
 * - mid = left + (right - left) / 2 避免溢出
 *
 * 示例：nums=[-10,-3,0,5,9] → 可能的一种 BST：根 0，左子树根 -3，右子树根 9
 */
public class LC108sortedArrayToBST {

    /**
     * 将升序数组转换为高度平衡 BST
     *
     * @param nums 升序数组
     * @return BST 根节点
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        return buildBST(nums, 0, nums.length - 1);
    }

    /**
     * 在 [left, right] 区间内构建 BST
     *
     * 关键点逐行说明：
     * - left > right 时返回 null（区间为空）
     * - 取中间元素为根，保证左右子树节点数均衡
     * - 左子树 [left, mid-1]，右子树 [mid+1, right]
     *
     * 例如：nums=[1,2,3,4,5], left=0, right=4 → mid=2，根为 3，左 [1,2] 右 [4,5]
     *
     * @param nums 原数组
     * @param left 左边界（含）
     * @param right 右边界（含）
     * @return 当前子树根节点
     */
    private TreeNode buildBST(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = buildBST(nums, left, mid - 1);
        node.right = buildBST(nums, mid + 1, right);

        return node;
    }
}
