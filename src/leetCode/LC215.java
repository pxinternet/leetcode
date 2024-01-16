package leetCode;

public class LC215 {

    //看起来这个不能用排序，通过一遍或者两遍遍历，空间复杂度可以为N.如果空间复杂度为1怎么弄？通过一遍遍历找到。

    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        return quickSelect(nums, 0, n-1, n-k);

//        Map<Integer, Integer> indexMap = new TreeMap<>(Comparator.reverseOrder());
//
//        if(k > nums.length) return -1;
//
////        int[] indexArr = new int[nums.length];
//        //默认初始化为0
//
//        for (int num : nums) {
//            indexMap.merge(num, 1, Integer::sum);
//        }
//
//        for(Map.Entry<Integer, Integer> entry : indexMap.entrySet()) {
//            Integer key = entry.getKey();
//            Integer value = entry.getValue();
//
//            System.out.println("key : " + key + ", value : " +value);
//
//            k = k - value;
//            if (k <= 0) {
//                return key;
//            }
//        }
//
//        return -1;
    }

    int quickSelect(int[] nums, int l, int r, int k) {
        if (l == r) return nums[k];
        int x = nums[l], i = l -1, j = r +1;
        while ( i < j) {
            do i++; while (nums[i] < x);
            do j--; while (nums[j] > x);
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        if (k <= j) return quickSelect(nums, l, j, k);
        else return quickSelect(nums, j +1, r,k);
    }

    public static void main(String[] args) {
        LC215 lc215 = new LC215();

        int[] nums = new int[]{3,2,3,1,2,4,5,5,6};
        int k = 4;

        int res = lc215.findKthLargest(nums, k);
        System.out.println(res);
    }


}
