package round3;

import java.util.List;

public class TestFindSubString {
    public static void main(String[] args) {
        FindSubString fs = new FindSubString();

        String s1 = "barfoothefoobarman";
        String[] words1 = {"foo", "bar"};
        List<Integer> r1 = fs.findSunString(s1, words1);
        System.out.println("Test1 expected [0, 9], actual: " + r1);

        String s2 = "wordgoodgoodgoodbestword";
        String[] words2 = {"word","good","best","word"};
        List<Integer> r2 = fs.findSunString(s2, words2);
        System.out.println("Test2 expected [], actual: " + r2);

        String s3 = "aaa";
        String[] words3 = {"a","a"};
        List<Integer> r3 = fs.findSunString(s3, words3);
        System.out.println("Test3 expected [0, 1], actual: " + r3);
    }
}

