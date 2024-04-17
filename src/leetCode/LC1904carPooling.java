package leetCode;

public class LC1904carPooling {

    public boolean carPooling(int[][] trips, int capacity) {

        int n = 1000;
        int[] diff = new int[n + 1];
        for (int[] trip : trips) {
            diff[trip[1]] += trip[0];
            diff[trip[2]] -= trip[0];
        }
        if (diff[0] > capacity) {
            return false;
        }
        for (int i = 1; i<=n; i++) {
            diff[i] += diff[i - 1];
            if (diff[i] > capacity) {
                return false;
            }
        }
        return true;
    }

    //不差分，直接算也行

}
