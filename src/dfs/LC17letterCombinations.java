package dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

import java.util.ArrayList;

public class LC17letterCombinations {

    private String[] letterMap = {
        "",
        "",
        "abc",
        "def",
        "ghi",
        "jkl",
        "mno",
        "pqrs",
        "tuv",
        "wxyz"
    };

    private ArrayList<String> res;
    private StringBuilder s;
    public List<String> letterCombinations(String digits) {

        res = new ArrayList<>();
        s = new StringBuilder();

        if (digits == null || digits.isEmpty()) {
            return res;
        }

        findCombinations(digits, 0);

        return res;
    }

    private void findCombinations(String digits, int index) {
        if (index == digits.length()) {
            res.add(s.toString());
            return;
        }

        char c = digits.charAt(index);

        String letter = letterMap[c - '0'];

        for (int i = 0; i < letter.length(); i++) {
            s.append(letter.charAt(i));
            findCombinations(digits, index + 1);
            s.deleteCharAt(s.length() - 1);
        }

    }

}
