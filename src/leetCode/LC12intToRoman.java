package leetCode;

public class LC12intToRoman {

    /*
     题目：整数转罗马数字（LeetCode 12）
     思路摘要（贪心）：从大到小枚举所有能表示的罗马符号及其对应的值，
     对于每个符号尽可能多地使用它（重复减去对应值并追加符号），直到剩余数值小于该符号的值，再尝试下一个较小的符号。

     下面代码提供两种等价实现：
     - intToRoman：用 while + tmp 的方式实现（通过试探是否能用当前符号）；
     - intToRomanBetter：更直观的 for + while 嵌套实现（对每个符号尝试尽可能多次）。

     正确性说明（简洁）：
     我们列出的符号集合包含了所有标准罗马表示所需的“基元”：
     {1000:M, 900:CM, 500:D, 400:CD, 100:C, 90:XC, 50:L, 40:XL, 10:X, 9:IX, 5:V, 4:IV, 1:I}
     在这个集合下，每次优先使用当前最大的可用符号是安全的（贪心正确），因为若当前数值 >= 某个符号的值，那么使用该符号不会阻碍我们在剩余部分得到标准表示——同时任意可以合并为更大符号的组合在集合中已经以单个符号显式给出（例如 900 为 CM）。

     复杂度：
     - 时间复杂度：若把 num 视作输入量级，则 worst-case 为 O(num)（因为可能多次减 1），但在题目约束（通常 1..3999）内可视为常数时间复杂度 O(1)。
     - 空间复杂度：O(1) 除了输出字符串占用的空间。
    */

    // 第一种实现：使用 while + tmp 的试探方式
    public String intToRoman(int num) {
        // romans 和 values 数组按从大到小顺序一一对应，包含了所有标准符号和减法记法
        String[] romans = new String[] {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[] {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        int n = values.length; // n == 13

        // 使用 StringBuilder 逐步拼接结果，避免频繁创建 String 对象
        StringBuilder res = new StringBuilder();

        // i 表示当前尝试使用的符号下标（从最大开始）
        int i = 0;
        // 循环条件：当数字仍大于 0 且仍有符号可试时继续
        while(num > 0 && i < n) {
            // tmp 用来试探是否可以使用 values[i]（不立即改变 num，先计算差值）
            int tmp = num - values[i];
            if (tmp >= 0) {
                // 如果 tmp >= 0，说明当前符号可以使用一次
                res.append(romans[i]); // 把对应罗马字符追加到结果末尾
                num = tmp;             // 使用后更新剩余值
                // 注意：此时不移动 i，表示还会继续尝试使用相同符号（可能重复多次）
            } else {
                // 当前符号过大，尝试下一个较小的符号
                i++;
            }
            // 循环保持不变式：在每次迭代开始时，num 为尚未转换的剩余数值；i 指向当前候选符号
        }

        // 当 num == 0 时转换完成，返回结果字符串
        return res.toString();
    }

    // 更直观、更常用的实现：对每个符号用 while 循环尽可能多次使用该符号
    public String intToRomanBetter(int num) {
        // 和上面相同的符号表
        String[] romans = new String[] {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[] {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        int n = values.length;

        StringBuilder res = new StringBuilder();

        // 外层遍历每一种符号（从大到小），内层不断使用该符号直到剩余值不够
        for (int i = 0; i < values.length && num > 0; i++) {
            // 当 num >= values[i] 时，说明还可以再使用一次符号 i
            while(num >= values[i]) {
                num -= values[i];         // 减去该符号对应的数值
                res.append(romans[i]);    // 将符号追加到结果
                // 继续 while：再次判断是否还能使用当前符号（处理重复使用的情况，如 MMM）
            }
            // 当退出内层 while，说明当前符号不能再用，继续尝试下一个较小的符号
        }
        return res.toString();
    }

}
