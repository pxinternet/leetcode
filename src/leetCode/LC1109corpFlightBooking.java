package leetCode;

public class LC1109corpFlightBooking {

    public int[] corpFlightBooking(int[][] bookings, int n) {

        int[] res = new int[n];
        int size = bookings.length;

        for (int i = 0; i < bookings.length; i++) {

            for (int start = bookings[i][0]; start <= bookings[i][1]; start++) {
                res[start - 1] += bookings[i][3];
            }
        }
        return res;
    }

    /**
     * 差分数组的思想是，对于某个区间的操作，我们不需要对区间中的每一个元素都进行操作，而是只对区间的两个端点进行操作。  在这个问题中，我们需要将预订的起始位置和结束位置的航班数量增加 k。使用差分数组的方法，我们只需要将起始位置的航班数量增加 k，并将结束位置后一个位置的航班数量减少 k。这样，当我们计算差分数组的前缀和时，起始位置到结束位置的航班数量都会增加 k。  这个方法的正确性可以通过以下方式来理解：
     * 对于每个预订，我们将起始位置的航班数量增加 k，并将结束位置后一个位置的航班数量减少 k。这样，当我们计算差分数组的前缀和时，起始位置到结束位置的航班数量都会增加 k。
     * 对于结束位置后一个位置到数组末尾的位置，由于我们将它们的航班数量减少了 k，所以在计算前缀和时，它们的航班数量不会改变。
     * 对于起始位置前一个位置到数组开头的位置，由于我们没有改变它们的航班数量，所以在计算前缀和时，它们的航班数量也不会改变。
     * 因此，这个方法是正确的，它可以正确地更新每个预订的航班数量。
     * @param bookings
     * @param n
     * @return
     */
    public int[] corpFlightBookingBetter(int[][] bookings, int n) {
        int[] res = new int[n];

        for (int[] booking : bookings) {
            res[booking[0] - 1] += booking[2];
            if (booking[1] < n) {
                res[booking[1]] -= booking[2];
            }
        }

        for (int i = 1; i < n; i++) {
            res[i] += res[i - 1];
        }

        return res;
    }
}
