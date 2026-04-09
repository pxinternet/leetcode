package round3;

/**
 * RotateSearch - 旋转排序数组二分查找工具集（LeetCode 33/153/154/162 等）
 *
 * 题目（概要）：提供 search（查找 target）、findPeakElement、findMin、findMin2（有重复）、
 * findFL（严格大于 target 的最小元素）等方法。
 *
 * 算法原理：
 * - 旋转数组二分：左右两段中至少一段有序，根据 nums[left]/nums[mid]/nums[right] 判断 target 所在半区。
 * - search：左有序则 target 在 [left,mid] 内收缩 right；右有序同理。
 *
 * 核心逻辑（分步）：
 * - search：nums[left]<nums[mid] 则左有序，target 在 [left,mid] 则 right=mid-1 否则 left=mid+1；右有序对称。
 * - findPeak：nums[mid]<nums[mid+1] 则峰值在右，left=mid+1；否则 right=mid。
 * - findMin：nums[mid]>nums[right] 则最小在右，left=mid+1；否则 right=mid。
 *
 * 关键洞察：判断哪侧有序；target 在有序区间内则收缩到该侧，否则到另一侧。
 *
 * 时间复杂度：search/findPeak/findMin O(log n)；findMin2 有重复时最坏 O(n)
 * 空间复杂度：O(1)
 *
 * 示例：nums=[4,5,6,7,0,1,2], target=0 → search 返回 4
 */
public class RotateSearch {

    /** 在旋转排序数组中查找 target 的索引（无重复），未找到返回 -1 */
    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) {
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public int findPeakElement(int[] nums) { // 方法：在数组中找到一个峰值元素的索引（符合题意即可）
        int left = 0, right = nums.length - 1; // 初始化左右指针覆盖整个数组

        while (left < right) { // 当未收敛到单点时继续
            int mid = left + (right - left) / 2; // 计算中点

            if (nums[mid] < nums[mid + 1]) { // 如果中点比右侧小，峰值在右侧
                left = mid + 1; // 将左指针移动到 mid+1
            } else {
                right = mid; // 否则峰值在左侧或就是 mid，将右指针收缩到 mid
            }
        }
        return left; // 收敛时 left==right，返回该索引作为峰值索引
    }

    public int findMin(int[] nums) { // 方法：在旋转排序数组中找到最小值（无重复元素假设）
        int left = 0, right = nums.length - 1; // 初始化左右指针

        while (left < right) { // 二分查找直到左右相遇
            int mid = left + (right - left) / 2; // 计算中点

            if (nums[mid] > nums[right]) { // 如果中点大于右端，最小值在右侧
                left = mid + 1; // 移动左指针到 mid+1
            } else {
                right = mid; // 否则最小值在左侧（包含 mid），收缩右指针
            }
        }
        return nums[left]; // 返回最小值所在位置的元素
    }


    public int findMin2(int[] nums) { // 方法：在可能包含重复元素的旋转数组中找到最小值
        int left = 0, right = nums.length - 1; // 初始化左右指针

        while (left < right) { // 二分查找直到收敛
            int mid = left + (right - left) / 2; // 计算中点

            if (nums[mid] > nums[right]) { // 中点大于右端，最小值在右侧
                left = mid + 1; // 左指针移动到 mid+1
            } else if (nums[mid] < nums[right]) { // 中点小于右端，最小值在左侧（包含 mid）
                right = mid; // 收缩右指针到 mid
            } else { // nums[mid] == nums[right] 时，无法判断最小值在哪一侧
                right--; // 通过减小 right 来跳过重复值以逐步逼近（最坏 O(n)）
            }
        }
        return nums[left]; // 返回最小值
    }

    public int findFLInSequence(int[] nums, int start, int end, int target) { // 方法：在有序区间 nums[start..end] 中查找严格大于 target 的最小元素

        int left = start, right = end; // 初始化左右指针为给定区间

        int result = -1; // 用于存放找到的第一个大于 target 的元素值，默认 -1 表示未找到

        while (left <= right) { // 经典二分查找模板
            int mid = left + (right - left) / 2; // 计算中点
            if (nums[mid] > target) { // 如果 mid 的值大于 target
                result = nums[mid]; // 更新 result，记录候选最小大于 target 的值
                right = mid - 1; // 并尝试在左侧寻找更小的符合条件的值
            } else {
                left = mid + 1; // 否则在右侧继续查找
            }
        }
        return result; // 返回找到的值或 -1

    }

    public int findFL(int[] nums, int target) { // 方法：在旋转排序数组中查找严格大于 target 的最小元素（含环回）

        int left = 0; // 区间左端初始为 0
        int right = nums.length - 1; // 区间右端初始为数组末尾

        if (target < nums[0] && target >= nums[right]) { // 特殊判断：如果 target 在数组顺序与旋转关系之间，使得 nums[0] 为答案
            return nums[0]; // 直接返回数组首元素
        }

        if (nums[0] > nums[right]) { // 判断数组是否被旋转（首元素大于末尾元素时说明被旋转）

            while (left < right) { // 找到旋转点（最小元素的索引），标准二分
                int mid = left + (right - left) / 2; // 计算中点
                if (nums[mid] > nums[right]) { // 如果 mid 值大于右端，最小元素在右侧
                    left = mid + 1; // 左指针移动至 mid+1
                } else {
                    right = mid; // 否则最小元素在左侧或为 mid，右指针收缩到 mid
                }
            }

            if (target < nums[left]) { // 若 target 小于旋转点的最小值
                return nums[left]; // 则旋转点元素就是严格大于 target 的最小元素
            }

            if (target >= nums[left] && target < nums[nums.length - 1]) { // 若 target 在旋转后的右有序段范围内
                return findFLInSequence(nums, left, nums.length - 1, target); // 在右有序区间查找下一个更大元素
            } else {
                return findFLInSequence(nums, 0, left - 1, target); // 否则在左边有序区间查找
            }
        } else { // 如果数组没有被旋转（整体有序）
            return findFLInSequence(nums, 0, right, target); // 直接在整个数组范围内查找
        }
    }
}
