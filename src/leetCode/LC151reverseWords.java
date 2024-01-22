package leetCode;

public class LC151reverseWords {

    public String reverseWords(String s) {
        //分两步，第一步消除空格，第二步reverse
        int n = s.length();
        int j = 0;
        int i = 0;

        char[] chars = s.toCharArray();

        while(j < n) {
            while(j < n && s.charAt(j) == ' ') j++; //跳过头部空格
            while(j < n && s.charAt(j) !=' ')  chars[i++] = chars[j++];
            while(j < n && s.charAt(j) == ' ') j++;
            if(j < n) chars[i++] = ' ';
        }

        reverse(chars, 0, i - 1);

        int start = 0, end = 0;
        while (start < i) {
            while (end < i && chars[end] != ' ') end++;
            reverse(chars, start, end - 1);
            start = end + 1;
            end++;
        }

        return new String(chars, 0 , i);




    }

    private void reverse(char[] chars, int start, int end) {
        if (start < 0 || end >= chars.length) {
            return;
        }

        while (start < end) {
            char tmp = chars[start];
            chars[start] = chars[end];
            chars[end] = tmp;
            start++;
            end--;
        }
    }

}
