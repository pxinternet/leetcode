public class LC4FindMedianSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //方法一，合并两个有序数组，然后找中位数
        //方法二，不合并，直接找中位数，直接找中间的那个数字。时间复杂度 o（m+n）循环一半的数组
        //时间复杂度 o（log(m+n)）,肯定是二分法
        int m = nums1.length;
        int n = nums2.length;

        int left = (m +n + 1) / 2;
        int right = (m +n + 2) / 2;

        return (getKth(nums1, 0, m - 1, nums2, 0, n - 1, left) + getKth(nums1, 0, m - 1, nums2, 0, n - 1, right)) / 2.0;
    }

    private int getKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;

        //先寻找退出条件，然后再递归
        //永远让len1最小，首先空
        if (len1 > len2) return getKth(nums2, start2, end2, nums1, start1, end1, k);

        //一个数组为空
        if (len1 == 0) return nums2[start2 + k - 1];

        if (k == 1) return Math.min(nums1[start1], nums2[start2]);
        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            //最后一位是抛弃掉的数字的数量
            return getKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return getKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }



    public double findMedianSortedArraysCut(int[] nums1, int[] nums2)  {
        //找第k个小的数的优化版本
        int m = nums1.length;
        int n = nums2.length;

        if ( m > n) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int iMin = 0, iMax = m;

        while(iMin <= iMax) {
            //找到短数组的中间位置
            int i = (iMin + iMax) / 2;
            //找到长数组的中间位置,并且这种分发，保证左边的数比右边多一个
            //对于偶数的情况 i + j = m - i  + n - j
            //对于奇数的情况 i + j = m - i  + n - j + 1，但是不管奇数还是偶数都可以映射成下面这个公式
            int j = (m + n + 1) / 2 - i;


            if (i != m && j != 0 && nums1[i] < nums2[j - 1]) {
                //如果短数组的右侧最小值，小于长数组的左侧最大值，那么往前折半查找
                iMin = i + 1;
            } else if (i != 0 && j != n && nums1[i - 1] > nums2[j]) {
                //如果长数组的右侧最小值，小于短数组的左侧最大值，那么短数组，折半往前查找。
                iMax = i - 1;
            } else {
                int maxLeft = 0;
                if (i == 0) maxLeft = nums2[j - 1];
                else if (j == 0) maxLeft = nums1[i - 1];
                else {
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }
                if ((m+n) % 2 == 1) return maxLeft;

                int minRight = 0;
                if(i == m) minRight = nums2[j];
                else if (j == n) minRight = nums1[i];
                else {
                    minRight = Math.min(nums1[i], nums2[j]);
                }
                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

    public static void main(String[] args) {
        LC4FindMedianSortedArrays lc4FindMedianSortedArrays = new LC4FindMedianSortedArrays();
        int[] nums1 = new int[]{1,2};
        int[] nums2 = new int[] {3, 4};

        double mid = lc4FindMedianSortedArrays.findMedianSortedArraysCut(nums1, nums2);
        System.out.println(mid);
    }
}
