package leetCode;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class LC316removeDuplicateLetters {

    public String removeDuplicateLetters(String s) {
        int[] count = new int[26];
        boolean[] inStack = new boolean[26];

        Deque<Character> stack = new LinkedList<>();

        for (char ch : s.toCharArray()){
            count[ch - 'a']++;
        }

        for (char ch : s.toCharArray()) {
            count[ch - 'a']--;

            if (inStack[ch - 'a']) {
                continue;
            }
            while(!stack.isEmpty() && ch < stack.peek() && count[stack.peek() - 'a'] > 0) {
                inStack[stack.pop() - 'a'] = false;
            }

            stack.push(ch);
            inStack[ch - 'a'] = true;


        }

        StringBuilder res = new StringBuilder();
        while(!stack.isEmpty()) {
            res.append(stack.pollLast());
        }

        return res.toString();
    }

}
