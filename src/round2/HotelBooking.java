package round2;

import java.util.ArrayList;
import java.util.List;

/**
 * 扫描线（事件排序）判断是否有足够房间接待所有预订。
 *
 * 说明：对于同一时间点的退房和入住，业务上存在两种常见语义：
 * - 半开区间 [start, end)：在时间点 t 的退房可被同时间点的入住复用（退房先处理）
 * - 闭区间 [start, end]：退房仍算占用，若在同一时间点有入住则视为冲突（入场先处理）
 *
 * 这里提供默认的 {@link #canAccommodate(int,int[][])} 方法，采用半开语义（退房先处理），
 * 并额外提供 {@link #canAccommodateClosed(int,int[][])} 用于闭区间语义。
 */
public class HotelBooking {

    /**
     * 默认方法：按半开区间语义 [start, end) 处理（退房先于同一时间点的入住），并进行输入校验。
     *
     * @param rooms 可用房间数（必须 >= 0）
     * @param bookings 每个 booking 为长度为 2 的 int[]：{start, end}
     * @return 如果所有预订在 rooms 下都能被接纳返回 true，否则返回 false
     */
    public static boolean canAccommodate(int rooms, int[][] bookings) {
        return canAccommodateInternal(rooms, bookings, true);
    }

    /**
     * 闭区间语义：把区间视作 [start, end]（end 也占用房间），在同一时间点优先处理入住事件。
     *
     * @param rooms 可用房间数（必须 >= 0）
     * @param bookings 预订数组
     * @return 是否可接纳
     */
    public static boolean canAccommodateClosed(int rooms, int[][] bookings) {
        return canAccommodateInternal(rooms, bookings, false);
    }

    // 内部实现：根据 halfOpen 决定同时间点入/出事件的先后顺序
    private static boolean canAccommodateInternal(int rooms, int[][] bookings, boolean halfOpen) {
        // 输入校验
        if (rooms < 0) {
            throw new IllegalArgumentException("rooms must be non-negative");
        }
        if (bookings == null || bookings.length == 0) {
            // 无预订一定可以接纳
            return true;
        }

        List<int[]> events = new ArrayList<>();

        // 把每个 booking 拆成两个事件：{time, delta}，delta = +1 表示占用，-1 表示释放
        for (int[] booking : bookings) {
            if (booking == null || booking.length < 2) {
                throw new IllegalArgumentException("each booking must be int[2] {start,end}");
            }
            int start = booking[0];
            int end = booking[1];
            // 这里不对 start/end 大小做自动交换；若业务允许，请在调用方确保 start <= end
            events.add(new int[] { start, 1 });
            events.add(new int[] { end, -1 });
        }

        // 排序：先按时间升序；若时间相同，半开语义下先处理 -1（释放）再 +1（占用）
        // 若为闭区间语义，应先处理 +1（占用）再 -1（释放），即把比较方向反转
        events.sort((a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            // a[0] == b[0]
            return halfOpen ? Integer.compare(a[1], b[1]) : Integer.compare(b[1], a[1]);
        });

        int currentRoomsUsed = 0;

        // 遍历事件，累加 delta 并检查是否超过 rooms
        for (int[] event : events) {
            currentRoomsUsed += event[1];
            if (currentRoomsUsed > rooms) {
                return false;
            }
            // currentRoomsUsed 可以为负（如果输入有不合理的 booking，比如 end < start），
            // 但一般业务中不会发生。若需严格检测，可在此处抛异常或归零。
        }
        return true;
    }

}
