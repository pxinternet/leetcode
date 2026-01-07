package leetCode;

import java.util.*;

/**
 * 二叉树节点工具类（附带构造/打印辅助方法与 main 测试）
 *
 * 说明：此类为常用的二叉树节点表示，包含：
 * - 基本构造函数
 * - 通过层序数组（Integer[]，允许 null 表示缺失节点）构建二叉树的静态方法
 * - 将二叉树按层序打印为字符串的 toString 方法（用于测试/调试）
 *
 * 约定：节点值为 int。fromArray 使用数组索引按完整二叉树规则映射到左右子节点
 * （i 的左子节点索引为 2*i+1，右子节点索引为 2*i+2），数组中的 null 表示该位置没有节点。
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    // 原始的构造器，保留向后兼容
    public TreeNode(int x) { val = x; }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /**
     * 通过层序数组构建二叉树（支持 null 表示缺失节点）。
     * 示例：new Integer[]{1,2,3,null,4} 表示：
     *      1
     *     / \
     *    2   3
     *     \
     *      4
     *
     * @param arr 层序数组，允许 null
     * @return 根节点，若 arr 为空或 arr[0] 为 null 则返回 null
     */
    public static TreeNode fromArray(Integer[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null) return null;
        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (!q.isEmpty() && i < arr.length) {
            TreeNode cur = q.poll();
            // 左子
            if (i < arr.length) {
                Integer v = arr[i++];
                if (v != null) {
                    cur.left = new TreeNode(v);
                    q.offer(cur.left);
                }
            }
            // 右子
            if (i < arr.length) {
                Integer v = arr[i++];
                if (v != null) {
                    cur.right = new TreeNode(v);
                    q.offer(cur.right);
                }
            }
        }
        return root;
    }

    /**
     * 将二叉树按层序转换为字符串，便于打印与比较（不一定是唯一序列化格式，但对调试够用）
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(this);
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (cur == null) {
                sb.append("null").append(",");
                continue;
            }
            sb.append(cur.val).append(",");
            q.offer(cur.left);
            q.offer(cur.right);
        }
        // 去掉末尾多余的 null 可以使输出更短
        String res = sb.toString();
        // trim trailing "null,"
        while (res.endsWith("null,")) {
            res = res.substring(0, res.length() - 5);
        }
        if (res.endsWith(",")) res = res.substring(0, res.length() - 1);
        return "[" + res + "]";
    }

    /**
     * main 测试：构造若干二叉树并打印 toString 结果（示例 + 边界）
     */
    public static void main(String[] args) {
        // 示例 1：完整二叉树
        Integer[] a1 = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        TreeNode t1 = TreeNode.fromArray(a1);
        System.out.println("t1=" + t1);

        // 示例 2：含空节点
        Integer[] a2 = new Integer[]{1, 2, 3, null, 4};
        TreeNode t2 = TreeNode.fromArray(a2);
        System.out.println("t2=" + t2);

        // 示例 3：空数组 -> null
        Integer[] a3 = new Integer[]{};
        TreeNode t3 = TreeNode.fromArray(a3);
        System.out.println("t3=" + t3);

        // 简单断言（人工观察）
        System.out.println("示例完成 —— 若输出符合注释中的树结构，则通过 smoke test。");
    }

}
