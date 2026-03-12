package leetCode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LC71 - 简化路径
 *
 * 题目（概要）：给定 Unix 风格路径 path，将其简化为规范路径。规范路径：以 / 开头，不含 . 和 ..，多个 / 合并为一个。
 *
 * 解法说明：
 * - 核心思想：按 / 分割，用栈（双端队列）模拟。遇到 "." 或空串跳过；".." 则弹栈；否则入栈。
 * - 最终从栈底到栈顶拼接 "/" + 名。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(n)
 *
 * 边界与注意事项：
 * - 根路径返回 "/"
 *
 * 示例：path="/a/./b/../../c/" → "/c"
 */
public class LC71simplifyPath {

    /**
     * 栈模拟路径简化
     *
     * @param path Unix 路径
     * @return 简化后的规范路径
     */
    public String simplifyPath(String path) {
        Deque<String> deQue = new ArrayDeque<>();

        String[] names = path.split("/");

        StringBuilder res = new StringBuilder();

        for (String name : names) {
            if (name.isEmpty() || name.equals(".")) {
                continue;
            } else if(name.equals("..")){
                if (!deQue.isEmpty()) {
                    deQue.pollLast();
                }
            } else {
                deQue.offerLast(name);
            }
        }


        if (deQue.isEmpty()) {
            res.append("/");
        } else {
            while(!deQue.isEmpty()) {
                res.append("/").append(deQue.pollFirst());
            }
        }


        return res.toString();


    }
}
