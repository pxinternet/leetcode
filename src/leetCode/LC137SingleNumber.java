package leetCode;

public class LC137SingleNumber {

    public int singleNumber(int[] nums) {
        int[] bits = new int[32];
        int result = 0;

        for (int i = 0; i < 32; i++) {
            for (int num : nums) {
                bits[i] += (num >> i) & 1;
            }
            result |= (bits[i] % 3) << i;
        }

        return result;
    }
}
