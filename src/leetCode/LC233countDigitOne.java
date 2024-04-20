package leetCode;

public class LC233countDigitOne {


    //
    public int countDigitOne(int n) {
        long mulk = 1;
        int ans = 0;
        for (int k = 0; n >= mulk; ++k) {
            ans += (int) ((n / (mulk * 10)) * mulk  + Math.min(Math.max(n % (mulk * 10) - mulk + 1, 0), mulk));
            mulk *= 10;
        }
        return ans;
    }

    //这道题套用公式就行
}
