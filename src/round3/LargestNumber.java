package round3;

import java.util.Arrays;

public class LargestNumber {

    public static String largestNumber(int[] nums) {

        String[] strNums = new String[nums.length];

        for (int i = 0; i < nums.length; i++) {
            strNums[i] = String.valueOf(nums[i]);
        }

        Arrays.sort(strNums,
                (o1, o2) -> {
                    String order1 = o1 + o2;
                    String order2 = o1 + o2;

                    return order2.compareTo(order1);
            });

        if (strNums[0].equals("0")) {
            return "0";
        }

        StringBuilder res = new StringBuilder();

        for (String str : strNums) {
            res.append(str);
        }

        return res.toString();
}
