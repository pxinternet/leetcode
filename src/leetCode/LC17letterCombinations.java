package leetCode;

import java.util.ArrayList;
import java.util.List;

public class LC17letterCombinations {

    /*
     * 题目说明（LeetCode 17）:
     * 给定一串数字字符串 digits（由数字字符组成，例如 "23"），返回按电话按键映射
     * 映射到字母的所有可能组合（顺序不限）。例如 "23" -> ["ad","ae","af","bd","be","bf","cd","ce","cf"].
     *
     * 算法选择：回溯（Backtracking）/ 深度优先搜索（DFS）。
     * 基本想法：对每一位数字，枚举它对应的所有字母；对每个选择继续处理下一位，直到所有位都处理完，得到一个完整组合。
     * 回溯模板（choose -> explore -> un-choose）自然适合此问题。
     *
     * 正确性（直观证明）：
     * - 完备性（不遗漏）：每一位都在其字母集合上进行了穷举选择，回溯树的叶节点恰好对应所有可能的长度为 n 的字符序列（n 为 digits 长度），因此不会遗漏任何合法组合。
     * - 无重复：每条递归路径代表一个唯一的选择序列，生成的字符串唯一对应一条路径；不做重复的路径遍历，因此不会产生重复结果。
     * - 终止性：递归层数固定为 digits.length()，在到达叶子节点后回溯返回，递归深度有限，算法终止。
     *
     * 复杂度分析：
     * - 设输入长度为 n，假设每位最多映射到 m 个字母（电话键中 m<=4），则组合数为 O(m^n)，时间复杂度至少与输出规模成正比，
     *   且每生成一个字符串需要 O(n) 来复制到结果列表，因此总时间复杂度为 O(n * m^n)（最坏情况）。
     * - 空间复杂度：递归栈与当前构造字符串占 O(n)，输出大小占 O(n * m^n)。
     *
     * 实现说明与注意点：
     * - letterMap 用数组按索引（数字字符 - '0'）映射到对应字符串；'0' 和 '1' 对应空字符串（题目通常只包含 '2'..'9'）。
     * - 代码中使用成员变量 res 和 StringBuilder s 以简化递归访问；也可以改为将它们作为局部变量并传入递归函数。
     * - 如果输入包含 '0' 或 '1'（或其它非 2..9 的字符），当前实现会按空串处理，导致无输出；可根据需求调整为抛异常或忽略这些字符。
     *
     * 例子（递归展开）: digits = "23"
     * - '2' -> "abc"， '3' -> "def"
     * - 递归树（简化）：
     *   选择 'a' -> 选择 'd' -> add "ad"
     *                -> 选择 'e' -> add "ae"
     *                -> 选择 'f' -> add "af"
     *   选择 'b' -> ... -> add "bd","be","bf"
     *   选择 'c' -> ... -> add "cd","ce","cf"
     */

    // 电话键映射表：索引与数字字符相对应（'0' -> index 0，'1' -> index 1，...）
    private String[] letterMap = {
            "",   // 0 没有映射（根据题目可视为无效）
            "",   // 1 没有映射
            "abc",// 2
            "def",// 3
            "ghi",// 4
            "jkl",// 5
            "mno",// 6
            "pqrs",//7
            "tuv",//8
            "wxyz"//9

    };

    // 结果列表：保存所有生成的组合；使用类字段供递归方法访问
    private ArrayList<String> res;
    // 当前构造的字符串（回溯时 append/deleteCharAt）
    StringBuilder s;

    /**
     * 主方法：给定 digits 返回所有字母组合
     * @param digits 由数字字符组成的字符串，例如 "23"；通常只包含 '2'..'9'
     * @return 所有可能的字母组合（按回溯生成顺序）
     */
    public List<String> letterCombinations(String digits) {

        // 初始化结果与构造器
        res = new ArrayList<>();
        s = new StringBuilder();

        // 边界处理：若 digits 为 null 或空字符串，返回空列表（题目期望如此）
        if(digits == null || digits.isEmpty()) {
            return res;
        }

        // 从第 0 位开始回溯
        findCombination(digits, 0);

        return res;

    }

    /**
     * 递归函数：处理 digits[index..end]
     * 模板：如果 index 到达末尾，将当前构造字符串加入结果；否则对当前位的所有字母逐一选择并递归。
     * @param digits 源数字字符串
     * @param index  当前处理的数字索引（0-based）
     */
    private void findCombination(String digits, int index) {
        // 递归基：当 index 等于 digits 长度时，说明已经为每一位选择了字母，拼接完成
        if (index == digits.length()) {
            // 将当前构造的字符串加入到结果中（注意需要拷贝 StringBuilder 的内容）
            res.add(s.toString());
            return;
        }

        // 取出当前数字字符，并通过映射表找到对应的字母串
        char c = digits.charAt(index);
        // c - '0' 将字符转为对应的整数索引（例如 '2' -> 2）
        String letter = letterMap[c - '0'];

        // 如果 letter 为空（例如输入包含 '0' 或 '1'），下面的循环不会执行，直接返回；
        // 这样会导致没有结果被加入（res 仍为空）。根据需要可以把这种情况视为非法输入并抛出异常。
        for (int i = 0; i < letter.length(); i++) {
            // 选择当前字母：追加到 StringBuilder
            s.append(letter.charAt(i));
            // 递归处理下一位
            findCombination(digits, index+1);
            // 回溯：撤销上一步选择，删除最后一个字符
            s.deleteCharAt(s.length() - 1);
        }


    }
}
