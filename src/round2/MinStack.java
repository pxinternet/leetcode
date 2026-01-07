package round2;

import java.util.Stack;

/**
 * MinStack - 支持在 O(1) 时间内获取栈的最小值的栈实现
 *
 * 设计思路（不变量）：
 * - 使用两个栈：mainStack 保存所有入栈元素；minStack 保存当前的最小值序列。
 * - minStack 的栈顶始终是 mainStack 当前所有元素中的最小值。
 * - 当 push 一个值 val 时，如果 minStack 为空或 val <= minStack.peek()，将 val 也 push 到 minStack。
 * - 当 pop 时，如果被弹出的值等于 minStack.peek()，说明最小值也要随之改变，需要同时弹出 minStack 的栈顶。
 *
 * 时间复杂度：push/pop/top/getMin 均为 O(1)。
 * 空间复杂度：在最坏情况下（元素单调递减），minStack 与 mainStack 大小相同，额外空间为 O(n)。
 */
public class MinStack {

    // 保存所有元素的主栈
    private Stack<Integer> mainStack;

    // 保存当前最小值的栈：栈顶为当前最小值，栈内保持的是 "非严格递减" 序列（允许相等值）
    private Stack<Integer> minStack;

    // 构造器，初始化两个栈
    public MinStack() {
        mainStack = new Stack<>();
        minStack = new Stack<>();
    }

    /**
     * 将元素压入栈中
     * 1) mainStack 一定要 push 元素
     * 2) 只有当 minStack 为空或当前元素小于等于 minStack 的栈顶时，才把该元素也 push 到 minStack
     *
     * 这样做的理由：当出现新的更小（或相等）的元素时，最小值会发生变化，我们把变化记录到 minStack 中。
     * 当该最小值被弹出时，通过比较即可从 minStack 中恢复上一个最小值。
     */
    public void push(int val) {
        // 把元素放入主栈
        mainStack.push(val);
        // 如果 minStack 为空，或者当前值比当前最小值小（或相等），则把当前值也放入 minStack
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    /**
     * 弹出栈顶元素
     * 要点：只有在弹出的值等于 minStack 的栈顶时，才从 minStack 中弹出元素。
     * 这是因为 minStack 中保存的是每次最小值变更时的那个值；当该值被移除，最小值需要还原为上一个记录的值。
     */
    public void pop() {
        // 从主栈弹出元素（如果调用者在空栈上调用会抛出 EmptyStackException，这里遵循原始实现）
        int popedVal = mainStack.pop();
        // 如果弹出的值正好是当前最小值（minStack 的栈顶），则同步弹出 minStack
        if (popedVal == minStack.peek()) {
            minStack.pop();
        }
    }

    /**
     * 返回栈顶元素但不弹出
     * 直接返回 mainStack.peek()（空栈时会抛出 EmptyStackException，与题目默认行为一致）
     */
    public int top() {
        return mainStack.peek();
    }

    /**
     * 返回当前栈的最小元素
     * 直接返回 minStack.peek()（minStack 不为空时为 mainStack 的最小元素）
     */
    public int getMin() {
        return minStack.peek();
    }

    /*
     * 不变量证明（正确性说明，逐步说明）：
     * 1) 初始状态：mainStack 和 minStack 均为空，minStack.peek() 未定义，主张成立。
     * 2) push(val)：
     *    - mainStack.push(val) 始终成立，所有元素都存在 mainStack 中。
     *    - 若 minStack 为空或 val <= minStack.peek()，则将 val push 到 minStack，保持 minStack 的栈顶为当前最小元素。
     *      如果 val > minStack.peek()，说明当前最小值不发生改变，不需要修改 minStack；因此 minStack 的栈顶仍然是 mainStack 的最小值。
     *    因此在任意时刻，minStack.peek() 都等于 mainStack 中所有元素的最小值。
     * 3) pop()：
     *    - 从 mainStack pop 出的元素 x，如果 x != minStack.peek()，说明 x 不是当前的最小值，minStack 不变，minStack.peek() 仍然是正确的最小值；
     *    - 若 x == minStack.peek()，则同步 pop minStack，恢复上一个最小值，仍然保持不变量。
     *
     * 边界情况：
     * - 重复的最小值：由于 push 时使用的是 <=，当存在重复的最小值（例如连续 push 多个 1）时，minStack 会记录每一次出现的最小值。
     *   当弹出其中一个 1 时，minStack 仍然保留下一个 1，确保 getMin 正确。
     * - 空栈操作：本实现没有对空栈调用做额外保护，直接使用 Java 的 Stack 会抛出 EmptyStackException，
     *   这与 LeetCode 上 MinStack 题目的默认行为一致（通常不会对空栈进行无效操作）。
     *
     * 复杂度：
     * - 时间：push/pop/top/getMin 均为 O(1)
     * - 空间：O(n)，其中 n 为 push 的次数；minStack 最坏情况下大小与 mainStack 相同（当输入严格递减或含大量相等的最小值时）。
     */

    /**
     * 操作流程示例（逐步展示两个栈的变化）：
     * 假设执行序列：push(2), push(0), push(3), push(0), getMin(), pop(), getMin(), pop(), top(), getMin()
     * 初始：mainStack = []，minStack = []
     * push(2): mainStack=[2]，minStack=[2]
     * push(0): mainStack=[2,0]，minStack=[2,0]  （0 <= 2，推入）
     * push(3): mainStack=[2,0,3]，minStack=[2,0]   （3 > 0，不推入）
     * push(0): mainStack=[2,0,3,0]，minStack=[2,0,0]  （0 <= 0，推入）
     * getMin(): 返回 minStack.peek() = 0
     * pop(): 弹出 mainStack 的 0，同时 minStack.peek() == 0，minStack 同步弹出 -> mainStack=[2,0,3]，minStack=[2,0]
     * getMin(): 返回 minStack.peek() = 0
     * pop(): 弹出 mainStack 的 3（3 != minStack.peek()），minStack 不变 -> mainStack=[2,0]，minStack=[2,0]
     * top(): 返回 mainStack.peek() = 0
     * getMin(): 返回 minStack.peek() = 0
     *
     * 以上示例展示了 minStack 如何记录最小值变化以及在弹出最小值时正确恢复上一个最小值。
     */
}
