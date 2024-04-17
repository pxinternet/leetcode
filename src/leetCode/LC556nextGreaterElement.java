package leetCode;

public class LC556nextGreaterElement {

    public int nextGreaterElement(int n) {
        //一看就要考虑边界条件了
        char[] array = String.valueOf(n).toCharArray();
        int i = array.length - 2;

        while (i >= 0 && array[i + 1] <= array[i]) {
            i--;
        }
        if (i < 0) {
            return -1;
        }

        int j = array.length - 1;
        while (j >= 0 && array[j] <= array[i]) {
            j--;
        }
        swap(array, i, j);
        reverse(array, i + 1);
        long res = Long.parseLong(new String(array));
        return res <= Integer.MAX_VALUE ? (int) res : -1;
    }

    private void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void reverse(char[] array, int left) {
        int right = array.length - 1;
        while (left < right) {
            swap(array, left, right);
            left++;
            right--;
        }
    }
}
