package round2;

import java.util.HashSet;
import java.util.Set;

public class UniqueNumberInString {

    public static int numUniqueNumbers(String s) {
        Set<String> uniqueNumbers = new HashSet<>();

        int n = s.length();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                currentNumber.append(c);
            } else {
                if (currentNumber.length() > 0) {
                    String num = trimLeadingZeros(currentNumber.toString());
                    uniqueNumbers.add(num);
                    currentNumber = new StringBuilder();
                }
            }

        }

        if (currentNumber.length() > 0) {
            String num = trimLeadingZeros(currentNumber.toString());
            uniqueNumbers.add(num);
        }
        return uniqueNumbers.size();

    }

    private static String trimLeadingZeros(String num) {
        int i = 0;
        while (i < num.length() && num.charAt(i) == '0') {
            i++;
        }
        return i == num.length() ? "0" : num.substring(i);
    }

}
