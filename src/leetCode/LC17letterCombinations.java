package leetCode;

import java.util.ArrayList;
import java.util.List;

public class LC17letterCombinations {

    private String[] letterMap = {
            "", //0
            "", //1
            "abc", //2
            "def", //3
            "ghi", //4
            "jkl", //5
            "mno", //6
            "pqrs", //7
            "tuv", //8
            "wxyz" //9

    };

    private ArrayList<String> res;
    StringBuilder s;
    public List<String> letterCombinations(String digits) {

        res = new ArrayList<>();
        s = new StringBuilder();
        if(digits == null || digits.isEmpty()) {
            return res;
        }

        findCombination(digits, 0);

        return res;

    }

    private void findCombination(String digits, int index) {
        if (index == digits.length()) {
            //推出条件，找到一个符合条件的结果
            res.add(s.toString());
            return;
        }

        char c = digits.charAt(index);
        String letter = letterMap[c - '0'];

        for (int i = 0; i < letter.length(); i++) {
            s.append(letter.charAt(i));
            findCombination(digits, index+1);
            s.deleteCharAt(s.length() - 1);
        }


    }
}
