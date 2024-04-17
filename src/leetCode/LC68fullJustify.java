package leetCode;

import java.util.ArrayList;
import java.util.List;

public class LC68fullJustify {

    public List<String> fullJustify(String[] words, int maxWidth) {

        List<String> res = new ArrayList<>();
        int n = words.length;
        int i = 0;
        while (i < n) {
            int j = i, len = 0;
            //算出来每一行可以承载的单词数量
            while (j < n && len + words[j].length() + j - i <= maxWidth) {
                len += words[j++].length();
            }
            //左对齐 ，直接把这一行加进去
            StringBuilder line = new StringBuilder(words[i]);
            boolean isLastLine = (j == n); //最后一行。或者只有一个单词
            if (j - i == 1 || isLastLine) {
                //一行只有一个单词或最后一行的情况
                for(int k = i + 1; k < j; k++) {
                    line.append(" ").append(words[k]);
                }
                while(line.length() < maxWidth) {
                    line.append(" ");
                }

            } else {


                //`extraSpaces`的计算逻辑是基于文本对齐的需求。在这段代码中，每一行的文本需要尽可能均匀地分布在整行中。`spaces`变量表示每两个单词之间的基本空格数，而`extraSpaces`表示需要额外添加的空格数。
                //
                //`extraSpaces`的计算公式是`(maxWidth - len) % (j - i - 1)`。这里，`maxWidth`是每行的最大宽度，`len`是当前行所有单词的总长度，`j - i - 1`是当前行的单词间隔数。
                //
                //`(maxWidth - len)`表示当前行剩余的空格数，`(maxWidth - len) % (j - i - 1)`表示在均匀分配空格后，剩余的空格数。这些剩余的空格需要从左到右依次添加到单词间隔中，直到用完为止。这就是`extraSpaces`的计算逻辑。
                int spaces = (maxWidth - len) / (j - i - 1);
                int extraSpaces = (maxWidth - len) % (j - i - 1);
                for (int k = i + 1; k < j; k++) {
                    for(int s = spaces; s > 0; s--) {
                        line.append(" ");
                    }
                    if (extraSpaces-- > 0) {
                        line.append(" ");
                    }
                    line.append(words[k]);

                }
            }
            res.add(line.toString());
            i = j;

        }
        return res;
    }

    public static void main(String[] args) {
        LC68fullJustify lc68fullJustify = new LC68fullJustify();

        String[] words = new String[]{"What!","must","be","acknowledgment","shall","be"};

        List<String> res = lc68fullJustify.fullJustify(words, 16);
        Tools.printList(res);

    }




}
