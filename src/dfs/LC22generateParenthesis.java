package dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LC22generateParenthesis {

    private static class Node {
        String current;
        int left;
        int right;

        Node(String current, int left, int right) {
            this.current = current;
            this.left = left;
            this.right = right;
        }
    }


    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        Stack<Node> stack = new Stack<>();

        stack.push(new Node("", n, n));

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if (node.left == 0 && node.right == 0) {
                result.add(node.current);
            }

            if (node.left > 0) {
                stack.push(new Node(node.current + "(", node.left - 1, node.right));
            }

            if (node.right > node.left) {
                stack.push(new Node(node.current + ")", node.left, node.right - 1));
            }
        }
        return result;
    }



    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();

        generate(result, "", n, n);

        return result;
    }

    private void generate(List<String> result, String current, int left, int right) {
        if (left ==0 && right ==0) {
            result.add(current);
            return;
        }

        if (left > 0) {
            generate(result, current + "(", left - 1, right);
        }
        if (right > left) {
            generate(right, current + ")", left, right - 1);
        }
    }

}
