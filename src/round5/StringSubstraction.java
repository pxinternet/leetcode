package round5;

public class StringSubstraction {

    public static boolean isGreaterOrEqual(String num1, String num2) {
        if (num1.length() > num2.length()) {
            return true;
        } else if (num1.length() < num2.length()) {
            return false;
        }

        for (int i = 0; i < num1.length(); i++) {
            if (num1.charAt(i) > num2.charAt(i)) {
                return true;
            } else if (num1.charAt(i) < num2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static String subtract(String num1, String num2) {

        if (num1.equals(num2))
            return "0";

        boolean negative = false;

        if (!isGreaterOrEqual(num1, num2)) {
            negative = true;
            String temp = num1;
            num1 = num2;
            num2 = temp;
        }

        StringBuilder res = new StringBuilder();

        int i = num1.length();
        int j = num2.length();

        int borrow = 0;

        while (i >= 0 || j >= 0) {

            int digit1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int digit2 = j >= 0 ? num2.charAt(j) - '0' : 0;

            int sub = digit1 - digit2 - borrow;

            if (sub < 0) {
                sub += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            res.append(sub);

            i--;
            j--;
        }

        while (res.length() > 1 && res.charAt(res.length() - 1) == '0') {
            res.deleteCharAt(res.length() - 1);
        }
        if (negative) {
            res.append('-');
        }
        return res.reverse().toString();
    }

}
