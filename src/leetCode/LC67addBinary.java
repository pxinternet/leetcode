package leetCode;

public class LC67addBinary {

    public String addBinary(String a, String b) {
        int len1 = a.length();
        int len2 = b.length();

        int carry = 0;

        StringBuilder res = new StringBuilder();
        while (len1 > 0 && len2 > 0) {
            int ans1 = (a.charAt(len1 - 1) - '0') + (b.charAt(len2 - 1) - '0') + carry;
            res.append(ans1 % 2);
            carry = ans1 / 2;

            len1--;
            len2--;
        }

        while (len1 > 0) {
            int ans1 = (a.charAt(len1 - 1) - '0') + carry;
            res.append(ans1 % 2);
            carry = ans1 /2;
            len1--;
        }

        while (len2 > 0) {
            int ans = (b.charAt(len2 - 1) - '0') + carry;
            res.append(ans % 2);
            carry = ans / 2;
            len2--;
        }
        if (carry > 0) {
            res.append(carry);
        }

        return res.reverse().toString();

    }

    public static void main(String[] args) {
        LC67addBinary lc67addBinary = new LC67addBinary();
        String a  = "11";
        String b = "1";

        String res = lc67addBinary.addBinary(a, b);
        System.out.println(res);

    }
}
