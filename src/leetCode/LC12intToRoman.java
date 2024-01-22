package leetCode;

public class LC12intToRoman {

    public String intToRoman(int num) {
        String[] romans = new String[] {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[] {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        int n = values.length;

        StringBuilder res = new StringBuilder();

        int i = 0;
        while(num > 0 && i < n) {
            int tmp = num - values[i];
            if (tmp >= 0) {
                res.append(romans[i]);
                num = tmp;
            } else {
                i++;
            }
        }

        return res.toString();
    }

    public String intToRomanBetter(int num) {
        String[] romans = new String[] {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = new int[] {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        int n = values.length;

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < values.length && num > 0; i++) {
            while(num >= values[i]) {
                num -= values[i];
                res.append(romans[i]);
            }
        }
        return res.toString();
    }



}
