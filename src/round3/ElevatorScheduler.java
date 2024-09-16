package round3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElevatorScheduler {

    private int currentFloor;

    private boolean movingUp;

    public ElevatorScheduler(int initFloor, boolean movingUp) {
        this.currentFloor = initFloor;
        this.movingUp = movingUp;
    }


    public List<Integer> schedule(List<Integer> requests) {

        List<Integer> floorsToVisit = new ArrayList<>(requests);

        Collections.sort(floorsToVisit);

        List<Integer> result = new ArrayList<>();

        int index = 0;

        if (movingUp) {
            while (index < floorsToVisit.size() && floorsToVisit.get(index) < currentFloor) {
                index++;
            }

            for (int i = index; i < floorsToVisit.size(); i++) {
                result.add(floorsToVisit.get(i));
            }

            for (int i = index - 1; i >= 0; i--) {
                result.add(floorsToVisit.get(i));
            }
        } else {

            while (index < floorsToVisit.size() && floorsToVisit.get(index) < currentFloor) {
                index++;
            }

            for (int i = index - 1; i >= 0; i--) {
                result.add(floorsToVisit.get(i));
            }


            for (int i = index; i < floorsToVisit.size(); i++) {
                result.add(floorsToVisit.get(i));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        ElevatorScheduler scheduler = new ElevatorScheduler(5, false);
        List<Integer> requests = List.of(1, 3, 7, 9, 4, 8);

        List<Integer> scheduledFloors = scheduler.schedule(requests);

        System.out.print(scheduledFloors);
    }

}
