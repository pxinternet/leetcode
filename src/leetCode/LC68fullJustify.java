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
                System.out.println("len : " + len + " i = " + i + " j = " + j);
                int spaces = (maxWidth - len) / (j - i - 1);
                System.out.println("spaces : " + spaces);
                int extraSpaces = (maxWidth - len) % (j - i - 1);
                System.out.println("extraSpaces : " + extraSpaces);
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
