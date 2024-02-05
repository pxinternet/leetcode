package leetCode;

public class LC172trailingZeros {

    public int trailingZeroes(int n) {
        //主要是看n里面有几个5 和几个10
        int count = 0;
        while (n > 0) {
            n /= 5;
            count += n;
        }
        return count;
    }
}
