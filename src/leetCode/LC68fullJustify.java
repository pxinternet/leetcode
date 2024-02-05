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
            while (j < n && len + words[j].length() + j - i <= maxWidth) {
                len += words[j++].length();
            }
            StringBuilder line = new StringBuilder(words[i]);
            boolean isLastLine = (j == n);
            if (j - i == 1 || isLastLine) {
                //一行只有一个单词或最后一行的情况
                for(int k = i + 1; k < j; k++) {
                    line.append(" ").append(words[k]);
                }
                while(line.length() < maxWidth) {
                    line.append(" ");
                }

            } else {
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
}
