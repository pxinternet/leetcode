package leetCode;

import java.util.*;

public class LC49groupAnagrams {

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> groupmap= new HashMap<>();

        for (String str : strs) {
            String key = sortStrForKey(str);
            List<String> line = groupmap.get(key);
            if (line != null) {
                line.add(str);
            } else {
                line = new ArrayList<>();
                line.add(str);
                groupmap.put(key, line);
            }
        }

        for (List<String> value : groupmap.values()) {
            res.add(value);
        }

        return res;
    }

    String sortStrForKey(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

}
