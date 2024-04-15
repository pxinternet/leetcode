import leetCode.Tools;

import javax.tools.Tool;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class LC1361validateBinaryTreeNodes {

    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        int[] inDeg = new int[n];

        for (int i = 0; i < n; i++) {
            if (leftChild[i] != -1) {
                ++inDeg[leftChild[i]];
            }
            if (rightChild[i] != -1) {
                ++inDeg[rightChild[i]];
            }
        }


        int root = -1;

        Tools.printArray(inDeg);

        for (int i = 0; i < n; i++) {
            if (inDeg[i] == 0) {
                root = i;
                break;
            }
        }

        if (root == -1) {
            //没有根节点
            return false;
        }

        Set<Integer> seen = new HashSet<>();

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(root);
        seen.add(root);

        while (!queue.isEmpty()) {
            Tools.printSet(seen);
            int node = queue.poll();

            if (leftChild[node] != -1) {
                if (seen.contains(leftChild[node])) {
                    return false;
                }
                seen.add(leftChild[node]);
                queue.offer(leftChild[node]);
            }

            if (rightChild[node] != -1) {
                if (seen.contains(rightChild[node])) {
                    return false;
                }
                seen.add(rightChild[node]);
                queue.offer(rightChild[node]);
            }
        }
        Tools.printSet(seen);
        return seen.size() == n;
    }

    public static void main(String[] args) {
        LC1361validateBinaryTreeNodes lc1361validateBinaryTreeNodes = new LC1361validateBinaryTreeNodes();

        int[] left = new int[]{1,-1,3,-1};
        int[] right = new int[]{2,-1,-1,-1};

        boolean res = lc1361validateBinaryTreeNodes.validateBinaryTreeNodes(4, left, right);
        System.out.println(res);
    }
}
