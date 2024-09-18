package round2;

import java.util.ArrayList;
import java.util.List;

public class HotelBooking {

    public static boolean canAccommodate(int rooms, int[][]  bookings) {

        List<int[]> events = new ArrayList<>();

        for (int[] booking : bookings) {
            events.add(new int[] { booking[0], 1 });
            events.add(new int[] { booking[1], -1 });
        }

        events.sort((a, b) -> (a[0] == b[0]) ? a[1] - b[1] : a[0] - b[0]);

        int currentRoomsUsed = 0;

        for (int[] event : events) {
            currentRoomsUsed += event[1];

            if (currentRoomsUsed > rooms) {
                return false;
            }
        }
        return true;

    }

}
