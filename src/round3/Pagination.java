package round3;

import java.util.ArrayList;
import java.util.List;

/**
 * Pagination - 文本分页（按字符数，单词不拆分）
 *
 * 题目（概要）：给定文本 text 和每页最大字符数 count，按空格分词，将单词分配到各页，每页字符数不超过 count。
 * 单词不被拆分，页与页之间用空格分隔。
 *
 * 算法原理：
 * - 贪心：当前页能放下下一个单词（含前导空格）则追加，否则新开一页。
 * - 单词边界：按空格 split，保证单词完整。
 *
 * 核心逻辑（分步）：
 * - 步骤 1：words=text.split(" ")；遍历每个 word。
 * - 步骤 2：若 currentPage 为空或 currentPage.length+word.length+1<=count，则追加（非首词加空格）。
 * - 步骤 3：否则 pages.add(currentPage)，currentPage 重置，再追加 word。
 * - 步骤 4：最后若有剩余 currentPage 则加入 pages。
 *
 * 关键洞察：+1 为空格占用；不在单词内截断，保证可读性。
 *
 * 时间复杂度：O(n)，n 为单词总字符数
 * 空间复杂度：O(n)
 *
 * 示例："hello world foo", count=10 → ["hello world", "foo"]
 */
public class Pagination {

    public static List<String> pagination(String text, int count) {
        String[] words = text.split(" ");

        List<String> pages = new ArrayList<>();

        StringBuilder currentPage = new StringBuilder();

        for (String word : words) {
            if (currentPage.length() == 0 || currentPage.length() + word.length() + 1 <= count) {
                if (currentPage.length() > 0) {
                    currentPage.append(" ");
                }
                currentPage.append(word);
            } else {
                pages.add(currentPage.toString());
                currentPage = new StringBuilder();
            }
        }

        if (currentPage.length() > 0) {
            pages.add(currentPage.toString());
        }
        return pages;
    }

}
