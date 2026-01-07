package leetCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class LC6convert {
    /**
     * Convert a string to its ZigZag (LeetCode 6) form and read line by line.
     *
     * Contract:
     * - Input: s (non-null String), numRows (number of rows, >= 1 assumed by problem)
     * - Output: String representing characters placed in a zigzag pattern with numRows and
     *   then read row-by-row.
     *
     * Algorithm (idea):
     * - When writing characters into numRows in a zigzag, the pattern repeats every
     *   period = 2 * numRows - 2 (for numRows > 1). Within a period the row index
     *   goes 0,1,2,...,numRows-1,numRows-2,...,1. We can map a character at index i
     *   to its row by taking p = i % period; if p < numRows then row = p, else row = period - p.
     * - Append characters into a StringBuilder for each row while scanning s once.
     * - Finally concatenate the rows to produce the result.
     *
     * Correctness (brief):
     * - The period and mapping guarantee that every character is assigned to the same
     *   row it would occupy if we actually drew the zigzag and read row-by-row.
     * - Because we iterate i from 0..s.length()-1 and append to row buffers in that order,
     *   the left-to-right order within each row is preserved.
     * - Special-case numRows == 1: the zigzag degenerates to a single row, returning s.
     *
     * Time complexity: O(n) where n = s.length() (each character processed once;
     * appends are amortized O(1)).
     * Space complexity: O(n) for the output (and O(numRows) auxiliary StringBuilders).
     *
     * Edge cases:
     * - numRows == 1 -> return s (no period defined when numRows=1).
     * - numRows >= s.length() -> result is s (each char falls into its own row or single row)
     * - s empty -> returns "".
     *
     * Examples:
     * - s = "PAYPALISHIRING", numRows = 3 -> "PAHNAPLSIIGYIR"
     * - s = "PAYPALISHIRING", numRows = 4 -> "PINALSIGYAHRPI"
     * - s = "ABC", numRows = 1 -> "ABC"
     */
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        List<StringBuilder> rowList = new ArrayList<>(numRows);

        for (int i = 0; i < numRows; i++) {
            rowList.add(new StringBuilder());
        }

        int mod = 2 * numRows - 2;
        for (int i = 0; i < s.length(); i++) {
            int index = i % mod;
            if (index >= numRows) index = mod - index;

            rowList.get(index).append(s.charAt(i));
        }
        StringBuilder res = new StringBuilder();

        for (StringBuilder sb : rowList) {
            res.append(sb);
        }

        return res.toString();
    }


    public int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        for (int i = 0; i + m < n; i++) {
            boolean flag = true;

            for (int j = 0; j < m; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return i;
            }
        }
        return -1;
    }


    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();

        int n = words.length;

        int i = 0;

        while (i < n ) {
            int j = i, len = 0;

            while (j < n && len + words[j].length() + j - i <= maxWidth) {
                len += words[j++].length();
            }

            StringBuilder line = new StringBuilder(words[i]);

            boolean isLast = (j == n);

            if (j - i == 1 || isLast) {
                for (int k = i + 1; k < j; k++) {
                    line.append(" ").append(words[k]);
                }
                while (line.length() < maxWidth) {
                    line.append(" ");
                }
            } else {
                int spaces = (maxWidth - len) / (j - i + 1);
                int extraSpaces = (maxWidth - len) % (j - i + 1);

                for (int k = i + 1; k < j; k++) {
                    for (int s = spaces; s > 0; s--) {
                        line.append(" ");
                    }
                    if (extraSpaces-- > 0) {
                        line.append(" ");
                    }
                    line.append(words[k]);
                }
            }
            res.add(line.toString());
            i = j;
        }
        return res;
    }


    public int[] twoSum(int[] nums, int target) {
        int n = nums.length;

        int left = 0;
        int right = n - 1;

        int[] ans = new int[2];

        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum > target) {
                right--;
            } else if (sum < target) {
                left++;
            } else {
                ans[0] = left + 1;
                ans[1] = right +  1;
            }
        }
        return ans;
    }


    public List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums);
        int n = nums.length;

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;

                int j = i + 1;
                int k = n - 1;

                while (j < k) {
                    int s = nums[i] + nums[j] + nums[k];
                    if (s > 0) k--;
                    else if(s < 0) j++;
                    else {
                        List<Integer> ans = new ArrayList<>();
                        ans.add(nums[i]);
                        ans.add(nums[j]);
                        ans.add(nums[k]);
                        res.add(ans);
                        j++;
                        while (j < k && nums[j] == nums[j - 1]) j++;
                        k--;
                        while(j < k && nums[k] == nums[k + 1]) k--;
                    }
                }

        }
        return res;
    }

    public int maxArea(int[] height) {
        int n = height.length;
        int maxArea = 0;
        int left = 0;
        int right = n -1;

        while (left < right) {
            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));
            if (height[left] < height[right])
                left++;
            else {
                right--;
            }
        }
        return maxArea;
    }


    public void rotate(int[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }

        for (int i = 0; i < n; i++) {
            int start = 0, end = n -1;
            while (start < end) {
                int tmp = matrix[i][start];
                matrix[i][start] = matrix[i][end];
                matrix[i][end] = tmp;
                start++;
                end--;
            }
        }
    }


}
