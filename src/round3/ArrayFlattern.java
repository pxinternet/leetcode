package round3;

import java.util.ArrayList;
import java.util.Stack;

/**
 * ArrayFlattern - 嵌套数组扁平化
 *
 * 题目（概要）：将嵌套的 Object[] 展平为一维数组。如 [1, [2, [3, 4]], 5] → [1, 2, 3, 4, 5]。
 *
 * 算法原理：
 * - 递归：遍历元素，若为数组则递归展平，否则加入结果。
 * - 迭代：用栈模拟，逆序入栈保证顺序；弹栈时若为数组则逆序拆开入栈。
 *
 * 核心逻辑（分步）：
 * - flattern：遍历 array，instanceof Object[] 则递归 flattern(sub, res)，否则 res.add。
 * - flatternNoRecusive：array 逆序入栈；弹栈时数组则逆序子元素入栈，否则加入 res。
 *
 * 关键洞察：递归简洁；迭代避免栈溢出；逆序入栈保证子元素顺序。
 *
 * 时间复杂度：O(n)，n 为总元素数（含嵌套）
 * 空间复杂度：O(d)，d 为最大嵌套深度
 *
 * 示例：[1,[2,[3,4]],5] → [1,2,3,4,5]
 */
public class ArrayFlattern {

    public static Object[] flattern(Object[] array, ArrayList<Object> res) {

        for (Object element : array) {
            if (element instanceof Object[]) {
                flattern((Object[]) element, res);
            } else {
                res.add(element);
            }
        }
        return res.toArray(new Object[] {});

    }

    public static Object[] flatternNoRecusive(Object[] array) {

        Stack<Object> stack = new Stack<>();

        ArrayList<Object> res = new ArrayList<>();

        for (int i = array.length - 1; i >= 0; i--) {
            stack.push(array[i]);
        }

        while (!stack.isEmpty()) {
            Object current = stack.pop();

            if (current instanceof Object[]) {
                Object[] currentArr = (Object[]) current;

                for (int i = currentArr.length - 1; i >= 0; i--) {
                    stack.push(currentArr[i]);
                }

            } else {
                res.add(current);
            }
        }
        return res.toArray(new Object[] {});

    }

    public static void main(String[] args) {
        Object[] elements = { 1, 2, new Object[] { 3, 4 }, new Object[] { new Object[] { 5, 6 }, 7 } };

        ArrayList<Object> res = new ArrayList<>();
        // Object[] res1 = flattern(elements, res);
        Object[] res1 = flatternNoRecusive(elements);
        for (int i = 0; i < res1.length; i++) {
        System.out.print(res1[i] + ", ");

        }
    }

}
