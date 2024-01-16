
import java.util.ArrayList;
import java.util.List;


public class LC6convert {
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        List<StringBuilder> rowList = new ArrayList<>(numRows);

        for (int i = 0; i < numRows; i++) {
            rowList.add(new StringBuilder());
        }

        int mod = 2 * numRows - 2;
        for (int i = 0; i < s.length(); i++) {
            int index = i % mod;
            if (index >= numRows) index = mod - index;

            rowList.get(index).append(s.charAt(i));
        }
        StringBuilder res = new StringBuilder();

        for (StringBuilder sb : rowList) {
            res.append(sb);
        }

        return res.toString();
    }
}
